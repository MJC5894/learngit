package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.UserTemplate;
import cn.sunline.tiny.demo.mapper.*;
@Service("userTemplateService")
public class UserTemplateService extends BaseService<UserTemplate,String> {
	@Autowired
	private UserTemplateMapper userTemplateMapper;

	public BaseMapper<UserTemplate,String> getMapper() {
		return userTemplateMapper;
	}
	public int InserUserTem(UserTemplate template) {
		return userTemplateMapper.InserUserTem(template);
	}
	public  UserTemplate getUsertemByDevId(String devId) {
		return userTemplateMapper.getUsertemByDevId(devId);
	}
}
