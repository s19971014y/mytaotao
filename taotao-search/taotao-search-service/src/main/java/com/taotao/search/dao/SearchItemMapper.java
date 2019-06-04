package com.taotao.search.dao;

import com.taotao.result.SearchItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SearchItemMapper {


    /**
     * 查询数据库得到所有的商品信息，并且转换为Lucene结构
     * @return
     */
    @Select("SELECT a.id,a.title,a.sellPoint,a.image,b.name categoryname,c.itemDesc FROM tbitem a LEFT JOIN tbitemcat b ON a.cid = b.id LEFT JOIN tbitemdesc c" +
            " ON a.id = c.itemId WHERE a.status=1")
    List<SearchItem>  getItemList();
}
