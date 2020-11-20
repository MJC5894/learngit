package cn.sunline.tiny.demo.mapper;
import com.sun.tools.javac.util.List;

import cn.sunline.tiny.demo.entity.User;

public interface UserMapper extends BaseMapper<User,String> {
	public User getMobileAndPwd(User user);
	public User getUserByDevId(String devId);
	public User getUserByDevIdAndState(User user);
	public int InserUser(User user);
}
