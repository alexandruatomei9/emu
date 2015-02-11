<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>EMU</title>
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords"
	content="content slider, responsive image gallery, responsive image gallery, image slider, image fade, image rotator">
<link
	href="<c:url value="/resources/layout/styles/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/quiz.js" />"></script>
<script>
	var CCOUNT = 20;

	var t, count;

	function cddisplay() {
		// displays time in span
		document.getElementById('timespan').innerHTML = count;
	};

	function countdown() {
		// starts countdown
		if(count<5){
			$("#timespan").addClass("red");
		} else {
			$("#timespan").removeClass("red");

		}
		cddisplay();
		if (count == 0) {
			timeExpired();
		} else {
			count--;
			t = setTimeout("countdown()", 1000);
		}
	};

	function cdpause() {
		// pauses countdown
		clearTimeout(t);
	};

	function cdreset() {
		// resets countdown
		cdpause();
		count = CCOUNT;
		cddisplay();
	};

	function timeExpired() {
		var questId = $("#hiddenId").text();
		takeValue(questId, null);
	}
	var i = 0;
	function takeValue(questionId, answerId) {
		var rate_value = answerId;
		var object = {
			id : questionId,
			answerId : rate_value
		};
		cdreset();
		jQuery.ajax("quiz/question/", {
			type : "GET",
			data : object,
			success : function(response) {
				$("#divQuestion").replaceWith(response);
				if (i < 9)
					countdown();
				i++;
			}
		});
	};

	$(document).ready(function() {
		$("#divQuestion").addClass("loading");
		$.ajax({
			type : "GET",
			url : "quiz/generate",
			success : function(response) {
				$("#divQuestion").replaceWith(response);
				$("#divQuestion").removeClass("loading");
				cdreset();
				countdown();
			}
		});
	});
</script>
</head>
</html>
<body id="top" onload="cdreset()">
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
							<a class="navbar-brand" href="./"><img
								src="<c:url value="/resources/layout/styles/images/emu.jpg" />"
								class="img-brand" />Emu</a>
						</div>

						<div class="collapse navbar-collapse"
							id="bs-example-navbar-collapse-6">
							<ul class="nav navbar-nav">
								<li><a href="./">Home</a></li>
								<li><a href="./map">Map</a></li>
								<li class="active"><a href="quiz">Quiz</a></li>
							</ul>
						</div>
					</div>
				</nav>
				<br class="clear" />
			</div>
		</div>

		<div class="wrapper col3">
			<div class="timespan">Time left <span id="timespan"></span></div>
		</div>
		<div id="divQuestion" class="wrapper col4" style="height: 80%;">

		</div>
		<div class="wrapper row col5">
			<div id="copyright">
				<p class="fl_left">Emu - Semantic web-enhanced museum</p>
				<p class="fl_right">
					WADE - 2015</a>
				</p>
				<br class="clear" />
			</div>
		</div>
	</div>
	</div>
</body>
</html>