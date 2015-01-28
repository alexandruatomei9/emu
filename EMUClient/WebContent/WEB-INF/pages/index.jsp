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

<link href="<c:url value="/resources/layout/styles/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/index.js" />"></script>
</head>
<body id="top">
<div class="container">
	<c:set var="hashtag" scope="session" value="#" />

	<div class="wrapper row col2">
		<div id="header" class="col-xs-12">
			<div id="topnav" style="float:right;">
				<ul>
					<li class="last"><a href="#" onclick="javascript:getMap()">Map</a><span>Test
							Text Here</span></li>
					<li><a href="quiz">Quiz</a><span>Test Text Here</span></li>
					<li class="active"><a href="home">Homepage</a><span>Test
							Text Here</span></li>
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
	
		<div id="intro" class="wrapper row col3">
			<ul>
				<c:forEach var="museumThumb" items="${museumThumbs}"
					varStatus="myIndex">
					<c:choose>
						<c:when test="${myIndex.index != museumThumbs.size()}">
							<li class="col-xs-12" resource="${museumThumb.getDetailsUrl}"
								typeof="dbpedia-owl:Museum"><img class="gal"
								src="${museumThumb.imageUrl}" alt=""
								rel="dbpedia-owl:thumbnail foaf:thumbnail" /> <a
								href="${museumThumb.getDetailsUrl}""><span
									rel="rdfs:label foaf:name">${museumThumb.museumName}</span>
									&raquo;</a></li>
						</c:when>

						<c:otherwise>
							<li class="col-xs-12"><img class="gal"
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
	
	<div class="wrapper row col4" resource="#current" typeof="dbpedia-owl:Museum">
		<div id="container">
			<div id="content">
				<h2>
					<a href="${museumRDF.getWebsite().getSecond()}">${museumRDF.getName().getSecond()}</a>
				</h2>
				<img class="imgl"
					rel="dbpedia-owl:thumbnail foaf:thumbnail"
					src="<c:url value="${museumRDF.getThumbnail().getSecond()}" />" alt=""
					width="125" height="125" />
				<p class="justify" rel="${museumRDF.getAbstract().getFirst()}">${museumRDF.getAbstract().getSecond()}</p>
			</div>
			<div id="column">
				<h2>Details</h2>
				<div class="scrollbar">
					<ul id="latestnews">
						<c:if
							test="${not empty museumRDF.getLatitude() && not empty museumRDF.getLongitude()}">
							<p>
								<strong>Location</strong>
							</p>
							<p>
								(<span property="${museumRDF.getLatitude().getFirst()}" datatype="xsd:float">${museumRDF.getLatitude().getSecond()}</span>,<span property="${museumRDF.getLongitude().getFirst()}" datatype="xsd:float">${museumRDF.getLongitude().getSecond()}</span>)
							</p>
						</c:if>
					</ul>
				</div>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div class="wrapper row col5">
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