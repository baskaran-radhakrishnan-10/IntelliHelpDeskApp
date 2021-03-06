
var $messages = $('.messages-content'),d, h, m,i = 0;

$(document).ready(function(){

	$( window ).resize(function() {
		positionMessageTags();
	});

	$('#send_chat_id').keypress(function(e) {
		
		if(e.which == 13) {
			
			var message=$('.message-input').val().trim();

			if(message.length > 0){

				$('<div class="message message-personal">' + message + '<div class="timestamp">' + moment(new Date()).format('hh:mm:ss') +'</div></div>').appendTo($('.mCSB_container')).addClass('new');

				$('.message-input').val("");

				$('<div class="message loading new"><figure class="avatar"><img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg" /></figure><span></span></div>').appendTo($('.mCSB_container'));

				updateScrollbar();

				sendMessage(message);

			}
		}
	});

	$('.messages-content').mCustomScrollbar({
		callbacks:{
			onTotalScrollBack :function(){},
			onScroll :function(){}
		}
	});

	$(document).on("click",".oldChatHistoryId",function(event){
		
		console.log($(document).find('.dateSeperatorId').text());

		var sterday = moment($(document).find('.dateSeperatorId').text().trim(), 'DD-MMM-YY').subtract(1, 'day');
		console.log("sterday :"+new Date(sterday));

		getChatHistory(new Date(sterday));

		$(this).remove();

	});

	if(null != sessionStorageObj){
		if(null == sessionStorageObj.getItem("CHAT_HISTORY")){
			getChatHistory(new Date());
		}
	}

	initChatHistoryPage();

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

		var noChatHistroyId = '#noChatHistoryId'+moment(sysAnswerTime).format('DD-MMM-YY');

		$('#mCSB_1_container').find(noChatHistroyId).remove();

		if("T" == isJson){
			$('<div class="message new tableDiv"><figure class="avatar_sysres"><img src="'+$('#sys_response_logo_path').val()+'" /></figure><a id="sysResponseMessageId"  >'+constructTable(sysAnswer,serverData['sysAnswerTimeStamp'])+'</a><div class="timestamp">' + moment(sysAnswerTime).format('hh:mm:ss') +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
		}else{
			$('<div class="message new"><figure class="avatar_sysres"><img src="'+$('#sys_response_logo_path').val()+'" /></figure>' + formatSysResponse(sysAnswer) + '<div class="timestamp">' + moment(sysAnswerTime).format('hh:mm:ss') +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
		}

		updateScrollbar();

		$('.mCSB_container').find('table').show();
		
		$('.mCSB_container').find('table').css("font-weight","normal");
		
		$('.mCSB_container').find('.tableDiv').css({"border-radius":"0px 0px 0px 0px","padding":"3px 3px 3px"});

		var chatHistoryObject = {};

		if(null != sessionStorageObj){

			if(null == sessionStorageObj.getItem("CHAT_HISTORY")){

				sessionStorageObj.setItem("CHAT_HISTORY",chatHistoryObject);

			}else{

				chatHistoryObject = sessionStorageObj.getItem("CHAT_HISTORY");

			}

			var innerDayHistoryObject = chatHistoryObject[moment(sysAnswerTime).format('DD-MMM-YY')];

			if(!(jQuery.type(innerDayHistoryObject) === "array")){

				innerDayHistoryObject = [];

			}

			innerDayHistoryObject.push(convertObject(serverData));

			chatHistoryObject[moment(sysAnswerTime).format('DD-MMM-YY')]=innerDayHistoryObject;

			sessionStorageObj.setItem("CHAT_HISTORY",chatHistoryObject);

		}
	}
}

function convertObject(chatHistoryEtyObj){
	var chatHistoryObj = {};
	chatHistoryObj['GKEY']=chatHistoryEtyObj['rowId'];
	chatHistoryObj['IS_SYSRESPONSE_JSON']=chatHistoryEtyObj['jsonSysResponse'];
	chatHistoryObj['SYSTEM_ANSWER']=chatHistoryEtyObj['sysAnswer'];
	chatHistoryObj['SYSTEM_ANSWER_TIME']=chatHistoryEtyObj['sysAnswerTime'];
	chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']=chatHistoryEtyObj['sysAnswerTimeStamp'];
	chatHistoryObj['USER_QUERY']=chatHistoryEtyObj['userQuery'];
	chatHistoryObj['USER_QUERY_TIME']=chatHistoryEtyObj['queryTime'];
	chatHistoryObj['USER_QUERY_TIME_STAMP']=chatHistoryEtyObj['queryTimeStamp'];
	return chatHistoryObj;
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

function initChatHistoryPage(){
	if(null != sessionStorageObj){
		if(null != sessionStorageObj.getItem("CHAT_HISTORY")){
			var chatHistoryObject = sessionStorageObj.getItem("CHAT_HISTORY");
			console.log(chatHistoryObject);

			var chatDateArray = _.keys(chatHistoryObject).sort() ;

			$.each(chatDateArray,function(index,chatMonth){

				console.log("Month :"+chatMonth);

				var chatHistoryArray = chatHistoryObject[chatMonth];

				console.log(chatHistoryArray);

				var todayId = "dateSeperatorId"+chatMonth;

				var noChatHistroyId = "noChatHistoryId"+chatMonth;



				if(index == 0){
					var sterday = moment(chatMonth, 'DD-MMM-YY').subtract(1, 'day');
					var sterdayId = "oldChatHistoryId"+moment(new Date(sterday)).format('DD-MMM-YY');
					$('<div class="message message-personal oldChatHistoryId" onclick="" id="'+sterdayId+'" style="background: linear-gradient(120deg, rgb(80, 208, 247), rgb(1, 234, 223));border-radius: 5px;left: -44%;position: relative;cursor: pointer;">'+moment(new Date(sterday)).format('DD-MMM-YY')+' Messages </div>').appendTo($('.mCSB_container'));
				}

				$('<div class="message message-personal dateSeperatorId" id="'+todayId+'" style="background: linear-gradient(120deg, #495049, #495049);border-radius: 5px 5px 5px 5px;left: -48%;position: relative;margin: -2px 0;">'+chatMonth+'</div>').appendTo($('.mCSB_container'));

				if(jQuery.type(chatHistoryArray) === "array"){
					$.each(chatHistoryArray,function(index,chatHistoryObj){
						var userQueryTime = new Date(chatHistoryObj['USER_QUERY_TIME_STAMP']);
						var userQuery = chatHistoryObj['USER_QUERY'];
						var sysAnswerTime = new Date(chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']);
						var sysAnswer = chatHistoryObj['SYSTEM_ANSWER'];
						var isJson = chatHistoryObj['IS_SYSRESPONSE_JSON'];
						var gkey = chatHistoryObj['GKEY'];
						$('<div class="message message-personal" id="'+"chatId" + chatHistoryObj['USER_QUERY_TIME_STAMP']+'">' + userQuery + '<div class="timestamp">' + moment(userQueryTime).format('hh:mm:ss') +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
						if("T" == isJson){
							$('<div class="message new tableDiv" id="'+"chatId" + chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']+'"><figure class="avatar_sysres"><img src="'+$('#sys_response_logo_path').val()+'" /></figure><a id="sysResponseMessageId"  >'+constructTable(sysAnswer,chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP'])+'</a><div class="timestamp">' + moment(sysAnswerTime).format('hh:mm:ss') +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
						}else{
							$('<div class="message new" id="'+"chatId" + chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']+'"><figure class="avatar_sysres"><img src="'+$('#sys_response_logo_path').val()+'" /></figure>' + formatSysResponse(sysAnswer) + '<div class="timestamp">' + moment(sysAnswerTime).format('hh:mm:ss') +'</div></div>').appendTo($('.mCSB_container')).addClass('new');
						}
					})
				}else{
					$('<div class="message message-personal noChatHistroyId" id="'+noChatHistroyId+'" style="background: linear-gradient(120deg, #f5ee0e, #f3ec1c);border-radius: 0px 0px 0px 0px;color: #e82626;left: -38%;"> No Chat History Available For ' +  chatMonth + '</div>').appendTo($('.mCSB_container')).addClass('new');
				}
				updateScrollbar();

				positionMessageTags();

			});

			//updateScorllBarPosition();

			$('.mCSB_container').find('table').show();
			
			$('.mCSB_container').find('table').css("font-weight","normal");
			
			$('.mCSB_container').find('.tableDiv').css({"border-radius":"0px 0px 0px 0px","padding":"3px 3px 3px"});

		}
	}

}

function getChatHistorySuccess(respose,inputData){

	console.log(inputData);

	console.log(respose);

	var chatHistoryObject = {};

	if(null != sessionStorageObj){

		if(null == sessionStorageObj.getItem("CHAT_HISTORY")){

			sessionStorageObj.setItem("CHAT_HISTORY",chatHistoryObject);

		}else{

			chatHistoryObject = sessionStorageObj.getItem("CHAT_HISTORY");

		}

	}

	if(respose['STATUS'] == 'SUCCESS'){

		var sterday = moment(inputData['startDate'], 'DD-MMM-YY').subtract(1, 'day');		

		var serverData = respose['SERVER_DATA'];

		var todayId = "dateSeperatorId"+inputData['startDate'];

		var sterdayId = "oldChatHistoryId"+moment(new Date(sterday)).format('DD-MMM-YY');

		var noChatHistroyId = "noChatHistoryId"+inputData['startDate'];

		$('.mCSB_container').prepend('<div class="message message-personal dateSeperatorId" id="'+todayId+'" style="background: linear-gradient(120deg, #495049, #495049);border-radius: 5px 5px 5px 5px;left: -48%;position: relative;margin: -2px 0;">'+inputData['startDate']+'</div>');

		$('<div class="message message-personal oldChatHistoryId" onclick="" id="'+sterdayId+'" style="background: linear-gradient(120deg, rgb(80, 208, 247), rgb(1, 234, 223));border-radius: 5px;left: -44%;position: relative;cursor: pointer;">'+moment(new Date(sterday)).format('DD-MMM-YY')+' Messages </div>').insertBefore($('.mCSB_container #'+todayId));

		serverData.length == 0 ? chatHistoryObject[inputData['startDate']] = "NO CHAT HISTORY" : chatHistoryObject[inputData['startDate']] = serverData;

		if(null != sessionStorageObj.getItem("CHAT_HISTORY")){

			sessionStorageObj.setItem("CHAT_HISTORY",chatHistoryObject);

		}

		if(serverData.length == 0){

			$('<div class="message message-personal" id="'+noChatHistroyId+'" style="background: linear-gradient(120deg, #f5ee0e, #f3ec1c);border-radius: 0px 0px 0px 0px;color: #e82626;left: -38%;"> No Chat History Available For ' +  inputData['startDate'] + '</div>').insertAfter($('.mCSB_container #'+todayId)).addClass('new');

			positionMessageTags();

			return false;

		}

		var html = "";

		var messageId = "chatId";

		$.each(serverData,function(index,chatHistoryObj){

			var userQueryTime = new Date(chatHistoryObj['USER_QUERY_TIME_STAMP']);

			var userQuery = chatHistoryObj['USER_QUERY'];

			var sysAnswerTime = new Date(chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']);

			var sysAnswer = chatHistoryObj['SYSTEM_ANSWER'];

			var isJson = chatHistoryObj['IS_SYSRESPONSE_JSON'];

			var gkey = chatHistoryObj['GKEY'];

			var startEleId = (index == 0) ? todayId : messageId;

			$('<div class="message message-personal" id="'+"chatId" + chatHistoryObj['USER_QUERY_TIME_STAMP']+'">' + userQuery + '<div class="timestamp">' + moment(userQueryTime).format('hh:mm:ss') +'</div></div>').insertAfter($(document).find('.mCSB_container #'+startEleId)).addClass('new');

			messageId = "chatId" + chatHistoryObj['USER_QUERY_TIME_STAMP'];

			if("T" == isJson){
				$('<div class="message new tableDiv" id="'+"chatId" + chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']+'"><figure class="avatar_sysres"><img src="'+$('#sys_response_logo_path').val()+'" /></figure><a id="sysResponseMessageId" >'+constructTable(sysAnswer,chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP'])+'</a><div class="timestamp">' + moment(sysAnswerTime).format('hh:mm:ss') +'</div></div>').insertAfter($(document).find('.mCSB_container #'+messageId)).addClass('new');
			}else{
				$('<div class="message new" id="'+"chatId" + chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP']+'"><figure class="avatar_sysres"><img src="'+$('#sys_response_logo_path').val()+'" /></figure>' + formatSysResponse(sysAnswer) + '<div class="timestamp">' + moment(sysAnswerTime).format('hh:mm:ss') +'</div></div>').insertAfter($(document).find('.mCSB_container #'+messageId)).addClass('new');
			}

			messageId = "chatId" + chatHistoryObj['SYSTEM_ANSWER_TIME_STAMP'];

			updateScrollbar();

			positionMessageTags();

		});

		//$(document).find('#dateSeperatorId').css("border-bottom","6px solid rgba(0, 0, 0, 0.3)");

		$('.mCSB_container').find('table').show();
		
		$('.mCSB_container').find('table').css("font-weight","normal");
		
		$('.mCSB_container').find('.tableDiv').css({"border-radius":"0px 0px 0px 0px","padding":"3px 3px 3px"});
		
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

function updateScorllBarPosition(){
	if(null != sessionStorageObj){
		if(null != sessionStorageObj.getItem("SCROLL_TOP_POSITION_DIV") && null != sessionStorageObj.getItem("SCROLL_TOP_POSITION_BAR")){
			console.log("SCROLL_TOP_POSITION_DIV :"+sessionStorageObj.getItem("SCROLL_TOP_POSITION_DIV"));
			console.log("SCROLL_TOP_POSITION_BAR :"+sessionStorageObj.getItem("SCROLL_TOP_POSITION_BAR"));
			$(document).find('#mCSB_1_container').css('top',sessionStorageObj.getItem("SCROLL_TOP_POSITION_DIV"));
			$(document).find('#mCSB_1_dragger_vertical').css('top',sessionStorageObj.getItem("SCROLL_TOP_POSITION_BAR"));
		}
	}
}


function constructTable(sysRes,sysResTime){

	var systemResponse = JSON.parse(sysRes);

	if(jQuery.type(systemResponse) === "array"){

		var keys = null

		var indexArray = [];

		var cloneQuerySolutionTabel=$('#querySolutionTable').clone();

		$(cloneQuerySolutionTabel).find('thead').find('#tableHeadId').html("");

		$(cloneQuerySolutionTabel).find('tbody').html("");

		$.each(systemResponse,function(index,obj){

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
				$(cloneQuerySolutionTabel).find('thead').find('#tableHeadId').html(tableHeadHtml);
				$(cloneQuerySolutionTabel).css({"width":"100%","left": "0%" , "margin-bottom" : "0px","background-color" : "rgb(222, 222, 222)"});
			}
			var values = _.values(obj);

			var tableRowHtml = '<tr>';

			var index = 1;

			$.each(values,function(index,value){
				if(_.indexOf(indexArray, index) == -1){
					if(jQuery.type(value) === "number"){
						var numberStr = value+"";
						//console.log("length :"+numberStr.length);
						if(numberStr.length >12){
							value = moment(new Date(value)).format('DD-MMM-YYYY');
						}
					}
					tableRowHtml += '<td>'+value+'</td>';
				}
				index ++;
			});

			tableRowHtml += '</tr>';

			$(cloneQuerySolutionTabel).find('tbody').append(tableRowHtml);

		});

		var newTableId = $(cloneQuerySolutionTabel).attr("id")+sysResTime;

		$(cloneQuerySolutionTabel).attr("id",newTableId);

		return $(cloneQuerySolutionTabel)[0].outerHTML;

	}
}

function positionMessageTags(){

	var messageDivWidth = $('.messages').width() / 2 ;

	var oldChatHistoryDivWidth = $('.oldChatHistoryId').width() / 2;

	var dateSeperatorDivWidth = $('.dateSeperatorId').width() / 2;

	var noChatHistroyDivWidth = $('.noChatHistroyId').width() / 2;

	$('.oldChatHistoryId').css("left",parseInt(-(messageDivWidth-oldChatHistoryDivWidth))+"px");

	$('.dateSeperatorId').css("left",parseInt(-(messageDivWidth-dateSeperatorDivWidth))+"px");

	$('.noChatHistroyId').css("left",parseInt(-(messageDivWidth-noChatHistroyDivWidth))+"px");
	
}

function formatSysResponse(sysRes){
	var returnData=sysRes;
	if(jQuery.type(sysRes) === "number"){
		sysRes = sysRes+"";
	}
	if (null != sysRes && sysRes.length > 11 && "Invalid Date" != new Date(parseInt(sysRes))) {
		returnData=moment(new Date(parseInt(sysRes))).format('DD-MMM-YYYY');
	}
	return returnData;
}	

