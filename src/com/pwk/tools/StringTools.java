package com.pwk.tools;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-19
 * Time: 下午9:27
 * To change this template use File | Settings | File Templates.
 */
public class StringTools {
    public static List<String> getPics(String pic){
        List<String> list = new ArrayList<String>();
        String[] pics = pic.split("###");
        for(String picture :pics){
            if(StringUtils.isBlank(StringUtils.trim(picture))||StringUtils.contains(picture,"#")){
                 continue;
            }else{
                list.add(picture);
            }
        }
        return  list;
    }

    public static String getFileType(String file) {
        if (StringUtils.isNotBlank(file)) {
            int dot = file.lastIndexOf('.');
            if ((dot > -1) && (dot < (file.length() - 1))) {
                return file.substring(dot + 1);
            }
        }
        return null;
    }

    /**
     * 求百分比
     */
    public static String getPercentage(String num,String num2){
        double count = Double.valueOf(num)/Double.valueOf(num2);
        DecimalFormat df1 = new DecimalFormat("##.00");
        return df1.format(count);
    }

    /**
     * 根据类型和id取出图片
     */

    /**
     * 获取项目路径
     * @return
     */
    public static String getImageDir(){
        return "/opt/pic_bak";
    }

    public static String getRealIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static JSONArray StringToJsonArray(String str){
        return JSONArray.fromObject(str);
    }

    public static String getCurrentYear(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getCurrentMonth(Date date){
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    public static String getCurrentDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }
}
