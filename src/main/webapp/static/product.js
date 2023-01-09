
function getproductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

//BUTTON ACTIONS
function addproduct(event){
	//Set the values to update
	var $form = $("#product-form");
	console.log($("#product-form"));
	console.log("Hello");
	var json = toJson($form);
	var url = getproductUrl();
	console.log(json);
        console.log(url,"K");
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getproductList();  
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateproduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var product_id=$("#product-edit-form input[name=product_id]").val();
	var url = getproductUrl() + "/" + product_id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);
	console.log(json);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getproductList();   
	   },
	   error: handleAjaxError
	});

	return false;
}


function getproductList(){
	var url = getproductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayproductList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteproduct(product_id){
	var url = getproductUrl() + "/" + product_id;
    console.log(url);
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getproductList();  
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
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
	var url = getproductUrl();

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

function displayproductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		console.log("hello");
		console.log(e);
		var buttonHtml = '<button onclick="deleteproduct(' + e.product_id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditproduct(' + e.product_id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.product_id + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>'  + e.barcode + '</td>'
		+ '<td>'  + e.mrp + '</td>'
        + '<td>'  +e.brand_id+'</td'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditproduct(product_id){
    console.log(product_id);
	var url = getproductUrl() + "/" + product_id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayproduct(data);   
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
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
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-product-modal').modal('toggle');
}

function displayproduct(data){
	$("#product-edit-form input[name=name]").val(data.name);	
	$("#product-edit-form input[name=barcode]").val(data.barcode);	
	$("#product-edit-form input[name=brand_id]").val(data.brand_id);	
	$("#product-edit-form input[name=mrp]").val(data.mrp);	
	$("#product-edit-form input[name=product_id]").val(data.product_id);	

	$('#edit-product-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-product').click(addproduct);
	$('#update-product').click(updateproduct);
	$('#refresh-data').click(getproductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getproductList);

