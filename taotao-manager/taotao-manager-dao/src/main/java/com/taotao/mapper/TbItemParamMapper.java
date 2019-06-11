package com.taotao.mapper;


import com.taotao.pojo.TbItemParam;

public interface TbItemParamMapper {

    /**
     * 根据商品分类id查询该分类是否有指定模板
     * @param itemCatId 商品分类id
     * @return 指定商品分类下的模板对象
     */
    TbItemParam findItemParamByCid(Long itemCatId);

    /**
     * 插入规格参数模板
     * @param tbItemParam
     */
    void insertItemParam(TbItemParam tbItemParam);
}