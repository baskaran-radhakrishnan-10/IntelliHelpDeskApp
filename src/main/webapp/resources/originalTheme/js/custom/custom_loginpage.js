$(document).ready(function(){
	
	if(null != sessionStorageObj){
		if(null != sessionStorageObj.getItem("CHAT_HISTORY")){
			sessionStorageObj.removeItem("CHAT_HISTORY");
		}
	}
		
	
});