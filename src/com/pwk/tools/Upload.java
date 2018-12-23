package com.pwk.tools;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 14-1-13.
 */
public class Upload {

    public static JSONObject uploadPics(HttpServletRequest request,List<File> files,List<String> names, String path, float compressQuality,String type) {
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                String uploadPath = "/opt/pic_bak/" + path;
                File uploadFile = new File(uploadPath);
                if (!uploadFile.exists()) {
                    uploadFile.mkdirs();
                }
                JSONObject returnObject = new JSONObject();
                for(int i = 0;i < files.size();i++){
                    File file = files.get(i);
                    String format = StringTools.getFileType(names.get(i));
                    File tempFile = new File(uploadPath, "temp_"+String.valueOf(System.currentTimeMillis()) + "." + type );
                    File newFile = new File(uploadPath, String.valueOf(System.currentTimeMillis()) + "." + format );
                    FileUtils.copyFile(file,newFile);
                    //watermark
                    if(StringUtils.equals(type,"product")){
                        toSmallerImage(newFile, 800, 800, 1);
                        returnObject.put("miniPic",path+"/mini/"+newFile.getName());
                    }else if(StringUtils.contains(path,"color")){
                        FileUtils.copyFile(file,tempFile);
                        resizeImage(tempFile, newFile, 80, 80, "");
                        FileUtils.forceDelete(tempFile);
                    }else{
                        FileUtils.copyFile(file,newFile);
                    }
                    returnObject.put("pic",path + "/" + newFile.getName());
                }
                return returnObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param temp 源文件路径
     * @param width
     * @param height
     * @param per 质量百分比
     */
    private static void toSmallerImage(File temp, int width, int height,float per){
        try {
            Image src = ImageIO.read(temp);
            int old_w=src.getWidth(null); //得到源图宽
            int old_h=src.getHeight(null);   //得到源图长

            int new_w=0;
            int new_h=0;
            double w2=(old_w*1.00)/(width*1.00);    //w2:原图宽/目标图宽
            double h2=(old_h*1.00)/(height*1.00);  //h2:原图高/目标图高

            //图片跟据长宽留白，成一个正方形图。
            BufferedImage oldPic;
            if(old_w>old_h)
            {
                oldPic=new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB);
            }else {
                if (old_w < old_h) {
                    oldPic = new BufferedImage(old_h, old_h, BufferedImage.TYPE_INT_RGB);
                } else {
                    oldPic = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
                }
            }

            Graphics2D g = oldPic.createGraphics();
            g.setColor(Color.white);

            if(old_w>old_h)
            {
                g.fillRect(0, 0, old_w, old_w);
                g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null);
            }else{
                if(old_w<old_h){
                    g.fillRect(0,0,old_h,old_h);
                    g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null);
                }else{
                    g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null);
                }
            }
            g.dispose();
            src = oldPic; //图片调整为方形结束

            if(old_w>width)
                new_w=(int)Math.round(old_w/w2);   //new_w 原图宽/w2
            else
                new_w=old_w;
            if(old_h>height)
                new_h=(int)Math.round(old_h/h2);//计算新图长宽  //new_h 原图高/h2
            else
                new_h=old_h;

            BufferedImage tag = new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null);
            File destFile = new File(temp.getParent()+"/mini/");
            if(!destFile.exists())
                destFile.mkdirs();
            FileOutputStream newImage=new FileOutputStream(new File(destFile,temp.getName()));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newImage);

             /* compression quality */
            encoder.encode(tag);

            newImage.flush();
            newImage.close();

            //watermark
//            WaterMark.markImage(new File(destFile,temp.getName()), new File("/opt/pic_bak/", "watermark.png"), 0.8f, WaterMark.MARK_RIGHT_BOTTOM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void resizeImage(File temp,File newFile, int width, int height, String format) throws IOException {
        Image src = ImageIO.read(temp);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        OutputStream os = new FileOutputStream(newFile);
        image.getGraphics().drawImage(src, 0, 0, width, height, null);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        encoder.encode(image);
        os.flush();
        os.close();
    }

    public static JSONObject uploadFtp(List<File> files,List<String> names, String path){
        String url = "183.81.167.241";
        int port = 22;
        String username = "root";
        String password = "YmY0MmJlZWEyNWZmM2I3ZjkxMjdmNmZm";
        String fileName = "";
        JSONObject returnObject = new JSONObject();
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            ftp.connect(url,port);
            ftp.login(username,password);
            int reply = ftp.getReply();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                returnObject.put("flag","false");
                return returnObject;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            for(int i = 0;i<files.size();i++){
                String type = StringTools.getFileType(names.get(i));
                fileName = (System.currentTimeMillis() + "." + type);
                ftp.storeFile(fileName,new FileInputStream(files.get(i)));
                returnObject.put("pic", path + "/" + fileName);
                returnObject.put("miniPic",path+"/mini/"+fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                ftp.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return returnObject;
    }

    public static JSONObject uploadSftp(List<File> files,List<String> names, String path){
        ChannelSftp sftp = null;
        String url = "183.81.167.241";
        int port = 22;
        String username = "root";
        String password = "YmY0MmJlZWEyNWZmM2I3ZjkxMjdmNmZm";
        String fileName = "";
        JSONObject returnObject = new JSONObject();
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, url, port);
            Session sshSession = jsch.getSession(username, url, port);
            sshSession.setPassword(password);

            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
            System.out.println(path);
            try {
                sftp.mkdir("/opt/upload/");
            }catch (Exception e){
                System.out.println("建立文件夹出问题！");
            }
            sftp.cd("/opt/upload/");
            for(int i = 0;i<files.size();i++){
                String type = StringTools.getFileType(names.get(i));
                fileName = (System.currentTimeMillis() + "." + type);
                sftp.put(new FileInputStream(files.get(i)), fileName);
                returnObject.put("pic", path + "/" + fileName);
                returnObject.put("miniPic",path+"/mini/"+fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(sftp!=null){
                sftp.disconnect();
            }
        }
        return returnObject;
    }

    /**
     * 异步上传后返回数据的方法
     * @param request
     * @param response
     * @param returnObject
     */
    public static void returnAjaxUpload(HttpServletRequest request, HttpServletResponse response,JSONObject returnObject){
        try {
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            String callback = request.getParameter("CKEditorFuncNum");
            if(StringUtils.isNotBlank(callback)){
                out.println("<script type='text/javascript'>");
                out.println("window.parent.CKEDITOR.tools.callFunction("+ callback + ",'/"+ returnObject.optString("picPath") + "','')");
                out.println("</script>");
            }else{
                out.print(returnObject.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
