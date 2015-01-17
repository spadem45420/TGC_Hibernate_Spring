package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.simple.JSONArray;


//import org.json.JSONArray;




import model.BoardGames;
import model.StoreInformation;
import model.service.StoreMemberService;



@WebServlet("/GetGamesJson")
public class GetGamesJson extends HttpServlet {
	
	public GetGamesJson(){
		super();
	}

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		StoreMemberService service = new StoreMemberService();
		String temp1 = request.getParameter("storeId");
		String temp2 = request.getParameter("type");
		JSONArray json = new JSONArray();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(temp1 != null && temp2 != null){
				int storeId = Integer.parseInt(temp1);
				int type = Integer.parseInt(temp2);
				List<BoardGames> list = service.findByType(storeId, type);
				for(BoardGames bean : list){
					System.out.println(bean.getBoardGameName());
					json.add(bean.getBoardGameName());
				}
				out.print(json);
		}
	}
}
