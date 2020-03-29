package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Parcel;
import exception.ConfigException;
import manager.ParcelManager;

/**
 * Servlet implementation class SignDocumentServlet
 */
@WebServlet("/SignDocumentServlet")
public class SignDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignDocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Parcel parcel = null;
        try {
            parcel = ParcelManager.get(request.getParameter("parcel"));
            request.setAttribute("parcel", parcel);
        } catch (ConfigException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving document");
        } catch (NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving document");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving document");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/parcel/signDocument.jsp").forward(request, response);
	}

}
