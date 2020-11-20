package cn.sunline.tiny.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.sunline.tiny.demo.entity.Certificate;
import cn.sunline.tiny.demo.mapper.*;
@Service("certificateService")
public class CertificateService extends BaseService<Certificate,String> {
	@Autowired
	private CertificateMapper certificateMapper;

	public BaseMapper<Certificate,String> getMapper() {
		return certificateMapper;
	}
	public Certificate findCertificateByUserId(String id) {
		return certificateMapper.findCertificateByUserId(id);
	}

}
