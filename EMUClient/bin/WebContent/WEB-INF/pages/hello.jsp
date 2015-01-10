<html>
<link type="text/css" rel="stylesheet"
	href="https://www.gstatic.com/freebase/suggest/4_1/suggest.min.css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/freebase/suggest/4_1/suggest.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#myinput")
				.suggest(
						{
							key : "AIzaSyBS5BYpKRPYtUqBnu8KzDAAFMI5yPOYgWQ",
							filter : '(all type:/architecture/museum)',
							animate : true,
							status : [ "Start typing to get museums...",
									"Searching...",
									"Select a museum from the list:",
									"Sorry, something went wrong. Please try again later" ]
						})
				.bind("fb-select", function(e, data) {
						  alert(data.name + ", " + data.id);
						});	
	});
</script>
<body>
	<h1>Message : ${message}</h1>
	<input type="text" id="myinput" />
</body>
</html>