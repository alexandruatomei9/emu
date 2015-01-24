<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>EMU</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="keywords" content="content slider, responsive image gallery, responsive image gallery, image slider, image fade, image rotator">
<link href="<c:url value="/resources/layout/styles/layout.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
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
	var theIp= myIP();

    if (name != undefined || name != null) {
        window.location = 'map?ip=' + theIp;
    }
}
</script>
</head>
<body id="top">
<div class="wrapper col2">
  <div id="header">
    <div id="topnav">
      <ul>
        <li class="last"><a href="#" onclick="javascript:getMap()">Map</a><span>Test Text Here</span></li>
        <li><a href="full-width.jsp">Full Width</a><span>Test Text Here</span></li>
        <li><a href="quiz">Quiz</a><span>Test Text Here</span></li>
        <li class="active"><a href="index.jsp">Homepage</a><span>Test Text Here</span></li>
      </ul>
    </div>
    <div id="logo">
      <h1><a href="index.jsp">Emu</a></h1>
      <p>Semantic Web-Enhanced Museum</p>
    </div>
    <br class="clear" />
  </div>
</div>
<div class="wrapper col3">
  	<div id="intro">
  		<ul>
    	<c:forEach var="museumThumb" items="${museumThumbs}" varStatus="myIndex">
    		<c:choose>
      			<c:when test="${myIndex.index != museumThumbs.size()}">
      				<li><img class="gal" src="${museumThumb.imageUrl}" alt="" /> <a href="${museumThumb.getDetailsUrl}">${museumThumb.museumName} &raquo;</a></li>
      			</c:when>

      			<c:otherwise>
      				<li class="last"><img class="gal" src="${museumThumb.imageUrl}" alt="" /> <a href="#">${museumThumb.museumName} &raquo;</a></li>
      			</c:otherwise>
			</c:choose>
    	</c:forEach>
    	</ul>
      <br class="clear" />
  	</div>
</div>
<div class="wrapper col4">
  <div id="container">
    <div id="content">
      <h2><a href="${museum.url}">${museum.name}</a></h2>
      <img class="imgl" src="<c:url value="${museum.image}" />" alt="" width="125" height="125" />
      <p class="justify">${museum.description}</p>
    </div>
    <div id="column">
      <h2>Details</h2>
      <div class="scrollbar">
        <ul id="latestnews">
        <c:forEach var="detail" items="${museum.details}" varStatus="myIndex">
    		<c:choose>
      			<c:when test="${myIndex.index != museum.details.size()}">
      				<li>
      					<p><strong>${detail.name}</strong></p>
            			<p>${detail.value}</p>
      				</li>
      			</c:when>

      			<c:otherwise>
      				<li class="last">
      				<p><strong>${detail.name}</strong></p>
            		<p>${detail.value}</p>
      				</li>
      			</c:otherwise>
			</c:choose>
    	</c:forEach>
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
          <input type="text" value="Enter Email Here&hellip;"  onfocus="this.value=(this.value=='Enter Email Here&hellip;')? '' : this.value ;" />
          <input type="submit" name="news_go" id="news_go" value="GO" />
        </fieldset>
      </form>
      <p>To unsubscribe please <a href="#">click here &raquo;</a></p>
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
    <p class="fl_left">Copyright &copy; 2014 - All Rights Reserved - <a href="#">Domain Name</a></p>
    <p class="fl_right">Template by <a target="_blank" href="http://www.os-templates.com/" title="Free Website Templates">OS Templates</a></p>
    <br class="clear" />
  </div>
</div>
</body>
</html>