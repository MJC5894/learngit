package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.Account;
import cn.sunline.tiny.demo.mapper.*;
@Service("accountService")
public class AccountService extends BaseService<Account,String> {
	@Autowired
	private AccountMapper accountMapper;

	public BaseMapper<Account,String> getMapper() {
		return accountMapper;
	}
	public List<Account> findAccountAllByUserId(String userid){
		return accountMapper.findAccountAllByUserId(userid);
	}
}
