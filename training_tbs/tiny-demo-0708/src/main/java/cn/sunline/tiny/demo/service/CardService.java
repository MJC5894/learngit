package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.Card;
import cn.sunline.tiny.demo.mapper.*;
@Service("cardService")
public class CardService extends BaseService<Card,String> {
	@Autowired
	private CardMapper cardMapper;

	public BaseMapper<Card,String> getMapper() {
		return cardMapper;
	}
}
