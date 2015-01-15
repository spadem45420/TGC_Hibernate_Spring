package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GroupRoom;
import model.StoreInformation;
import model.Interface.GroupRoomDAO_Interface;
import model.Interface.StoreInformationDAO_Interface;
import model.service.*;

import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet(
		name="map",
		urlPatterns={"/controller/GetMap"}
		)
public class MapServlet extends HttpServlet{
	GroupService service = null;
	GetXY convert = null;
	GroupRoomDAO_Interface grdao = null;
	StoreInformationDAO_Interface sdao = null;
	public MapServlet() {
		service = new GroupService();
		convert = new GetXY();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		grdao = (GroupRoomDAO_Interface) context.getBean("GroupRoomDAO");
		sdao = (StoreInformationDAO_Interface) context
				.getBean("StoreInformationDAO");
	}
//	public String getMaps(){
//		Map map = new HashMap();
//		List<JSONObject> listmapjson = new ArrayList<JSONObject>();
//		List<GroupRoom> list=service.getAll();
//		JSONObject mapjson = new JSONObject();
//		for(int i=0;i<list.size();i++){	
//			JSONObject ddd=new JSONObject();
//			ddd.put("workName", list.get(i).getWorkName());
//			ddd.put("natureWork", list.get(i).getNatureWork());	
//			ddd.put("wrorkDetailed", list.get(i).getWrorkDetailed());	
//			ddd.put("wrorkSalary", list.get(i).getWorkSalary());	
//			ddd.put("wrorkLongitude", list.get(i).getWorkLongitude());	
//			ddd.put("wrorkLatitude", list.get(i).getWorkLatitude());	
//			ddd.put("phone", list.get(i).getCompany_WorkContactInformationVO().getPhone());
//			ddd.put("companyName", list.get(i).getCompanyAccountVO().getCompanyName());	
//			listmapjson.add(ddd);
////			json.add();
//		}
//		mapjson.put("map",listmapjson);
//		System.out.println(listmapjson);
//		for(int i=0;i<mapjson.size();i++){	
//		System.out.println(mapjson);
//		}
//		HttpServletRequest request = ServletActionContext.getRequest(); // 取得HttpServletRequest
//		HttpSession session = request.getSession();// 取得HttpSession
//		session.setAttribute("listmapjson", listmapjson); 
//		session.setAttribute("mapjson", mapjson); 
//		return "success";
//	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String id = request.getParameter("id");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map map = new HashMap();
		JSONObject mapjson = new JSONObject();
		List<JSONObject> listmapjson = new ArrayList<JSONObject>();
		List<GroupRoom> list = service.getAll();
		for(GroupRoom bean : list){
			String storename = bean.getStoreName();
			String roomname = bean.getGroupRoomName();
			List<StoreInformation> store = sdao.findByStoreName(storename);
			for(StoreInformation bean2 : store){
				String StoreName = bean2.getStoreName();
				String Address = bean2.getStoreAddress();
				Double X = convert.getX(Address);
				Double Y = convert.getY(Address);
				int Upper = bean.getGroupUpperLimit();
				int StoreId = bean2.getStoreId();
				String EndTime = sdf.format(bean.getReserveGroupEndTime());
				
				
				JSONObject json=new JSONObject();
				json.put("workName", roomname);
				json.put("wrorkLongitude", X);
				json.put("wrorkLatitude", Y);
				json.put("StroeName", StoreName);
				json.put("Upper", Upper);
				json.put("EndTime", EndTime);
				json.put("StoreId", StoreId);
				listmapjson.add(json);
			}
		}
//		JSONObject mapjson = new JSONObject();
//		for(int i=0;i<list.size();i++){	
//			JSONObject json=new JSONObject();
//			json.put("workName", "卡牌屋");
//			json.put("wrorkLongitude", 25.0594851);
//			json.put("wrorkLatitude", 121.49462370000003);
//			listmapjson.add(json);
////			json.add();
//		}
		mapjson.put("map",listmapjson);
		System.out.println(listmapjson);
		for(int i=0;i<mapjson.size();i++){	
		System.out.println(mapjson);
		}
		HttpSession session = request.getSession();// 取得HttpSession
		session.setAttribute("listmapjson", listmapjson); 
		session.setAttribute("mapjson", mapjson);
//		response.sendRedirect("findjodmap.jsp");
		RequestDispatcher rd = request
				.getRequestDispatcher("/findRoomMap.jsp");
		rd.forward(request, response);
	}
	
	public static void main(String[] args) {
		
	}
}
