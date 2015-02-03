<div class="row invisible">
	<div>
		<img src="${thumbnail.getSecond()}" />
	</div>
	<div class="description">
		<h2>
			<span id="title">${name.getSecond()}</span> - <a rel='popover' data-placement='auto' id="authorName">${authorName.getSecond()}</a>
		</h2>
		<p>${description.getSecond()}</p>
	</div>

	<div class="auth">
	<div class="authorData">
		<div class="authorDataImage">
			<img src="${authorThumbnail.getSecond()}"/>
		</div>
		<div class="authorDataContent">
			<h4>${authorName.getSecond()}
				[<span id="birthdate">${authorBirthDate.getSecond().replace("Z","")}</span>] - [<span
					id="deathdate">${authorDeathDate.getSecond().replace("Z","")}</span>]
			</h4>
			<p>${authorAbstract.getSecond()}</p>
			<p><a href="${authorWiki.getSecond()}" target="blank">Details &raquo;</a></p>
		</div>
	</div>
	</div>
</div> 
 