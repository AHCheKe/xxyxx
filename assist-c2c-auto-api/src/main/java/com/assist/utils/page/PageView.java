package com.assist.utils.page;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class PageView<T> {
	/** 分页数据 **/
	private List<T> records;
	/** 页码索引和结束索**/
	private PageIndex pageindex;
	/** 总页数**/
	private long totalpage = 1;
	/** 每页显示记录数**/
	private int maxresult = 10;
	/** 当前页数**/
	private int currentpage = 1;
	/** 总记录数 **/
	private long totalrecord;
	/** 页码数量 **/
	private int pagecode = 3;

	/** 要获取记录的索引 **/
	public int getFirstResult() {
		return (this.currentpage - 1) * this.maxresult;
	}

	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	public PageView() {
	}

	public PageView(int maxresult, int currentpage) {
		this.maxresult = maxresult;
		this.currentpage = currentpage;
	}

	public long getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
		setTotalpage(this.totalrecord % this.maxresult == 0 ? this.totalrecord
				/ this.maxresult : this.totalrecord / this.maxresult + 1);
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public PageIndex getPageindex() {
		return pageindex;
	}

	public long getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
		this.pageindex = PageIndex.getPageIndex(pagecode, currentpage,
				totalpage);
	}

	public void setMaxresult(int maxresult) {
		this.maxresult = maxresult;
	}

	public int getMaxresult() {
		return maxresult;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	/**
	 * 通过请求参数构建分页器
	 * @param reqMap
	 * @return
	 */
	public PageView<T> startPageWxmp(JSONObject reqMap){
		PageView<T> pageView = new PageView<>();
		if(reqMap.containsKey("page")) {
			int page = reqMap.getInteger("page");
			pageView.setCurrentpage(page);
		}
		if(reqMap.containsKey("pageSize")) {
			int size = reqMap.getInteger("pageSize");
			pageView.setMaxresult(size);
		}
		return pageView;
	}

	public PageView<T> startPage(JSONObject reqMap){
		PageView<T> pageView = new PageView<>();
		if(reqMap.containsKey("pageNo")) {
			int page = reqMap.getInteger("pageNo");
			pageView.setCurrentpage(page);
		}
		if(reqMap.containsKey("pageSize")) {
			int size = reqMap.getInteger("pageSize");
			pageView.setMaxresult(size);
		}
		return pageView;
	}
}
