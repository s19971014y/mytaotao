package com.taotao.controller;

import com.taotao.result.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(Integer itemId){
		return itemService.findItemById(itemId);
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIResult getItemList(Integer page,Integer rows){
		EasyUIResult result = itemService.getItemList(page, rows);
		return result;
	}
}
