package cn.sunline.tiny.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.entity.UserTemplate;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.demo.service.UserTemplateService;
import cn.sunline.tiny.demo.util.IsNull;
import cn.sunline.tiny.web.Context;

/**
 * 验证码控制
 * 
 * @author MJC
 *
 */
@Component("code_flow")
public class CodeProFlow extends JavaFlow {
	private static final Logger log = LoggerFactory.getLogger(CodeProFlow.class);
	@Autowired
	UserService userService;
	@Autowired
	UserTemplateService userTempService;

	// 发送验证码，验证手机号码
	@FlowCom(in = "true", succ = "15")
	public String flow_sendcode(Context ct, PriCache pri, PubCache pub) {
		log.debug("code: {}", "sendcode");
		pub.remove("code");
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = (JSONObject) pri.get("jsonsObj");

		// 获取设备id
		String devId = json.getString("devid");
		String mobile = json.getString("mobile");
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		boolean isMatch = m.matches();
		// 手机号码判空
		if (IsNull.isNull(mobile)) {
			map.put("message", 999006);
			pri.put("view", JSON.toJSONString(map));
			return END;
		} else if (!isMatch || 11 != mobile.length()) {
//			手机号格式错误
			map.put("message", 999007);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}
		int code = ((int) ((Math.random() * 9 + 1) * 100000));
		// 获取判断表示（是否为找回密码）
		String findpwd = json.getString("findpwd");
//		用户注册
		if ("999009".equals(findpwd)) {
			UserTemplate t = new UserTemplate();
			t.setDevid(devId);
			t.setId(UUID.randomUUID().toString().replace("-", ""));
			t.setMobile(json.getString("mobile"));
			t.setUserid(UUID.randomUUID().toString().replaceAll("-", ""));
			t.setCreatdate(new Date());
			t.setModifydate(new Date());
			int i = userTempService.InserUserTem(t);
			map.put("message", 999011);
			map.put("code", code);
			pri.put("view", JSON.toJSONString(map));
			pub.put("code", code);
			return END;
		} else if ("999010".equals(findpwd)) {
//			用户密码找回
			User byDevId = userService.getUserByDevId(devId);
			if (byDevId.getMobile().equals(json.getString("mobile"))) {
				map.put("code", code);
				map.put("message", 999011);
				pub.put("code", code);
				pri.put("view", JSON.toJSONString(map));
			} else {
				map.put("message", 999008);
				pri.put("view", JSON.toJSONString(map));
			}
			return END;
		}
		return END;
	}
	//验证码比较接口
	@FlowCom(in = "true", succ = "15")
	public String flow_compare(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		int code = (int) pub.get("code");
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String pwd = json.getString("pwd");
		if (code == Integer.parseInt(pwd)) {
			map.put("message", true);
			pri.put("view", JSON.toJSONString(map));
			return END;
		} else {
			map.put("message", false);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}
	}

}
