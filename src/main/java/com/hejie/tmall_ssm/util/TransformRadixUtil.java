package com.hejie.tmall_ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * <p>工具类:TransformRadix </p>
 * <p>Description: 进制转换</p>
 * @author 何杰
 * @date 2019年8月15日
 * @version 1.0
 * @since JDK 1.8
 */
public class TransformRadixUtil {
	
	private static final char[] ALPHABET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private static Logger logger = LoggerFactory.getLogger(TransformRadixUtil.class);
	
	public static String getRadix(BigDecimal num, int a, int b) {		
		//num = new BigDecimal("413.54984");
		//a = 18;
		//b = 10;
		String rtnStr = num.toString();
		
		if (!isNScale(num, a)) {
			logger.error(" < " + num.toString() + "不符合" + a + "进制规则 >");
			return null;
		} 
		
		logger.debug(a + "进制 -> " + b + "进制");
		logger.debug("原始数：" + rtnStr);
		
		if (a == b) {

			logger.debug("转换后：" + rtnStr);
			
		} else {
			
			rtnStr = radix(num, a, b);		
		}
		
		return rtnStr;
	}
	
	/*
	 * 任意进制数转换为任意进制数
	 */
	private static String radix(BigDecimal num, int a, int b) {
		String radixNum;
		
		if (num.compareTo(new BigDecimal(num.intValue())) != 0) {
			
			logger.debug("注：小数点后保留16位");
			radixNum = radixDecimals(num, a, b);
			
			
		} else {
			
			radixNum = radixInteger(num, a, b);		
		}	
		
		return radixNum;
	}
	
	/*
	 * 任意进制整数转换为任意进制整数
	 */
	private static String radixInteger(BigDecimal num, int a, int b) {		
		String newNum = "";
		
		if (a == 10 || b == 10) {
			
			if (a == 10 && b != 10) {
				
				newNum = Integer.toString(num.intValue(), b); //10进制转为n进制
				
			} else if (a != 10) {
				
				newNum = Integer.valueOf(num.toString(), a).toString(); //n进制转为10进制
			}
			
		} else {
			
			newNum = Integer.toString(Integer.valueOf(num.toString(), a), b);
		}
		
		logger.debug("转换后：" + newNum);
		return newNum;
		
	}
	
	/*
	 * 10进制小数转换为任意进制小数
	 */
	private static String radix10Decimals(BigDecimal num, int b) {
		int[] mantissa = new int[16];
		int temp;
		double tempNum = num.doubleValue();
		int intNum = (int) tempNum;
		tempNum -= intNum;
		StringBuilder newNum = new StringBuilder(Integer.toString(intNum, b) + ".");

		for (temp = 0; temp < 16; temp++) {
			
			tempNum *= b;
			mantissa[temp] = (int) tempNum; //取整
			
			if (tempNum >= 1.0) {
				tempNum -= mantissa[temp];
			}
			
			newNum.append(Integer.toString(mantissa[temp], b));
		}

		logger.debug("转换后:" + newNum);
		return newNum.toString();
	}
	
	/*
	 * 任意进制小数转换为10进制小数
	 */
	private static BigDecimal radixTo10Decimals(BigDecimal num, int a) {
		double rtnNum = 0.0;
		String tempNum = num.toString();
		
		for (int i = 0; i < tempNum.length(); i++) {
			
			int exp = tempNum.charAt(i) - '0';
			exp = - (i + 1) * exp;
			
			if (exp != 0) {
				
				rtnNum += Math.pow(a, exp);
			}
			
		}
		
		return new BigDecimal(rtnNum);

	}
	
	/*
	 * 任意进制小数转换为任意进制小数
	 */
	private static String radixDecimals(BigDecimal num, int a, int b) {
		String radixNum;
		
		if (a != 10) {
			
			num = radixTo10Decimals(num, a);		
		}
		
		if (b != 10) {
			
			radixNum = radix10Decimals(num, b);
			
		} else {
			
			radixNum = num.toString();
			logger.debug("转换后：" + num.toString());		
		}	
	
		return radixNum;
	}
	
	/*
	 * 判断是否为n进制
	 */
	private static boolean isNScale(BigDecimal num, int n) {	
		String tempNum = num.toString();
		
		char[] newAlphabet = Arrays.copyOfRange(ALPHABET, n, 36);
				
		for (int i = 0; i < newAlphabet.length; i++) {
			
			if (tempNum.indexOf(newAlphabet[i]) != -1) {
				
				return false;
				
			}
		}
		
		return true;				
	}

}
