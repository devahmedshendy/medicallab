package medicallab.web.misc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component("accessDeniedHandler")
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	
	@Autowired 
	private Uri uri;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(uri.get("403"));
		requestDispatcher.forward(request, response);
		
	}

}
