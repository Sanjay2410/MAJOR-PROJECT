package action;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import pack.Dbconnection;
import pack.decryption;

public class download extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		try {
			
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey secretKey = keyGen.generateKey();
			
			String fileid=request.getParameter("fileid");
			String filename=request.getParameter("filename");
		
			Connection con= Dbconnection.getConn();
			Statement st=con.createStatement();
			Statement st1=con.createStatement();

			HttpSession user=request.getSession();
			String uname=user.getAttribute("username").toString();   
			
			List<Integer> fileIds=new ArrayList<Integer>();
	
			ResultSet rt=st.executeQuery("select * from fileblocks where fileid='"+fileid+"'");

			while(rt.next()){
				
				fileIds.add(rt.getInt("blockid"));
			}
			
			Iterator<Integer> it=fileIds.iterator();
			
			String content="";
			
			while(it.hasNext())
			{
				Integer blockid=it.next();
				
				System.out.println("block id :\t"+blockid);
				
				ResultSet rs=st.executeQuery("select * from blocks where bid='"+blockid+"'");

				while(rs.next()){
					
					String data="";
					
					data=rs.getString("content");
					
					String dec=new decryption().decrypt(data.toString(),rs.getString("blockkey"));
					
					System.out.println("decryption block :\t"+dec);
					
					content=content+dec;
				}
			}
			
			System.out.println("total data is :\t"+content);
			
			response.setHeader("Content-Disposition","attachment;filename=\""+filename+"\"");        
			out.write(content);

			Calendar cal=Calendar.getInstance();
			DateFormat dateformat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");          
			String time=dateformat.format(cal.getTime());
			System.out.println("download time:"+time);

			st1.executeUpdate("insert into downloads (filename,username,time)values('"+filename+"','"+uname+"','"+time+"')");

		}
		catch(Exception e){
			e.printStackTrace();
		} finally {            
			out.close();
		}
	}	
}
