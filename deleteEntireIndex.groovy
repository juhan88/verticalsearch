

def deleteAllIndex(){

	println "Deleting Entire Index on local host..."
	def deleteCommand = "curl http://localhost:8983/solr/update?commit=true --data '<delete><query>*:*</query></delete>' -H 'Content-type:text/xml; charset=utf-8' "

	// def commitCommand = "curl http://localhost:8983/solr/update?commit=true --data '<commit/>' -H 'Content-type:text/xml; charset=utf-8'"
	def delete = deleteCommand.execute()
	println delete.text
	// delete.waitFor()

	// println "return code: ${ delete.exitValue()}"
	// // println "stderr: ${delete.err.text}"
	// println "stdout: ${delete.in.text}"

	// def commit = commitCommand.execute()
	// commit.waitFor()
	// println "return code: ${ commit.exitValue()}"
	// // println "stderr: ${commit.err.text}"
	// println "stdout: ${commit.in.text}"

	println "Check if its empty http://localhost:8983/solr/#/collection1/query"

}

deleteAllIndex()