<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="divQuestion" class="wrapper col3">
<center>
		<h2>Your score: <span>${score}</span></h2>
	<center>
<div id="quizQuestion">
					<p>${question.id + 1}.${question.text}</p>
					<c:forEach var="answer" items="${question.answers}" varStatus="myIndex">
						<button id="quizBtn" class="btn" type="button" onclick="takeValue(${question.id},${myIndex.index});">${answer.value}</button>
					</c:forEach>
</div>
</div>