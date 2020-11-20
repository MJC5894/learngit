package cn.sunline.tiny.demo.mapper;


import java.util.List;

import cn.sunline.tiny.demo.entity.Product;

public interface ProductMapper extends BaseMapper<Product,String> {
	public List<Product> findProductAll();
	public List<Product> findProductbyType(String type);
	public Product findProductById(String id);
 }
