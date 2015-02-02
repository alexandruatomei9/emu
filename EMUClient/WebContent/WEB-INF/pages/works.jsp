<div class="row">
	<div class="col-sm-4">
		<img src="${thumbnail.getSecond()}" />
	</div>
	<div class="description col-sm-8">
		<h2>
			<span id="title">${name.getSecond()}</span> - <a rel='popover' data-placement='bottom' id="authorName">${authorName.getSecond()}</a>
		</h2>
		<p>${description.getSecond()}</p>
	</div>

	<div id="${name.getSecond()}-${authorName.getSecond()}" style="width:250px;">
		<img src="${authorThumbnail.getSecond()}" width="50" height="50"/>
		<div style="display:inline;">
		<h3>${authorName.getSecond()}
			[<span id="birthdate">${authorBirthDate.getSecond()}</span> - <span
				id="deathdate">${authorDeathDate.getSecond()}</span>]
		</h3>
		<p>${authorAbstract.getSecond()}</p>
		<p><a href="${authorWiki.getSecond()}">Details &raquo;</a></p>
		</div>
	</div>
</div> 
 