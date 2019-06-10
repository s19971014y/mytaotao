package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

import java.util.Arrays;

public class Item extends TbItem {

   public Item(TbItem tbItem){
       this.setBarcode(tbItem.getBarcode());
       this.setCid(tbItem.getCid());
       this.setCreated(tbItem.getCreated());
       this.setId(tbItem.getId());
       this.setImage(tbItem.getImage());
       this.setNum(tbItem.getNum());
       this.setPrice(tbItem.getPrice());
       this.setSellPoint(tbItem.getSellPoint());
       this.setStatus(tbItem.getStatus());
       this.setTitle(tbItem.getTitle());
       this.setUpdated(tbItem.getUpdated());
   }

    public String[] getImages() {
        if(this.getImage() != null&&!"".equals(this.getImage())){
            String[] strings = super.getImage().split(",");
            return strings;
        }
        return null;
    }
}
