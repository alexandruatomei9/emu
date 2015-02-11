<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height: 100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMU</title>
<link href="<c:url value="/resources/layout/styles/layout.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script>
function showlocation() {
	navigator.geolocation.getCurrentPosition(callback);
}
function callback(position) {
	var lat = position.coords.latitude;
	var lon = position.coords.longitude;
	var latLng = "latitude=" + lat + "&longitude=" + lon;
	console.log(latLng);
	if (latLng != 'undefined') {
		window.location = 'map/generate?' + latLng;
	}
}

$(document).ready(function(){
	$('body').addClass("loading");
	showlocation();
});

</script>
</head>
<body style="height: 100%;">

</body>
</html>