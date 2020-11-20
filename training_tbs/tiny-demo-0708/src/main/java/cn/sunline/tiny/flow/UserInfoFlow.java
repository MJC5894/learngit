package cn.sunline.tiny.flow;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.tools.doclets.internal.toolkit.Content;

import cn.sunline.tiny.core.FlowCom;
import cn.sunline.tiny.core.JavaFlow;
import cn.sunline.tiny.core.PriCache;
import cn.sunline.tiny.core.PubCache;
import cn.sunline.tiny.demo.entity.Certificate;
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.entity.UserTemplate;
import cn.sunline.tiny.demo.service.AccountService;
import cn.sunline.tiny.demo.service.CertificateService;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.demo.service.UserTemplateService;
import cn.sunline.tiny.demo.util.GetNum;
import cn.sunline.tiny.demo.util.IsNull;
import cn.sunline.tiny.web.Context;
import cn.sunline.tiny.web.util.MD5Util;

/**
 * 用户信息
 * 
 * @author MJC
 *
 */
@Component("userinfo_flow")
public class UserInfoFlow extends JavaFlow {
	private static final Logger log = LoggerFactory.getLogger(UserInfoFlow.class);
	@Autowired
	UserTemplateService usertem;
	@Autowired
	UserService userService;
	@Autowired
	CertificateService certiService;
	// 用户注册或密码找回
	@FlowCom(in = "true")
	public String flow_registerandfindpwd(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String pwd = json.getString("pwd");
		String pwdok = json.getString("pwdok");
		String changPwd = json.getString("changpwd");
		if (!pwd.equals(pwdok)) {
			map.put("message", false);
		} else {
			UserTemplate usertemByDevId = usertem.getUsertemByDevId(pub.getString("devid"));
			User user = new User();
			// 判断是否为修改手势密码操作
			if ("999009".equals(changPwd)) {
				user.setUserid(usertemByDevId.getUserid());
				user.setDevid(usertemByDevId.getDevid());
				user.setCreatdate(usertemByDevId.getCreatdate());
				user.setModifydate(new Date());
				user.setMobile(usertemByDevId.getMobile());
				user.setPwd(MD5Util.getMD5(pwd.getBytes()));
				int i = userService.insert(user);
				// 修改临时表删除状态
				UserTemplate usertemp = new UserTemplate();
				usertemp.setDel("1");
				usertemp.setModifydate(new Date());
				usertemp.setId(usertemByDevId.getId());
				int update = usertem.update(usertemp);
			} else if ("999010".equals(changPwd)) {
				// 当为修改密码时，通过设备id获取用户id，再通过用户id修改手势密码
				user.setPwd(MD5Util.getMD5(pwd.getBytes()));
				user.setUserid(usertemByDevId.getUserid());
				user.setModifydate(new Date());
				int update = userService.update(user);
			}
			pub.put("MOBILE", usertemByDevId.getMobile());
			map.put("message", true);
			map.put("mobile", usertemByDevId.getMobile());
		}
		pri.put("view", JSON.toJSONString(map));
		return END;
	}

	// 判断是否已注册，未注册返回false，通知前台注册
	@FlowCom(in = "true")
	public String flow_judge(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String devId = json.getString("devid");
		// 将设备id放入公共池
		pub.put("devid", devId);
		User userByDevId = userService.getUserByDevId(devId);
		if (userByDevId == null) {
			//已注册
			map.put("message", 999004);
		} else {
			//未注册
			map.put("message", 999005);
		}
		pri.put("view", JSON.toJSONString(map));
		return END;
	}

