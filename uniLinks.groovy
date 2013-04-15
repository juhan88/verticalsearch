

import groovy.json.*

def getUniLinks(){
	f3 = new File("schools.txt")
	def schools = []
	def message = []
	wid = 5000
	f3.eachLine{
		uni ->

		def json = new JsonBuilder()
		
		p = uni.split(",")

		json{
			id wid 
			university p[0]
			uniLink p[1].trim()
		}
		message.add(json)
		wid += 1
	}	
	def prettyJson = JsonOutput.prettyPrint(message.toString())
	
	f = new File("university_links.json")
	f.delete()
	f.append(prettyJson)
}

getUniLinks()