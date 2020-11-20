package cn.sunline.tiny.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import cn.sunline.tiny.core.data.database.TinyJdbcDao;
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.entity.UserTemplate;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.demo.service.UserTemplateService;
import cn.sunline.tiny.web.Context;
/**
 * 学习示例类
 * @author MJC
 *
 */
@Component("usertem_flow")
public class UserTemplateFlow extends JavaFlow {

	private static final Logger log = LoggerFactory.getLogger(UserTemplateFlow.class);

	@Autowired
	UserService userService;
	@Autowired
	UserTemplateService userTempService;

	@Autowired
	TinyJdbcDao tinyJdbcDao;
	//判断验证码输入是否一致
//	@FlowCom(in = "true", succ = "15")
//	public String flow_compare(Context ct, PriCache pri, PubCache pub) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		int code = (int) pub.get("code");
//		log.debug("pri: {}", pri);
//		log.debug("pub: {}", pub);
//		JSONObject json = (JSONObject) pri.get("jsonsObj");
//		String pwd = json.getString("pwd");
//		if (code == Integer.parseInt(pwd)) {
//			map.put("message", true);
//			pri.put("view", JSON.toJSONString(map));
//			return END;
//		} else {
//			map.put("message", false);
//			pri.put("view", JSON.toJSONString(map));
//			return END;
//		}
		
//        String id = pub.getParam("id");
//		String id = json.getString("id");
//		log.debug("phone: {}", devId);
//		log.debug("id: {}", id);
//		User u = new User();
//		u.setMobile(id);
//        u.setPwd(pwd);
//		User user=userService.getMobileAndPwd(u);
//       
//        
//        
//        pub.put("phone", pwd);
//        pri.put("view", JSON.toJSONString(tinyJdbcDao.queryList("select * from t_user where id=?", id)));
//        pri.put("view", JSON.toJSONString(user));

//		return END;
//        return SUCCESS;
//	}

//	@FlowCom(name = "view")
//	public String flow_15(Context ct, PriCache pri, PubCache pub) {
//		log.debug("pri: {}", pri);
//		return "test";
//	}

}
