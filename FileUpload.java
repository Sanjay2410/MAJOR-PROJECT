package drivehq;

import java.io.File;

public class FileUpload {

	public static synchronized boolean fileUpload(String filePath)
	{
		boolean isUploaded=false;
		
		MyFTPClient client=new MyFTPClient();
		
		client = new MyFTPClient();
		
		try {
			
			boolean isConnected=client.ftpConnect("ftp.drivehq.com","Sanjay2410","Sanjay@2410",21);
			
			if(isConnected)
			{
				if(client.ftpUpload(filePath,new File(filePath).getName(),"cloudfiles"))
				{
					System.out.println("success");
					isUploaded=true;
				}
				else
				{
					System.out.println("failed");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		client.ftpDisconnect();
		
		return isUploaded;
	}
}
