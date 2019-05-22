package com.taotao.result;

import java.io.Serializable;
import java.util.List;

/**
 * easyUIDataGrid对象返回值
 */

/**
 * 必须实现Serializable接口才能在电脑和电脑之间传递
 */
public class EasyUIResult implements Serializable {

	private Integer total;
	//？ 代表任意类  Object
	private List<?> rows;
	
	public EasyUIResult(Integer total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public EasyUIResult(long total, List<?> rows) {
		this.total = (int) total;
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	
}
