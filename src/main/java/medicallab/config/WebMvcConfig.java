package medicallab.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("medicallab.web")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		/* 
		 * You're asking DispatcherServlet to forward requests for static resources 
		 * to the servlet container’s default servlet and not to try to handle them itself 
		 */
		configurer.enable();
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions(new String[] {
				"/WEB-INF/layouts/tiles.xml"
		});
		
		tiles.setCheckRefresh(true);
		
		return tiles;
	}
	
	@Bean
	public ViewResolver tilesViewResolver() {
		TilesViewResolver tilesResolver = new TilesViewResolver();
		return tilesResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages", "validation-messages");
		
		return messageSource;
	}
	
	// jspViewResolver Bean
//		@Bean
//		public ViewResolver jspViewResolver() {
//			InternalResourceViewResolver jspResolver = new InternalResourceViewResolver();
//			
//			jspResolver.setPrefix("/WEB-INF/views/");
//			jspResolver.setSuffix(".jsp");
//			jspResolver.setExposeContextBeansAsAttributes(true);
//			
//			return jspResolver;
//		}
	
//	@Bean
//	public SimpleMappingExceptionResolver exceptionResolver() {
//		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
//		
//		exceptionResolver.setExceptionMappings(exceptionMappings());
//		
//		return exceptionResolver;
//	}
//	
//	@Bean
//	public Properties exceptionMappings() {
//		Properties exceptionMappings = new Properties();
//		
//		exceptionMappings.setProperty("java.lang.Exception", "/error");
//		
//		return exceptionMappings;
//	}
	
}
