<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>EMU</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords" content="content slider, responsive image gallery, responsive image gallery, image slider, image fade, image rotator">
<link href="<c:url value="/resources/layout/styles/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/layout.css" />" rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/quiz.js" />"></script>
</head>
</html>
<body id="top">
<div class="container">
<div class="wrapper col2">
  <div id="header">
    <nav class="navbar navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-6">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="./"><img src="<c:url value="/resources/layout/styles/images/emu.jpg" />" class="img-brand"/>Emu</a>
					</div>

					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-6">
						<ul class="nav navbar-nav">
							<li><a href="./">Home</a></li>
							<li><a href="javascript:showlocation()">Map</a></li>
							<li class="active"><a href="quiz">Quiz</a></li>
						</ul>
					</div>
				</div>
			</nav>
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
<div class="wrapper row col6">
			<div id="copyright">
				<p class="fl_left">
					Copyright &copy; 2014 - All Rights Reserved - <a href="#">Domain
						Name</a>
				</p>
				<p class="fl_right">
					Template by <a target="_blank" href="http://www.os-templates.com/"
						title="Free Website Templates">OS Templates</a>
				</p>
				<br class="clear" />
			</div>
		</div>
</div>
</body>
</html>