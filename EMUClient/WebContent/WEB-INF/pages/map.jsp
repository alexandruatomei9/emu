<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
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
	<center>
		<div id="map-canvas" style="height: 500px; width: 700px"></div>
		<p>Your city: ${city}</p>
	</center>
</body>
</html>