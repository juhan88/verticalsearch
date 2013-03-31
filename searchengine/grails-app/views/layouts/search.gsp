<!DOCTYPE html>
<html>

  <head>
    <title><g:layoutTitle default="Prof : Engine" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}" type="text/css">
		<script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyASI7VvnfYj_axUxRX8YHrxqLYORnuwMqo&sensor=false">
    </script>
		<g:javascript src="map.js" />
  </head>

	<body onload="initialize()">

    <tmpl:/nav />

		<tmpl:/header />	
	
    <g:layoutBody />

		<div id="map-canvas"></div>
									
		<tmpl:/footer />

  </body>

</html>