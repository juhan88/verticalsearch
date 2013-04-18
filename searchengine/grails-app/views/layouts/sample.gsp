<!DOCTYPE html>
<html>

  <head>
     <link rel="shortcut icon" href="images/favicon.ico" >
     <title><g:layoutTitle default="Sample Queries"/></title>
	 <link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}" type="text/css">
     <r:layoutResources />
  </head>

	<%-- call to initialize map  --%>
	<body onload="initialize()">

		<%-- wrapper div for gradient effect --%>
		<div class="wrapper">

			<%-- main div for page content --%>
			<div class="content">

    		<tmpl:/nav />

				<tmpl:/header />

				<%--<tmpl:/header />--%>


				<%-- dynamic page content --%>				
    		<g:layoutBody />

				<tmpl:/footer />				

			</div>

		</div>

  </body>

</html>