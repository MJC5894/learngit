package cn.sunline.tiny.flow;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.sunline.tiny.core.FlowCom;
import cn.sunline.tiny.core.JavaFlow;
import cn.sunline.tiny.core.PriCache;
import cn.sunline.tiny.core.PubCache;
import cn.sunline.tiny.demo.entity.Account;
import cn.sunline.tiny.demo.entity.Bill;
import cn.sunline.tiny.demo.entity.Fund;
import cn.sunline.tiny.demo.entity.Product;
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.entity.UtilEntity;
/**
 * Account账户生成等操作
 * @author MJC
 *
 */
import cn.sunline.tiny.demo.service.AccountService;
import cn.sunline.tiny.demo.service.BillService;
import cn.sunline.tiny.demo.service.FundService;
import cn.sunline.tiny.demo.service.ProductService;
import cn.sunline.tiny.demo.service.UserService;
import cn.sunline.tiny.demo.util.GetNum;
import cn.sunline.tiny.demo.util.IsNull;
import cn.sunline.tiny.demo.util.TimeABS;
import cn.sunline.tiny.web.Context;
import cn.sunline.tiny.web.util.MD5Util;

/**
 * 用户购买存款商品的相关操作
 * 
 * @author MJC
 *
 */
@Component("account_flow")
public class AccountFlow extends JavaFlow {
	private static final Logger log = LoggerFactory.getLogger(AccountFlow.class);
	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	FundService fundService;
	@Autowired
	BillService billService;

//	用户购买存款商品，进行相应判断操作
	@FlowCom(in = "true")
	public String flow_regist(Context ct, PriCache pri, PubCache pub) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		String proid = json.getString("id");
		String paypwd = json.getString("paypwd");
		String protitle = json.getString("protitle");
		String rate = json.getString("rate");
		String time = json.getString("time");
		String way = json.getString("way");
		String payway = json.getString("payway");
		String price = json.getString("price");
		price = price.toString().replaceAll(",", "");

