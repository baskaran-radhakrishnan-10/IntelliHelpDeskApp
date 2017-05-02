var chatHistoryObject = null;

$(document).ready(function(){
	
	$('.messages-content').mCustomScrollbar({
		callbacks:{
			onTotalScrollBack :function(){},
			onScroll :function(){}
		}
	});
	
	$(document).on("click",".oldChatHistoryId",function(event){
		var sterday = moment($(document).find('.dateSeperatorId').text().trim(), 'DD-MMM-YY').subtract(1, 'day');
		getChatHistory(new Date(sterday));
		$(this).remove();
	});
	
	if(null == chatHistoryObject){
		
		chatHistoryObject = new ChatHistoryOperation();
		
		chatHistoryObject.onDocumentLoad();
		
	}
	
});

function ChatHistoryOperation(){
	
	var $messages = $('.messages-content'),d, h, m,i = 0;
	
	var oldHistoryMessageTag = '<div class="message message-personal oldChatHistoryId" id="{STERDAY_ID}" style="background: linear-gradient(120deg, rgb(80, 208, 247), rgb(1, 234, 223));border-radius: 5px;position: relative;cursor: pointer;">{OLD_DATE_MESSAGE}</div>';
	
	var actualDateMessageTag = '<div class="message message-personal dateSeperatorId" id="{TODAY_ID}" style="background: linear-gradient(120deg, #495049, #495049);border-radius: 5px 5px 5px 5px;position: relative;margin: -2px 0;">{ACTUAL_DATE}</div>';
	
	var userQueryMessageTag = '<div class="message message-personal userQueryId" id="{USER_QUERY_ID}">{USER_QUERY}<div class="timestamp">{USER_QUERY_TIME}</div></div>';
	
	var systemResponseMessageTag = '<div class="message new" id="{SYS_RESPONSE_ID}"><figure class="avatar_sysres"><img src="{SYS_LOGO_PATH}" /></figure><a>{SYSTEM_RESPONSE_MESSAGE}</a><div class="timestamp">{SYS_RESPONSE_TIME}</div></div>';
	
	var systemResponseTableMessageTag = '<div class="message new tableDiv" id="{SYS_RESPONSE_ID}"><figure class="avatar_sysres"><img src="{SYS_LOGO_PATH}" /></figure><a>{SYSTEM_RESPONSE_MESSAGE}</a><div class="timestamp">{SYS_RESPONSE_TIME}</div></div>';
	
	var noChatHistoryMessageTag = '<div class="message message-personal" id="{NO_CHAT_HISORY_ID}" style="background: linear-gradient(120deg, #f5ee0e, #f3ec1c);border-radius: 0px 0px 0px 0px;color: #e82626;">{NO_CHAT_HISTORY_MESSAGE}</div>';
	
}

ChatHistoryOperation.prototype.onDocumentLoad = function(){
	
	console.log("This Method will be called when ever document is loading or page refreshed");
	
	if(null != sessionStorageObj){
		if(null == sessionStorageObj.getItem("CHAT_HISTORY")){
			ChatHistoryOperation.prototype.getChatHistory(new Date());
		}
	}
	
	ChatHistoryOperation.prototype.initChatHistoryPage();

};

ChatHistoryOperation.prototype.getChatHistory = function(date){
	
	var dateObj = moment(date).format('DD-MMM-YY');
	
	var dataObj = {};
	
	dataObj['startDate'] = dateObj;
	
	dataObj['endDate'] = dateObj;
	
	dataObj['GET_CHAT_HISTORY_BY_DATE']=true;
	
	dataObj=JSON.stringify(dataObj);
	
	ajaxHandler("POST", dataObj, "application/json", getApplicationRootPath()+"chat/getChatHistory", 'json', ChatHistoryOperation.prototype.getChatHistoryError, ChatHistoryOperation.prototype.getChatHistorySuccess,true);
}

