<html>

<head>
	<title>Search Results</title>
</head>

<body>
	<table border="1">
		<g:each in="${data}" status="i" var="thisData">
		  <tr>
				<td>${thisData}</td>
			</tr>
		</g:each>
	</table>
</body>

</html>