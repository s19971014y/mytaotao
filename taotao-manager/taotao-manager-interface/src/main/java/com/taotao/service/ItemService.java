package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.result.EasyUIResult;
import com.taotao.result.TaotaoResult;

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

	/**
	 * 添加一个商品对象到数据库中，里面包含了商品基本信息对象和商品描述对象
	 * @param tbItem 商品基本信息对象
	 * @param desc   描述，在实现类中用商品描述对象的itemDesc属性装
	 * @return TaotaoResult对象，里面有四个属性   MAPPER(jackson对象),status状态码，msg描述信息，data数据
	 */
	TaotaoResult addItem(TbItem tbItem,String desc);
}
