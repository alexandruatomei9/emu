<div class="auth" about="${resourceName}" typeof="foaf:Person">
		<div class="authorData">
			<div class="authorDataImage">
				<img rel="${thumbnail.getFirst()}"
					src="${thumbnail.getSecond()}" />
			</div>
			<div property="${name.getFirst()}"
				content="${name.getSecond()}" class="authorDataContent">
				<h4>${name.getSecond()}</h4>
				<p property="${description.getFirst()}">${description.getSecond()}</p>
				<p property="${wiki.getFirst()}"
					content="${wiki.getSecond()}">
					<a href="${wiki.getSecond()}" target="blank">Details
						&raquo;</a>
				</p>
			</div>
		</div>
</div>