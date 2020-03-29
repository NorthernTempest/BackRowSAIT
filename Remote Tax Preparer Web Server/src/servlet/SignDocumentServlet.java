package servlet;

import domain.Document;
import domain.Parcel;
import exception.ConfigException;
import manager.DocumentManager;
import manager.ParcelManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signDoc")
public class SignDocumentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
