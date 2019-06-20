package com.blankj.subutil.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Create by MilkZS on 2019/1/9 13:36
 */
public final class HttpsUtil {

    private static final int CONNECT_TIMEOUT_TIME = 15000;
    private static final int READ_TIMEOUT_TIME    = 19000;

    /**
     * POST + JSON
     *
     * @param data send data
     * @param url  target url
     * @return data receive from server
     * @author MilkZS
     */
    public static String postJson(String data, String url) {
        return doHttpAction(data, true, true, url);
    }

    /**
     * POST + FORM
     *
     * @param data send data
     * @param url  target url
     * @return data receive from serv
     * @author MilkZS
     */
    public static String postForm(String data, String url) {
        return doHttpAction(data, false, true, url);
    }

    /**
     * GET + JSON
     *
     * @param data send data
     * @param url  target url
     * @return data receive from server
     * @author MilkZS
     */
    public static String getJson(String data, String url) {
        return doHttpAction(data, true, false, url);
    }

    /**
     * GET + FORM
     *
     * @param data send data
     * @param url  target url
     * @return data receive from server
     * @author MilkZS
     */
    public static String getForm(String data, String url) {
        return doHttpAction(data, false, false, url);
    }

    private static String doHttpAction(String data, boolean json, boolean post, String url) {
        HttpURLConnection connection = null;
        DataOutputStream os = null;
        InputStream is = null;
        try {
            URL sUrl = new URL(url);
            connection = (HttpURLConnection) sUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_TIME);
            connection.setReadTimeout(READ_TIMEOUT_TIME);
            if (post) {
                connection.setRequestMethod("POST");
            } else {
                connection.setRequestMethod("GET");
            }
            //允许输入输出
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // 是否使用缓冲
            connection.setUseCaches(false);
            // 本次连接是否处理重定向，设置成true，系统自动处理重定向；
            // 设置成false，则需要自己从http reply中分析新的url自己重新连接。
            connection.setInstanceFollowRedirects(true);
            // 设置请求头里的属性
            if (json) {
                connection.setRequestProperty("Content-Type", "application/json");
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", data.length() + "");
            }
            connection.connect();

            os = new DataOutputStream(connection.getOutputStream());
            os.write(data.getBytes(), 0, data.getBytes().length);
            os.flush();
            os.close();

            is = connection.getInputStream();
            Scanner scan = new Scanner(is);
            scan.useDelimiter("\\A");
            if (scan.hasNext()) return scan.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
