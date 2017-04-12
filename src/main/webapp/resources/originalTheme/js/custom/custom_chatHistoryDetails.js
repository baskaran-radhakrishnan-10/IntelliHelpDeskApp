$(document).ready(function(){
	
	var selectedChatId = $('#selectedChatId').val();
	
	getChatHistoryDetails(selectedChatId);
	
	$('#backToChatWindowId').on("click",function(event){
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
			
			$.each(systemResponse,function(index,obj){
				
				console.log(obj);
				if(null == keys){
					keys = _.keys(obj);
					var tableHeadHtml = "";
					$.each(keys,function(index,key){
						tableHeadHtml += '<th>'+key+'</th>';
					})
					$('#querySolutionTable').find('thead').find('#tableHeadId').html(tableHeadHtml);
				}
				var values = _.values(obj);
				
				var tableRowHtml = '<tr>';
				
				$.each(values,function(index,value){
					tableRowHtml += '<td>'+value+'</td>';
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