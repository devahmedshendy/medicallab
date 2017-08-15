package medicallab.config;

import java.util.Properties;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/medicallabDB");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		
		return dataSource;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory().getObject());
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean localSessionFactory = new LocalSessionFactoryBean();
		
		try {
			localSessionFactory.setDataSource(dataSource());
			localSessionFactory.setHibernateProperties(hibernateProperties());
			localSessionFactory.setPackagesToScan("medicallab.web.model");
			
			
			localSessionFactory.afterPropertiesSet();			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return localSessionFactory;
	}
	
	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return hibernateProperties;
	}
}
