<!DOCTYPE html>
<html>

  <head>
    <title><g:layoutTitle default="Prof : Engine" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}" type="text/css">
		<script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyASI7VvnfYj_axUxRX8YHrxqLYORnuwMqo&sensor=false">
    </script>
		<g:javascript src="map.js" />
		<g:javascript library="jquery" />
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
				
				<%-- dynamic page content --%>				
    		<g:layoutBody />
									
				<tmpl:/footer />				
				
			</div>
				
		</div>

  </body>

</html>