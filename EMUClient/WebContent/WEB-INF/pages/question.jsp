<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords" content="content slider, responsive image gallery, responsive image gallery, image slider, image fade, image rotator">
<link href="<c:url value="/resources/layout/styles/layout.css" />" rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/quiz.js" />"></script>
<div id="divQuestion" class="wrapper col4">
<br>
<br>
<br>
<h1 id="quizH1">Quiz</h1>
<br>
<br>
<div id ="quizQuestion"> 
<h2 style="margin-bottom: 2%;">Question</h2>
<p>${question.id}. ${question.text}</p>
	<c:forEach var="answer" items="${question.answers}" varStatus="myIndex">
		<input id="answer${answer.id}" type="radio" name="answer" value="${answer.value}"> ${answer.value} ${answer.correctAnswer} <br><p>
    	</c:forEach>
		<button id="responseQuiz" type="button" onclick="takeValue(${question.id});">Response</button>
</div>
</div>