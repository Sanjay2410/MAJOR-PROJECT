package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pack.Dbconnection;

public class user_login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try {
			String uname=request.getParameter("username");
			String pass=request.getParameter("password");

			Connection con= Dbconnection.getConn();
			Statement st=con.createStatement();
			ResultSet rt=st.executeQuery("select * from user_reg where username='"+uname+"'");
			if(rt.next()){
				String p=rt.getString("password");
				String name=rt.getString("name");

				if(pass.equalsIgnoreCase(p)){
					HttpSession user=request.getSession(true);
					user.setAttribute("name", name);
					user.setAttribute("username", uname);
					response.sendRedirect("user_page1.jsp");
				}
				else{
					out.println("incorrect password");
				}
			}
			else{
				out.println("Incorrect username");
			}
		}
		catch(Exception e){
			out.println(e);
		} finally {            
			out.close();
		}
	}
}
