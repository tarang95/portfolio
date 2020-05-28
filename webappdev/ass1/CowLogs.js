var cowLogs = [];
var currentcowIndex = -1;
var latitude;
var longitude;

$(document).ready(function() {
	// Initialize the cows
	if(!loadFromLocalStorage()) {
		cowLogs = [];
		
		cowLogs.push({"name" : "Angus", "logs" : []});
		cowLogs.push({"name" : "Hereford", "logs" : []});
		cowLogs.push({"name" : "Brahman", "logs" : []});
		cowLogs.push({"name" : "Shorthorn", "logs" : []});
		cowLogs.push({"name" : "Brangus", "logs" : []});
	}

	// Display the cow logs in a list view
	for(var i = 0; i < cowLogs.length; i++) {
		$("#listview_cow_logs").append(
			"<li>" + 
				"<a href='javascript:void(0)' onclick='showLogPage(" + i + ")'>" + 
					cowLogs[i]["name"] + 
				"</a>" + 
			"</li>"
		);
	}

	$("#listview_cow_logs").listview("refresh");
});

// Load all data from local storage
function loadFromLocalStorage() {
	cowLogs = localStorage.getItem("cow_logs");

	if(cowLogs === null) {
		return false;
	}

	cowLogs = JSON.parse(cowLogs);
	return true;
}

// Save all data to local storage
function saveToLocalStorage() {
	localStorage.setItem("cow_logs", JSON.stringify(cowLogs));
}

// Clear the log entry form, resets it back to default
function clearLogEntryForm() {
	var displayIds = ["start_time", "first_break", "second_break", "end_time"];
	var buttonIds = ["button_start_time", "button_first_break", "button_second_break", "button_end_time"];

	// Reset the form to its original structure
	//$("#log_time").hide();
	//$("#button_log_time").show();
	$("#input_id").val("");
	$("#input_weight").val("");
	$("#input_age").val("");
	//$("#select_destination").val("");

	for(var i = 0; i < displayIds.length; i++) {
		$("#" + displayIds[i]).html("");
		$("#" + displayIds[i]).hide();
		$("#" + buttonIds[i]).show();
	}
}

// Show the log page for the current cow
function showLogPage(cowIndex) {
	currentcowIndex = cowIndex;

	$("#page_log_title").html(cowLogs[currentcowIndex]["name"])
	clearLogEntryForm();
	$(":mobile-pagecontainer").pagecontainer("change", "#page_log");
	if (navigator.geolocation)
    {
        navigator.geolocation.getCurrentPosition(successFunction, errorFunction);
    }
    else 
    {
        alert('It seems like Geolocation, which is required for this page, is not enabled in your browser.');
    } 
}

// Display the next cow log page
function showNextcowLogPage() {
	if(currentcowIndex + 1 >= cowLogs.length) {
		return;
	}

	showLogPage(currentcowIndex + 1);
}

// Display the previous cow log page
function showPreviouscowLogPage() {
	if(currentcowIndex - 1 < 0) {
		return;
	}

	showLogPage(currentcowIndex - 1);
}

// Record the current time into the display
/*function logTime() {
	var today = new Date();
	var date = today.getDate() + "/" + (today.getMonth() + 1) + "/" + today.getFullYear();
	var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

	$("#log_time").show();
	$("#button_log_time").hide();		
	$("#log_time").html(date + " " + time);
}*/

// Save the current log entry
function saveLogEntry() {
	// Get the entere details
	var id = $("#input_id").val();
	
	if(id == ""){
		alert("You must enter an ID ");
		alert("Log not saved. Please fix problems and try again.");
		return;
	}
	
	var weight = $("#input_weight").val();
	
	if(weight<0 || weight>10000 || weight == ""){
		alert("Weight should be between 1 to 10000kg ");
		alert("Log not saved. Please fix problems and try again.");
		return;
	}

	var age = $("#input_age").val();
	
	if(age<0 || age>200 || age == ""){
		alert("Age should be between 0 to 200 weeks ");
		alert("Log not saved. Please fix problems and try again.");
		return;
	}
	
	
	var condition = $("#select_condition").val();

	if(condition == "") {
		alert("You must select condition of cow");
		alert("Log not saved. Please fix problems and try again.");
		return;
	}
       



	var today = new Date();
	var date = today.getDate() + "/" + (today.getMonth() + 1) + "/" + today.getFullYear();
	var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();	
	var logTime= date+" "+time;
	//$("#log_time").html(date + " " + time);

	// If everything is complete save the log
	cowLogs[currentcowIndex]["logs"].push({
		"id" : id,
		"weight" : weight,
		"age"	:	age,
		"logTime" : logTime,
		"condition" : $("#select_condition").val(),
		"latitude" : latitude,
		"longitude" : longitude,
	});

	console.log(cowLogs[currentcowIndex]["logs"]);
	saveToLocalStorage();
	showLogPage(currentcowIndex);

	alert("Log saved");
}

// Display the log entries of each cow
function showLogEntriesPage() {
	
	$("#page_log_entries_title").html(cowLogs[currentcowIndex]["name"])
	$("#log_entries").html("");

	for(var i = 0; i < cowLogs[currentcowIndex]["logs"].length; i++) {
		var log = cowLogs[currentcowIndex]["logs"][i];
		$("#log_entries").append(log["id"] + ", ");
		$("#log_entries").append(log["logTime"] + ", ");
		$("#log_entries").append(log["latitude"]+", ");
		$("#log_entries").append(log["longitude"]+", ");
		$("#log_entries").append(log["weight"] + ", ");
		$("#log_entries").append(log["age"] + ", ");
		$("#log_entries").append(log["condition"] + "<br />");
	}

	$(":mobile-pagecontainer").pagecontainer("change", "#page_log_entries");
}

// Send the log entries of the current cow
function sendLogEntries() {
	// Confirm the user first
	if(!confirm("Do you want to send all logs, this has the effect of deleting all logs?")) {
		return;
	}

	cowLogs[currentcowIndex]["logs"] = [];
	saveToLocalStorage();

	alert("Logs sent");
	showLogPage(currentcowIndex);
}

function successFunction(position) 
{
    latitude = position.coords.latitude;
    longitude = position.coords.longitude;
    //alert('Your latitude is :'+latitude+' and longitude is '+longitude);
}

function errorFunction(position) 
{
    alert('Error!');
}