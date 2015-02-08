<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>EMU</title>
<!-- StyleSheets -->
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/layout/styles/jquery.dataTables.css" />"
	rel="stylesheet">

<!-- Scripts -->
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>

<script>
	var myLat = "${myLat}";
	var myLong = "${myLong}";
	var myCity = "${city}";
	var dataTableSource = "";
	var map;
	var directionsDisplay;
	var directionsService = new google.maps.DirectionsService();
	var markers = [];
	var museumListRequest;
	$(function() {
		$("#slider")
				.slider(
						{
							value : 100,
							min : 100,
							max : 3000,
							step : 100,
							slide : function(event, ui) {
								$("#radius").val(" " + ui.value + "km");
								deleteMarkers();
								if (typeof museumListRequest !== 'undefined'
										&& museumListRequest.readyState > 0
										&& museumListRequest.readyState < 4) {
									museumListRequest.abort();
								}
								museumListRequest = $
										.ajax({
											type : "GET",
											url : "http://localhost:8080/EMUServer/rest/map/getNearbyMuseums",
											data : {
												'latitude' : myLat,
												'longitude' : myLong,
												'radius' : ui.value
											},
											success : handleSuccessMuseumList
										});
							}
						});
		$("#radius").val(" " + $("#slider").slider("value") + "km");
	});

	function handleSuccessMuseumList(response) {
		var code = response.code;
		if (code == "OK") {
			var museumList = response.response;
			updateDataTable(museumList);
			for ( var i in museumList) {
				var latitude = museumList[i].latitude;
				var longitude = museumList[i].longitude;
				var resourceURI = museumList[i].resourceURI;
				var name = museumList[i].name;
				var location = new google.maps.LatLng(latitude, longitude);
				addMarker(location, name, resourceURI);
			}
			setAllMap(map);
		}
	}

	function initialize() {
		directionsDisplay = new google.maps.DirectionsRenderer();
		var myLatlng = new google.maps.LatLng(myLat, myLong);

		var mapOptions = {
			zoom : 3,
			center : myLatlng
		};
		map = new google.maps.Map(document.getElementById('googleMap'),
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
		directionsDisplay.setMap(map);
	}
	google.maps.event.addDomListener(window, 'load', initialize);

	//Draw the route between current location and selected museum
	function calcRoute() {
		var myLatlng = new google.maps.LatLng(myLat, myLong);
		var tr = $('tr.selected');
		var td = tr.children('td').eq(1);
		var destinationString = td.html();
		destinationString = destinationString.substring(1);
		destinationString = destinationString.substring(0,
				destinationString.length - 1);
		var array = destinationString.split(',');

		var request = {
			origin : myLatlng,
			destination : new google.maps.LatLng(array[0], array[1]),
			travelMode : google.maps.TravelMode.DRIVING
		};
		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				directionsDisplay.setDirections(response);
			}
		});
	}
	// Add a marker to the map and push to the array.
	function addMarker(location, title, uri) {
		var marker = new google.maps.Marker({
			position : location,
			title : title
		});
		google.maps.event.addListener(marker, 'click', function() {
			window.location.href = uri;
		});
		markers.push(marker);
	}

	// Sets the map on all markers in the array.
	function setAllMap(map) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(map);
		}
		directionsDisplay.set('directions', null);
	}

	// Removes the markers from the map, but keeps them in the array.
	function clearMarkers() {
		setAllMap(null);
	}

	// Shows any markers currently in the array.
	function showMarkers() {
		setAllMap(map);
	}

	// Deletes all markers in the array by removing references to them.
	function deleteMarkers() {
		clearMarkers();
		markers = [];
	}

	// Update the table content
	function updateDataTable(dataset) {
		var table = $('#example').DataTable();
		table.clear().draw();
		for ( var i in dataset) {
			var latitude = dataset[i].latitude;
			var longitude = dataset[i].longitude;
			var resourceURI = dataset[i].resourceURI;
			var name = dataset[i].name;
			var country = dataset[i].country;
			table.row.add(
					[
							name,
							"[" + latitude.toString() + ", "
									+ longitude.toString() + "]", country,
							resourceURI, "See more >>" ]).draw();
		}
	}

	$(document)
			.ready(
					function() {
						museumListRequest = $
								.ajax({
									type : "GET",
									url : "http://localhost:8080/EMUServer/rest/map/getNearbyMuseums",
									data : {
										'latitude' : myLat,
										'longitude' : myLong,
										'radius' : 100
									},
									success : handleSuccessMuseumList
								});

						$('#demo')
								.html(
										'<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>');
						var table = $('#example').dataTable({
							"data" : "",
							"columns" : [ {
								"title" : "Museum"
							}, {
								"title" : "Geolocation",
								"class" : "center"
							}, {
								"title" : "Country",
								"class" : "center"
							}, {
								"title" : "Resource",
							}, {
								"title" : "Details",
							} ]
						});
						$('#example tbody').on('click', 'tr', function() {
							if ($(this).hasClass('selected')) {
								$(this).removeClass('selected');
								directionsDisplay.set('directions', null);
							} else {
								table.$('tr.selected').removeClass('selected');
								$(this).addClass('selected');
								calcRoute();
							}
						});

					});
</script>
</head>
<body>
	<div class="wrapper col2">
		<div id="header">
			<div id="topnav">
				<ul>
					<li class="active last"><a href="#">Map</a><span>Nearby
							museums</span></li>
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
		<p>
			<label for="radius"> Museum in area: </label> <input type="text"
				id="radius" readonly
				style="border: 0; color: #f6931f; font-weight: bold;">
		</p>

		<div id="slider" style="width: 200px"></div>

	</div>
	<div class="wrapper col4">
		<center>
			<div id="map-canvas">
				<div id="googleMap"></div>
			</div>
			<div id="demo"></div>
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