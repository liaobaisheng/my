package cn.itheima.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itheima.jd.po.Result;
import cn.itheima.jd.servic.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/list.action")
	public String list(Model model,String queryString, String catalog_name, String price, Integer page, String sort){
		Result result = productService.list(queryString, catalog_name, price, page, sort);
		//保存数据
		model.addAttribute("result",result);
		//回显
		model.addAttribute("queryString",queryString);
		model.addAttribute("catalog_name",catalog_name);
		model.addAttribute("price",price);
		model.addAttribute("page",page);
		model.addAttribute("sort",sort);
		
		return "product_list";
	}
}
