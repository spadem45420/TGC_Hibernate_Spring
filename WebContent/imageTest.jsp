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
<!-- �^���ɦW -->
<%-- <img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=Bob4'> --%>

<!-- �����ɦW --><!-- id="username�W��"�A�n���T�{��Ʈw�����S����Ϥ� -->
<img src='${pageContext.servletContext.contextPath}/controller/GetImage?id=1'>
</body>
</html>