package servlet;

import domain.User;
import exception.ConfigException;
import manager.SessionManager;
import manager.UserManager;
import util.cesar.Debugger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        User user;

        HttpSession session = request.getSession();
        String email = SessionManager.getEmail(session.getId());
        User curUser;


        try {
            user = UserManager.getUser(request.getParameter("email"));
            curUser = UserManager.getUser(email);
            if (email.equals(user.getEmail()) || curUser.getPermissionLevel() > 1) {
                request.setAttribute("user",user);
                request.setAttribute("role", curUser.getPermissionLevel());
                Debugger.log("User: "+user.getEmail());
            } else {
                response.sendRedirect("/inbox");
            }
        } catch (ConfigException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage","Error retrieving user data");
        } catch (NullPointerException e) {
            response.sendRedirect("/inbox");
        }


        getServletContext().getRequestDispatcher("/WEB-INF/user/view.jsp").forward(request, response);



    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String email = request.getParameter("email");

        if(action.equals("deactivate")) {
            if (email != null && UserManager.userExists(email)) {
                UserManager.adminDeleteAccount(email, request.getSession().getId());
                request.setAttribute("successMessage", "Account "+email+" deactivated.");
            } else {
                request.setAttribute("errorMessage", "Error processing request. Could not deactivate "+email);
            }
        }
        if(action.equals("activate")) {
            if (email != null && UserManager.userExists(email)) {
                UserManager.adminRestoreAccount(email, request.getSession().getId());
                request.setAttribute("successMessage", "Account "+email+" activated.");
            } else {
                request.setAttribute("errorMessage", "Error processing request. Could not activate "+email);
            }
        }

        doGet(request, response);
    }

}
