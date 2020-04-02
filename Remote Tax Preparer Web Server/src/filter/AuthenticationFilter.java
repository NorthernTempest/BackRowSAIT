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

import manager.SessionManager;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AuthenticationFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpSession session = request2.getSession();
		String context = request2.getServletPath();

		request2.setAttribute("role", SessionManager.getPermissionLevel(session.getId()));

		if (SessionManager.isSessionActive(session.getId())) {
			if (context != null && ( context.equals("/login"))) {
				String action = request2.getParameter("action");

				if (action != null && action.equals("logout"))
					chain.doFilter(request, response);
				else
					((HttpServletResponse) response).sendRedirect("inbox");
			} else if (context.equals("/recover")) {
				((HttpServletResponse) response).sendRedirect("/inbox");
			} else
				chain.doFilter(request, response);
		} else if (context != null && (context.equals("/login") || context.equals("/recover")
				|| context.equals("/register") || (context.equals("/payments") && request2.getMethod().equals("POST"))))
			chain.doFilter(request, response);
		else
			((HttpServletResponse) response).sendRedirect("/login");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
