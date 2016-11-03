<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Search Results</title>
		<style>	
			html {
				background: #005da4;
				font-family: 'Century Gothic', CenturyGothic, AppleGothic, sans-serif;
			}
			#searchButton {
				position:relative;
                padding:6px 15px;
                left:-8px;
                border:2px solid #df9a16;
                background-color:#df9a16;
                color:#fafafa;
			}	
			#searchButton:hover {
				background-color:#787877;
				border:2px solid #787877;
                color:#207cca;
			}
			#searchBox {
				padding: 8px 5px;
                border:0px solid #dbdbdb;
			}
			a {
				color: #fdb813;
				<!--color: #E2B326;-->
			}
			h3 {
				color: #fdb813;
			}
			p {
				color: #FFFFFF;
			}
		</style>
	</head>
	<body>
		<div style="text-align:left"> <a href="main.jsp"><img src="SmallLogo.png"></a></div>
		<form action="printResult" method="get" style="text-align:left">
	        <input type="text" id="searchBox" placeholder="Search..." name="searchQuery" required="required" style="width: 400px">
	        <input type="submit" id="searchButton" value="Search">
		</form>
		
 		<c:if test="${empty printUrlInfo}">
 			<h3>No Search Results "<%=request.getAttribute("userQuery")%>"</h3>
 		</c:if>
 		
 		<c:if test="${!empty printUrlInfo}">
 			<h3>Search Results for "<%=request.getAttribute("userQuery")%>"</h3>
 			<table cellpadding="20" style="text-align:left">
 				<c:forEach items="${printUrlInfo}" var="url">
 					<tr>
 						<td>
 							<a href="<c:out value="${url.getUrl()}"/>" >${url.getUrl()}</a>
 							<p>${url.getText()}</p>
 						</td>
 					</tr>
 				</c:forEach>
 			</table>
 		</c:if>
 		
	</body>
</html>