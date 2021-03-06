package Bank;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabaseManager.Manager;
import UserPojo.UserPojo;

@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserPojo user = new UserPojo();
		Calendar calendar = Calendar.getInstance();
		System.out.println("CALENDAR : "+calendar.getTimeInMillis());
		Date date = new Date(calendar.getTimeInMillis());
		System.out.println("DATE : "+date);
		System.out.println("HELLO");
		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));
		user.setAge(Integer.parseInt(request.getParameter("age")));
		user.setStartAmt(request.getParameter("startAmt"));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		Manager manager = new Manager();
		if(manager.addUser(user)) {
			ServletContext context = super.getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/LoginJSP.jsp");
			dispatcher.forward(request, response);
		} //end of if condition
		else {
			ServletContext context = super.getServletContext();
			RequestDispatcher dispatcher =  context.getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request, response);
		} //end of else;
		
	} //end of doPost;

} //end of CreateAccountServlet class
