<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	table{
		vertical-align: middle;
	}
	
	img{
		height:200px;
	}
</style>
<title>Show Game Info</title>
</head>
<body>
	<table border='1' bordercolor='#CCCCFF' width='500'>
		<tr><td align='center' valign='middle' >${info.boardGamesId}</td></tr>
		<tr><td align='center' valign='middle' ><img src="${pageContext.servletContext.contextPath}/controller/GetImage?id=${info.boardGamesId}"></td></tr>
		<tr><td align='center' valign='middle' >${info.boardGameName}</td></tr>
		<tr><td align='center' valign='middle' >${info.boardGameNumber}</td></tr>
		<tr><td align='center' valign='middle' >${info.boardGameExplan}</td></tr>
	</table>
</body>
</html>