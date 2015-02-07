function showlocation() {
	navigator.geolocation.getCurrentPosition(callback);
}
function callback(position) {
	var lat = position.coords.latitude;
	var lon = position.coords.longitude;
	var latLng = "latitude=" + lat + "&longitude=" + lon;
	console.log(latLng);
	if (latLng != 'undefined') {
		window.location = 'map?' + latLng + "&radius=100";
	}
}

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