package pack;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {  


	public boolean sendMail(String[] recipients, String[] bccRecipients, String subject, String message1) {  

		try {  

			mailsend(message1,recipients[0],subject);

		}
		catch(Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public static void mailsend(String key, String email,String subject) throws MessagingException
	{

		System.out.println("email is :\t"+email);
		System.out.println("key is :\t"+key);
		System.out.println("subject is :\t"+subject);

		String host = "smtp.gmail.com";
		String from = "sanjaykumar.k2410@gmail.com";
		String pass = "xafcgofgvtfgujsz";

		Properties props = System.getProperties();

		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		String[] to = {email}; // added this line

		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] toAddress = new InternetAddress[to.length];

		// To get the array of addresses

		for( int i=0; i < to.length; i++ ) 
		{ 
			// changed from a while loop
			toAddress[i] = new InternetAddress(to[i]);
		}

		for( int i=0; i < toAddress.length; i++)
		{ 
			// changed from a while loop
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}

		message.setSubject(subject);
		message.setText("Signature\t:"+key);

		Transport transport = session.getTransport("smtp");

		transport.connect(host, from, pass);
		transport.sendMessage(message, message.getAllRecipients());

		transport.close();
	}

} 
