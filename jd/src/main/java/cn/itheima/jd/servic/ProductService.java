package cn.itheima.jd.servic;

import cn.itheima.jd.po.Result;

public interface ProductService {
	
	public Result list(String queryString,String catalog_name,String price,Integer page,String sort);

}
