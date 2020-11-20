package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.Product;
import cn.sunline.tiny.demo.mapper.*;
@Service("productService")
public class ProductService extends BaseService<Product,String> {
	@Autowired
	private ProductMapper productMapper;

	public BaseMapper<Product,String> getMapper() {
		return productMapper;
	}
	public List<Product> findProductAll(){
		return productMapper.findProductAll();
	}
	public List<Product> findProductbyType(String type){
		return productMapper.findProductbyType(type);
	}
	public Product findProductById(String id) {
		return productMapper.findProductById(id);
	}
}
