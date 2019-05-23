package com.taotao.service.impl;

import com.taotao.result.PictureResult;
import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class PictureServiceImpl implements PictureService {

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FILI_UPLOAD_PATH}")
    private String FILI_UPLOAD_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public PictureResult uploadFile(byte[] bytes, String name) {
        /**
         * 1.调用上传图片类,来上传图片
         * 2.要把byte数组变回图片
         * client.storeFile()方法需要一个输入流对象
         * 3.要把图片的名称修改，不然传一个图片会被覆盖
         */
        PictureResult result = new PictureResult();
        try{
            //把一个byte数组变成InputStream的子类 ctrl+h 查看继承树
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            //name表示图片的名称得到后缀名，通过ID生成器(当前时间加3位随机数 生成) + .后缀名为新的名字
            String newName = IDUtils.genImageName() + name.substring(name.lastIndexOf("."));
            //以当前年月日作为文件路径
            String filePath = new DateTime().toString("yyyy/MM/dd");
            //工具类上传图片
            FtpUtil.uploadFile(FTP_ADDRESS,FTP_PORT,FTP_USERNAME,FTP_PASSWORD,FILI_UPLOAD_PATH,filePath,newName,bis);

            result.setError(0);
            result.setUrl(IMAGE_BASE_URL + "/" + filePath + "/" +newName);
            result.setMessage("上传成功");
        }catch (Exception ex){

            result.setError(1);
            result.setMessage("上传失败");
        }
        return result;
    }
}
