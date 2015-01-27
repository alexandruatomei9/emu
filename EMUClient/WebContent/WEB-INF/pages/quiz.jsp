<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>EMU</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords" content="content slider, responsive image gallery, responsive image gallery, image slider, image fade, image rotator">
<link href="<c:url value="/resources/layout/styles/layout.css" />" rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/quiz.js" />"></script>
</head>
<body id="quizBody">
</body>
</html>
<body id="top">
<div class="wrapper col2">
  <div id="header">
    <div id="topnav">
      <ul>
        <li class="last"><a href="#" onclick="javascript:getMap()">Map</a><span>Test Text Here</span></li>
        <li><a href="full-width.jsp">Full Width</a><span>Test Text Here</span></li>
        <li class="active"><a href="quiz">Quiz</a><span>Test Text Here</span></li>
        <li><a href="index.jsp">Homepage</a><span>Test Text Here</span></li>
      </ul>
    </div>
    <div id="logo">
      <h1><a href="index.jsp">Emu</a></h1>
      <p>Semantic Web-Enhanced Museum</p>
    </div>
    <br class="clear" />
  </div>
</div>
<div class="wrapper col3">
  	<div id="intro">
  		
  	</div>
</div>
<div id="divQuestion" class="wrapper col4">
<br>
<br>
<br>
<h1 id="quizH1">Quiz</h1>
<br>
<br>
<div id ="quizQuestion"> 
<h2 style="margin-bottom: 2%;">Question</h2>
<p>${question.id + 1}. ${question.text}</p>
	<c:forEach var="answer" items="${question.answers}" varStatus="myIndex">
		<input id="answer${answer.id}" type="radio" name="answer" value="${answer.value}"> ${answer.value} ${answer.correctAnswer} <br><p>
    	</c:forEach>
		<button id="responseQuiz" type="button" onclick="takeValue(${question.id});">Response</button>
</div>
</div>
<footer class="footer">
<div class="wrapper col6">
  <div id="copyright">
    <p class="fl_left">Copyright &copy; 2014 - All Rights Reserved - <a href="#">Domain Name</a></p>
    <p class="fl_right">Template by <a target="_blank" href="http://www.os-templates.com/" title="Free Website Templates">OS Templates</a></p>
    <br class="clear" />
  </div>
</div>
</footer>
</body>
</html>