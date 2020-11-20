package cn.sunline.tiny;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

import cn.sunline.tiny.demo.mapper.TinyMapper;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages= {"cn.sunline.tiny.demo.mapper"})
@Order(1)
public class DataSourceConfig {

	@Autowired
	private Environment env;

	@Bean     //声明其为Bean实例
	@Primary  //在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource(){
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(env.getProperty("spring.datasource.url"));
		datasource.setUsername(env.getProperty("spring.datasource.username"));
		datasource.setPassword(env.getProperty("spring.datasource.password"));
		datasource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));

		//configuration
		datasource.setInitialSize(getInt("spring.datasource.initialSize"));
		datasource.setMinIdle(getInt("spring.datasource.minIdle"));
		datasource.setMaxActive(getInt("spring.datasource.maxActive"));
		datasource.setMaxWait(getInt("spring.datasource.maxWait"));
		datasource.setTimeBetweenEvictionRunsMillis(getInt("spring.datasource.timeBetweenEvictionRunsMillis"));
		datasource.setMinEvictableIdleTimeMillis(getInt("spring.datasource.minEvictableIdleTimeMillis"));
		datasource.setValidationQuery(env.getProperty("spring.datasource.validationQuery"));
		datasource.setTestWhileIdle( Boolean.parseBoolean(env.getProperty("spring.datasource.testWhileIdle")) );
		datasource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("spring.datasource.testOnBorrow")));
		datasource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("spring.datasource.testOnReturn")));
		datasource.setPoolPreparedStatements(Boolean.parseBoolean(env.getProperty("spring.datasource.poolPreparedStatements")));
		datasource.setMaxPoolPreparedStatementPerConnectionSize(getInt("spring.datasource.maxPoolPreparedStatementPerConnectionSize"));
		try {
			datasource.setFilters(env.getProperty("spring.datasource.filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		datasource.setConnectionProperties(env.getProperty("spring.datasource.connectionProperties"));

		return datasource;
	}

	private int getInt(String str) {
		return Integer.parseInt(env.getProperty(str));
	}
	
    @Bean
	public DruidStatInterceptor druidStatInterceptor(){
		return new DruidStatInterceptor();
	}


	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		return TinyMapper.getSqlSessionFactoryBean(dataSource);
	}


	@Bean(name="defaultTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}