ChatHistoryOperation.prototype.getChatHistorySuccess = function(respose,inputData){

	var chatHistoryObject = {};

	if(null != sessionStorageObj){

		if(null == sessionStorageObj.getItem("CHAT_HISTORY")){

			sessionStorageObj.setItem("CHAT_HISTORY",chatHistoryObject);

		}else{

			chatHistoryObject = sessionStorageObj.getItem("CHAT_HISTORY");

		}

	}

	if(respose['STATUS'] == 'SUCCESS'){

		var serverData = respose['SERVER_DATA'];
		
		serverData.length == 0 ? chatHistoryObject[inputData['startDate']] = "NO CHAT HISTORY" : chatHistoryObject[inputData['startDate']] = serverData;

		if(null != sessionStorageObj.getItem("CHAT_HISTORY")){

			sessionStorageObj.setItem("CHAT_HISTORY",chatHistoryObject);

		}
	}
};

ChatHistoryOperation.prototype.getChatHistoryError = function(error){
	console.log(error);
};

ChatHistoryOperation.prototype.sendMessage = function(message){
	var data = {};
	data["userQuery"] = message;
	data["queryTime"] = new Date();
	data["queryTimeStamp"]=new Date().getTime();
	data=JSON.stringify(data);
	ajaxHandler("POST", data, "application/json", getApplicationRootPath()+"chat/addChatHistory", 'json', ChatHistoryOperation.prototype.sendMessageError, ChatHistoryOperation.prototype.sendMessageSuccess,true);
}

ChatHistoryOperation.prototype.sendMessageSuccess = function(respose){
	
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
};

ChatHistoryOperation.prototype.sendMessageError = function(error){
	console.log(error);
};

ChatHistoryOperation.prototype.initChatHistoryPage = function (){
	
	if(null != sessionStorageObj){
		
		if(null != sessionStorageObj.getItem("CHAT_HISTORY")){
			
			var chatHistoryObject = sessionStorageObj.getItem("CHAT_HISTORY");

			var chatDateArray = _.keys(chatHistoryObject).sort() ;

			$.each(chatDateArray,function(index,chatMonth){

				var chatHistoryArray = chatHistoryObject[chatMonth];

				var todayId = "dateSeperatorId"+chatMonth;

				var noChatHistroyId = "noChatHistoryId"+chatMonth;

				if(index == 0){
					
					var sterday = moment(chatMonth, 'DD-MMM-YY').subtract(1, 'day');
					
					var sterdayId = "oldChatHistoryId"+moment(new Date(sterday)).format('DD-MMM-YY');
					
					var tagMessage = moment(new Date(sterday)).format('DD-MMM-YY')+' Messages';
					
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
						
					});
					
				}else{
					$('<div class="message message-personal noChatHistroyId" id="'+noChatHistroyId+'" style="background: linear-gradient(120deg, #f5ee0e, #f3ec1c);border-radius: 0px 0px 0px 0px;color: #e82626;left: -38%;"> No Chat History Available For ' +  chatMonth + '</div>').appendTo($('.mCSB_container')).addClass('new');
				}
				
				updateScrollbar();

				positionMessageTags();

			});

			$('.mCSB_container').find('table').show();
			
			$('.mCSB_container').find('table').css("font-weight","normal");
			
			$('.mCSB_container').find('.tableDiv').css({"border-radius":"0px 0px 0px 0px","padding":"3px 3px 3px"});

		}
	}

}

ChatHistoryOperation.prototype.positionMessageTags = function(){

	var messageDivWidth = $('.messages').width() / 2 ;

	var oldChatHistoryDivWidth = $('.oldChatHistoryId').width() / 2;

	var dateSeperatorDivWidth = $('.dateSeperatorId').width() / 2;

	var noChatHistroyDivWidth = $('.noChatHistroyId').width() / 2;

	$('.oldChatHistoryId').css("left",parseInt(-(messageDivWidth-oldChatHistoryDivWidth))+"px");

	$('.dateSeperatorId').css("left",parseInt(-(messageDivWidth-dateSeperatorDivWidth))+"px");

	$('.noChatHistroyId').css("left",parseInt(-(messageDivWidth-noChatHistroyDivWidth))+"px");
	
};

ChatHistoryOperation.prototype.constructTable=function(sysRes,sysResTime){

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
};

ChatHistoryOperation.prototype.convertObject = function(chatHistoryEtyObj){
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

ChatHistoryOperation.prototype.formatSysResponse =function(sysRes){
	var returnData=sysRes;
	if (null != sysRes && sysRes.length > 11 && "Invalid Date" != new Date(parseInt(sysRes))) {
		returnData=moment(new Date(parseInt(sysRes))).format('DD-MMM-YYYY');
	}
	return returnData;
}