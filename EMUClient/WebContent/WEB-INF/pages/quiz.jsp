<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div>
<p>${question.id}</p>
<p>${question.text}</p>
	<c:forEach var="answer" items="${question.answers}" varStatus="myIndex">
    		<p>${answer.id}</p>
    		<p>${answer.value}</p>
    	</c:forEach>
    	<a href="quiz/question?id=${question.id}">Response</a>
</div>
</body>
</html>