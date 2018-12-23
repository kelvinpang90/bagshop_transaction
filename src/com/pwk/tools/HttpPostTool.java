package com.pwk.tools;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by wenkai.peng on 2014/5/23.
 */
public class HttpPostTool {
    public static String post(String url,String reqEncoding,String respEncoding,List<NameValuePair> param){
        HttpClient httpclient=new DefaultHttpClient();
        String resStr = "";
        HttpPost httppost = new HttpPost(url);
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(param);
            InputStream is = uefEntity.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is,writer);
            System.out.println("entity="+writer.toString());
            httppost.setEntity(uefEntity);
            HttpResponse response = httpclient.execute(httppost);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode()== HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resStr = EntityUtils.toString(entity);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
        }
        return resStr;
    }
}
