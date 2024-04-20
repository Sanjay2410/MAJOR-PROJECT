package action;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pack.Dbconnection;
import pack.encryption;


public class updating extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			String[] s=request.getQueryString().split(",");
			String id=s[0];
			String fname=s[1];

			String content=request.getParameter("content");

			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey secretKey = keyGen.generateKey();
			System.out.println("secret key:"+secretKey);

			encryption e=new encryption();
			String encryptedtext=e.encrypt(content,secretKey);
			byte[] b=secretKey.getEncoded();//encoding secretkey
			String skey=Base64.encode(b);
			System.out.println("converted secretkey to string:"+skey);

			Connection con=Dbconnection.getConn();
			Statement st=con.createStatement();

			HttpSession user=request.getSession();
			String uname=user.getAttribute("username").toString();

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			String time=dateFormat.format(date);

			st.executeUpdate("insert into updates (filename, username, time)values('"+fname+"','"+uname+"','"+time+"')");

			int i= st.executeUpdate("update files set content='"+encryptedtext+"',file_key='"+skey+"' where idfiles='"+id+"'");
			
			if(i!=0){
				response.sendRedirect("update.jsp?updated='yes'");
			}
			else{
				out.println("error while updating");
			}

		}
		catch(Exception e){
			out.println(e);
		}
		finally {            
			out.close();
		}
	}
}
