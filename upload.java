package action;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import drivehq.FileUpload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pack.Dbconnection;
import pack.MailUtil;
import pack.encryption;
import service.AppService;

public class upload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String filepath="D:/";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			
			String filename=(String)request.getSession().getAttribute("filename");
			
			System.out.println("1 filename :\t"+filename);

			Connection con= Dbconnection.getConn();
			
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			SecretKey secretKey = keyGen.generateKey();
			
			byte[] b=secretKey.getEncoded();//encoding secretkey
			String skey=Base64.encode(b);
			
			HttpSession user=request.getSession(true);
			String owner=user.getAttribute("username").toString();
			
			System.out.println("2 :\t"+owner);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//get current date time with Date()
			Date date = new Date();
			String time=dateFormat.format(date); 

			encryption e=new encryption();
			
			Statement st1=con.createStatement();
			int dummy=0;
			
			String accessDate=(String)request.getSession().getAttribute("time");
			
			System.out.println("access date in upload :\t"+accessDate);
			
			java.sql.Date sd=new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(accessDate).getTime());
			
			String pattern="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			
			String accessKey="";
			
			for(int i=0;i<=8;i++)
			{
				accessKey=accessKey+pattern.charAt(new Random().nextInt(60));
			}
			
			st1.executeUpdate("insert into files values('"+dummy+"','"+filename+"','"+owner+"','"+time+"','"+sd+"','"+accessKey+"')");
			
			ResultSet rs=st1.executeQuery("select max(idfiles) from files");
			
			int fileID=0;
			
			while(rs.next()){

				fileID=rs.getInt(1);
			}
			
			System.out.println("3 :\t"+fileID);
			
			//====================================================
			
			Iterator<String> it=((ArrayList<String>)request.getSession().getAttribute("filelist")).iterator();
			
			while(it.hasNext())
			{
				String blockData=it.next();
				
				String encryptedtext=e.encrypt(blockData,secretKey);
				
				String sha1Hash=AppService.SHA1(blockData.toString());
				
				int blockId=0;
				
				Statement st=con.createStatement();
				ResultSet rt3=st.executeQuery("select * from blocks where sha1hash='"+sha1Hash+"'");
				
				while(rt3.next()){

					blockId=rt3.getInt(1);
				}
				
				System.out.println("7 :\t"+blockId);
				
				if(blockId==0)
				{
					st1.executeUpdate("insert into blocks values('"+dummy+"','"+encryptedtext+"','"+sha1Hash+"','"+skey+"')");
					
					ResultSet rs1=st1.executeQuery("select max(bid) from blocks");
					
					int newBlockID=0;
					
					while(rs1.next()){

						newBlockID=rs1.getInt(1);
					}
					
					String fileName="D:\\"+newBlockID+".txt";
					
					FileOutputStream fos=new FileOutputStream(fileName);
					fos.write(encryptedtext.getBytes());
					fos.close();
					
					FileUpload.fileUpload(fileName);
					
					st1.executeUpdate("insert into fileblocks values('"+dummy+"','"+fileID+"','"+newBlockID+"')");
				}
				else
				{
					st1.executeUpdate("insert into fileblocks values('"+dummy+"','"+fileID+"','"+blockId+"')");
				}
				
				String email="";
				
				Statement est=con.createStatement();
				ResultSet ers=est.executeQuery("select * from user_reg where username='"+owner+"'");
				
				while(ers.next()){

					email=ers.getString("mail");
				}
				
				try {
					MailUtil.mailsend("Access Key:"+accessKey,email,"Acess key for the File of:"+filename);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		response.sendRedirect("upload.jsp?status='uploded'");
	}
}
