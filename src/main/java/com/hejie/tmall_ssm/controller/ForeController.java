package com.hejie.tmall_ssm.controller;

import com.github.pagehelper.PageHelper;
import com.hejie.tmall_ssm.comparator.*;
import com.hejie.tmall_ssm.pojo.*;
import com.hejie.tmall_ssm.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: 前端相关管理
 * @Author: hejie
 * @Create: 2019-07-22 09:30
 */
@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    /**
      * @Description: 前端主页面
      * @Author: hejie
      * @Date: 2019/7/22
      */
    @RequestMapping("fore_home")
    public String home(Model model) {
        List<CategoryExpand> categoryExpands = categoryService.listE();
        productService.fill(categoryExpands);
        productService.fillByRow(categoryExpands);
        model.addAttribute("cs", categoryExpands);   //分类拓展类集合

        return "fore/home";
    }

    /**
      * @Description: 用户注册
      * @Author: hejie
      * @Date: 2019/7/25
      */
    @RequestMapping("fore_register")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if (exist) {
            String msg = "用户名已经存在，无法使用";
            model.addAttribute("msg", msg);
            model.addAttribute("user", null);
            return "fore/register";
        }

        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    /**
     * @Description: 用户登录
     * @Author: hejie
     * @Date: 2019/7/26
     */
    @RequestMapping("fore_login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        } else {
            session.setAttribute("user", user);
            if ("admin".equals(user.getName())) {
                return "redirect:admin_category_list";
            } else {
                return "redirect:fore_home";
            }
        }
    }

    /**
      * @Description: 用户退出
      * @Author: hejie
      * @Date: 2019/7/29
      */
    @RequestMapping("fore_logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:fore_home";
    }

    /**
     * @Description: 跳转产品页面
     * @Author: hejie
     * @Date: 2019/7/31
     */
    @RequestMapping("fore_product")
    public String product( int pid, Model model) {
        ProductExpand productExpand = productService.getPe(pid);

        List<ProductImage> productSingleImages = productImageService.list(productExpand.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(productExpand.getId(), ProductImageService.type_detail);
        productExpand.setProductSingleImages(productSingleImages);
        productExpand.setProductDetailImages(productDetailImages);

        List<PropertyValueExpand> propertyValueExpands = propertyValueService.listE(productExpand.getId());
        List<ReviewExpand> reviewExpands = reviewService.listE(productExpand.getId());
        productService.setSaleAndReview(productExpand);
        model.addAttribute("reviews", reviewExpands);   //评论
        model.addAttribute("p", productExpand);   //产品
        model.addAttribute("pvs", propertyValueExpands);   //属性值

        return "fore/product";
    }

    /**
     * @Description: 登陆用户检查
     * @Author: hejie
     * @Date: 2019/8/2
     */
    @RequestMapping("fore_login_check")
    @ResponseBody
    public String login_check(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "success";
        } else {
            return "failure";
        }
    }

    /**
     * @Description: 登陆用户密码校验
     * @Author: hejie
     * @Date: 2019/8/2
     */
    @RequestMapping("fore_login_ajax")
    @ResponseBody
    public String login_ajax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "success";
        } else {
            return "failure";
        }
    }

    /**
     * @Description: 分类页面
     * @Author: hejie
     * @Date: 2019/8/2
     */
    @RequestMapping("fore_category")
    public String category (int cid, String sort, Model model) {
        CategoryExpand categoryExpand = categoryService.getE(cid);
        productService.fill(categoryExpand);
        productService.setSalesAndReviews(categoryExpand.getProducts());

        if (sort != null) {
            switch (sort) {
                case "review":
                    categoryExpand.getProducts().sort(new ProductReviewComparator());
                    break;
                case "date":
                    categoryExpand.getProducts().sort(new ProductDateComparator());
                    break;
                case "sales":
                    categoryExpand.getProducts().sort(new ProductSalesComparator());
                    break;
                case "price":
                    categoryExpand.getProducts().sort(new ProductPriceComparator());
                    break;
                case "all":
                    categoryExpand.getProducts().sort(new ProductAllComparator());
                    break;
            }
        } else {
            categoryExpand.getProducts().sort(new ProductAllComparator());
        }

        model.addAttribute("c", categoryExpand);

        return "fore/category";
    }

    /**
      * @Description: 页面搜索
      * @Author: hejie
      * @Date: 2019/8/2
      */
    @RequestMapping("fore_search")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<ProductExpand> productExpands = productService.search(keyword);
        productService.setSalesAndReviews(productExpands);
        model.addAttribute("ps", productExpands);

        return "fore/searchResult";
    }

    /**
     * @Description: 立即购买
     * @Author: hejie
     * @Date: 2019/8/7
     */
    @RequestMapping("fore_buyone")
    public String buyone(int pid, int num, HttpSession session) {
        int oiid = uOrAOrderItem(pid, num, session);

        return "redirect:fore_buy?oiid=" + oiid;
    }

    /**
     * @Description: 结算页面
     * @Author: hejie
     * @Date: 2019/8/8
     */
    @RequestMapping("fore_buy")
    public String buy(Model model, String[] oiid, HttpSession session) {
        List<OrderItemExpand> orderItemExpands = new ArrayList<>();
        float total = 0;

        for (String strId : oiid) {
            int id = Integer.parseInt(strId);
            OrderItemExpand orderItemExpand = orderItemService.getE(id);
            total += orderItemExpand.getProduct().getPromote_price() * orderItemExpand.getNumber();
            orderItemExpands.add(orderItemExpand);
        }

        session.setAttribute("ois", orderItemExpands);
        model.addAttribute("total", total);

        return "fore/buy";
    }

    /**
     * @Description: 添加购物车
     * @Author: hejie
     * @Date: 2019/8/8
     */
    @RequestMapping("fore_addcart")
    @ResponseBody
    public String addcart(int pid, int num, HttpSession session) {
        uOrAOrderItem(pid, num, session);

        return "success";
    }

    /**
     * @Description: 更新或修改订单项
     * @Author: hejie
     * @Date: 2019/8/8
     */
    private int uOrAOrderItem(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        int oiid = 0;
        User user = (User)session.getAttribute("user");
        boolean isfound = false;
        List<OrderItemExpand>  orderItemExpands = orderItemService.listEByU(user.getId());

        for (OrderItemExpand orderItemExpand : orderItemExpands) {

            if (orderItemExpand.getProduct().getId().intValue() == product.getId().intValue()) {
                orderItemExpand.setNumber(orderItemExpand.getNumber() + num);
                orderItemService.update(orderItemExpand);
                isfound = true;
                oiid = orderItemExpand.getId();
                break;
            }
        }

        if (!isfound) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }

        return oiid;
    }

    /**
     * @Description: 查看购物车
     * @Author: hejie
     * @Date: 2019/8/8
     */
    @RequestMapping("fore_cart")
    public String cart(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        List<OrderItemExpand> orderItemExpands = orderItemService.listEByU(user.getId());
        model.addAttribute("ois", orderItemExpands);

        return "fore/cart";
    }

    /**
     * @Description: 调整订单项数量
     * @Author: hejie
     * @Date: 2019/8/8
     */
    @RequestMapping("fore_orderitem_change")
    @ResponseBody
    public String orderitem_change(HttpSession session, int pid, int number) {
        User user = (User)session.getAttribute("user");

        if (null == user) {
            return "failure";
        }

        List<OrderItemExpand> orderItemExpands = orderItemService.listEByU(user.getId());

        for (OrderItemExpand orderItemExpand : orderItemExpands) {

            if (orderItemExpand.getProduct().getId() == pid) {
                orderItemExpand.setNumber(number);
                orderItemService.update(orderItemExpand);
                break;
            }
        }

        return "success";
    }

    /**
     * @Description: 删除订单项
     * @Author: hejie
     * @Date: 2019/8/8
     */
    @RequestMapping("fore_orderitem_delete")
    @ResponseBody
    public String orderitem_delete(HttpSession session, int oiid) {
        User user = (User)session.getAttribute("user");

        if (null == user) {
            return "failure";
        }

        orderItemService.delete(oiid);

        return "success";
    }

    /**
     * @Description: 新建订单
     * @Author: hejie
     * @Date: 2019/8/9
     */
    @RequestMapping("fore_order_create")
    public String order_create(Model model, Order order, HttpSession session) {
        User user = (User)session.getAttribute("user");
        Date date = new Date();
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + RandomUtils.nextInt(10000);
        order.setOrder_code(orderCode);
        order.setCreate_date(date);
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        List<OrderItemExpand> orderItemExpands = (List<OrderItemExpand>) session.getAttribute("ois");
        float total = orderService.add(order, orderItemExpands);
        String param = "?oid=" + order.getId() + "&total=" + total;

        return "redirect:foreAlipay" + param;
    }

    /**
     * @Description: 支付跳转成功页
     * @Author: hejie
     * @Date: 2019/8/9
     */
    @RequestMapping("fore_payed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPay_date(new Date());
        orderService.update(order);
        model.addAttribute("o", order);

        return "fore/payed";
    }

    /**
     * @Description: 已购买订单
     * @Author: hejie
     * @Date: 2019/8/12
     */
    @RequestMapping("fore_bought")
    public String bought(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderExpand> orderExpands = orderService.list(user.getId(), OrderService.delete);
        orderItemService.fill(orderExpands);
        model.addAttribute("os", orderExpands);

        return "fore/bought";
    }

    /**
     * @Description: 确认收货准备
     * @Author: hejie
     * @Date: 2019/8/12
     */
    @RequestMapping("fore_confirm")
    public String comfirm(Model model, int oid) {
        OrderExpand orderExpand = orderService.getE(oid);
        orderItemService.fill(orderExpand);
        model.addAttribute("o", orderExpand);

        return "fore/confirm";
    }

    /**
     * @Description: 确认收货
     * @Author: hejie
     * @Date: 2019/8/12
     */
    @RequestMapping("fore_confirmed")
    public String comfirmed(int oid) {
        OrderExpand orderExpand = orderService.getE(oid);
        orderExpand.setStatus(OrderService.waitReview);
        orderExpand.setConfirm_date(new Date());
        orderService.update(orderExpand);

        return "fore/confirmed";
    }

    /**
     * @Description: 删除订单
     * @Author: hejie
     * @Date: 2019/8/12
     */
    @RequestMapping("fore_order_delete")
    @ResponseBody
    public String order_delete(int oid) {
        OrderExpand orderExpand = orderService.getE(oid);
        orderExpand.setStatus(OrderService.delete);
        orderService.update(orderExpand);

        return "success";
    }

    /**
     * @Description: 评论商品
     * @Author: hejie
     * @Date: 2019/8/12
     */
    @RequestMapping("fore_review")
    public String review(Model model, int oid) {
        OrderExpand orderExpand = orderService.getE(oid);
        orderItemService.fill(orderExpand);
        ProductExpand productExpand = orderExpand.getOrderItemExpands().get(0).getProductExpand();
        List<ReviewExpand> reviewExpands = reviewService.listE(productExpand.getId());
        productService.setSaleAndReview(productExpand);
        model.addAttribute("o", orderExpand);
        model.addAttribute("p", productExpand);
        model.addAttribute("reviews", reviewExpands);

        return "fore/review";
    }

    /**
     * @Description: 提交评论
     * @Author: hejie
     * @Date: 2019/8/13
     */
    @RequestMapping("fore_reviewed")
    public String reviewed(HttpSession session, @RequestParam("oid") int oid, @RequestParam("pid") int pid, String content) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        content = HtmlUtils.htmlEscape(content);
        User user = (User) session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreate_date(new Date());
        review.setUid(user.getId());
        reviewService.add(review, order);
        String params = "?oid="+oid+"&showonly=true";

        return "redirect:fore_review" + params;
    }

}
