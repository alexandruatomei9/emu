<c:forEach items="${works}" var="work"> 
  	<div class="row">
	<div class="col-sm-4">
		<img
			src="${work.thumbnail().getSecond()}" />
	</div>
	<div class="description col-sm-8">
		<h2>${work.getName().getSecond()}</h2>
		<p>${work.getAbstract().getSecond()}</p>
	</div>
	</div>
</c:forEach>