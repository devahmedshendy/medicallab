package medicallab.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
	@Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		insertFilters(servletContext, new MultipartFilter());
	}
}
