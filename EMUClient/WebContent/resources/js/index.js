$(function() {
	$("#museum").autocomplete({
		source : function(request, response) {
			jQuery.get("search", {
				q : request.term
			}, function(data) {
				var array = data.split('*');
				response(array);
			});
		},
		minLength : 3,
		delay: 2000,
		select : function(event, ui) {
			console.log(ui.item.label);
			$.ajax({
				type : "GET",
				url : "museumDetails",
				data : {
					museum : ui.item.label
				},
				success : function(response) {
					$('#mainContainer').replaceWith(response);
					onReadyState();
				}
			});
		},
		open : function() {
			$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close : function() {
			$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	});
});