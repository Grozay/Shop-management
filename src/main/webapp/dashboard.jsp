<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
</head>
<body>
    <h1>Dashboard</h1>
    <p>wellcome: ${username}!</p>
    <p>login date: ${loginDate}</p>
    <p><a href="${pageContext.request.contextPath}/logout">logout</a></p>
</body>
</html>