package com.hejie.tmall_ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * <p>Title:PoiThreadHelper </p>
 * <p>Description: 线程执行类</p>
 * @author 何杰
 * @date 2019年9月27日
 * @version 1.0
 * @since JDK 1.8
 */
public class PoiThreadHelper {
	
	private static Logger logger = LoggerFactory.getLogger(PoiThreadHelper.class);
	
	public void execute(PoiUtil<?> poiUtil) {
		
		logger.debug("开始执行线程-->>");
		//一个sheet分配一个线程
		int runSize = poiUtil.getWorkbook().getNumberOfSheets();
		ExecutorService executorService = Executors.newFixedThreadPool(runSize);
		CountDownLatch countDownLatch = new CountDownLatch(runSize);
		
		for (int i = 0; i < runSize; i++) {
			executorService.execute(new PoiThread(i, i + 1, poiUtil, countDownLatch));
		}
		
		logger.debug("主线程阻塞，等待所有子线程执行完成");
		
		try {
			countDownLatch.await();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		logger.debug("所有线程执行完成-->>");
		
		executorService.shutdown();	
		logger.debug("executorService关闭");
	}
}
