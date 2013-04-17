<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}

			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="status" role="complementary">
			<h1>Application Status</h1>
			<ul>
				<li>App version: <g:meta name="app.version"/></li>
				<li>Grails version: <g:meta name="app.grails.version"/></li>
				<li>Groovy version: ${GroovySystem.getVersion()}</li>
				<li>JVM version: ${System.getProperty('java.version')}</li>
				<li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
				<li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
				<li>Domains: ${grailsApplication.domainClasses.size()}</li>
				<li>Services: ${grailsApplication.serviceClasses.size()}</li>
				<li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
			</ul>
			<h1>Installed Plugins</h1>
			<ul>
				<g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
					<li>${plugin.name} - ${plugin.version}</li>
				</g:each>
			</ul>
		</div>
		<div id="page-body" role="main">
			<h1>Welcome to Grails</h1>
			<p>Congratulations, you have successfully started your first Grails application! At the moment
			   this is the default page, feel free to modify it to either redirect to a controller or display whatever
			   content you may choose. Below is a list of controllers that are currently deployed in this application,
			   click on each to execute its default action:</p>

			<div id="controller-list" role="navigation">
				<h2>Available Controllers:</h2>
				<ul>
					<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
						<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
					</g:each>
				</ul>
			</div>
			
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

			Cras facilisis tempor vestibulum. In ultricies pellentesque nunc, non hendrerit arcu fringilla pharetra. In aliquet molestie posuere. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent at urna in lectus venenatis dapibus ultrices non elit. Praesent vitae nunc commodo lectus ultrices tempus non sit amet metus. Nunc at pharetra arcu.

			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut in arcu felis, quis iaculis turpis. Proin eget leo eget purus tempus hendrerit eu et velit. Nullam sed mi mauris, nec commodo mauris. Proin lobortis massa a ante molestie et auctor magna sagittis. In eu erat eu lorem scelerisque vulputate. Donec eget turpis metus. In at tortor dolor.

			Cras eget odio enim, sollicitudin tincidunt turpis. Sed interdum viverra est vitae pretium. Cras ullamcorper pellentesque leo quis hendrerit. Fusce in viverra tellus. Ut luctus justo at neque volutpat molestie id quis urna. Maecenas risus quam, pretium nec condimentum sed, feugiat ac turpis. Quisque aliquet congue commodo.

			Proin in tellus ullamcorper justo tristique eleifend sed non urna. Donec id adipiscing est. Ut dictum lacus et mi ullamcorper a mattis tortor aliquet. Praesent pretium fermentum mattis. Nam sapien nisi, dapibus sed tempus vel, auctor vel purus. Cras ut dapibus lacus. Nullam feugiat enim id tellus vehicula non pretium arcu condimentum. Proin euismod est nec tellus vehicula interdum. Proin imperdiet metus ac dolor facilisis cursus.

			Sed imperdiet sem ac libero vulputate vel condimentum odio ultrices. Curabitur rhoncus mauris at mi posuere eu facilisis enim convallis. Fusce sodales leo quis ipsum aliquet elementum. Aenean vel mauris purus. Duis bibendum convallis enim, eget blandit nunc tristique vitae. Quisque ut elit nec felis dapibus luctus. Ut metus ante, interdum vitae sollicitudin sit amet, rhoncus at mauris. Vivamus eu dignissim orci. Vestibulum et nibh mauris. Aenean tristique pellentesque dictum. Suspendisse varius diam quis lorem rutrum sollicitudin. Proin risus diam, interdum non fringilla id, elementum sed leo. Donec tincidunt placerat quam, in ullamcorper erat semper id. Ut vulputate eros vel massa porttitor gravida. Etiam in enim ut massa aliquam commodo at vel erat. Mauris tristique, lacus eu accumsan accumsan, lacus libero auctor est, sed pellentesque purus dolor feugiat odio.

			Cras facilisis tempor vestibulum. In ultricies pellentesque nunc, non hendrerit arcu fringilla pharetra. In aliquet molestie posuere. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent at urna in lectus venenatis dapibus ultrices non elit. Praesent vitae nunc commodo lectus ultrices tempus non sit amet metus. Nunc at pharetra arcu.	
			
			
		</div>
	</body>
</html>
