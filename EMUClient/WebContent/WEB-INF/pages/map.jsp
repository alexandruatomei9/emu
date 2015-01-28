<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>EMU</title>
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
	var map;
	function initialize() {
		var myLat = "${myLat}";
		var myLong = "${myLong}";
		var myCity = "${city}";
		var myLatlng = new google.maps.LatLng(myLat, myLong);

		var mapOptions = {
			zoom : 3,
			center : myLatlng
		};
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);

		var pinColor = "0099FF";
		var pinImage = new google.maps.MarkerImage(
				"http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|"
						+ pinColor, new google.maps.Size(21, 34),
				new google.maps.Point(0, 0), new google.maps.Point(10, 34));

		var marker = new google.maps.Marker({
			position : myLatlng,
			map : map,
			title : myCity,
			icon : pinImage
		});

		var pins = '${pins}';
		$.each(JSON.parse(pins), function(idx, obj) {
			var myNewLatlng = new google.maps.LatLng(obj.latitude,
					obj.longitude);
			var newmarker = new google.maps.Marker({
				position : myNewLatlng,
				map : map,
				title : obj.name,
			});
			google.maps.event.addListener(newmarker, 'click', function() {
				  window.location.href = obj.resourceURI;
			});
		});
		
		

	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>
	<div class="wrapper col2">
		<div id="header">
			<div id="topnav">
				<ul>
					<li class="active last"><a href="#">Map</a><span>Nearby museums</span></li>
					<li><a href="quiz">Quiz</a><span>Test Your Knowledge</span></li>
					<li><a href="home">Homepage</a><span>Find out more</span></li>
				</ul>
			</div>
			<div id="logo">
				<h1>
					<a href="home">Emu</a>
				</h1>
				<p>Semantic Web-Enhanced Museum</p>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div class="wrapper col3">
	<center>
	<p>Your city: ${city}</p>
	</center>
	</div>
	<div class="wrapper col4">
	<center>
		<div id="map-canvas" style="height: 500px; width: 700px; padding-top:20px; padding-bottom:20px;"></div>
	</center>
	</div>
	<div class="wrapper col5">
		<div id="footer">
			<div id="newsletter">
				<h2>Stay In The Know !</h2>
				<p>Please enter your email to join our mailing list</p>
				<form action="#" method="post">
					<fieldset>
						<legend>News Letter</legend>
						<input type="text" value="Enter Email Here&hellip;"
							onfocus="this.value=(this.value=='Enter Email Here&hellip;')? '' : this.value ;" />
						<input type="submit" name="news_go" id="news_go" value="GO" />
					</fieldset>
				</form>
				<p>
					To unsubscribe please <a href="#">click here &raquo;</a>
				</p>
			</div>
			<div class="footbox">
				<h2>Lacus interdum</h2>
				<ul>
					<li><a href="#">Praesent et eros</a></li>
					<li><a href="#">Praesent et eros</a></li>
					<li><a href="#">Lorem ipsum dolor</a></li>
					<li><a href="#">Suspendisse in neque</a></li>
					<li class="last"><a href="#">Praesent et eros</a></li>
				</ul>
			</div>
			<div class="footbox">
				<h2>Lacus interdum</h2>
				<ul>
					<li><a href="#">Praesent et eros</a></li>
					<li><a href="#">Praesent et eros</a></li>
					<li><a href="#">Lorem ipsum dolor</a></li>
					<li><a href="#">Suspendisse in neque</a></li>
					<li class="last"><a href="#">Praesent et eros</a></li>
				</ul>
			</div>
			<div class="footbox">
				<h2>Lacus interdum</h2>
				<ul>
					<li><a href="#">Praesent et eros</a></li>
					<li><a href="#">Praesent et eros</a></li>
					<li><a href="#">Lorem ipsum dolor</a></li>
					<li><a href="#">Suspendisse in neque</a></li>
					<li class="last"><a href="#">Praesent et eros</a></li>
				</ul>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div class="wrapper col6">
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
</body>
</html>