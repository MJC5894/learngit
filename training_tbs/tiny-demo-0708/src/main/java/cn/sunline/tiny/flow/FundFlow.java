package cn.sunline.tiny.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.sunline.tiny.core.FlowCom;
import cn.sunline.tiny.core.JavaFlow;
import cn.sunline.tiny.core.PriCache;
import cn.sunline.tiny.core.PubCache;
import cn.sunline.tiny.demo.entity.Certificate;
import cn.sunline.tiny.demo.entity.Fund;
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.service.CertificateService;
import cn.sunline.tiny.demo.service.FundService;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.demo.util.IsNull;
import cn.sunline.tiny.web.Context;
import cn.sunline.tiny.web.util.MD5Util;

/**
 * 电子账户
 * 
 * @author MJC
 *
 */

@Component("fund_flow")
//@Transactional
public class FundFlow extends JavaFlow {
	private static final Logger log = LoggerFactory.getLogger(FundFlow.class);
	@Autowired
	UserService UserService;
	@Autowired
	FundService fundServie;
	@Autowired
	CertificateService cerfificateService;
	//注册时开通电子账户
	@FlowCom(in = "true")
	public String flow_register(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 身份证验证
		String idNum = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
				+ "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String cardnumber = json.getString("cardnumber");
		String card = json.getString("card");
		String name = json.getString("name");
		String paypwd = json.getString("paypwd");
		String confirmpwd = json.getString("confirmpwd");
		Pattern p = Pattern.compile(idNum);
		Matcher m = p.matcher(cardnumber);
		boolean isMatch = m.matches();
		String reg="(^\\d{6}$)"; 
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher = pattern.matcher(paypwd);
		boolean matches = matcher.matches();
		
		if (IsNull.isNull(cardnumber) || IsNull.isNull(card) || IsNull.isNull(name) || IsNull.isNull(paypwd)
				|| IsNull.isNull(confirmpwd)) {
			//数据填写不完整
			map.put("message", 999014);
		} else if (!isMatch) {
			// 身份证格式有误
			map.put("message", 999013);
		} else if(!matches) {
			//支付密码格式错误
			map.put("message", 999015);
		}
		else if (!paypwd.equals(confirmpwd)) {
			// 两次输入支付密码不一致
			map.put("message", 999012);
		} else {
			//设置用户支付密码，生成用户电子账户
			String mobile = pub.getString("MOBILE");
			String devId = pub.getString("devid");
			log.debug("devid :{}", devId);
			User userByDevId = UserService.getUserByDevId(devId);
			userByDevId.setSpace1(MD5Util.getMD5(paypwd.getBytes()));
			UserService.update(userByDevId);
			Fund fund=new Fund();
			fund.setFundid(UUID.randomUUID().toString().replaceAll("-", ""));
			fund.setUserid(userByDevId.getUserid());
			fund.setCreatdate(new Date());
			fund.setModifydate(new Date());
			int j = fundServie.insert(fund);
			log.debug("fund.insert: {}"+j);
			//生成相关的证件信息
			Certificate certificate=new Certificate();
			certificate.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			certificate.setType(card);
			certificate.setCernumber(cardnumber);
			certificate.setUserid(userByDevId.getUserid());
			certificate.setSpace1(new Date());
			certificate.setSpace2(new Date());
			certificate.setSpace3(name);
			int i = cerfificateService.insert(certificate);
			log.debug("cerfificateService.insert: {}"+i);
			if(i>0&&j>0) {
				map.put("message", 999000);		
			}else {
				map.put("message", 999999);
			}
			
		}
		pri.put("view", JSON.toJSONString(map));
		return END;
	}
}
