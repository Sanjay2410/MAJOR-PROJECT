package action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class admin_login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			String uname=request.getParameter("username");
			String pass=request.getParameter("password");

			if(uname.equalsIgnoreCase("admin")&&pass.equalsIgnoreCase("admin")){
				response.sendRedirect("admin_page.jsp");
			}
			else{
				out.println("incorrect username or password ");
			}
		} 
		catch(Exception e){
			out.println(e);
		} finally {            
			out.close();
		}
	}    
}
