<html>

<head>
	<title>Search Form</title>
</head>

<body>
	<table border="1">
		<g:each in="${allSearch}" status="i" var="thisSearch">
			<tr>
				<td>${thisSearch.lastname}</td>
				<td>${thisSearch.firstname}</td>
				<td>${thisSearch.area}</td>
				<td>${thisSearch.education}</td>
				<td>${thisSearch.research}</td>
				<td>${thisSearch.teaching}</td>
				<td>${thisSearch.courses}</td>
				<td>${thisSearch.publications}</td>
			</tr>
		</g:each>
	</table>
</body>

</html>