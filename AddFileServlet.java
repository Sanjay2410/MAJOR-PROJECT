package action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;


@WebServlet("/AddFileServlet")
public class AddFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String uploadFilename = "";
		
		String time="";

		String path ="D:\\";

		boolean isUploaded = false;

		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if(isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				// Parse the request
				List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));

				for (FileItem item : items) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						//this will be true if file field is found in the List
						uploadFilename = item.getName();

						path=path+uploadFilename;

						try {
							File savedFile = new File(path);
							//out.print(savedFile.getAbsolutePath());
							item.write(savedFile);
							isUploaded = true;
						} catch(Exception e) {
							isUploaded = false;
							e.printStackTrace();
						}
					}
					else
					{
						time=item.getString();
					}
				}
			}//try
			catch (FileUploadException e) {
				e.printStackTrace();
			} 
			
			request.getSession().setAttribute("time",time);

			if(isUploaded){

				response.sendRedirect("viewuploadfile.jsp?filename="+uploadFilename);

			}
			else
			{
				response.sendRedirect("viewuploadfile.jsp?status=uploadfailed");
			}
		}
	}
}
