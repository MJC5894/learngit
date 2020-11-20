package cn.sunline.tiny.demo.mapper;
import cn.sunline.tiny.demo.entity.Fund;

public interface FundMapper extends BaseMapper<Fund,String> {
	public Fund findFundByUserId(String userid);
}
