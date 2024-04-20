package drivehq;

import java.io.File;

public class FileDownload {

	public static synchronized boolean fileDownload(String filepath)
	{
		boolean isDownloaded=false;
		
		MyFTPClient client=new MyFTPClient();
		
		client = new MyFTPClient();
		
		try {
			
			boolean isConnected=client.ftpConnect("ftp.drivehq.com","Sanjay2410","Sanjay@2410",21);
			
			if(isConnected)
			{
				if(client.ftpDownload(new File(filepath).getName(), filepath))
				{
					System.out.println("success");
					isDownloaded=true;
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
		
		return isDownloaded;
	}
}
