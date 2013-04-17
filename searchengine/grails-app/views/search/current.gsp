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
			
			// remove placeholder text from text field
			$("#address").click(function(){
				if ($(this).val("start here")){
					$(this).val("")
				}
			})
			
			// enable 'enter' key to invoke search
			$("#address").keypress(function(e){
				if (e.which == 13){
					codeAddress()
				}
			})
			
			// clear the search field when Clear All is invoked
			$('input[value="Clear All"]').click(function(){
				$("#address").val("")
			})
			
		})
	</script>
		
	<%-- search field --%>
	<div id="querybox">
		<g:textField name="address" value="start here" />
		
		<%-- submit query to map --%>
		<g:actionSubmit value="OK" onclick="codeAddress()" />
		
		<%-- clear map markers --%>
		<g:actionSubmit value="Clear All" onclick="deleteMarkers()" />
	</div>
	
	<%-- interactive map --%>	
	<div id="map-canvas"></div>
			
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
	
	<div><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut in arcu felis, quis iaculis turpis. Proin eget leo eget purus tempus hendrerit eu et velit. Nullam sed mi mauris, nec commodo mauris. Proin lobortis massa a ante molestie et auctor magna sagittis. In eu erat eu lorem scelerisque vulputate. Donec eget turpis metus. In at tortor dolor.

	Cras eget odio enim, sollicitudin tincidunt turpis. Sed interdum viverra est vitae pretium. Cras ullamcorper pellentesque leo quis hendrerit. Fusce in viverra tellus. Ut luctus justo at neque volutpat molestie id quis urna. Maecenas risus quam, pretium nec condimentum sed, feugiat ac turpis. Quisque aliquet congue commodo.

	Proin in tellus ullamcorper justo tristique eleifend sed non urna. Donec id adipiscing est. Ut dictum lacus et mi ullamcorper a mattis tortor aliquet. Praesent pretium fermentum mattis. Nam sapien nisi, dapibus sed tempus vel, auctor vel purus. Cras ut dapibus lacus. Nullam feugiat enim id tellus vehicula non pretium arcu condimentum. Proin euismod est nec tellus vehicula interdum. Proin imperdiet metus ac dolor facilisis cursus.

	Sed imperdiet sem ac libero vulputate vel condimentum odio ultrices. Curabitur rhoncus mauris at mi posuere eu facilisis enim convallis. Fusce sodales leo quis ipsum aliquet elementum. Aenean vel mauris purus. Duis bibendum convallis enim, eget blandit nunc tristique vitae. Quisque ut elit nec felis dapibus luctus. Ut metus ante, interdum vitae sollicitudin sit amet, rhoncus at mauris. Vivamus eu dignissim orci. Vestibulum et nibh mauris. Aenean tristique pellentesque dictum. Suspendisse varius diam quis lorem rutrum sollicitudin. Proin risus diam, interdum non fringilla id, elementum sed leo. Donec tincidunt placerat quam, in ullamcorper erat semper id. Ut vulputate eros vel massa porttitor gravida. Etiam in enim ut massa aliquam commodo at vel erat. Mauris tristique, lacus eu accumsan accumsan, lacus libero auctor est, sed pellentesque purus dolor feugiat odio.

	Cras facilisis tempor vestibulum. In ultricies pellentesque nunc, non hendrerit arcu fringilla pharetra. In aliquet molestie posuere. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent at urna in lectus venenatis dapibus ultrices non elit. Praesent vitae nunc commodo lectus ultrices tempus non sit amet metus. Nunc at pharetra arcu.

	Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut in arcu felis, quis iaculis turpis. Proin eget leo eget purus tempus hendrerit eu et velit. Nullam sed mi mauris, nec commodo mauris. Proin lobortis massa a ante molestie et auctor magna sagittis. In eu erat eu lorem scelerisque vulputate. Donec eget turpis metus. In at tortor dolor.

	Cras eget odio enim, sollicitudin tincidunt turpis. Sed interdum viverra est vitae pretium. Cras ullamcorper pellentesque leo quis hendrerit. Fusce in viverra tellus. Ut luctus justo at neque volutpat molestie id quis urna. Maecenas risus quam, pretium nec condimentum sed, feugiat ac turpis. Quisque aliquet congue commodo.

	Proin in tellus ullamcorper justo tristique eleifend sed non urna. Donec id adipiscing est. Ut dictum lacus et mi ullamcorper a mattis tortor aliquet. Praesent pretium fermentum mattis. Nam sapien nisi, dapibus sed tempus vel, auctor vel purus. Cras ut dapibus lacus. Nullam feugiat enim id tellus vehicula non pretium arcu condimentum. Proin euismod est nec tellus vehicula interdum. Proin imperdiet metus ac dolor facilisis cursus.

	Sed imperdiet sem ac libero vulputate vel condimentum odio ultrices. Curabitur rhoncus mauris at mi posuere eu facilisis enim convallis. Fusce sodales leo quis ipsum aliquet elementum. Aenean vel mauris purus. Duis bibendum convallis enim, eget blandit nunc tristique vitae. Quisque ut elit nec felis dapibus luctus. Ut metus ante, interdum vitae sollicitudin sit amet, rhoncus at mauris. Vivamus eu dignissim orci. Vestibulum et nibh mauris. Aenean tristique pellentesque dictum. Suspendisse varius diam quis lorem rutrum sollicitudin. Proin risus diam, interdum non fringilla id, elementum sed leo. Donec tincidunt placerat quam, in ullamcorper erat semper id. Ut vulputate eros vel massa porttitor gravida. Etiam in enim ut massa aliquam commodo at vel erat. Mauris tristique, lacus eu accumsan accumsan, lacus libero auctor est, sed pellentesque purus dolor feugiat odio.

	Cras facilisis tempor vestibulum. In ultricies pellentesque nunc, non hendrerit arcu fringilla pharetra. In aliquet molestie posuere. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent at urna in lectus venenatis dapibus ultrices non elit. Praesent vitae nunc commodo lectus ultrices tempus non sit amet metus. Nunc at pharetra arcu.	
	
	Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut in arcu felis, quis iaculis turpis. Proin eget leo eget purus tempus hendrerit eu et velit. Nullam sed mi mauris, nec commodo mauris. Proin lobortis massa a ante molestie et auctor magna sagittis. In eu erat eu lorem scelerisque vulputate. Donec eget turpis metus. In at tortor dolor.

	Cras eget odio enim, sollicitudin tincidunt turpis. Sed interdum viverra est vitae pretium. Cras ullamcorper pellentesque leo quis hendrerit. Fusce in viverra tellus. Ut luctus justo at neque volutpat molestie id quis urna. Maecenas risus quam, pretium nec condimentum sed, feugiat ac turpis. Quisque aliquet congue commodo.

	Proin in tellus ullamcorper justo tristique eleifend sed non urna. Donec id adipiscing est. Ut dictum lacus et mi ullamcorper a mattis tortor aliquet. Praesent pretium fermentum mattis. Nam sapien nisi, dapibus sed tempus vel, auctor vel purus. Cras ut dapibus lacus. Nullam feugiat enim id tellus vehicula non pretium arcu condimentum. Proin euismod est nec tellus vehicula interdum. Proin imperdiet metus ac dolor facilisis cursus.

	Sed imperdiet sem ac libero vulputate vel condimentum odio ultrices. Curabitur rhoncus mauris at mi posuere eu facilisis enim convallis. Fusce sodales leo quis ipsum aliquet elementum. Aenean vel mauris purus. Duis bibendum convallis enim, eget blandit nunc tristique vitae. Quisque ut elit nec felis dapibus luctus. Ut metus ante, interdum vitae sollicitudin sit amet, rhoncus at mauris. Vivamus eu dignissim orci. Vestibulum et nibh mauris. Aenean tristique pellentesque dictum. Suspendisse varius diam quis lorem rutrum sollicitudin. Proin risus diam, interdum non fringilla id, elementum sed leo. Donec tincidunt placerat quam, in ullamcorper erat semper id. Ut vulputate eros vel massa porttitor gravida. Etiam in enim ut massa aliquam commodo at vel erat. Mauris tristique, lacus eu accumsan accumsan, lacus libero auctor est, sed pellentesque purus dolor feugiat odio.

	Cras facilisis tempor vestibulum. In ultricies pellentesque nunc, non hendrerit arcu fringilla pharetra. In aliquet molestie posuere. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent at urna in lectus venenatis dapibus ultrices non elit. Praesent vitae nunc commodo lectus ultrices tempus non sit amet metus. Nunc at pharetra arcu.

	Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut in arcu felis, quis iaculis turpis. Proin eget leo eget purus tempus hendrerit eu et velit. Nullam sed mi mauris, nec commodo mauris. Proin lobortis massa a ante molestie et auctor magna sagittis. In eu erat eu lorem scelerisque vulputate. Donec eget turpis metus. In at tortor dolor.

	Cras eget odio enim, sollicitudin tincidunt turpis. Sed interdum viverra est vitae pretium. Cras ullamcorper pellentesque leo quis hendrerit. Fusce in viverra tellus. Ut luctus justo at neque volutpat molestie id quis urna. Maecenas risus quam, pretium nec condimentum sed, feugiat ac turpis. Quisque aliquet congue commodo.

	Proin in tellus ullamcorper justo tristique eleifend sed non urna. Donec id adipiscing est. Ut dictum lacus et mi ullamcorper a mattis tortor aliquet. Praesent pretium fermentum mattis. Nam sapien nisi, dapibus sed tempus vel, auctor vel purus. Cras ut dapibus lacus. Nullam feugiat enim id tellus vehicula non pretium arcu condimentum. Proin euismod est nec tellus vehicula interdum. Proin imperdiet metus ac dolor facilisis cursus.

	Sed imperdiet sem ac libero vulputate vel condimentum odio ultrices. Curabitur rhoncus mauris at mi posuere eu facilisis enim convallis. Fusce sodales leo quis ipsum aliquet elementum. Aenean vel mauris purus. Duis bibendum convallis enim, eget blandit nunc tristique vitae. Quisque ut elit nec felis dapibus luctus. Ut metus ante, interdum vitae sollicitudin sit amet, rhoncus at mauris. Vivamus eu dignissim orci. Vestibulum et nibh mauris. Aenean tristique pellentesque dictum. Suspendisse varius diam quis lorem rutrum sollicitudin. Proin risus diam, interdum non fringilla id, elementum sed leo. Donec tincidunt placerat quam, in ullamcorper erat semper id. Ut vulputate eros vel massa porttitor gravida. Etiam in enim ut massa aliquam commodo at vel erat. Mauris tristique, lacus eu accumsan accumsan, lacus libero auctor est, sed pellentesque purus dolor feugiat odio.

	Cras facilisis tempor vestibulum. In ultricies pellentesque nunc, non hendrerit arcu fringilla pharetra. In aliquet molestie posuere. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent at urna in lectus venenatis dapibus ultrices non elit. Praesent vitae nunc commodo lectus ultrices tempus non sit amet metus. Nunc at pharetra arcu.</p></div>

</body>

</html>