<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Lougle Search</title>	
		<style>	
			html {
				background: #005da4;
				<!--background: #3a3b95;-->
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
		</style>
	</head>
	<body>
		<div style="text-align:center" > <a href="main.jsp"><img src="Logo.png"></a></div>
		
		<form action="printResult" method="get" style="text-align:center">
	        <input type="text" id="searchBox" placeholder="Search..." name="searchQuery" required="required" style="width: 400px">
	        <input type="submit" id="searchButton" value="Search">
		</form>	
	</body>
</html>