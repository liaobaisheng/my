package cn.itheima.jd.po;

import java.util.List;

public class Result {
	
	private Integer curPage;//当前页
	
	private Integer pageCount;//总共页数
	
	private Integer recordCount;//总共商品数
	
	//列表数据
	private List<Product> productList;

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	

}
