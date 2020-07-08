package com.hejie.tmall_ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * <p>Title:POIThread </p>
 * <p>Description: Excel相关线程类</p>
 * @author 何杰
 * @date 2019年9月20日
 * @version 1.0
 * @since JDK 1.8
 */
public class PoiThread implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(PoiThread.class);
	private int startSheet;
	private int endSheet;
	private PoiUtil<?> poiUtil;
	private CountDownLatch countDownLatch;
	
	/*
	 * 构造方法
	 */
	public PoiThread(int startSheet, int endSheet, PoiUtil<?> poiUtil, CountDownLatch countDownLatch) {
		this.startSheet = startSheet;
		this.endSheet = endSheet;
		this.poiUtil = poiUtil;
		this.countDownLatch = countDownLatch;
		logger.debug("初始化PoiThread成功");
	}
	
	/*
	 * 实现run方法
	 */
	@Override
	public void run() {
		
		try {
			
			poiUtil.getBeanList(startSheet, endSheet);
			
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			
			e.printStackTrace();
			logger.error("执行PoiThread.run方法失败，错误信息 < " + e.getMessage() + " > ");
			
		} finally {
			
			logger.debug("子线程执行中，< countDownLatch.getCount() = " + countDownLatch.getCount() + " >");
			countDownLatch.countDown();
		}
		
		logger.debug("执行PoiThread.run方法成功");
	}	
}
