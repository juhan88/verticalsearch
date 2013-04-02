

// import groovyx.net.http.*
// import static groovyx.net.http.ContentType.*
// import static groovyx.net.http.Method.*



def simpleQuery(){
	print "Enter Query >>"
	// BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
	// def userInput = br.readLine()
	// println "Query: $userInput"

	
	def defaultQueryString = "http://localhost:8983/solr/select?q="	

	def userInput = "country:India"
	def type = "&wt=json"

	@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.6' )
	def http = new groovyx.net.http.HTTPBuilder(defaultQueryString+userInput+type)
	http.request(GET,JSON){		
		response.success = { resp, json ->
			json.response.docs.each{
				println it.professor
				println it.schools
			}	 	
    
		}
	}
	
}

simpleQuery()



