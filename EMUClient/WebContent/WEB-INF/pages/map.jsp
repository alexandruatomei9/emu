<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>EMU</title>
<!-- StyleSheets -->
<link
	href="<c:url value="/resources/layout/styles/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/layout/styles/jquery.dataTables.css" />"
	rel="stylesheet">
<link href="http://cdn.datatables.net/responsive/1.0.4/css/dataTables.responsive.css"
	rel="stylesheet">

<!-- Scripts -->
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
<script src="http://cdn.datatables.net/responsive/1.0.4/js/dataTables.responsive.js"></script>
<script>
	var myLat = "${myLat}";
	var myLong = "${myLong}";
	var myCity;
	$.get("http://ipinfo.io", function(response) {
	    myCity = response.city;
	}, "jsonp");
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
							slide: function(event, ui){
								$("#radius").text(" " + ui.value + "km");
							},
							stop : function(event, ui) {
								
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
		$("#radius").text(" " + $("#slider").slider("value") + "km");
	});

	function handleSuccessMuseumList(response) {
		var code = response.code;
		if (code == "OK") {
			deleteMarkers();
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
							'<a href="'+resourceURI+'" target="_blank">DBpedia Link</a>', "See more >>" ]).draw();
		}
	}

	$(document)
			.ready(function() {
					 $("#country-select").selectmenu({width: 200}).selectmenu( "menuWidget" ).addClass( "overflow" );
					 $("#museum-type-select").selectmenu({width: 200}).selectmenu( "menuWidget" ).addClass( "overflow" );
					 
					 $("#go").button().click(function( event ) {
				          var countryVal = $("#country-select").val();
				          var typeVal = $("#museum-type-select").val();
				          
				          var params;
				          if(countryVal != 'x' && typeVal != 'y'){
				        	  params="country="+countryVal+"&type="+typeVal;
				          }else if(countryVal != 'x'){
				        	  params="country="+countryVal;
				          }else if(typeVal != 'y'){
				        	  params="type="+typeVal;
				          }else{
				        	  return;
				          }
				          
				          var myUrl = "http://localhost:8080/EMUServer/rest/museums?"+params;
				          console.log(myUrl);
				          museumListRequest = $
							.ajax({
								type : "GET",
								url : myUrl,
								success : handleSuccessMuseumList
							});
				     });
					 
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
										'<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%"></table>');
						var table = $('#example').DataTable({
							"data" : "",
							"responsive": "true",
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
<body id="top">
	<div class="container">
	<div id="header" class="wrapper row col2">
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
						<a class="navbar-brand" href=""><img src="<c:url value="/resources/layout/styles/images/emu.jpg" />" class="img-brand"/>Emu</a>
					</div>

					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-6">
						<ul class="nav navbar-nav">
							<li><a href="home">Home</a></li>
							<li class="active"><a href="javascript:showlocation()">Map</a></li>
							<li><a href="quiz">Quiz</a></li>
						</ul>
					</div>
				</div>
			</nav>
			<br class="clear" />
	</div>
	<div class="wrapper col3">
		<center>
			<div id="map-canvas">
				<div id="googleMap"></div>
			</div>
			<div id="map-controls">
			<center>
			<p>
				Museums in a <span id="radius" style="border: 0; color: #f6931f; font-weight: bold;"></span> radius centered in <span id="radiusCenter">your current location</span>
			</p>
			<div id="slider" style="width: 200px;"></div>
			</center>
			</div>
			<div class="container">
			<div class="col-sm-4">
			<select id="country-select" title="Country">
			<option value="x">Select a country</option>
			<option value="Afghanistan">Afghanistan</option>
			<option value="Albania">Albania</option>
			<option value="Algeria">Algeria</option>
			<option value="American Samoa">American Samoa</option>
			<option value="Andorra">Andorra</option>
			<option value="Angola">Angola</option>
			<option value="Antarctica">Antarctica</option>
			<option value="Antigua and Barbuda">Antigua and Barbuda</option>
			<option value="Argentina">Argentina</option>
			<option value="Armenia">Armenia</option>
			<option value="Australia">Australia</option>
			<option value="Austria">Austria</option>
			<option value="Azerbaijan">Azerbaijan</option>
			<option value="Bahamas">Bahamas</option>
			<option value="Bahrain">Bahrain</option>
			<option value="Bangladesh">Bangladesh</option>
			<option value="Barbados">Barbados</option>
			<option value="Belarus">Belarus</option>
			<option value="Belgium">Belgium</option>
			<option value="Belize">Belize</option>
			<option value="Benin">Benin</option>
			<option value="Bermuda">Bermuda</option>
			<option value="Bhutan">Bhutan</option>
			<option value="Bolivia">Bolivia</option>
			<option value="Bosnia and Herzegovina">Bosnia and
				Herzegovina</option>
			<option value="Botswana">Botswana</option>
			<option value="Brazil">Brazil</option>
			<option value="Bulgaria">Bulgaria</option>
			<option value="Burkina Faso">Burkina Faso</option>
			<option value="Burundi">Burundi</option>
			<option value="Cambodia">Cambodia</option>
			<option value="Cameroon">Cameroon</option>
			<option value="Canada">Canada</option>
			<option value="Cape Verde">Cape Verde</option>
			<option value="Cayman Islands">Cayman Islands</option>
			<option value="Central African Republic">Central African
				Republic</option>
			<option value="Chad">Chad</option>
			<option value="Chile">Chile</option>
			<option value="China">China</option>
			<option value="Christmas Island">Christmas Island</option>
			<option value="Colombia">Colombia</option>
			<option value="Comoros">Comoros</option>
			<option value="Congo">Congo</option>
			<option value="Cook Islands">Cook Islands</option>
			<option value="Costa Rica">Costa Rica</option>
			<option value="Cote D'ivoire">Cote D'ivoire</option>
			<option value="Croatia">Croatia</option>
			<option value="Cuba">Cuba</option>
			<option value="Cyprus">Cyprus</option>
			<option value="Czech Republic">Czech Republic</option>
			<option value="Denmark">Denmark</option>
			<option value="Djibouti">Djibouti</option>
			<option value="Dominica">Dominica</option>
			<option value="Dominican Republic">Dominican Republic</option>
			<option value="Ecuador">Ecuador</option>
			<option value="Egypt">Egypt</option>
			<option value="El Salvador">El Salvador</option>
			<option value="Equatorial Guinea">Equatorial Guinea</option>
			<option value="Eritrea">Eritrea</option>
			<option value="Estonia">Estonia</option>
			<option value="Ethiopia">Ethiopia</option>
			<option value="Faroe Islands">Faroe Islands</option>
			<option value="Fiji">Fiji</option>
			<option value="Finland">Finland</option>
			<option value="France">France</option>
			<option value="French Guiana">French Guiana</option>
			<option value="French Polynesia">French Polynesia</option>
			<option value="Gabon">Gabon</option>
			<option value="Gambia">Gambia</option>
			<option value="Georgia">Georgia</option>
			<option value="Germany">Germany</option>
			<option value="Ghana">Ghana</option>
			<option value="Gibraltar">Gibraltar</option>
			<option value="Greece">Greece</option>
			<option value="Greenland">Greenland</option>
			<option value="Grenada">Grenada</option>
			<option value="Guadeloupe">Guadeloupe</option>
			<option value="Guam">Guam</option>
			<option value="Guatemala">Guatemala</option>
			<option value="Guernsey">Guernsey</option>
			<option value="Guinea">Guinea</option>
			<option value="Guinea-bissau">Guinea-bissau</option>
			<option value="Guyana">Guyana</option>
			<option value="Haiti">Haiti</option>
			<option value="Honduras">Honduras</option>
			<option value="Hong Kong">Hong Kong</option>
			<option value="Hungary">Hungary</option>
			<option value="Iceland">Iceland</option>
			<option value="India">India</option>
			<option value="Indonesia">Indonesia</option>
			<option value="Iran">Iran</option>
			<option value="Iraq">Iraq</option>
			<option value="Ireland">Ireland</option>
			<option value="Israel">Israel</option>
			<option value="Italy">Italy</option>
			<option value="Jamaica">Jamaica</option>
			<option value="Japan">Japan</option>
			<option value="Jersey">Jersey</option>
			<option value="Jordan">Jordan</option>
			<option value="Kazakhstan">Kazakhstan</option>
			<option value="Kenya">Kenya</option>
			<option value="Kiribati">Kiribati</option>
			<option value="Korea, Democratic People's Republic of">Korea,
				Democratic People's Republic of</option>
			<option value="Korea, Republic of">Korea, Republic of</option>
			<option value="Kuwait">Kuwait</option>
			<option value="Kyrgyzstan">Kyrgyzstan</option>
			<option value="Lao People's Democratic Republic">Lao
				People's Democratic Republic</option>
			<option value="Latvia">Latvia</option>
			<option value="Lebanon">Lebanon</option>
			<option value="Lesotho">Lesotho</option>
			<option value="Liberia">Liberia</option>
			<option value="Libyan Arab Jamahiriya">Libyan Arab
				Jamahiriya</option>
			<option value="Liechtenstein">Liechtenstein</option>
			<option value="Lithuania">Lithuania</option>
			<option value="Luxembourg">Luxembourg</option>
			<option value="Macao">Macao</option>
			<option value="Macedonia, The Former Yugoslav Republic of">Macedonia,
				The Former Yugoslav Republic of</option>
			<option value="Madagascar">Madagascar</option>
			<option value="Malawi">Malawi</option>
			<option value="Malaysia">Malaysia</option>
			<option value="Maldives">Maldives</option>
			<option value="Mali">Mali</option>
			<option value="Malta">Malta</option>
			<option value="Marshall Islands">Marshall Islands</option>
			<option value="Martinique">Martinique</option>
			<option value="Mauritania">Mauritania</option>
			<option value="Mauritius">Mauritius</option>
			<option value="Mayotte">Mayotte</option>
			<option value="Mexico">Mexico</option>
			<option value="Moldova">Moldova</option>
			<option value="Monaco">Monaco</option>
			<option value="Mongolia">Mongolia</option>
			<option value="Montenegro">Montenegro</option>
			<option value="Montserrat">Montserrat</option>
			<option value="Morocco">Morocco</option>
			<option value="Mozambique">Mozambique</option>
			<option value="Myanmar">Myanmar</option>
			<option value="Namibia">Namibia</option>
			<option value="Nauru">Nauru</option>
			<option value="Nepal">Nepal</option>
			<option value="Netherlands">Netherlands</option>
			<option value="New Caledonia">New Caledonia</option>
			<option value="New Zealand">New Zealand</option>
			<option value="Nicaragua">Nicaragua</option>
			<option value="Niger">Niger</option>
			<option value="Nigeria">Nigeria</option>
			<option value="Niue">Niue</option>
			<option value="Norfolk Island">Norfolk Island</option>
			<option value="Norway">Norway</option>
			<option value="Oman">Oman</option>
			<option value="Pakistan">Pakistan</option>
			<option value="Palau">Palau</option>
			<option value="Panama">Panama</option>
			<option value="Papua New Guinea">Papua New Guinea</option>
			<option value="Paraguay">Paraguay</option>
			<option value="Peru">Peru</option>
			<option value="Philippines">Philippines</option>
			<option value="Pitcairn">Pitcairn</option>
			<option value="Poland">Poland</option>
			<option value="Portugal">Portugal</option>
			<option value="Puerto Rico">Puerto Rico</option>
			<option value="Qatar">Qatar</option>
			<option value="Reunion">Reunion</option>
			<option value="Romania">Romania</option>
			<option value="Russian Federation">Russian Federation</option>
			<option value="Rwanda">Rwanda</option>
			<option value="Saint Helena">Saint Helena</option>
			<option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
			<option value="Saint Lucia">Saint Lucia</option>
			<option value="Samoa">Samoa</option>
			<option value="San Marino">San Marino</option>
			<option value="Sao Tome and Principe">Sao Tome and Principe</option>
			<option value="Saudi Arabia">Saudi Arabia</option>
			<option value="Senegal">Senegal</option>
			<option value="Serbia">Serbia</option>
			<option value="Seychelles">Seychelles</option>
			<option value="Sierra Leone">Sierra Leone</option>
			<option value="Singapore">Singapore</option>
			<option value="Slovakia">Slovakia</option>
			<option value="Slovenia">Slovenia</option>
			<option value="Solomon Islands">Solomon Islands</option>
			<option value="Somalia">Somalia</option>
			<option value="South Africa">South Africa</option>
			<option value="Spain">Spain</option>
			<option value="Sri Lanka">Sri Lanka</option>
			<option value="Sudan">Sudan</option>
			<option value="Suriname">Suriname</option>
			<option value="Swaziland">Swaziland</option>
			<option value="Sweden">Sweden</option>
			<option value="Switzerland">Switzerland</option>
			<option value="Syrian Arab Republic">Syrian Arab Republic</option>
			<option value="Taiwan">Taiwan</option>
			<option value="Tajikistan">Tajikistan</option>
			<option value="Tanzania">Tanzania</option>
			<option value="Thailand">Thailand</option>
			<option value="Timor-leste">Timor-leste</option>
			<option value="Togo">Togo</option>
			<option value="Tokelau">Tokelau</option>
			<option value="Tonga">Tonga</option>
			<option value="Trinidad and Tobago">Trinidad and Tobago</option>
			<option value="Tunisia">Tunisia</option>
			<option value="Turkey">Turkey</option>
			<option value="Turkmenistan">Turkmenistan</option>
			<option value="Turks and Caicos Islands">Turks and Caicos
				Islands</option>
			<option value="Tuvalu">Tuvalu</option>
			<option value="Uganda">Uganda</option>
			<option value="Ukraine">Ukraine</option>
			<option value="United Arab Emirates">United Arab Emirates</option>
			<option value="United Kingdom">United Kingdom</option>
			<option value="United States">United States</option>
			<option value="Uruguay">Uruguay</option>
			<option value="Uzbekistan">Uzbekistan</option>
			<option value="Vanuatu">Vanuatu</option>
			<option value="Venezuela">Venezuela</option>
			<option value="Viet Nam">Viet Nam</option>
			<option value="Yemen">Yemen</option>
			<option value="Zambia">Zambia</option>
			<option value="Zimbabwe">Zimbabwe</option>
		</select>
		</div>
		<div class="col-sm-4">
		<select id="museum-type-select">
			<option value="y">Select a type</option>
			<option value="Art">Art Museum</option>
			<option value="Auto">Auto Museum</option>
			<option value="Computer">Computer Museum</option>
			<option value="Farm">Farm Museum</option>
			<option value="Geology">Geology Museum</option>
			<option value="History">History Museum</option>
			<option value="Photography">Photography Museum</option>
			<option value="Railway">Railway Museum</option>
			<option value="Science">Science Museum</option>
			<option value="University">University Museum</option>
			<option value="War">War Museum</option>
		</select>
		</div> 
		<div class="col-sm-4">
		<button id="go" style="width:50px;">Go!</button>
		</div>
		</div>
		<br class="clear"/><br class="clear"/><br class="clear"/>
		</center>
	</div>
	<div class="wrapper col4">
		<div id="demo"></div>
	</div>
	<div class="wrapper row col5">
			<div id="copyright">
				<p class="fl_left">
					Emu - Semantic web-enhanced museum
				</p>
				<p class="fl_right">
					WADE - 2015</a>
				</p>
				<br class="clear" />
			</div>
</div>
	</div>
</body>
</html>