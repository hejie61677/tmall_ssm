package com.hejie.tmall_ssm.test;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * @Program: tmall_ssm
 * @Description: 杂项测试
 * @Author: hejie
 * @Create: 2019-07-29 08:35
 */
public class Test {

    public static void main(String[] args) throws Exception {

        new Test().OCRtest();

    }

    private void OCRtest() throws TesseractException {
        ITesseract instance = new Tesseract();
        instance.setDatapath("E:\\myApp\\Kit\\Tess4j");
        instance.setLanguage("chi_sim");
        File imgDir = new File("C:/Users/何杰/Desktop/捕获.JPG");

        System.out.println(instance.doOCR(imgDir));
    }

}