	// 用户手势密码登录
	@FlowCom(in = "true")
	public String flow_login(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String pwd = json.getString("pwd");
		// String devid = pub.getString("devid");
		String devid = json.getString("devid");
		// 测试使用
		pub.put("devid", devid);
		User byDevId = userService.getUserByDevId(devid);
		if (byDevId.getPwd().equals(MD5Util.getMD5(pwd.getBytes()))) {
			pub.put("MOBILE", byDevId.getMobile());
			//用户登录成功
			map.put("message", 999001);
			map.put("mobile", byDevId.getMobile());
			map.put("isloss", byDevId.getSpace2());//用于传递判断是否挂失
			
		} else {
			//用户登录失败
			map.put("message", 999002);
		}
		pri.put("view", JSON.toJSONString(map));
		return END;
	}
	//用户挂失数据校验
	@FlowCom(in="true")
	public String flow_losschekout(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String name = json.getString("name");
		String number = json.getString("number");
		String mobile = json.getString("mobile");
		String devid = json.getString("devid");
		String isclearloss=json.getString("isclearloss");//标记是否为解挂失操作
		String idNum = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
				+ "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		Pattern phonecheckout = Pattern.compile(regex);
		Pattern numbercheckout = Pattern.compile(idNum);
		Matcher mobileMatcher = phonecheckout.matcher(mobile);
		Matcher numberMatcher=numbercheckout.matcher(number);
		boolean isMobileMatcher = mobileMatcher.matches();
		boolean isNumberMatcher=numberMatcher.matches();
		// 数据判空
		if (IsNull.isNull(mobile)||IsNull.isNull(number)||IsNull.isNull(name)) {
			//数据为空
			map.put("message", 999006);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}else if (!isMobileMatcher || 11 != mobile.length()) {
			//手机号格式错误
			map.put("message", 999007);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}else if (!isNumberMatcher) {
			// 身份证格式有误
			map.put("message", 999013);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}
		User userByDevId = userService.getUserByDevId(devid);
		Certificate certificateByUserId = certiService.findCertificateByUserId(userByDevId.getUserid());
//		判断前台数据是否与用户数据一致
		if(!userByDevId.getMobile().equals(mobile)||!certificateByUserId.getCernumber().equals(number)||!certificateByUserId.getSpace3().equals(name)) {
			//数据填写不正确
			map.put("message", 999025);
		}else {
			if("0".equals(isclearloss)) {
				map.put("message", 999000);
				map.put("date", new Date());
				map.put("userid", userByDevId.getUserid());
			}else if("1".equals(isclearloss)) {
				//解挂失操作
				User user=new User();
				user.setUserid(userByDevId.getUserid());
				user.setSpace2("0");
				user.setModifydate(new Date());
				int update = userService.update(user);
				if(update>0) {
//					解挂成功
					map.put("message", 999024);
				}else {
//					解挂失败
					map.put("message", 999021);
				}
			}
			
		}
		
		pri.put("view", JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));
		return END;
	}
	
	//用户挂失设置
	@FlowCom(in="true")
	public String flow_userloss(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String userid = json.getString("userid");
		User user=new User();
		user.setUserid(userid);
		user.setSpace2("1");
		user.setModifydate(new Date());
		int update = userService.update(user);
		if(update>0) {
//			挂失成功
			map.put("message", 999000);
		}else {
//			挂失失败
			map.put("message", 999021);
		}
		pri.put("view", JSON.toJSONString(map));
		return END;
	}
	//用户添加头像图片
	@FlowCom(in="true")
	public String flow_addimg(Context ct, PriCache pri, PubCache pub) throws IllegalStateException, IOException {
		HttpRequest req=(HttpRequest) ct.getRequest();
		MultipartHttpServletRequest multRequest=(MultipartHttpServletRequest) req;
		Iterator<String> iter = multRequest.getFileNames();
		String devid = multRequest.getParameter("devid");
		log.debug("addimg: {}", "添加头像");
		
		File file=new File("image");
		String fileName="picture_" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
		while(iter.hasNext()) {
			MultipartFile multipartFile=multRequest.getFile(iter.next());	
			if(!file.exists()) {
				file.mkdirs();
			}
			file=new File(file+file.separator+fileName);
			multipartFile.transferTo(file);
		}
		log.debug("addimg: {}", "添加头像测试");
		return END;
		
	}
	
	// 用户退出登录showdown
	@FlowCom(in = "true")
	public String flow_shutdown(Context ct, PriCache pri, PubCache pub) {
		pub.remove("MOBILE");
		pub.remove("devid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", 999003);
		pri.put("view", JSON.toJSONString(map));
		return END;
	}
}
