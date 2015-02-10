<div about="${resourceName}" typeof="foaf:Person">
	<div class="authorData">
		<div class="authorDataImage">
			<img rel="${directorThumbnail.getFirst()}"
				src="${directorThumbnail.getSecond()}" />
		</div>
		<div property="${directorName.getFirst()}"
			content="${directorName.getSecond()}" class="authorDataContent">
			<h4>${directorName.getSecond()}</h4>
			<p property="${directorDescription.getFirst()}">${directorDescription.getSecond()}</p>
			<p property="${directorWiki.getFirst()}"
				content="${directorWiki.getSecond()}">
				<a href="${directorWiki.getSecond()}" target="blank">Details
					&raquo;</a>
			</p>
		</div>
	</div>
</div>