package com.taotao.mapper;


import com.taotao.pojo.TbContent;

import java.util.List;

public interface TbContentMapper {

    List<TbContent> findContentByCategoryId(Long categoryId);
}