$(document).ready(function(){

	$('#send_chat_id').on("click",function(event){

		console.log("send button clicked!!!");
		
		var message=$('.message-input').val();
		
		if(message.length > 0){
			
			sendMessage(message);
			
		}

	});
	
	$('.messages-content').mCustomScrollbar({
		callbacks:{
			onTotalScrollBack :function(){
				console.log("Scrolling started...");
			},
			onScroll :function(){
				console.log("scroll event completes");
			}
		}
	});

});

function sendMessage(message){
	var data = {};
	data["userQuery"] = message;
	data["queryTime"] = new Date();
	data=JSON.stringify(data);
	ajaxHandler("POST", data, "application/json", getApplicationRootPath()+"chat/addChatHistory", 'json', sendMessageError, sendMessageSuccess,true);
}

function sendMessageSuccess(serverData){
	console.log(serverData);
}

function sendMessageError(error){
	console.log(error);
}
