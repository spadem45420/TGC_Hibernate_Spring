package initialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.sql.DataSource;

@WebServlet(
		name="java_duke",
		urlPatterns={"/controller/GetImage"}
		)
public class GetImageFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		Connection conn = null;
		OutputStream os = null;
		InputStream is = null;
//		String type = request.getParameter("type");
		
		try {
			String id = request.getParameter("id");
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup(GlobalService.JNDI_DB_NAME);
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select imgFileName,memberImage from "
					+ "Member where username=?");
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				String fileName = rs.getString(1);
				is = rs.getBinaryStream(2);
				String mimeType = getServletContext().getMimeType(fileName);
				response.setContentType(mimeType);//設定輸出的type
//				response.addHeader("Content-Disposition","attachment;filename=" + fileName);//設定檔名
//				response.addHeader("Content-Disposition","attachment;filename=" 
//							+ new String( "中文".getBytes("Big5"), "ISO8859_1" ));//中文測試
				response.addHeader("Content-Disposition","attachment;filename="
							+ new String( fileName.getBytes(), "ISO8859_1" ));
				os = response.getOutputStream();
				
				int count = 0;
				byte[] bytes = new byte[1024];
				while ((count = is.read(bytes)) != -1) {
					os.write(bytes, 0, count);
				}
				
			}
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close(); // 一定要註解此行來執行本程式五次
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				os.close();
			}
		}
		
	}
}
