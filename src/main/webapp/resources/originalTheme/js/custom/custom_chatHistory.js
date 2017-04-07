var $messages = $('.messages-content'),d, h, m,i = 0;

$(document).ready(function(){

	$('#send_chat_id').on("click",function(event){

		console.log("send button clicked!!!");

		var message=$('.message-input').val();

		if(message.length > 0){
			
			var d = new Date()

			$('#send_chat_id').prop('disabled',true);

			$('<div class="message message-personal">' + message + '<div class="timestamp">' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds() +'</div></div>').appendTo($('.mCSB_container')).addClass('new');

			setDate();

			$('.message-input').val(null);

			updateScrollbar();
			
			$('<div class="message loading new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure><span></span></div>').appendTo($('.mCSB_container'));
			
			updateScrollbar();

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

function sendMessageSuccess(respose){
	console.log(respose);
	if(respose['STATUS'] == 'SUCCESS'){
		var serverData = respose['SERVER_DATA'];
		var sysAnswer = serverData['sysAnswer'];
		var sysAnswerTime = serverData['sysAnswerTime'];
		
		$('.message.loading').remove();
	    $('<div class="message new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure>' + sysAnswer + '</div>').appendTo($('.mCSB_container')).addClass('new');
	    setDate();
	    updateScrollbar();
		
		$('#send_chat_id').prop('disabled',false);
	}
}

function sendMessageError(error){
	console.log(error);
}

function setDate(){
	d = new Date()
	if (m != d.getMinutes()) {
		m = d.getMinutes();
		$('<div class="timestamp">' + d.getHours() + ':' + m + '</div>').appendTo($('.message:last'));
	}
}

function updateScrollbar() {
	$messages.mCustomScrollbar("update").mCustomScrollbar('scrollTo', 'bottom', {
		scrollInertia: 10,
		timeout: 0
	});
}
