<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="search"/>
</head>

<body>
	
	<%-- jQuery --%>
	<script type='text/javascript'>
	  
		// ready the DOM
		$(document).ready(function(){
			
			// enable 'enter' key to invoke search
			$("#address").keypress(function(e){
				if (e.which == 13){
					//codeAddress()
				}
			})
			
			// clear the search field when Clear All is invoked
			$('input[value="Clear All"]').click(function(){
				$("#address").val("")
			})
			
		})
	</script>
		
	<div id="querybox">
		
		<div class="header">
			<h1 id="bigTitle">Prestigious</h1>
			<h3 id="subTitle">sfu professor ranklist.</h3>
		</div>
		
		<%-- ajax form to send query to searchcontroller.myquery --%>
		<g:formRemote name="myForm" update="results" url="[controller:'Search', action:'mainQuery']">		

			<%-- search field --%>
			<g:textField name="address" placeholder="start here" />

			<%-- submit query to map --%>
			<g:actionSubmit value="BAM!" />
				
			<%-- clear map markers --%>
			<%--<g:actionSubmit value="Clear All" onclick="deleteMarkers()" />--%>
			
		</g:formRemote>

	</div>
    address
	<div class="additionalpages">

	    <a href="./nextpage">1</a>
    </div>

	<%-- populate div with query results --%>
	<div id="results"></div>
	
	<%-- interactive map --%>
	<div id="map-canvas"></div>

</body>

</html>