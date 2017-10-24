package cn.itheima.jd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itheima.jd.po.Product;
import cn.itheima.jd.po.Result;
import cn.itheima.jd.servic.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private HttpSolrServer server;
	
	public Result list(String queryString, String catalog_name, String price, Integer page, String sort) {
		SolrQuery query = new SolrQuery();
		//查询语法
		if(StringUtils.isNotBlank(queryString)){
			query.setQuery(queryString);
		}else{
			query.setQuery("*:*");
		}
		
		//默认查询域
		query.set("df","product_keywords");
		
		//过滤条件
		if(StringUtils.isNotBlank(catalog_name)){
			catalog_name = "product_catalog_name : "+catalog_name;
		}
		if(StringUtils.isNotBlank(price)){
			//catalog_name = "catalog_name : "+catalog_name;
			String[] strs = price.split("-");
			
			price = "product_price : ["+Integer.parseInt(strs[0])+" TO "+Integer.parseInt(strs[1])+"]";
		}
		query.setFilterQueries(catalog_name,price);
		//分页
		if(page==null){
			page = 1;
		}
		int pageSize = 10;
		
		query.setStart((page-1)*pageSize);
		//每页显示记录数
		query.setRows(pageSize);
		//排序
		if("1".equals(sort)){
			query.setSort("product_price", ORDER.asc);
		}else{
			query.setSort("product_price", ORDER.desc);
		}
		//设置高亮区域
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		//查询
		QueryResponse queryResponse = null;
		try {
			queryResponse = server.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		//高亮
		Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
		//结果集
		SolrDocumentList results = queryResponse.getResults();
		//设置结果属性
		Result result = new Result();
		result.setCurPage(page);
		//总记录数
		//results.getNumFound()
		result.setRecordCount((int)results.getNumFound());
		//总页数
		int pageCount = 0;
		if(result.getRecordCount()%pageSize==0){
			pageCount = result.getRecordCount()/pageSize;
		}else{
			pageCount = result.getRecordCount()/pageSize+1;
		}
		result.setPageCount(pageCount);
		
		
		//列表数据
		List<Product> list2 = new ArrayList<Product>();
		for (SolrDocument solrDocument : results) {
			Product product = new Product();
			
			String id = solrDocument.get("id").toString();
			//获取name
			String name = "";
			List<String> list = map.get(id).get("product_name");
			if(list!=null && list.size()>0){
				name = list.get(0);
			}else{
				name = solrDocument.get("product_name").toString();
			}
			//获取价格
			String prices = solrDocument.get("product_price").toString();
			String picture = solrDocument.get("product_picture").toString();
			product.setPid(id);
			product.setName(name);
			product.setPrice(prices);
			product.setPicture(picture);
			list2.add(product);
		}
		result.setProductList(list2);
		
		return result;
		
		
		
		
		
		
		
		
		
		
		
	}

}
