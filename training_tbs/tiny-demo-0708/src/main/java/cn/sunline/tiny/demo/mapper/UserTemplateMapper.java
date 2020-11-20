package cn.sunline.tiny.demo.mapper;
import cn.sunline.tiny.demo.entity.UserTemplate;

public interface UserTemplateMapper extends BaseMapper<UserTemplate,String> {
	public int InserUserTem(UserTemplate template);
	public  UserTemplate getUsertemByDevId(String devId);
	
}
