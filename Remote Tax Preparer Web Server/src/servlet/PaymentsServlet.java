package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.TaxReturnBean;
import databaseAccess.PaymentDB;
import databaseAccess.SessionDB;
import databaseAccess.UserDB;
import domain.Payment;
import manager.PaymentManager;
import manager.TaxReturnManager;

/**
 * Servlet implementation class PaymentsServlet
 */
@WebServlet("/payments")
public class PaymentsServlet extends HttpServlet {
	private static final long serialVersionUID = -3307518268199908624L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Collection<PaymentBean> payments = PaymentManager.getPayments(request.getSession().getId());
		
		//request.setAttribute("payments", payments);
		
		request.setAttribute("user", UserDB.get(SessionDB.getEmail(request.getSession().getId())).copy());
		
		request.setAttribute("payments", PaymentManager.getPayments(request.getSession().getId()));
		
		Collection<TaxReturnBean> taxReturns = PaymentManager.getReturns(request.getSession().getId());
		
		request.setAttribute("taxReturns", taxReturns);
		
		getServletContext().getRequestDispatcher("/WEB-INF/payment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionID = request.getSession().getId();
		
		boolean sessionValid = true;
		
		String email = null;
		
		if(sessionID != null && !sessionID.equals(""))
			email = SessionDB.getEmail(sessionID);
		if(email == null) {
			email = request.getParameter("email");
			sessionValid = false;
		}
		String status = request.getParameter("status");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		double amount = Double.parseDouble(request.getParameter("amount"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date time = null;
		try {
			time = sdf.parse(request.getParameter("dateTime"));
			
			Calendar c = Calendar.getInstance(sdf.getTimeZone());
			
			c.setTime(time);
			
			c.setTimeZone(TimeZone.getDefault());
			
			time = c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year = Integer.parseInt(request.getParameter("taxYear"));
		
		PaymentDB.insert(new Payment(id, email, year, type, amount, time));
		
		if(sessionValid)
			doGet(request, response);
		else
			response.sendRedirect("/login");
	}
}
