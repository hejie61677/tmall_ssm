package com.hejie.tmall_ssm.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * 
 * <p>工具类:ImageWordRecognition </p>
 * <p>Description: 识别图片中的文字</p>
 * @author 何杰
 * @date 2019年8月15日
 * @version 1.0
 * @since JDK 1.8
 */
public class ImageWordRecognitionUtil {
	
	public static void main(String[] args) throws Exception {

        new ImageWordRecognitionUtil().OCRtest();

    }

    private void OCRtest() throws TesseractException {
        ITesseract instance = new Tesseract();
        instance.setDatapath("E:\\myApp\\Kit\\Tess4j");
        instance.setLanguage("chi_sim");
        File imgDir = new File("E:/myResourceLibrary/TEMP/afile/捕获.JPG");

        System.out.println(instance.doOCR(imgDir));
    }
	
}
