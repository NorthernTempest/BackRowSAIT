package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.ConfigException;
import manager.SessionManager;
import manager.UserManager;

/**
 * Servlet Filter implementation class AdminFilter
 */
public class AdminFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AdminFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpSession session = request2.getSession();
		String context = request2.getServletPath();
		
		try {
			request2.setAttribute("role", UserManager.getRole(session.getId().toString()));
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (context != null && context.equals("/admin")) {
			
			try {
				if (UserManager.getRole(session.getId()) > 2) {
					chain.doFilter(request, response);
				} else {
					request2.setAttribute("errorMessage", "Access Denied.");
					if (SessionManager.isSessionActive(session.getId()))
						request2.getServletContext().getRequestDispatcher("/inbox").forward(request2, response);
					
					else
						request2.getServletContext().getRequestDispatcher("/login").forward(request2, response);
				}
			} catch (ConfigException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		else if (context != null && context.equals("/reports")) {
			
			try {
				if (UserManager.getRole(session.getId()) > 1) {
					chain.doFilter(request, response);
				} else {
					request2.setAttribute("errorMessage", "Access Denied.");
					if (SessionManager.isSessionActive(session.getId()))
						request2.getServletContext().getRequestDispatcher("/inbox").forward(request2, response);
					
					else
						request2.getServletContext().getRequestDispatcher("/login").forward(request2, response);
				}
			} catch (ConfigException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else 
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
