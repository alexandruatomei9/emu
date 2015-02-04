<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="latitude" style="display:none;">${museumRDF.getLatitude().getSecond()}</div>
<div id="longitude" style="display:none">${museumRDF.getLongitude().getSecond()}</div>
<div id="container" class="wrapper row col4"
			about="${museumRDF.getResourceName()} " typeof="dbpedia-owl:Museum">
			<div id="content" class="col-sm-7">
				<h2>
					<a href="${museumRDF.getWebsite().getSecond()}">${museumRDF.getName().getSecond()}</a>
				</h2>
				<img class="imgl" rel="dbpedia-owl:thumbnail foaf:thumbnail"
					src="${museumRDF.getThumbnail().getSecond()}"
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
									style="width: 100%; height: 85%; margin: 0 auto; padding-top: 10px; padding-bottom: 20px;"></div>
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
							<ul id="hidden_works_list" style="display: none">
								<c:forEach var="workPair" items="${museumRDF.getWorks()}">
									<c:if test="${workPair.isValid()}">
									<li><c:out value="${workPair.getSecond().getURI()}"></c:out></li>
									</c:if>
								</c:forEach>
							</ul>
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