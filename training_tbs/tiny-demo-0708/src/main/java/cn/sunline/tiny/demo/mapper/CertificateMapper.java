package cn.sunline.tiny.demo.mapper;
import cn.sunline.tiny.demo.entity.Certificate;

public interface CertificateMapper extends BaseMapper<Certificate,String> {
	public Certificate findCertificateByUserId(String id);
}
