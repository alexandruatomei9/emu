function myIP() {
	if (window.XMLHttpRequest)
		xmlhttp = new XMLHttpRequest();
	else
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

	xmlhttp.open("GET", "http://api.hostip.info/get_html.php", false);
	xmlhttp.send();
	
	var i;
	hostipInfo = xmlhttp.responseText.split("\n");
	for (i = 0; hostipInfo.length >= i; i++) {
		ipAddress = hostipInfo[i].split(":");
		if (ipAddress[0] == "IP")
			return ipAddress[1];
	}

	return false;
}

function getMap() {
	var theIp = myIP();

	if (name != undefined || name != null) {
		window.location = 'map?ip=' + theIp;
	}
}

$(function() {
	function log(message) {
		$("<div>").text(message).prependTo("#log");
		$("#log").scrollTop(0);
	}

	$("#museum").autocomplete(
			{
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
					log(ui.item ? "Selected: " + ui.item.label
							: "Nothing selected, input was " + this.value);
				},
				open : function() {
					$(this).removeClass("ui-corner-all").addClass(
							"ui-corner-top");
				},
				close : function() {
					$(this).removeClass("ui-corner-top").addClass(
							"ui-corner-all");
				}
			});
});