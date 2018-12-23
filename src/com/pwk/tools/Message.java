package com.pwk.tools;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-26
 * Time: 上午8:38
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    public static void returnMessage(String msg,String url,String state, HttpServletResponse response) {
        try {
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", msg);
            jsonObject.put("url",url);
            jsonObject.put("state",state) ;
            out.print(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}
