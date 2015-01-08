package StoreMember;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.StoreMember;
import initialize.*;
import StoreMember.StoreMemberService;


@WebServlet(urlPatterns = { "/RegisterServletMB" })
@MultipartConfig(location = "E:\\temp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
public class RegisterServletSMB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 準備存放錯誤訊息的Map物件
		request.setCharacterEncoding("UTF-8"); // 文字資料轉內碼
		Map<String, String> errorMsg = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		// 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		session.setAttribute("MsgOK", msgOK); // 顯示常訊息

		String storeUsername = "";
		String storePswd = "";
		String storePswd1 = "";
		String storePswd2 = "";
		String storePhone = "";
		String imgFileName = "";
		String storeEmail = "";
		String storeWebsite = "";
		byte[] storeImage = null;
		InputStream is = null;
		long sizeInBytes = 0;

		Collection<Part> parts = request.getParts(); // 取出HTTP multipart
														// request內所有的parts
		//GlobalService.exploreParts(parts, request);
		// 由parts != null來判斷此上傳資料是否為HTTP multipart request
		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();
				String value = request.getParameter(fldName);
				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("storeUsername")) {
						storeUsername = value;
					} else if (fldName.equals("storePswd1")) {
						storePswd1 = value;
					} else if (fldName.equalsIgnoreCase("storePswd2")) {
						storePswd2 = value;
					} else if (fldName.equalsIgnoreCase("storePhone")) {
						storePhone = value;
					} else if (fldName.equalsIgnoreCase("storeEmail")) {
						storeEmail = value;
					} else if (fldName.equalsIgnoreCase("storeWebsite")) {
						storeWebsite = value;
					}
				} else {
					imgFileName = GlobalService.getFileName(p); // 此為圖片檔的檔名
					if (imgFileName != null && imgFileName.trim().length() > 0) {
						sizeInBytes = p.getSize();
						is = p.getInputStream();
					} else {
						errorMsg.put("errPicture", "必須挑選圖片檔");
					}
				}
			}
			// 2. 進行必要的資料轉換
			// 3. 檢查使用者輸入資料
			if (storeUsername == null || storeUsername.trim().length() == 0) {
				errorMsg.put("errorUserNameEmpty", "帳號欄必須輸入");
			}
			if (storePswd1 == null || storePswd1.trim().length() == 0) {
				errorMsg.put("errorPswd1Empty", "密碼欄必須輸入");
			}
			if (storePswd2 == null || storePswd2.trim().length() == 0) {
				errorMsg.put("errorPswd2Empty", "密碼確認欄必須輸入");
			}
			if (storePswd1.trim().length() > 0 && storePswd2.trim().length() > 0) {
				if (!storePswd1.trim().equals(storePswd2.trim())) {
					errorMsg.put("errorPswd2Empty", "密碼欄必須與確認欄一致");
					errorMsg.put("errorPswd1Empty", "*");
				} else {
					storePswd = storePswd2.trim();
				}
			}
			if (storePhone == null || storePhone.trim().length() == 0) {
				errorMsg.put("errorEmail", "電話欄必須輸入");
			}
			if (storeEmail == null || storeEmail.trim().length() == 0) {
				errorMsg.put("errorEmail", "電子郵件欄必須輸入");
			}
		} else {
			errorMsg.put("errTitle", "此表單不是上傳檔案的表單");
		}
		// 如果有錯誤
		if (!errorMsg.isEmpty()) {
			// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisterMember.jsp");
			rd.forward(request, response);
			return;
		}
		try {
			// 4. 進行Business Logic運算

			// 4.1.檢查帳號是否已經存在
			// 4.2.儲存會員的資料
			StoreMemberService service = new StoreMemberService();
			StoreMember bean = new StoreMember();
			if (service.select(bean) != null) {
				errorMsg.put("errorUserNameDup", "此帳號已存在，請換個帳號");
			} else {
				bean = new StoreMember();
				bean.setStoreUsername(storeUsername);
				bean.setStorePswd(storePswd.getBytes());
				bean.setStoreJoinDate(new java.util.Date());
				bean.setStorePhone(storePhone);
				bean.setImgFileName(imgFileName);
				bean.setStoreEmail(storeEmail);
				bean.setStoreWebsite(storeWebsite);
				try {
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					int nRead;
					byte[] data = new byte[1024];
					while ((nRead = is.read(data, 0, data.length)) != -1) {
					  buffer.write(data, 0, nRead);
					  buffer.flush();
					}
					data = buffer.toByteArray();
					storeImage = data;
					is.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bean.setStoreImage(storeImage);
				// 將MemberBean bean立即寫入Database
				String result = service.register(bean);
				if (result != null) {
					msgOK.put("InsertOK",
							"<Font color='red'>新增成功，請開始使用本系統</Font>");
					session.setAttribute("StoreUsername", bean.getStoreUsername());
					response.sendRedirect("Index.jsp");
//					RequestDispatcher rd = request
//							.getRequestDispatcher("ShowMyMemberData.jsp");
//					rd.forward(request, response);
					return;
				} else {
					errorMsg.put("errorUserNameDup",
							"新增此筆資料有誤(RegisterServletMB)");
				}
			}
			// 5.依照 Business Logic 運算結果來挑選適當的畫面
			if (!errorMsg.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				RequestDispatcher rd = request
						.getRequestDispatcher("RegisterMember.jsp");
				rd.forward(request, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.put("errorIDDup", e.getMessage());
			RequestDispatcher rd = request
					.getRequestDispatcher("RegisterMember.jsp");
			rd.forward(request, response);
		}
	}
}
