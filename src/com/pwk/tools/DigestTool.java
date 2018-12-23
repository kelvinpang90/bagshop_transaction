package com.pwk.tools;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-8
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class DigestTool {
    public static String digest(String password) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(password.getBytes("utf8"));
        return new String(messageDigest.digest(), "utf8");
    }
}
