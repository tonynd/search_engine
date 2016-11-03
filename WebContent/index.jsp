<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Lougle Initialization</title>	
		<style>	
			html {
				background: #005da4;
				font-family: 'Century Gothic', CenturyGothic, AppleGothic, sans-serif;
			}
			#initButton {
				position:relative;
                padding:6px 15px;
                left:-8px;
                border:2px solid #df9a16;
                background-color:#df9a16;
                color:#fafafa;
			}	
			#initButton:hover {
				background-color:#787877;
				border:2px solid #787877;
                color:#207cca;
			}
			h3 {
				text-align: center;
				color: #E2B326;
			}
		</style>
	</head>
	<body>
		<div style="text-align:center" > <a href="index.jsp"><img src="Logo.png"></a></div>
		
		<h3>Please initialize Index HashMap before proceeding.</h3>
		<!-- <h3>This process will take approximately 1-2 minute(s).</h3> -->
		<form action="initIndex" method="get" style="text-align:center">
	        <input type="submit" id="initButton" value="Initialize Index" style="font-size: 24px">
		</form>	
	</body>
</html>