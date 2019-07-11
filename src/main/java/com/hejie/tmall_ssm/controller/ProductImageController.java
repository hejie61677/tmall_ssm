package com.hejie.tmall_ssm.controller;

import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.ProductExpand;
import com.hejie.tmall_ssm.pojo.ProductImage;
import com.hejie.tmall_ssm.service.ProductImageService;
import com.hejie.tmall_ssm.service.ProductService;
import com.hejie.tmall_ssm.util.ImageUtil;
import com.hejie.tmall_ssm.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: 产品图片管理控制器
  * @Author: hejie
  * @Create: 2019/7/4
  */
@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired ProductService productService;

    @Autowired ProductImageService productImageService;

    /**
     * @Description: 产品图片列表查询
     * @Author: hejie
     * @date: 2019/7/4
     */
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {

        ProductExpand productExpand = productService.getPe(pid);

        List<ProductImage> productImagesSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> productImagesDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("p", productExpand);
        model.addAttribute("pisSingle", productImagesSingle);
        model.addAttribute("pisDetail", productImagesDetail);

        return "admin/listProductImage";
    }

    /**
     * @Description: 产品图片新增
     * @Author: hejie
     * @date: 2019/7/4
     */
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession httpSession, UploadedImageFile uploadedImageFile){

        productImageService.add(productImage);

        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = httpSession.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = httpSession.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = httpSession.getServletContext().getRealPath("img/productSingle_middle");
        } else {
            imageFolder = httpSession.getServletContext().getRealPath("img/productDetail");
        }

        File file = new File(imageFolder, fileName);
        file.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage bufferedImage = ImageUtil.change2jpg(file);
            ImageIO.write(bufferedImage, "jpg", file);

            if (ProductImageService.type_single.equals(productImage.getType())) {
                File file_small = new File(imageFolder_small, fileName);
                File file_middle = new File(imageFolder_middle, fileName);
                ImageUtil.resizeImage(file, 60, 60, file_small);
                ImageUtil.resizeImage(file, 220, 200, file_middle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
    }

    /**
     * @Description: 产品图片删除
     * @Author: hejie
     * @date: 2019/7/4
     */
    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession httpSession) {

        ProductImage productImage = productImageService.get(id);

        String fileName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = httpSession.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = httpSession.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = httpSession.getServletContext().getRealPath("img/productSingle_middle");
            File file = new File(imageFolder, fileName);
            File file_small = new File(imageFolder_small, fileName);
            File file_middle = new File(imageFolder_middle, fileName);
            file.delete();
            file_small.delete();
            file_middle.delete();
        } else {
            imageFolder = httpSession.getServletContext().getRealPath("img/productDetail");
            File file = new File(imageFolder, fileName);
            file.delete();
        }

        productImageService.delete(id);

        return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
    }

}