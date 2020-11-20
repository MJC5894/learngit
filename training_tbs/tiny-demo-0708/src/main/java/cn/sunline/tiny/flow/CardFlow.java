package cn.sunline.tiny.flow;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.sunline.tiny.core.FlowCom;
import cn.sunline.tiny.core.JavaFlow;
import cn.sunline.tiny.core.PriCache;
import cn.sunline.tiny.core.PubCache;
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.web.Context;

@Component("card_flow")
public class CardFlow extends JavaFlow {
	private static final Logger log = LoggerFactory.getLogger(CardFlow.class);
	@Autowired
	UserService userService;
//	@FlowCom(in="true")
//	public String getCardInfo(Context ct,PriCache pri,PubCache pub) {
//		Map<String,Object> map=new HashMap<String,Object>();
//		JSONObject json=(JSONObject)pri.get("jsonsObj");
//		String devid = json.getString("devid");
//		String mobile = json.getString("mobile");
//		User userByDevId = userService.getUserByDevId(devid);
//		if(userByDevId.getMobile().equals(mobile)) {
//			
//		}
//		
//	}
}
