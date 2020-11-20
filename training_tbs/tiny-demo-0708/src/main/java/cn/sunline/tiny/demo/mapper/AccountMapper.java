package cn.sunline.tiny.demo.mapper;
import java.util.List;

import cn.sunline.tiny.demo.entity.Account;

public interface AccountMapper extends BaseMapper<Account,String> {
	public List<Account> findAccountAllByUserId(String userid);
}
