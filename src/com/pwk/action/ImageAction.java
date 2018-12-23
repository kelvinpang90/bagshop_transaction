package com.pwk.action;

import com.pwk.action.base.BaseAction;
import com.pwk.entity.Image;
import com.pwk.service.ImageService;
import com.pwk.tools.ClusterFileSync;
import com.pwk.tools.Upload;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wenkai.peng on 9/22/2014.
 */
public class ImageAction extends BaseAction {
    @Resource
    private ImageService imageService;

    private List<File> uploadImages;
    private List<String> uploadImagesFileName;   //[fileName]FileName    获得上传文件的名称
    private List<String> uploadImagesContentType;//[fileName]ContentType  获得上传文件的类型

    public String uploadPic(){
        System.out.println("uploadPic in...");
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String type = getRequest().getParameter("type");
            String path = "upload/"+(StringUtils.isNotBlank(type)?type:"")+"/" + format.format(new Date());
            JSONObject picPaths = Upload.uploadPics(getRequest(),uploadImages,uploadImagesFileName, path,2,type);
//            JSONObject picPaths = Upload.uploadFtp(uploadImages,uploadImagesFileName,path);
//            JSONObject picPaths = Upload.uploadSftp(uploadImages,uploadImagesFileName,path);
            Image image = new Image();
            image.setPath(picPaths.optString("pic"));
            image.setMiniPath(picPaths.optString("miniPic"));
            image.setCreateTime(Timestamp.valueOf(format2.format(new Date())));
            imageService.add(image);
            JSONObject returnObject = new JSONObject();
            if(StringUtils.equals(type,"product")){
                returnObject.put("picPath",picPaths.optString("miniPic"));
            }else{
                returnObject.put("picPath",picPaths.optString("pic"));
            }
            returnObject.put("picId", image.getId());

            //sync
            String fullPath = "/opt/pic_bak/"+picPaths.optString("pic");
            new ClusterFileSync(fullPath).run();

            Upload.returnAjaxUpload(getRequest(),getResponse(),returnObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<File> getUploadImages() {
        return uploadImages;
    }

    public void setUploadImages(List<File> uploadImages) {
        this.uploadImages = uploadImages;
    }

    public List<String> getUploadImagesFileName() {
        return uploadImagesFileName;
    }

    public void setUploadImagesFileName(List<String> uploadImagesFileName) {
        this.uploadImagesFileName = uploadImagesFileName;
    }

    public List<String> getUploadImagesContentType() {
        return uploadImagesContentType;
    }

    public void setUploadImagesContentType(List<String> uploadImagesContentType) {
        this.uploadImagesContentType = uploadImagesContentType;
    }
}
