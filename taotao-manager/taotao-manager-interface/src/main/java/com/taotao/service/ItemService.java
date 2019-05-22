package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.result.EasyUIResult;

public interface ItemService {

	/**
	 * 查询得到商品信息
	 * @param itemId 商品ID
	 * @return 指定商品id的商品信息
	 */
	TbItem findItemById(Integer itemId);

	/**
	 * 分页显示商品信息
	 * @param page 当前页码
	 * @param rows 每一页的记录条数
	 * @return EasyUIResult对象里面有total(总记录条数) 和 rows (每一页显示的商品集合)
	 */
	EasyUIResult getItemList(int page,int rows);
}
