package com.zmh.core;

import com.alibaba.fastjson.JSONObject;

/**
 * 响应消息。controller中处理后，返回此对象，响应请求结果给客户端。
 * Created by Administrator on 2017/7/25.
 */
public class ResponseMessage {

    public static JSONObject ok() {
        return ok("OK");
    }

    public static JSONObject ok(Object data) {
        JSONObject res = new JSONObject();
        res.put("errcode", 200);
        res.put("errmsg", "OK");
        res.put("result", data.toString());
        return res;
    }

    public static JSONObject error(String message) {
        JSONObject res = new JSONObject();
        res.put("errcode", 500);
        res.put("errmsg", message);
        return res;
    }
}
