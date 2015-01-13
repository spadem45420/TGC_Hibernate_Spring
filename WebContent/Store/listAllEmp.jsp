<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="controller.*"%>
<%@ page import="model.*"%>
<%@ page import="model.service.*"%>
<%@ page import="model.Interface.*"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	ApplicationContext context = new ClassPathXmlApplicationContext(
			"model-config1-DriverManagerDataSource.xml");
	StoreMemberDAO_Interface dao = (StoreMemberDAO_Interface) context
			.getBean("StoreMemberDAO");
	StoreInformationDAO_Interface dao2 = (StoreInformationDAO_Interface) context
			.getBean("StoreInformationDAO");
	StoreMemberService service = new StoreMemberService();
// 	List<EmpVO> list = empSvc.getAll();
// 	Set<BoardGames> list = service.findStoreById(2).getBoardGameses();
	List<BoardGames> list = service.findGamesByStoreId(2);
	pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>桌遊顯示</title>
<style>
	img{
		height:200px;
	}
</style>
</head>
<body bgcolor='white'>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
<table border='1' bordercolor='#CCCCFF' width='800'>
<!-- 	<tr> -->
<!-- 		<th>桌遊圖片</th> -->
<!-- 	</tr> -->
	<%@ include file="pages/page1.file" %>
	<tr><!--將修改的那一筆加入對比色而已-->
	<c:forEach var="BoardGames" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
<%-- 		<tr align='center' valign='middle' ${(BoardGames.boardGamesId==param.boardGamesId) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已--> --%>
<%-- 			<td><img src="${pageContext.servletContext.contextPath}/controller/GetImage?id=${BoardGames.boardGamesId}"></td> --%>
<!-- 		</tr> -->
		<%n++;%>
			<td align='center' valign='middle' ${(BoardGames.boardGamesId==param.boardGamesId) ? 'bgcolor=#CCCCFF':''}>
				<a href="${pageContext.servletContext.contextPath}/controller/GetImageInfo?id=${BoardGames.boardGamesId}">
					<img src="${pageContext.servletContext.contextPath}/controller/GetImage?id=${BoardGames.boardGamesId}">
				</a>
			<br>
			${BoardGames.boardGameName}
			</td>
		<%if(n%5==0){%>
			</tr><tr>
		<%}%>
	</c:forEach>
	</tr>
</table>
<%@ include file="pages/page2.file" %>

<br>本網頁的路徑:<br><b>
   <font color=blue>request.getServletPath():</font> <%= request.getServletPath()%><br>
   <font color=blue>request.getRequestURI(): </font> <%= request.getRequestURI()%> </b>
</body>
</html>
