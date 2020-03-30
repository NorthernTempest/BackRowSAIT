package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ConfigException;
import manager.ParcelManager;

/**
 * Servlet implementation class SignDocumentServlet
 */
@WebServlet("/parcel/signDoc")
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 1) //0mb, 1mb, 1x 1mb
public class SignDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignDocumentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parcelID = request.getParameter("parcelID");
		
		try {
			request.setAttribute("parcel", ParcelManager.get(parcelID));
		} catch (ConfigException e) {
			request.setAttribute("errorMessage", "error, please try again");
			e.printStackTrace();
		}
		
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/signDocument.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
		
		//get signature
		//get pdf
		
		//check if theres a signature
		//if there isnt go back try again bit
		
		//smush them
		
		//make a parcel with preset message to taxPreparer saying its a signed document
		
		//let user know their signature has been confirmed
		//resend the parcel that had document to be signed attached, but this time without the req signature tag
		//delete the one with req signature tag
		
		
        getServletContext().getRequestDispatcher("/WEB-INF/parcel/signDocument.jsp").forward(request, response);
	}

}
