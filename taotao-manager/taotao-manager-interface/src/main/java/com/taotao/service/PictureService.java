package com.taotao.service;

import com.taotao.result.PictureResult;

public interface PictureService {
    /**
     *
     * @param bytes 图片的数组，dubbo不能传输MultipartFile类型,但是可以出参数Byte数组
     * @param name  图片的名称
     * @return      里面有三个属性，error(0表示成功,1表示失败),url(上传以后图片的地址),message(200或者500或者404)
     */
    PictureResult uploadFile(byte[] bytes,String name);
}
