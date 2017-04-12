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
	
	$(document).on("click",'#sysResponseMessageId',function(event){
		console.log("anchor tag clicked!!!");
		//event.preventDefault();
		console.log($(this));
		
		var href=$(this)[0]['href'];
		console.log("href :"+href);
		
	});
	
	getChatHistory(new Date());

});

function sendMessage(message){
	var data = {};
	data["userQuery"] = message;
	data["queryTime"] = new Date();
	data["queryTimeStamp"]=new Date().getTime();
	data=JSON.stringify(data);
	ajaxHandler("POST", data, "application/json", getApplicationRootPath()+"chat/addChatHistory", 'json', sendMessageError, sendMessageSuccess,true);
}

function sendMessageSuccess(respose){
	console.log(respose);
	if(respose['STATUS'] == 'SUCCESS'){
		
		var serverData = respose['SERVER_DATA'];
		var sysAnswer = serverData['sysAnswer'];
		var sysAnswerTime = new Date(serverData['sysAnswerTimeStamp']);
		var isJson = serverData['jsonSysResponse'];
		var gkey = serverData['rowId'];
		
		$('.message.loading').remove();
	    //$('<div class="message new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure><a id="sysResponseMessageId" href="'+gkey+'" >Please click this link , to see system response</a></div>').appendTo($('.mCSB_container')).addClass('new');
	    
	    if("T" == isJson){
			$('<div class="message new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure><a id="sysResponseMessageId" href="getChatHistory/'+gkey+'" >Please click this link , to see system response</a><div class="timestamp">' + sysAnswerTime.getHours() + ':' + sysAnswerTime.getMinutes() + ':' + sysAnswerTime.getSeconds() +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
		}else{
			$('<div class="message new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure>' + sysAnswer + '<div class="timestamp">' + sysAnswerTime.getHours() + ':' + sysAnswerTime.getMinutes() + ':' + sysAnswerTime.getSeconds() +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
		}
	    
	    setDate();
	    updateScrollbar();
		
		$('#send_chat_id').prop('disabled',false);
	}
}

function sendMessageError(error){
	console.log(error);
}

function getChatHistory(date){
	
	/*var year = date.getFullYear();
	var month = date.getMonth();
	if(month.length < 2){
		month = '0'+month;
	}
	var day = date.getDate();
	if(day.length < 2){
		day = '0'+day;
	}*/	
	var startDate = moment(date).format('DD-MMM-YY');
	var endDate = startDate;
	
	var dataObj = {};
	dataObj['startDate'] = startDate;
	dataObj['endDate'] = endDate;
	dataObj['GET_CHAT_HISTORY_BY_DATE']=true;
	dataObj=JSON.stringify(dataObj);
	ajaxHandler("POST", dataObj, "application/json", getApplicationRootPath()+"chat/getChatHistory", 'json', getChatHistoryError, getChatHistorySuccess,true);
}

function getChatHistorySuccess(respose){
	
	if(respose['STATUS'] == 'SUCCESS'){
		
		var serverData = respose['SERVER_DATA'];
		
		$.each(serverData,function(index,chatHistoryObj){
			
			var userQueryTime = new Date(chatHistoryObj['USER_QUERY_TIME_STAMP']);
			
			var userQuery = chatHistoryObj['USER_QUERY'];
			
			var sysAnswerTime = new Date(chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']);
			
			var sysAnswer = chatHistoryObj['SYSTEM_ANSWER'];
			
			var isJson = chatHistoryObj['IS_SYSRESPONSE_JSON'];
			
			//console.log(" isJson :"+isJson);
			
			var gkey = chatHistoryObj['GKEY'];
			
			//console.log(chatHistoryObj);
			
			$('<div class="message message-personal">' + userQuery + '<div class="timestamp">' + userQueryTime.getHours() + ':' + userQueryTime.getMinutes() + ':' + userQueryTime.getSeconds() +'</div></div>').appendTo($('.mCSB_container')).addClass('new');

			if("T" == isJson){
				$('<div class="message new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure><a id="sysResponseMessageId" href="getChatHistory/'+gkey+'" >Please click this link , to see system response</a><div class="timestamp">' + sysAnswerTime.getHours() + ':' + sysAnswerTime.getMinutes() + ':' + sysAnswerTime.getSeconds() +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
			}else{
				$('<div class="message new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure>' + sysAnswer + '<div class="timestamp">' + sysAnswerTime.getHours() + ':' + sysAnswerTime.getMinutes() + ':' + sysAnswerTime.getSeconds() +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
			}
			
			updateScrollbar();
			
		});
	}
}

function getChatHistoryError(error){
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
