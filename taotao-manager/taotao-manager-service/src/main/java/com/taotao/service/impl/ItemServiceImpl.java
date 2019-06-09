package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.result.EasyUIResult;
import com.taotao.result.TaotaoResult;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;


import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination topicDestination;

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
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

	@Override
	public TaotaoResult addItem(TbItem tbItem, String desc) throws Exception {
		//生成的商品id
		final long id = IDUtils.genItemId();
		//添加商品时间
		Date time = new Date();
		//设置id
		tbItem.setId(id);
		//设置创建商品时间
		tbItem.setCreated(time);
		//设置更改商品时间
		tbItem.setUpdated(time);

		//调用itemMapper
		tbItemMapper.insert(tbItem);


		//创建商品描述对象
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setCreated(time);
		itemDesc.setUpdated(time);
		itemDesc.setItemDesc(desc);

		tbItemDescMapper.insert(itemDesc);



		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(id+"");
				return textMessage;
			}
		});



		return TaotaoResult.ok();
	}


}
