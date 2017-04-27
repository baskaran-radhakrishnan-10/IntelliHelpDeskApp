<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/resources/originalTheme/css/chat/normalize.css" var="NormalizeCss" />
<spring:url value="/resources/originalTheme/css/chat/style.css"	var="StyleCss" />
<spring:url value="/resources/originalTheme/js/custom/custom_chat.js"	var="customChatJs" />
<spring:url value="/resources/originalTheme/js/custom/custom_chatHistory.js"	var="customChatHistoryJs" />

<link rel="stylesheet" href="${NormalizeCss}">
<link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.3/jquery.mCustomScrollbar.min.css'>
<link rel="stylesheet" href="${StyleCss}">

<div class="chat">
	<div class="chat-title" style="">
		<h1 style="text-transform: uppercase;color: rgba(255, 255, 255, 0.68);"><%=session.getAttribute("USER_NAME")%></h1>
		<h2 style="text-transform: lowercase;color: rgb(255, 255, 255);"><%=session.getAttribute("USER_ID")%></h2>
		<figure class="avatar">
			<img src="http://s3-us-west-2.amazonaws.com/s.cdpn.io/156381/profile/profile-80_4.jpg">
		</figure>
	</div>
	<div class="messages">
		<div class="messages-content"></div>
	</div>
	<div class="message-box">
		<input type="text" class="message-input" id="send_chat_id" placeholder="Type message..."></input>
		<!-- <button type="submit" id="send_chat_id" class="message-submit" style="height: 44px;width: 103px;">Send</button> -->
	</div>
</div>

<!-- <div id="tableDivId"> -->
	<table id="querySolutionTable" class="table table-striped">
		<thead>
			<tr class="warning" id="tableHeadId"></tr>
		</thead>
		<tbody id="tableBodyId"></tbody>
	</table>
<!-- </div> -->


<div class="bg"></div>

<script	src='https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.3/jquery.mCustomScrollbar.concat.min.js'></script> 	
<script src="${customChatHistoryJs}"></script>

