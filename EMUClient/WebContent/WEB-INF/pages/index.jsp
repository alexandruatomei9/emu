<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html version="HTML+RDFa 1.1" lang="en"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:dcplaces="http://purl.org/ontology/places#"
	<c:forEach var="ns" items="${namespaces}">xmlns:${ns.key}="${ns.value}"
	</c:forEach>>
<head>
<title property="dc:title">EMU</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords"
	content="content slider, responsive image gallery, responsive image gallery, image slider, image fade, image rotator">
<meta property="dc:date dc:created" content="2014-29-02T13:00:00">
<meta property="og:type" content="website">
<meta property="og:site_name" content="EMU">
<meta property="og:description" content="Semantic Web-Enhanced Museum">

<meta rel="dc:subject" href="http://dbpedia.org/resource/Semantic_Web" />
<meta rel="dc:subject" href="http://dbpedia.org/resource/RDFa" />
<meta rel="dc:subject" href="http://dbpedia.org/resource/HTML5" />
<meta rel="dc:subject" href="http://dbpedia.org/resource/SPARQL" />

<link
	href="<c:url value="/resources/layout/styles/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/layout/styles/jquery.bxslider.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/index.js" />"></script>
<script src="<c:url value="/resources/js/jquery.bxslider.min.js" />"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script>
	function initialize() {
		var map;
		var myLat = "${museumRDF.getLatitude().getSecond()}";
		var myLong = "${museumRDF.getLongitude().getSecond()}";
		var museum = "${museumRDF.getName().getSecond()}";
		var myLatlng = new google.maps.LatLng(myLat, myLong);

		var mapOptions = {
			zoom : 10,
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
			title : museum,
			icon : pinImage
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
<script type="text/javascript">
	$(function() {
		$('.accordion').accordion({
			speed : 'slow',
			heightStyle : "content",
			collapsible : true,
		});
	});
	
	$(document).ajaxStop(function () {
		$('.bxslider').removeClass("loading");
		
		$('.bxslider .row').each(function(i){
			$(this).removeClass("invisible");
		});
		 
		$("[rel='popover']").popover({
			html: 'true',
			content:  function() {
			      return $(this).closest(".row").find(".authorData").parent().html();
		    	  }
			});
		
		$("[rel='popover']").on('click', function(e) {
					e.stopPropagation();
				});
		$(document).on('click',
			function(e) {
				if (($('.popover').has(e.target).length == 0)
							|| $(e.target).is('.close')) {
						$("[rel='popover']").popover('hide');
					}
			});

		$('.bxslider').bxSlider({
			mode : 'fade',
			captions : true,
			adaptiveHeight : false,
			pagerType : 'short',
		});

	});

	$(document).ready(function() {
		$('.bxslider').addClass("loading");

		$('#hidden_works_list li').each(function(i) {
			$.ajax({
				type : "GET",
				url : "works",
				data : {
					workURI : $(this).html()
				},
				success : function(response) {
					$(".bxslider").append(response);
				}
			});
		});
	});
</script>

</head>
<body id="top">
	<div class="container">
		<c:set var="hashtag" scope="session" value="#" />

		<div id="header" class="wrapper row col2">
			<div class="col-sm-6" id="logo">
				<h1>
					<a href="home">Emu</a>
				</h1>
				<p>Semantic Web-Enhanced Museum</p>
			</div>


			<div class="col-sm-6" id="topnav">
				<ul>
					<li class="active"><a href="home">Homepage</a><span>Find
							out more</span></li>
					<li><a href="#" onclick="javascript:getMap()">Map</a><span>Nearby
							Museums</span></li>
					<li><a href="quiz">Quiz</a><span>Test Your Knowledge</span></li>
				</ul>
			</div>

			<br class="clear" />
		</div>

		<div id="intro" class="wrapper col3">

			<ul class="row">
				<c:forEach var="museumThumb" items="${museumThumbs}">
					<li class="col-sm-12" resource="${museumThumb.getDetailsUrl}"
						typeof="dbpedia-owl:Museum"><img class="gal"
						src="${museumThumb.imageUrl}" alt=""
						rel="dbpedia-owl:thumbnail foaf:thumbnail" /> <a
						href="${museumThumb.getDetailsUrl}"><span
							rel="rdfs:label foaf:name">${museumThumb.museumName}</span>
							&raquo;</a></li>
				</c:forEach>
			</ul>

			<br class="clear" />
			<center>
				<div class="ui-widget" style="margin-top: 5px;">
					<label for="museum">Search a Museum: </label> <input id="museum"
						style="width: 50%">
				</div>
			</center>
		</div>

		<div id="container" class="wrapper row col4"
			about="${museumRDF.getResourceName()} " typeof="dbpedia-owl:Museum">
			<div id="content" class="col-sm-7">
				<h2>
					<a href="${museumRDF.getWebsite().getSecond()}">${museumRDF.getName().getSecond()}</a>
				</h2>
				<img class="imgl" rel="dbpedia-owl:thumbnail foaf:thumbnail"
					src="<c:url value="${museumRDF.getThumbnail().getSecond()}" />"
					alt="" width="125" height="125" />
				<p class="justify" rel="${museumRDF.getAbstract().getFirst()}">${museumRDF.getAbstract().getSecond()}</p>
			</div>
			<div id="column" class="col-sm-5">
				<h2>Details</h2>
				<div class="scrollbar">
					<div class="accordion">
						<!-- Test for Location Detail -->
						<c:if
							test="${not empty museumRDF.getLatitude() && not empty museumRDF.getLongitude()}">
							<h3>Location</h3>

							<div style="height: 250px;">
								<p>
									<c:set var="locality" scope="request"
										value="${museumRDF.getLocality() }" />
									<c:set var="country" scope="request"
										value="${museumRDF.getCountry()}" />

									<c:if test="${not empty country}">
										<span property="dcplaces:country"><c:out
												value="${country}, " /></span>
									</c:if>
									<c:if test="${not empty locality}">
										<span property="dbpedia-owl:locality"><c:out
												value="${locality}" /></span>
									</c:if>
								</p>
								<p style="display: none;">
									(<span property="${museumRDF.getLatitude().getFirst()}"
										datatype="xsd:float">${museumRDF.getLatitude().getSecond()}</span>,<span
										property="${museumRDF.getLongitude().getFirst()}"
										datatype="xsd:float">${museumRDF.getLongitude().getSecond()}</span>)
								</p>
								<div id="map-canvas"
									style="width: 100%; height: 100%; margin: 0 auto; padding-top: 20px; padding-bottom: 20px;"></div>
							</div>
						</c:if>
					</div>
					<div class="accordion">
						<!-- Test for Established Year -->
						<c:if test="${not empty museumRDF.getEstablishedYear()}">
							<h3>Established Year</h3>
							<div>
								<p property="${museumRDF.getEstablishedYear().getFirst()}"
									datatype="xsd:integer">
									${museumRDF.getEstablishedYear().getSecond()}</p>
							</div>
						</c:if>
					</div>
					<div class="accordion">
						<!-- Test for Number of Visitors -->
						<c:if test="${not empty museumRDF.getNumberOfVisitors()}">
							<h3>Number of Visitors</h3>
							<div>
								<p property="${museumRDF.getNumberOfVisitors().getFirst()}"
									datatype="xsd:integer">
									${museumRDF.getNumberOfVisitors().getSecond()}</p>
							</div>
						</c:if>
					</div>
					<!-- Test for Director -->

					<!-- Test for Born People -->

					<!-- Test for Dead People -->

					<!-- Test for Website -->
					<div class="accordion">
						<c:if test="${not empty museumRDF.getWebsite()}">
							<h3>Website</h3>
							<div>
								<p property="${museumRDF.getWebsite().getFirst()} dc:URI"
									datatype="xsd:anyURI">
									<a href="${museumRDF.getWebsite().getSecond()}">${museumRDF.getWebsite().getSecond()}
									</a>
								</p>
							</div>
						</c:if>
					</div>
					<!-- Hidden List Of works -->
					<c:if test="${not empty museumRDF.getWorks()}">
						<c:if test="${fn:length(museumRDF.getWorks()) gt 0}">
							<ul id="hidden_works_list" style="display: none">
								<c:forEach var="workPair" items="${museumRDF.getWorks()}">
									<li><c:out value="${workPair.getSecond().getURI()}"></c:out></li>
								</c:forEach>
							</ul>
						</c:if>
					</c:if>
				</div>
			</div>
			<br class="clear" /> <br class="clear" /> <br class="clear" />
		</div>
		<div class="wrapper col5">
			<div id="footer">
				<div class="bxslider">

				</div>
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