package cn.sunline.tiny.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sunline.tiny.core.FlowCom;
import cn.sunline.tiny.core.JavaFlow;
import cn.sunline.tiny.core.PriCache;
import cn.sunline.tiny.core.PubCache;
import cn.sunline.tiny.demo.entity.Product;
import cn.sunline.tiny.demo.service.FundService;
import cn.sunline.tiny.demo.service.ProductService;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.web.Context;

/**
 * 产品相关操作
 * 
 * @author MJC
 *
 */
@Component("product_flow")
public class ProductFlow extends JavaFlow {
	private static final Logger log = LoggerFactory.getLogger(ProductFlow.class);
	@Autowired
	ProductService productService;
	@Autowired
	UserService userServie;
	@Autowired
	FundService fundService;

	// 获取所有的商品集合
	@FlowCom(in = "true")
	public String flow_findproductall(Context ct, PriCache pri, PubCache pub) {
		List<Product> productAll = productService.findProductAll();
		log.debug("producAll: {}", productAll);
		pri.put("view", JSON.toJSONString(productAll));
		return END;
	}

	// 通过商品类型获取商品集合
	@FlowCom(in = "true")
	public String flow_findproductbytype(Context ct, PriCache pri, PubCache pub) {
		List<Product> productbyType0 = productService.findProductbyType("0");
		List<Product> productbyType1 = productService.findProductbyType("1");
		log.debug("productbyType0: {}", productbyType0);
		log.debug("message: {}", "查看数据");
		// Map<String,Object> map=new HashMap<String,Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productbyType0", productbyType0);
		map.put("productbyType1", productbyType1);

		pri.put("view", JSON.toJSONString(map));
		return END;

	}

	// 通过商品id获取商品所有信息
	@FlowCom(in = "true")
	public String flow_findproductbyid(Context ct, PriCache pri, PubCache pub) {
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String id = json.getString("id");
		Product selectById = productService.findProductById(id);
		log.debug("product: {}", selectById);
		Map<String, Object> map = new HashMap<String, Object>();
		String devId = pub.getString("devid");
		Double total = fundService.findFundByUserId(userServie.getUserByDevId(devId).getUserid()).getTotal();
		map.put("total", total);
		map.put("message", selectById);
		pri.put("view", JSON.toJSONString(map));
		return END;
	}

}
