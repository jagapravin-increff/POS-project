
function getinventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

function getbrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

function getproductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}


//BUTTON ACTIONS

function getItems(event)
{  var item=$("#inputBrandCategory").val();
console.log(item);
  var url=getproductUrl() + "/check/" + item;
  	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
  var str = ""
  console.log("hello");
  console.log(data);
  for (var item of data) {
    str += "<option>" + item["name"]  + "</option>"
  }

  document.getElementById("inputItem").innerHTML = str;

    getinventoryList();
    $("#inventory-edit-form select[name=brand]").val(item);
	   },
	   error: handleAjaxError
	});
  
}

function getItemsModal(event)
{
  var item=$("#inputBrandCategorymodal").val()
  console.log(item);
  var url=getproductUrl() + "/check/" + item;
  	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
  var str = ""
  console.log("hello");
  console.log(data);
  for (var item of data) {
    str += "<option>" + item["name"]  + "</option>"
  }
 // document.getElementById("inputItemmodal").innerHTML=str;
  //getinventoryList();
   // $("#inventory-edit-form select[name=brand]").val(item);
	   },
	   error: handleAjaxError
	});
}



function getbrandList(){
	var url = getbrandUrl();
	console.log(url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
  var str = ""
  for (var item of data) {
    str += "<option>" +item["brand"]  + "-"+ item["category"]  + "</option>"
  }
  document.getElementById("inputBrandCategory").innerHTML = str;
    document.getElementById("inputBrandCategorymodal").innerHTML = str;
    getinventoryList();
	   },
	   error: handleAjaxError
	});
}

function addinventory(event){
	//Set the values to update
	var $form = $("#inventory-form");
	console.log($("#inventory-form"));
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
	var id = $("#inventory-edit-form input[name=id_hidden]").val();	
	var b = $("#inventory-edit-form input[name=quantity]").val();	

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
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.item + '</td>'
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
	   console.log(data);
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
	$("#inputBrandCategorymodal").val(data.brand);	
	console.log($("#inputBrandCategorymodal").val());
	$("#inputItemmodal").val(data.item);	
	$("#inputQuantity").val(data.quantity);
	$("#inventory-edit-form input[name=id_hidden]").val(data.id);
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
    $('#inventoryFile').on('change', updateFileName);
    $('#inputBrandCategory').click(getItems);
    $('#inputBrandCategoryModal').click(getItemsModal);
    
}
$(document).ready(function(){
   $(".active").removeClass("active");
   $("#inv-nav").addClass("active");
});

$(document).ready(init);
$(document).ready(getbrandList);

