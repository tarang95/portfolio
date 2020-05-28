var cityLogs = [];
var currentCityIndex = -1;
var service_url = "http://localhost:3000";
var curr_position = {
	lat: 0,
	long: 0
};
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
	navigator.geolocation.getCurrentPosition(onSuccess, onError);
}

$(document).ready(function () {
	// Initialize the cities
	if (!loadFromLocalStorage()) {
		cityLogs = [];
		cityLogs.push({
			"name": "Perth",
			"logs": []
		});
		cityLogs.push({
			"name": "Brisbane",
			"logs": []
		});
		cityLogs.push({
			"name": "Sydney",
			"logs": []
		});
		cityLogs.push({
			"name": "Melbourne",
			"logs": []
		});
		cityLogs.push({
			"name": "Adelaide",
			"logs": []
		});
	}

	// Display the city logs in a list view
	for (var i = 0; i < cityLogs.length; i++) {
		$("#listview_city_logs").append(
			"<li>" +
			"<a href='javascript:void(0)' onclick='showLogPage(" + i + ")'>" +
			cityLogs[i]["name"] +
			"</a>" +
			"</li>"
		);
	}
	$("#listview_city_logs").listview("refresh");
});

// Load all data from local storage
function loadFromLocalStorage() {
	cityLogs = localStorage.getItem("city_logs");

	if (cityLogs === null) {
		return false;
	}

	cityLogs = JSON.parse(cityLogs);
	return true;
}

// Save all data to local storage
function saveToLocalStorage() {
	localStorage.setItem("city_logs", JSON.stringify(cityLogs));
}

// Clear the log entry form, resets it back to default
function clearLogEntryForm() {
	var displayIds = ["start_time", "first_break", "second_break", "end_time"];
	var buttonIds = ["button_start_time", "button_first_break", "button_second_break", "button_end_time"];

	// Reset the form to its original structure
	$("#your_contact").val("");
	$("#your_invoice").val("");

	$("#log_time").html("");
	$("#button_log_time").show();
}

// Show the log page for the current city
function showLogPage(cityIndex) {
	currentCityIndex = cityIndex;

	$("#page_log_title").html(cityLogs[currentCityIndex]["name"])
	clearLogEntryForm();
	$(":mobile-pagecontainer").pagecontainer("change", "#page_log");
}

// Display the next city page
function showNextCityLogPage() {
	if (currentCityIndex + 1 >= cityLogs.length) {
		return;
	}

	showLogPage(currentCityIndex + 1);
}

// Display the previous city page
function showPreviousCityLogPage() {
	if (currentCityIndex - 1 < 0) {
		return;
	}

	showLogPage(currentCityIndex - 1);
}

// Record the current time into the display
function logTime() {
	var today = new Date();
	var date = today.getDate() + "/" + (today.getMonth() + 1) + "/" + today.getFullYear();
	var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

	$("#log_time").show();
	$("#button_log_time").hide();
	$("#log_time").html(date + " " + time);
}

// Save the current log entry
function saveLogEntry() {
	// Get the entered details
	var contact = $("#your_contact").val();
	navigator.geolocation.getCurrentPosition(onSuccess, onError);
	if (contact == "") {
		alert("You must enter a valid contact name");
		alert("Log not saved. Please fix problems and try again.");
		return;
	}

	var invoice = $("#your_invoice").val();

	if (invoice == "") {
		alert("You must enter an invoice number");
		alert("Log not saved. Please fix problems and try again.");
		return;
	}

	var logTime = $("#log_time").html();

	if (logTime == "") {
		// Show error if the time stamp not clicked
		alert("You must click the time button.");
		alert("Logs dont saved. Please fix it and try it again.");
		return;
	}

	// If all good save the log
	cityLogs[currentCityIndex]["logs"].push({
		"contact": contact,
		"invoice": invoice,
		"time": logTime,
		"latitude": curr_position.lat,
		"longitude": curr_position.long,
		"destination": $("#select_your_destination").val()
	});
	saveToLocalStorage();
	showLogPage(currentCityIndex);

	alert("Logs have been saved");
}

// Show the entries of each city
function showLogEntriesPage() {
	$("#page_log_entries_title").html(cityLogs[currentCityIndex]["name"])
	$("#log_entries").html("");
	for (const i in cityLogs[currentCityIndex]["logs"]) {
		console.log(cityLogs[currentCityIndex]["logs"][i]);
		var log = cityLogs[currentCityIndex]["logs"][i];
		console.log(log);
		console.log(log.time);
		$("#log_entries").append("<tr><td>" + cityLogs[currentCityIndex]["name"] +
			"</td><td>" + log.time +
			"</td><td>" + log.latitude +
			"</td><td> " + log.longitude +
			"</td><td>" + log.contact +
			"</td><td>" + log.invoice +
			"</td><td>" + log.destination +
			"</td></tr>");
	}
	$(":mobile-pagecontainer").pagecontainer("change", "#page_log_entries");
}

// Send the log entries of the current city
function sendLogEntries() {
	// first Confirm the user 
	if (!confirm("Do you want to send all logs?")) {
		return;
	} else {
		var data = {};
		data.city_logs = cityLogs[currentCityIndex]["logs"];
		$.post(service_url + "/city/" + cityLogs[currentCityIndex].name + "/log", data, function (res) {
			if (res.ok) {
				cityLogs[currentCityIndex]["logs"] = [];
				saveToLocalStorage();
				alert("Logs have been sent");
				showLogPage(currentCityIndex);
			} else {
				alert("Logs can not be sent");
				showLogPage(currentCityIndex);
			}
		});
	}
}
var onSuccess = function (position) {
	curr_position = {
		"lat": position.coords.latitude,
		"long": position.coords.longitude
	};
}

function onError(error) {
	curr_position = {
		"lat": 0,
		"long": 0
	};
}

function onGetCloudLogs() {
	$.get(service_url + "/city/search/" + cityLogs[currentCityIndex].name, function (res) {
		$("#cloud_log_list").html("");
		if (res.ok) {
			for (const i in res.data) {
				$("#cloud_log_list").append("<tr><td>" + cityLogs[currentCityIndex].name +
					"</td><td>" + res.data[i].time +
					"</td><td>" + res.data[i].latitude +
					"</td><td> " + res.data[i].longitude +
					"</td><td>" + res.data[i].contact +
					"</td><td>" + res.data[i].invoice +
					"</td><td>" + res.data[i].destination +
					"</td></tr>");
			}
		} else {
			alert("No logs found");
		}
	});
	location.hash = "page_cloud_city_logs";
}