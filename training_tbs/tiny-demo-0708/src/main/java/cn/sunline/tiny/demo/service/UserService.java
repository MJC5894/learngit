package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;

import com.sun.tools.javac.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.User;
import cn.sunline.tiny.demo.mapper.*;
@Service("userService")
public class UserService extends BaseService<User,String> {
	@Autowired
	private UserMapper userMapper;

	public BaseMapper<User,String> getMapper() {
		return userMapper;
	}
	public User getMobileAndPwd(User user) {
		return userMapper.getMobileAndPwd(user);
	}
	public User getUserByDevId(String devId){
		return userMapper.getUserByDevId(devId);
	}
	public int InserUser(User user) {
		return userMapper.InserUser(user);
	}
	public User getUserByDevIdAndState(User user) {
		return userMapper.getUserByDevIdAndState(user);
	}
}
