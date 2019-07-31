package com.hejie.tmall_ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hejie.tmall_ssm.pojo.Category;
import com.hejie.tmall_ssm.service.CategoryService;
import com.hejie.tmall_ssm.util.ImageUtil;
import com.hejie.tmall_ssm.util.Page;
import com.hejie.tmall_ssm.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: 商品分类管理控制器
  * @Author: hejie
  * @Create: 2019/5/29
  */
@Controller
@RequestMapping("")
public class CategoryController {

    @Autowired CategoryService categoryService;

    /**
      * @Description: 商品分类列表查询
      * @Author: hejie
      * @date: 2019/5/30
      */
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> categories = categoryService.list();
        int total = (int) new PageInfo<>(categories).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", categories);  //分类集合
        model.addAttribute("page", page);

        return "admin/listCategory";
    }

    /**
     * @Description: 商品类别新增
     * @Author: hejie
     * @date: 2019/5/30
     */
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        //插入表数据
        categoryService.add(category);

        //图片新增
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }

    /**
     * @Description: 商品类别删除
     * @Author: hejie
     * @date: 2019/5/30
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) {
        //删除表数据
        categoryService.delete(id);

        //删除图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }

    /**
     * @Description: 商品类别修改前查询
     * @Author: hejie
     * @date: 2019/5/30
     */
    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("c", category);

        return "admin/editCategory";
    }

    /**
      * @Description: 商品类别修改
      * @Author: hejie
      * @date: 2019/6/18
      */
    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        //更新表数据
        categoryService.update(category);

        //更新分类图片
        MultipartFile image = uploadedImageFile.getImage();

        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }

        return "redirect:/admin_category_list";
    }

}