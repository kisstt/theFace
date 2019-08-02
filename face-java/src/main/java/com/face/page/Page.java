/**
 * 
 */
package com.face.page;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 *
 */
@Data
public class Page extends BasePage {
	private static final long serialVersionUID = 1L;
	public static ThreadLocal<Page> threadLocal = new ThreadLocal<Page>();
	private List<?> rows;

	//扩展属性，用于配合其他业务使用
	private String ext;

	//用于mongodb分页查询，携带这个属性查询效率高
	private String lastId;

	public Page() {
	}

	public Page(int currentPage, int pageSize, int totalCount) {
		super(currentPage, pageSize, totalCount);
	}

	public Page(int page, int pageSize, int records, List<?> data) {
		super(page, pageSize, records);
		this.rows = data;
	}
}
