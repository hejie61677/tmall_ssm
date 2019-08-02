package com.hejie.tmall_ssm.controller;

import com.hejie.tmall_ssm.comparator.*;
import com.hejie.tmall_ssm.pojo.*;
import com.hejie.tmall_ssm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * @program: tmall_ssm
 * @description: 前端相关管理
 * @author: hejie
 * @create: 2019-07-22 09:30
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
    @RequestMapping("forehome")
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
    @RequestMapping("foreregister")
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
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        } else {
            session.setAttribute("user", user);
            return "redirect:forehome";
        }
    }

    /**
      * @Description: 用户退出
      * @Author: hejie
      * @Date: 2019/7/29
      */
    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        return "redirect:forehome";
    }

    /**
     * @Description: 跳转产品页面
     * @Author: hejie
     * @Date: 2019/7/31
     */
    @RequestMapping("foreproduct")
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
    @RequestMapping("forelogincheck")
    @ResponseBody
    public String logincheck(HttpSession session) {
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
    @RequestMapping("foreloginajax")
    @ResponseBody
    public String loginajax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
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
    @RequestMapping("forecategory")
    public String category (int cid, String sort, Model model) {
        CategoryExpand categoryExpand = categoryService.getE(cid);
        productService.fill(categoryExpand);
        productService.setSalesAndReviews(categoryExpand.getProducts());

        if (sort != null) {
            switch (sort) {
                case "review":
                    Collections.sort(categoryExpand.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(categoryExpand.getProducts(), new ProductDateComparator());
                    break;
                case "sales":
                    Collections.sort(categoryExpand.getProducts(), new ProductSalesComparator());
                    break;
                case "price":
                    Collections.sort(categoryExpand.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(categoryExpand.getProducts(), new ProductAllComparator());
                    break;
            }
        }

        model.addAttribute("c", categoryExpand);

        return "fore/category";
    }

}
