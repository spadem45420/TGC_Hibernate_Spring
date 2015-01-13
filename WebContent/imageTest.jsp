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
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=2'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=3'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=4'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=5'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=6'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=7'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=8'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=9'>
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=10'>
</body>
</html>