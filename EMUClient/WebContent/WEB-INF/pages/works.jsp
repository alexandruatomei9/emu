<div class="row invisible" about="${resourceName}"
	typeof="dbpedia-owl:ArtWork">
	<div class="museum_work_relation" rel="dbpprop:museum" resource="" />
	<div>
		<img rel="${thumbnail.getFirst()}" src="${thumbnail.getSecond()}" />
	</div>
	<div class="description">
		<h2>
			<span id="title" property="${name.getFirst()}">${name.getSecond()}</span>
			- <a rel='popover' data-placement='auto' id="authorName">${authorName.getSecond()}</a>
		</h2>
		<p property="${description.getFirst()}">${description.getSecond()}</p>
		<p property="dbpedia-owl:author" resource="${authorResourceName}" />
	</div>

	<div class="auth" about="${authorResourceName}" typeof="foaf:Person">
		<div class="authorData">
			<div class="authorDataImage">
				<img rel="${authorThumbnail.getFirst()}"
					src="${authorThumbnail.getSecond()}" />
			</div>
			<div property="${authorName.getFirst()}"
				content="${authorName.getSecond()}" class="authorDataContent">
				<h4>${authorName.getSecond()}
					[<span id="birthdate" property="${authorBirthDate.getFirst()}">${authorBirthDate.getSecond().replace("Z","")}</span>]
					- [<span id="deathdate" property="${authorDeathDate.getFirst()} }">${authorDeathDate.getSecond().replace("Z","")}</span>]
				</h4>
				<p property="${authorAbstract.getFirst()}">${authorAbstract.getSecond()}</p>
				<p property="${authorWiki.getFirst()}"
					content="${authorWiki.getSecond()}">
					<a href="${authorWiki.getSecond()}" target="blank">Details
						&raquo;</a>
				</p>
			</div>
		</div>
	</div>
</div>
