package medicallab.config;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		if ( "PRODUCTION".equals(env.getProperty("env")) ) {
			System.out.println("PRODUCTION PRODUCTION PRODUCTION PRODUCTION ");
			System.out.println("env.getProperty(\"DB_URL\") : " + env.getProperty("DB_URL"));
			System.out.println("env.getProperty(\"DB_USERNAME\") " + env.getProperty("DB_USERNAME"));
			System.out.println("env.getProperty(\"DB_PASSWORD\") " + env.getProperty("DB_PASSWORD"));
			
			dataSource.setUrl(env.getProperty("DB_URL"));
			dataSource.setUsername(env.getProperty("DB_USERNAME"));
			dataSource.setPassword(env.getProperty("DB_PASSWORD"));
		
		} else if ( "DEV".equals(env.getProperty("env")) ) {
			dataSource.setUrl("jdbc:mysql://localhost:3306/medicallabDB?createDatabaseIfNotExist=true");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
		}
		
		return dataSource;
	}
	
	@Autowired
	@Bean(name = "hibernateTransactionManager")
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean localSessionFactory = new LocalSessionFactoryBean();
		localSessionFactory.setDataSource(dataSource());
		localSessionFactory.setPackagesToScan("medicallab.web.model");
		localSessionFactory.setHibernateProperties(hibernateProperties());
//			localSessionFactory.afterPropertiesSet();			
		
		return localSessionFactory;
	}
	
	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.c3p0.min_size", "1");
		hibernateProperties.setProperty("hibernate.c3p0.max_size", "10");
		hibernateProperties.setProperty("hibernate.c3p0.timeout", "120");
		hibernateProperties.setProperty("hibernate.c3p0.max_statements", "10");
		hibernateProperties.setProperty("hibernate.connection.pool_size", "10");
		
		return hibernateProperties;
	}
}
