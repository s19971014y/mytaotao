package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.result.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper tbItemMapper;
	@Override
	public TbItem findItemById(Integer itemId) {
		return tbItemMapper.findItemById(itemId);
	}

	@Override
	public EasyUIResult getItemList(int page, int rows) {

		//使用分页插件，参数page 当前页,rows 总记录条数
		PageHelper.startPage(page,rows);

		List<TbItem> items = tbItemMapper.findItems();

		//取分页  表示从所有商品集合里 分出30个商品集合对象，而且根据当前页来分
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(items);

		//只要使用了pageInfo关联查出来的所有商品集合，它就会变成只有30个商品集合对象
		EasyUIResult result = new EasyUIResult(pageInfo.getTotal(),items);

		return result;
	}

}
