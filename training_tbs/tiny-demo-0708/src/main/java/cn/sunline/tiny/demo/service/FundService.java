package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.Fund;
import cn.sunline.tiny.demo.mapper.*;
@Service("fundService")
public class FundService extends BaseService<Fund,String> {
	@Autowired
	private FundMapper fundMapper;

	public BaseMapper<Fund,String> getMapper() {
		return fundMapper;
	}
	public Fund findFundByUserId(String userid) {
		return fundMapper.findFundByUserId(userid);
	}
}
