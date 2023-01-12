
function getinventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addinventory(event){
	//Set the values to update
	var $form = $("#inventory-form");
	console.log($("#inventory-form"));
	console.log("Hello");
	console.log("DF");
	var json = toJson($form);
	var url = getinventoryUrl();
    console.log(json,url);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getinventoryList();  
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateinventory(event){
	$('#edit-inventory-modal').modal('toggle');
	//Get the ID
	var id = $("#inventory-edit-form input[name=productid]").val();	
	var b = $("#inventory-edit-form input[name=quantity]").val();	
	console.log("d");
	console.log(id,b);
	var url = getinventoryUrl() + "/" + id;
	

	//Set the values to update
	var $form = $("#inventory-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getinventoryList();   
	   },
	   error: handleAjaxError
	});

	return false;
}


function getinventoryList(){
	var url = getinventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayinventoryList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteinventory(id){
	var url = getinventoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getinventoryList();  
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getinventoryUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayinventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		console.log(e);
		var buttonHtml = ' <button onclick="displayEditinventory(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditinventory(id){
	var url = getinventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayinventory(data);   
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-inventory-modal').modal('toggle');
}

function displayinventory(data){
	$("#inventory-edit-form input[name=inventory]").val(data.inventory);	
	$("#inventory-edit-form input[name=category]").val(data.category);	
	$("#inventory-edit-form input[name=id]").val(data.id);	
	$('#edit-inventory-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addinventory);
	$('#update-inventory').click(updateinventory);
	$('#refresh-data').click(getinventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getinventoryList);

