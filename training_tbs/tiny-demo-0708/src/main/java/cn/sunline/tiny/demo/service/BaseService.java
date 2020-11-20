package cn.sunline.tiny.demo.service;
import java.util.List;
import cn.sunline.tiny.demo.mapper.BaseMapper;
public abstract class BaseService<T,PK>{

	public void deleteByPK(PK id) {

		getMapper().deleteById(id);

	}

	public int insert(T t) {

		return getMapper().insert(t);

	}

	public int update(T t) {

		return getMapper().updateById(t);

	}

	public T selectById(PK id) {

		return getMapper().selectById(id);

	}

	 public abstract BaseMapper<T,PK> getMapper();
}