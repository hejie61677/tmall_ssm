package com.hejie.tmall_ssm.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Program: tmall_ssm
 * @Description: 保存图片文件
 * @Author: hejie
 * @Create: 2019/6/18
 */
public class UploadedImageFile {

    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}