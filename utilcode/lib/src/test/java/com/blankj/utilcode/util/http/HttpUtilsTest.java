package com.blankj.utilcode.util.http;

import com.blankj.utilcode.util.BaseTest;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/10
 *     desc  :
 * </pre>
 */
public class HttpUtilsTest extends BaseTest {

    private static final String BASE_URL = "http://127.0.0.1:8081";

//    @Test
//    public void getString() {
//        HttpUtils.call(Request.withUrl(BASE_URL + "/listUsers"), new ResponseCallback() {
//            @Override
//            public void onResponse(Response response) {
//                System.out.println(response.getHeaders());
//                System.out.println(response.getString());
//            }
//
//            @Override
//            public void onFailed(Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    @Test
//    public void getJson() {
//        HttpUtils.call(Request.withUrl(BASE_URL + "/listUsers"), new ResponseCallback() {
//            @Override
//            public void onResponse(Response response) {
//                System.out.println(response.getHeaders());
//                List<UserBean> users = response.getJson(GsonUtils.getListType(UserBean.class));
//                System.out.println(GsonUtils.toJson(users));
//            }
//
//            @Override
//            public void onFailed(Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    @Test
//    public void downloadFile() {
//        HttpUtils.call(Request.withUrl(BASE_URL + "/listUsers"), new ResponseCallback() {
//            @Override
//            public void onResponse(Response response) {
//                System.out.println(response.getHeaders());
//                File file = new File(TestConfig.PATH_HTTP + TimeUtils.getNowMills());
//                response.downloadFile(file);
//                System.out.println(FileIOUtils.readFile2String(file));
//                FileUtils.delete(file);
//            }
//
//            @Override
//            public void onFailed(Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

}