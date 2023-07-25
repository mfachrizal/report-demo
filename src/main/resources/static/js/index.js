$(document).ready(function() {
	$("#buttonReport").click(function() {
		var win = window.open('rest/create-report', '_blank');
        win.focus();
	});
});
