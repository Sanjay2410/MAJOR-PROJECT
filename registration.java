package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pack.Dbconnection;

public class registration extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
           String name=request.getParameter("name");
            String uname=request.getParameter("username");
            String pass=request.getParameter("password");
            String mail=request.getParameter("mail");
            String ph=request.getParameter("mobile");
            
          Connection con= Dbconnection.getConn();
          Statement st=con.createStatement();
           int i=st.executeUpdate("insert into user_reg (username,name,password,mail,phoneno)values('"+uname+"','"+name+"','"+pass+"','"+mail+"','"+ph+"')");
          con.close();
           if(i!=0){
              response.sendRedirect("user.jsp?status='registered'");
          }
        } 
        catch(Exception e){
            out.println(e);
        } finally {            
            out.close();
        }
    }
}
