package com.taotao.controller;

import com.taotao.result.EasyUIResult;
import com.taotao.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(Integer itemId){
		return itemService.findItemById(itemId);
	}

	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult getItemList(Integer page,Integer rows){
		EasyUIResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem tbItem,String desc){
		TaotaoResult result = itemService.addItem(tbItem, desc);
		return result;
	}
}
