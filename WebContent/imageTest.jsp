<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>imageTest</title>
<style>
	img{
		height: 200px;
	}
</style>
</head>
<body>
<!-- 英文檔名 -->
<%-- <img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=Bob4'> --%>

<!-- 中文檔名 --><!-- id="username名稱"，要先確認資料庫中有沒有放圖片 -->
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=1'>
</body>
</html>