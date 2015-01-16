package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;


//import org.json.JSONArray;



import model.BoardGames;
import model.StoreInformation;
import model.service.StoreMemberService;



@WebServlet("/Jsontest")
public class Jsontest extends HttpServlet {
	
	public Jsontest(){
		super();
	}

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		StoreMemberService service = new StoreMemberService();
		String temp1 = request.getParameter("storeId");
		String temp2 = request.getParameter("type");
		JSONArray json = null;
		PrintWriter out = response.getWriter();
		if(temp1 != null && temp2 != null){
				int storeId = Integer.parseInt(temp1);
				int type = Integer.parseInt(temp2);
				List<StoreInformation> list = service.findStoreByStoreName("卡牌屋-台北店");
				for(StoreInformation bean : list){
					json = new JSONArray();
					System.out.println(bean.getStoreAddress());
				}
//				out.print(json);
			
		}
	}
}
