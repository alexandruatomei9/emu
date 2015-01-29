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
<link href="<c:url value="/resources/layout/styles/jquery-ui.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/jquery.bxslider.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/layout/styles/layout.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/index.js" />"></script>
<script src="<c:url value="/resources/js/jquery.bxslider.min.js" />"></script>

<script type="text/javascript">
    $(function(){
        $('#accordion').accordion({ speed: 'slow'});
    });
    
    
    $(document).ready(function(){
  		$('.bxslider').bxSlider({
  			mode: 'fade',
  			captions: true
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
					<li class="active"><a href="home">Homepage</a><span>Find out more</span></li>
					<li><a href="#" onclick="javascript:getMap()">Map</a><span>Nearby Museums</span></li>
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
									&raquo;</a>
							</li>
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
	
	<div id="container" class="wrapper row col4" resource="#current" typeof="dbpedia-owl:Museum">
			<div id="content" class="col-sm-7">
				<h2>
					<a href="${museumRDF.getWebsite().getSecond()}">${museumRDF.getName().getSecond()}</a>
				</h2>
				<img class="imgl"
					rel="dbpedia-owl:thumbnail foaf:thumbnail"
					src="<c:url value="${museumRDF.getThumbnail().getSecond()}" />" alt=""
					width="125" height="125" />
				<p class="justify" rel="${museumRDF.getAbstract().getFirst()}">${museumRDF.getAbstract().getSecond()}</p>
			</div>
			<div id="column" class="col-sm-5">
				<h2>Details</h2>
				<div class="scrollbar">
					<div id="accordion">
						<c:if
							test="${not empty museumRDF.getLatitude() && not empty museumRDF.getLongitude()}">
							<h3>Location</h3>
							<div>
								<p>
									(<span property="${museumRDF.getLatitude().getFirst()}"
										datatype="xsd:float">${museumRDF.getLatitude().getSecond()}</span>,<span
										property="${museumRDF.getLongitude().getFirst()}"
										datatype="xsd:float">${museumRDF.getLongitude().getSecond()}</span>)
								</p>
							</div>
						</c:if>

						<h3>Section 2</h3>
						<div>
							<p>Sed non urna. Donec et ante. Phasellus eu ligula.
								Vestibulum sit amet purus. Vivamus hendrerit, dolor at aliquet
								laoreet, mauris turpis porttitor velit, faucibus interdum tellus
								libero ac justo. Vivamus non quam. In suscipit faucibus urna.</p>
						</div>
						<h3>Section 2</h3>
						<div>
							<p>Sed non urna. Donec et ante. Phasellus eu ligula.
								Vestibulum sit amet purus. Vivamus hendrerit, dolor at aliquet
								laoreet, mauris turpis porttitor velit, faucibus interdum tellus
								libero ac justo. Vivamus non quam. In suscipit faucibus urna.</p>
						</div>
					</div>
				</div>
			</div>
			<br class="clear" />
			<br class="clear" />
			<br class="clear" />
	</div>
	<div class="wrapper row col5">
		<div id="footer">
				<div class="bxslider">
					<div class="row collapse slide-pane">
						<div class="small-6 medium-6 large-6 columns">
							<img
								src="http://www.cruzine.com/wp-content/uploads/2013/06/001-original-artworks-shichigoroshingo.jpg" />
						</div>
						<div class="small-6 medium-6 large-6 columns text-pane">
							<h1>Titlu opera de arta</h1>
							<p>Descriere</p>
						</div>
					</div>
					<div class="row collapse slide-pane">
						<div class="small-6 medium-6 large-6 columns">
							<img
								src="http://behance.vo.llnwd.net/profiles4/113797/projects/731401/d9eb81ec157108a81bf1caafc61708dd.jpg" />
						</div>
						<div class="small-6 medium-6 large-6 columns text-pane">
							<h1>Titlu opera de arta</h1>
							<p>Descriere</p>
						</div>
					</div>
					<div class="row collapse slide-pane">
						<div class="small-6 medium-6 large-6 columns">
							<img
								src="http://behance.vo.llnwd.net/profiles4/113797/projects/731401/5dd0d4f20523e9ad2d51d97e9af3ef64.jpg" />
						</div>
						<div class="small-6 medium-6 large-6 columns text-pane">
							<h1>Titlu opera de arta</h1>
							<p>Descriere</p>
						</div>
					</div>
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
</body></html>