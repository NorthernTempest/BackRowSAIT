package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseAccess.SessionDB;
import databaseAccess.UserDB;
import domain.User;

/**
 * Servlet Filter implementation class AdminFilter
 */
public class AdminFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AdminFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpSession session = request2.getSession();
		
		String email = SessionDB.getEmail(session.getId());
		
		if( email != null && !email.equals("") ) {
			User u = UserDB.get(email);
			
			if( u == null || u.getPermissionLevel() < User.ADMIN && request2.getServletPath().equals("/admin"))
				((HttpServletResponse) response).sendRedirect("/inbox?errorMessage=Only admins can go there.");
			else if ( u == null || u.getPermissionLevel() < User.TAX_PREPARER && request2.getServletPath().equals("/reports"))
				((HttpServletResponse) response).sendRedirect("/inbox?errorMessage=Only admins can go there.");
			else
				chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
