<div about="${directorResourceName}" typeof="foaf:Person">

	<div class="personDataImage">
		<img rel="${directorThumbnail.getFirst()}"
			src="${directorThumbnail.getSecond()}" />
	</div>
	<div property="${directorName.getFirst()}"
		content="${directorName.getSecond()}" class="personDataContent">
		<h4 id="personName">${directorName.getSecond()}</h4>
		<p id="personDescription" property="${directorDescription.getFirst()}">${directorDescription.getSecond()}</p>
		<p property="${directorWiki.getFirst()}"
			content="${directorWiki.getSecond()}">
			<a href="${directorWiki.getSecond()}" target="blank">Details
				&raquo;</a>
		</p>
	</div>
	<div class="personData" rel="" resource=""></div>
</div>
