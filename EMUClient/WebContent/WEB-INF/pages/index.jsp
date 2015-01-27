<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html version="HTML+RDFa 1.1" lang="en"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	<c:forEach var="ns" items="${namespaces}">
    	xmlns:${ns.key}="${ns.value}"	
</c:forEach>>
<head>
<title property="dc:title">EMU</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
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


<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script type="application/javascript">
	
	
	
	
	
	
	
	
	
function myIP() {
    if (window.XMLHttpRequest) xmlhttp = new XMLHttpRequest();
    else xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.open("GET","http://api.hostip.info/get_html.php",false);
    xmlhttp.send();

    hostipInfo = xmlhttp.responseText.split("\n");
    for (i=0; hostipInfo.length >= i; i++) {
        ipAddress = hostipInfo[i].split(":");
        if ( ipAddress[0] == "IP" ) return ipAddress[1];
    }

    return false;
}









</script>
<script type="text/javascript">
	function getMap() {
		var theIp = myIP();

		if (name != undefined || name != null) {
			window.location = 'map?ip=' + theIp;
		}
	}
</script>
<script>
	$(function() {
		function log(message) {
			$("<div>").text(message).prependTo("#log");
			$("#log").scrollTop(0);
		}

		$("#museum").autocomplete(
				{
					source : function(request, response) {
						jQuery.get("search", {
							q : request.term
						}, function(data) {
							var array = data.split(',');
							response(array);
						});
					},
					minLength : 3,
					select : function(event, ui) {
						log(ui.item ? "Selected: " + ui.item.label
								: "Nothing selected, input was " + this.value);
					},
					open : function() {
						$(this).removeClass("ui-corner-all").addClass(
								"ui-corner-top");
					},
					close : function() {
						$(this).removeClass("ui-corner-top").addClass(
								"ui-corner-all");
					}
				});
	});
</script>
</head>
<body id="top">
	<c:set var="hashtag" scope="session" value="#" />

	<div class="wrapper col2">
		<div id="header">
			<div id="topnav">
				<ul>
					<li class="last"><a href="#" onclick="javascript:getMap()">Map</a><span>Test
							Text Here</span></li>
					<li><a href="full-width.jsp">Full Width</a><span>Test
							Text Here</span></li>
					<li><a href="quiz">Quiz</a><span>Test Text Here</span></li>
					<li class="active"><a href="index.jsp">Homepage</a><span>Test
							Text Here</span></li>
				</ul>
			</div>
			<div id="logo">
				<h1>
					<a href="index.jsp">Emu</a>
				</h1>
				<p>Semantic Web-Enhanced Museum</p>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div class="wrapper col3">
		<div id="intro">
			<ul>
				<c:forEach var="museumThumb" items="${museumThumbs}"
					varStatus="myIndex">
					<c:choose>
						<c:when test="${myIndex.index != museumThumbs.size()}">
							<li resource="${museumThumb.getDetailsUrl}"
								typeof="dbpedia-owl:Museum"><img class="gal"
								src="${museumThumb.imageUrl}" alt=""
								rel="dbpedia-owl:thumbnail foaf:thumbnail" /> <a
								href="${museumThumb.getDetailsUrl}""><span
									rel="rdfs:label foaf:name">${museumThumb.museumName}</span>
									&raquo;</a></li>
						</c:when>

						<c:otherwise>
							<li class="last"><img class="gal"
								src="${museumThumb.imageUrl}" alt="" /> <a href="#">${museumThumb.museumName}
									&raquo;</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
			<br class="clear" />
			<center>
				<div class="ui-widget" style="margin-top: 5px;">
					<label for="museum">Search a Museum: </label> <input id="museum"
						style="witdth: 50%">
				</div>
			</center>
		</div>
	</div>
	<div class="wrapper col4" resource="#current" typeof="dbpedia-owl:Museum">
		<div id="container">
			<div id="content">
				<h2>
					<a href="${museumRDF.website().getSecond()}">${museumRDF.name().getSecond()}</a>
				</h2>
				<img class="imgl"
					rel="dbpedia-owl:thumbnail foaf:thumbnail"
					src="<c:url value="${museumRDF.thumbnail().getSecond()}" />" alt=""
					width="125" height="125" />
				<p class="justify">${museumRDF.abstractValue().getSecond()}</p>
			</div>
			<div id="column">
				<h2>Details</h2>
				<div class="scrollbar">
					<ul id="latestnews">
						<c:if
							test="${not empty museumRDF.latitude() && not empty museumRDF.longitude()}">
							<p>
								<strong>Location</strong>
							</p>
							<p>
								(<span property="${museumRDF.latitude().getFirst()}" datatype="xsd:float">${museumRDF.latitude().getSecond()}</span>,<span property="${museumRDF.longitude().getFirst()}" datatype="xsd:float">${museumRDF.longitude().getSecond()}</span>)
							</p>
						</c:if>
					</ul>
				</div>
			</div>
			<br class="clear" />
		</div>
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