package com.taotao.mapper;

import com.taotao.pojo.TbItem;

import java.util.List;

public interface TbItemMapper {
	/**
	 * 根据商品id查询商品信息
	 * @param itemId 商品id
	 * @return 返回商品id的商品信息
	 */
	TbItem findItemById(Integer itemId);

	/**
	 * 查询所有商品信息
	 * @return 返回所有商品信息的集合对象
	 */

	List<TbItem> findItems();

}