		if (IsNull.isNull(time) || IsNull.isNull(price)) {
			// 数据不完整
			map.put("message", 999014);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}
		DateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
		Date stoptime = null;
		try {
			stoptime = formatdate.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		计算收益率
		double ratechange = 0.0;
		Date date = new Date();
		try {
			double timeABS = TimeABS.timeABS(date, time);
			if (3.0 == timeABS) {
				ratechange = Double.parseDouble(rate) * 0.8;
			} else if (6.0 == timeABS) {
				ratechange = Double.parseDouble(rate) * 0.9;
			} else {
				ratechange = Double.parseDouble(rate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.debug("timeABS: {}", "时间格式错误！！！");
		}
		DecimalFormat format = new DecimalFormat("#.00");
		ratechange = Double.parseDouble(format.format(ratechange));
		// 收益率：ratechange
		// 获取设备id
		String mobile = pub.getString("MOBILE");
		String devId = pub.getString("devid");
//		获取用户信息，判断是否登录正确
		User userByDevId = userService.getUserByDevId(devId);
		if (userByDevId == null) {
			// 未登录;
			map.put("message", 999018);

		} else if (!MD5Util.getMD5(paypwd.getBytes()).equals(userByDevId.getSpace1())) {
			// 支付密码错误;
			map.put("message", 999012);

		} else {
			Fund findFundByUserId = fundService.findFundByUserId(userByDevId.getUserid());
//			判断用户总额
			if (findFundByUserId.getTotal() < Double.parseDouble(price)) {
				// 总额不够
				map.put("message", 999019);
			} else {
				Product findProductById = productService.findProductById(proid);
				if (findProductById.getMinpp() > Double.parseDouble(price)) {
//					低于最低购买价格
					map.put("message", 999022);
					pri.put("view", JSON.toJSONString(map));
					return END;
				}
				Date star = findProductById.getStar();
				Date stop = findProductById.getStop();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = sdf.format(date);
				String star1 = sdf.format(star);
				String stop1 = sdf.format(stop);
				log.debug("message: {}", "商品购买");
				log.debug("formar-date: {}", date1);
				Date dateparse = sdf.parse(date1);
				log.debug("parse-date: {}", dateparse.getTime());
				Date starparse = sdf.parse(star1);
				Date stopparse = sdf.parse(stop1);
//				判断购买时间是否可买
				if (dateparse.getTime() < starparse.getTime() || dateparse.getTime() > stopparse.getTime()) {
					// 不在购买时间内
					map.put("message", 999020);
				} else {
					// 验证通过进行数据库的操作
					Account account = new Account();
					account.setAccountid(UUID.randomUUID().toString().replaceAll("-", ""));
					account.setCreatdate(new Date());
					account.setUserid(userByDevId.getUserid());
					account.setProductid(proid);
					account.setRealp(Double.parseDouble(price));
					account.setModifydate(new Date());
					account.setSpace1(stoptime);
					account.setSpace2(paypwd);
					account.setSpace3(way);
					account.setSpace4(ratechange);
					int i = accountService.insert(account);
//					商品账户创建成功后，进行商品金额更新，账单表创建
					if (i > 0) {
						// 更新商品已售金额
						// 商品账户创建成功
						Product t = new Product();
						t.setSaletotal(account.getRealp() + productService.findProductById(proid).getSaletotal());
						t.setProductid(proid);
						productService.update(t);
						// 更新用户所剩金额
						Fund fundUp = new Fund();
						fundUp.setTotal(findFundByUserId.getTotal() - Double.parseDouble(price));
						fundUp.setFundid(findFundByUserId.getFundid());
						fundService.update(fundUp);
						// 生成对应账单表
						Bill bill = new Bill();
						bill.setBillid(UUID.randomUUID().toString().replace("-", ""));
						bill.setCreatdate(new Date());
						bill.setFundid(findFundByUserId.getFundid());
						bill.setModifydate(new Date());
						bill.setProname(findProductById.getName());
						bill.setSerialnumber(GetNum.Getnum());
						bill.setProprice(Double.parseDouble(price));
						bill.setState("0");
						int j = billService.insert(bill);
						log.debug("j: {}", j);
						// 操作成功
						map.put("message", 999000);
					} else {
						log.debug("i: {}", i);
						// 商品账户创建失败
						map.put("message", 999021);
					}
				}

			}

		}

		pri.put("view", JSON.toJSONString(map));
		return END;
	}

//	获取用户所有商品账户信息
	@FlowCom(in = "true")
	public String flow_getaccountinfo(Context ct, PriCache pri, PubCache pub) {
		String devId = pub.getString("devid");
		User userByDevId = userService.getUserByDevId(devId);
		List<UtilEntity> listFlex = new ArrayList<UtilEntity>();
		List<UtilEntity> listFlexed = new ArrayList<UtilEntity>();
		List<Account> findAccountAll = accountService.findAccountAllByUserId(userByDevId.getUserid());
		Map<String, Object> mapinfo1 = new HashMap<String, Object>();
		Map<String, Object> mapinfo2 = new HashMap<String, Object>();
		// 计算定期总金额
		double sum = 0.0;
		for (Account account : findAccountAll) {
			Product productById = productService.findProductById(account.getProductid());
//			将活期产品与死期产品分别封装
			if ("0".equals(productById.getSpace1())) {
//				listProductflex.add(productById);
//				listAccountflex.add(account);
//				mapinfo.put("pro", listProductflex);
//				mapinfo.put("acc", listAccountflex);
//				listFlex.add(mapinfo);
//				封装实体类使用list赋给map
				UtilEntity util = new UtilEntity();
				util.setName(productById.getName());
				util.setId(account.getAccountid());
				util.setRate(account.getSpace4());
				util.setRealp(account.getRealp());
//				util.setId(account.getAccountid());
				listFlex.add(util);
			} else if ("1".equals(productById.getSpace1())) {
//				listProduct.add(productById);
//				listAccount.add(account);
//				mapinfo.put("pro", listProduct);
//				mapinfo.put("acc", listProduct);
//				listFlexed.add(mapinfo);
				sum += account.getRealp();
				UtilEntity util = new UtilEntity();
				util.setName(productById.getName());
				util.setId(account.getAccountid());
				util.setRate(account.getSpace4());
				util.setRealp(account.getRealp());
				util.setId(account.getAccountid());
				listFlexed.add(util);
			}

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listFlexed", listFlexed);
		map.put("listFlex", listFlex);
		map.put("totalprice", sum);
		pri.put("view", JSON.toJSONString(map));
		return END;
	}

	// 获取用户所有存款账户信息
	@FlowCom(in = "true")
	public String flow_getaccountinfoall(Context ct, PriCache pri, PubCache pub) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String userid = json.getString("userid");
		List<Account> findAccountAllByUserId = accountService.findAccountAllByUserId(userid);
		List<UtilEntity> list = new ArrayList<UtilEntity>();
		for (Account account : findAccountAllByUserId) {
			Product productById = productService.findProductById(account.getProductid());
			UtilEntity util = new UtilEntity();
			util.setName(productById.getName());
			util.setRealp(account.getRealp());
			list.add(util);
		}
		map.put("list", list);
		pri.put("view", JSON.toJSONString(map));
		return END;
	}

//	获取指定用户指定存款账户的信息
	@FlowCom(in = "true")
	public String flow_getaccountbyid(Context ct, PriCache pri, PubCache pub) throws ParseException {
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		String accountid = json.getString("accountid");
		Account selectById = accountService.selectById(accountid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", selectById);
		pri.put("view", JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));
		return END;

	}

//	验证支付密码，计算当前本息，判断是否为活期用户
	@FlowCom(in = "true")
	public String flow_datacheck(Context ct, PriCache pri, PubCache pub) {
//		params.paypwd=data;
//	    params.price=DATA.price;
//	    params.accountid=accountid;
		boolean isFlex = false;
		double tran = 0.0;
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		String paypwd = json.getString("paypwd");
		String price = json.getString("price");
		String accountid = json.getString("accountid");
		// 获取设备id，查询用户支付密码
		String devId = pub.getString("devid");
		Map<String, Object> map = new HashMap<String, Object>();
//		获取用户信息，判断是否登录正确,支付密码是否正确
		User userByDevId = userService.getUserByDevId(devId);
		if (userByDevId == null) {
			// 未登录;
			map.put("message", 999018);
			pri.put("view", JSON.toJSONString(map));
			return END;
		} else if (!MD5Util.getMD5(paypwd.getBytes()).equals(userByDevId.getSpace1())) {
			// 支付密码错误;
			map.put("message", 999012);
			pri.put("view", JSON.toJSONString(map));
			return END;
		}

		// 通过id获取此账户数据
		Account account = accountService.selectById(accountid);
		// 先判断此商品是否为活期存款商品,计算用户实际本息合计
		Product product = productService.findProductById(account.getProductid());
		// space1（备用字段）标记产品的类别
		if ("0".equals(product.getSpace1())) {
			isFlex = true;
			double rate = account.getSpace4();
			rate = rate / 100;
			tran = account.getRealp() + account.getRealp() * rate;
			DecimalFormat df = new DecimalFormat("#.00");
			String format = df.format(tran);
			tran = Double.parseDouble(format);
//			account.getRealp()*(1.0+account.getSpace4()/100);
		} else if ("1".equals(product.getSpace1())) {
			DecimalFormat df = new DecimalFormat("#.00");
			String format = df.format(Double.parseDouble(price));
			tran = Double.parseDouble(format);
		}
		// 将数据操作转移到完成页面--------------------------------------
		if (isFlex) {
			// 商品为活期产品，重新计算售出价格
			map.put("message", 999023);
		} else {
			// 商品为定期产品直接显示操作成功
			map.put("message", 999000);
		}
		map.put("tran", tran);
		map.put("date", new Date());
		map.put("accountid", accountid);
		pri.put("view", JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));
		return END;

//-----------------------------------------------------------------		
//		// 修改account账户状态（删除）、金额不变，用于后续维护查看
//		Account upAccount = new Account();
//		upAccount.setAccountid(accountid);
//		upAccount.setIssell("1");
//		int updateAccount = accountService.update(upAccount);
//		if (updateAccount > 0) {
//			// 账户状态修改成功后，修改用户电子账户余额
//			// 通过账户获取用户id，获取对应的电子账户信息
//			Fund findFundByUserId = fundService.findFundByUserId(account.getUserid());
//			Fund fund = new Fund();
//			fund.setFundid(findFundByUserId.getFundid());
//			fund.setTotal(findFundByUserId.getTotal() + tran);
//			// 修改电子账户信息
//			fundService.update(fund);
//			// 生成相关账单表
//			Bill bill = new Bill();
//			bill.setBillid(UUID.randomUUID().toString().replaceAll("-", ""));
//			bill.setCreatdate(new Date());
//			bill.setFundid(findFundByUserId.getFundid());
//			bill.setModifydate(new Date());
//			bill.setProname(productService.findProductById(account.getProductid()).getName());
//			bill.setSerialnumber(GetNum.Getnum());
//			bill.setProprice(tran);
//			bill.setDetail("存款产品出售");
//			int inserbill = billService.insert(bill);
//			// 操作成功
//			if (isFlex) {
//				// 商品为活期产品，重新计算售出价格
//				map.put("message", 999023);
//			} else {
//				System.out.println(" 操作成功");
//				map.put("message", 999000);
//			}
//			map.put("tran", tran);
//			map.put("date", new Date());
//		} else {
//			// 商品账户修改失败
//			map.put("message", 999021);
//		}
//---------------------------------------------------------------------------
		// pri.put("view",JSON.toJSONString(map));

	}

//	用户进行商品存款支取操作，修改账户、余额、账单三个表
	@FlowCom(in = "true")
	public String flow_updateaccountinfo(Context ct, PriCache pri, PubCache pub) {
		double tran = 0.0;
		JSONObject json = (JSONObject) pri.get("jsonsObj");
		log.debug("pri: {}", pri);
		log.debug("pub: {}", pub);
		String price = json.getString("price");
		String accountid = json.getString("accountid");
		tran = Double.parseDouble(price);
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = accountService.selectById(accountid);
		// 修改account账户状态（删除）、金额不变，用于后续维护查看
		Account upAccount = new Account();
		upAccount.setAccountid(accountid);
		upAccount.setIssell("1");
		upAccount.setModifydate(new Date());
		int updateAccount = accountService.update(upAccount);
		if (updateAccount > 0) {
			// 账户状态修改成功后，修改用户电子账户余额
			// 通过账户获取用户id，获取对应的电子账户信息
			Fund findFundByUserId = fundService.findFundByUserId(account.getUserid());
			Fund fund = new Fund();
			fund.setFundid(findFundByUserId.getFundid());
			fund.setTotal(findFundByUserId.getTotal() + tran);
			fund.setModifydate(new Date());
			// 修改电子账户信息
			fundService.update(fund);
			// 生成相关账单表
			Bill bill = new Bill();
			bill.setBillid(UUID.randomUUID().toString().replaceAll("-", ""));
			bill.setCreatdate(new Date());
			bill.setFundid(findFundByUserId.getFundid());
			bill.setModifydate(new Date());
			bill.setProname(productService.findProductById(account.getProductid()).getName());
			bill.setSerialnumber(GetNum.Getnum());
			bill.setProprice(tran);
			bill.setDetail("存款产品出售");
			int inserbill = billService.insert(bill);
			// 操作成功
			map.put("message", 999000);

		} else {
			// 商品账户修改失败
			map.put("message", 999021);
		}

		pri.put("view", JSON.toJSONString(map));
		return END;
	}
}
