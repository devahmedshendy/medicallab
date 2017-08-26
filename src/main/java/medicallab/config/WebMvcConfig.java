	package medicallab.config;
	
	import org.springframework.context.MessageSource;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.support.ResourceBundleMessageSource;
	import org.springframework.web.servlet.ViewResolver;
	import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
	import org.springframework.web.servlet.config.annotation.EnableWebMvc;
	import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
	import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
	import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
	import org.springframework.web.servlet.resource.ResourceUrlProvider;
	import org.springframework.web.servlet.resource.VersionResourceResolver;
	import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
	import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
	
	@Configuration
	@EnableWebMvc
	@ComponentScan("medicallab.web")
	public class WebMvcConfig extends WebMvcConfigurerAdapter {
		
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
//			CacheControl cacheControl = CacheControl.maxAge(100, TimeUnit.HOURS);

			VersionResourceResolver versionResolver = new VersionResourceResolver()
					.addContentVersionStrategy("/**");

			registry.addResourceHandler("/js/**", "/css/**", "/images/**")
					.addResourceLocations("/resources/js/", "/resources/css/", "/resources/images/")
					.setCachePeriod(10000)
					.resourceChain(false)
						.addResolver(versionResolver);
		}
		
		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
			/* 
			 * You're asking DispatcherServlet to forward requests for static resources 
			 * to the servlet container’s default servlet and not to try to handle them itself 
			 */
			configurer.enable();
		}
		
	
		@Bean("tilesConfigurer")
		public TilesConfigurer tilesConfigurer() {
			TilesConfigurer tiles = new TilesConfigurer();
			tiles.setDefinitions(new String[] {
					"/WEB-INF/layouts/tiles.xml"
			});
			
			tiles.setCheckRefresh(true);
			
			return tiles;
		}
		
		@Bean("tilesViewResolver")
		public ViewResolver tilesViewResolver() {
			TilesViewResolver tilesResolver = new TilesViewResolver();
			return tilesResolver;
		}
		
		@Bean("messageSource")
		public MessageSource messageSource() {
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasenames("messages");
			
			return messageSource;
		}
		
		@Bean("resourceUrlProvider")
		public ResourceUrlProvider resourceUrlProvider() {
			return new ResourceUrlProvider();
		}
		
		@Bean("resourceUrlEncodingFilter")
		public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
			return new ResourceUrlEncodingFilter();
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
	}
