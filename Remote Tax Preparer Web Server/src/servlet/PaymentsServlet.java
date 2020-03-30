package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PaymentBean;
import manager.PaymentManager;

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
		Collection<PaymentBean> payments = PaymentManager.getPayments(request.getSession().getId());
		
		request.setAttribute("payments", payments);
		
		getServletContext().getRequestDispatcher("/WEB-INF/payments.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
