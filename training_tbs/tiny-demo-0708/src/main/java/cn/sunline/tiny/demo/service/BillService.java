package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.Bill;
import cn.sunline.tiny.demo.mapper.*;
@Service("billService")
public class BillService extends BaseService<Bill,String> {
	@Autowired
	private BillMapper billMapper;

	public BaseMapper<Bill,String> getMapper() {
		return billMapper;
	}
}
