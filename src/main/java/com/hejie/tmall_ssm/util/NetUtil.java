package com.hejie.tmall_ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>工具类:NetUtil </p>
 * <p>Description:网络通讯相关工具类 </p>
 * @author: 何杰
 * @date: 2020年6月11日
 * @version: 1.0
 * @since: JDK 1.8
 */
public class NetUtil {

    private static Logger logger = LoggerFactory.getLogger(NetUtil.class);

    /**
     * 判断端口是否使用
     * parma: host,port
     */
    public static boolean isPortUsing(String host, int port) {
        boolean result = false;
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            Socket socket = new Socket(inetAddress, port);
            result = true;
            logger.debug("端口<" + port + "> 未被占用");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("端口<" + port + "> 已被占用");
        }
        return result;
    }

    /**
     * 判断本地端口是否被占用
     * parma: port
     */
    public static boolean isLoaclPortUsing(int port) {
        return isPortUsing("127.0.0.1", port);
    }

}
