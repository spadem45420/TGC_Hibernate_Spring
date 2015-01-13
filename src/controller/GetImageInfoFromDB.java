package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.sql.DataSource;

import model.BoardGames;
import model.Interface.BoardGamesDAO_Interface;
import model.service.GlobalService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet(
		name="java_duke2",
		urlPatterns={"/controller/GetImageInfo"}
		)
public class GetImageInfoFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"model-config1-DriverManagerDataSource.xml");
		BoardGamesDAO_Interface dao5 = 
				(BoardGamesDAO_Interface) context.getBean("BoardGamesDAO");
		String id = request.getParameter("id");
		try {
			int gameId = Integer.parseInt(id);
			BoardGames game = dao5.findByPrimeKey(gameId);
			Map<String, String> info = new HashMap<>();
			request.setAttribute("info", info);
			info.put("boardGamesId", String.valueOf(game.getBoardGamesId()));
			info.put("boardGameName", game.getBoardGameName());
			info.put("boardGameNumber", game.getBoardGameNumber());
			info.put("boardGameExplan", game.getBoardGameExplan());
			
			if(!info.isEmpty()){
				RequestDispatcher rd = request
						.getRequestDispatcher("/Store/showGameInfo.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
