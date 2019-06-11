package com.taotao.mapper;


import com.taotao.pojo.TbItemParamItem;

public interface TbItemParamItemMapper {
    /**
     * 插入商品规格参数到数据库中
     * @param itemParamItem 商品规格参数对象
     */
    void insert(TbItemParamItem itemParamItem);

    /**
     * 根据商品id查询数据库商品规格参数表,返回指定的规格参数对象
     * @param itemId
     * @return 指定的规格参数对象
     */
    TbItemParamItem findItemParamByItemId(Long itemId);
}