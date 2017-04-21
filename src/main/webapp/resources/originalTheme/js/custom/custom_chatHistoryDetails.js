$(document).ready(function(){
	
	var selectedChatId = $('#selectedChatId').val();
	
	getChatHistoryDetails(selectedChatId);
	
	$('#backToChatWindowId1,#backToChatWindowId2').on("click",function(event){
		event.preventDefault();
		console.log('backToChatWindowId clicked');
		window.location.href=getApplicationRootPath()+"chat/showChatHistory"
	});
	
});

function getChatHistoryDetails(chatId){
	var data = {};
	data["gkey"] = chatId;
	data=JSON.stringify(data);
	ajaxHandler("POST", data, "application/json", getApplicationRootPath()+"chat/getChatHistory", 'json', getChatHistoryDetailsError, getChatHistoryDetailsSuccess,true);
}

function getChatHistoryDetailsSuccess(respose){
	console.log(respose);
	if(respose['STATUS'] == 'SUCCESS'){
		var chatHistory = respose['SERVER_DATA'];
		var userQuery = chatHistory['userQuery'];
		var userQueryTimeStamp = chatHistory['queryTimeStamp'];
		var systemResponse = JSON.parse(chatHistory['sysAnswer']);
		var systemResponseTimeStamp =  chatHistory['sysAnswerTimeStamp'];
		
		console.log(systemResponse);
		
		$('#userQueryId').val(userQuery).attr('disabled',true);
		
		if(jQuery.type(systemResponse) === "array"){
			
			var keys = null
			
			var indexArray = [];
			
			$.each(systemResponse,function(index,obj){
				
				console.log(obj);
				
				if(null == keys){
					var index = 1;
					keys = _.keys(obj);
					var tableHeadHtml = "";
					$.each(keys,function(index,key){
						if("CREATED_BY" == key || "MODIFIED_BY" == key || "CREATED_ON" == key || "GKEY" == key || "MODIFIED_ON" == key){
							indexArray.push(index);
						}else{
							tableHeadHtml += '<th>'+key+'</th>';
						}
						index++;
					})
					$('#querySolutionTable').find('thead').find('#tableHeadId').html(tableHeadHtml);
				}
				var values = _.values(obj);
				
				var tableRowHtml = '<tr>';
				
				var index = 1;
				
				$.each(values,function(index,value){
					if(_.indexOf(indexArray, index) == -1){
						if(jQuery.type(value) === "number"){
							var numberStr = value+"";
							console.log("length :"+numberStr.length);
							if(numberStr.length >12){
								value = moment(new Date(value)).format('DD-MMM-YYYY');
							}
						}
						tableRowHtml += '<td>'+value+'</td>';
					}
					index ++;
				});
				
				tableRowHtml += '</tr>';
				
				$('#querySolutionTable').find('tbody').append(tableRowHtml);
				
			});
			
		}
		
	}
}

function getChatHistoryDetailsError(error){
	console.log(error);
}