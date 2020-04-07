package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import exception.ConfigException;
import manager.SessionManager;
import manager.UserManager;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/user/view")
public class ViewUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/user/view.jsp").forward(request, response);

        User user = null;

        HttpSession session = request.getSession();
        String email = SessionManager.getEmail(session.getId());
        User curUser = null;

        try {
            user = UserManager.getUser(request.getParameter("email"));
            curUser = UserManager.getUser(email);
        } catch (ConfigException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage","Error retrieving user data");
        }

        if (email.equals(user.getEmail()) || curUser.getPermissionLevel() > 1) {

            if(curUser.getPermissionLevel() > 1) {

            }
        } else {
            response.sendRedirect("/inbox");
        }


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
