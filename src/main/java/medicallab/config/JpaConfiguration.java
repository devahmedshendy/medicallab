package medicallab.config;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfiguration {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, Properties jpaProperties) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = 
				new LocalContainerEntityManagerFactoryBean();
		
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan("medicallab.model");
		entityManagerFactory.setJpaProperties(jpaProperties);
		
		return entityManagerFactory; 
	}

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/medicallab?createDatabaseIfNotExist=true");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setMaxActive(10);
		dataSource.setMaxWait(30000);
		dataSource.setMinIdle(10);
		
		return dataSource;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		
		return adapter;
	}
	
	@Bean
	public Properties jpaProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.show_sql", "true");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		jpaProperties.setProperty("hibernate.dialect.storage_engine", "innodb");
		
		return jpaProperties;
	}
	
	/*
	 * To use @PersistenceUnit and @PersistenceContext, you need to define this bean
	 * in order for Spring to understand them and inject an EntityManagerFactory or EntityManager
	 */
	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}
	
	/*
	 * @Repository will let PersistenceExceptionTranslationPostProcessor knows that 
	 * this is a bean for which exceptions should be translated into one of Spring’s 
	 * unified data-access exceptions
	 */
	@Bean
	public BeanPostProcessor persistenceExceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
