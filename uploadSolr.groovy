


def uploadJson(){
	// def jsonMessage = []
	// new File("outputjson").withReader{
	// 	reader->
	// 	while(reader.readLine() != null){
	// 		jsonMessage.add(reader.readLine())
	// 	}
	// }

	def url = "http://localhost:8983/solr/#/"

	// println jsonMessage
	// def upload = "curl $url -H 'Content-type:application/json' -d '$jsonMessage'".execute()
	// def confirm = "curl '$url?commit=true' ".execute()

// 	def testUpload = """
// 	curl $URL -H 'Content-type:application/json' -d '
// [
//   {
//     "id" : "MyTestDocument",
//     "title" : "This is just a test"
//   }
// ]'
// curl "$URL?commit=true"
// 	""" 

	def update = "curl http://localhost:8983/solr/update/json?commit=true --data-binary @prof.json -H 'Content-type:application/json'".execute()
	
	update.waitFor()
	println "return code: ${ update.exitValue()}"
	println "stderr: ${update.err.text}"
	println "stdout: ${update.in.text}"


}

uploadJson()

