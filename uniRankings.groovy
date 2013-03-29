
import groovy.json.*


def getRawDoc(webAddr){
	// println "Address scraping -> $webAddr"
	@Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.18')
	def doc = new XmlParser(new org.cyberneko.html.parsers.SAXParser()).parse(webAddr)
    return doc
}

def getRankings(doc){
	def universities = doc.depthFirst().TD.findAll { it.@class == 'views-field views-field-title'}	
	def ranking = []
	universities.each{
		uni ->
		uni.each{
			ranking.add(it.value().text())
		}
	}

	return ranking
}

def outputJson(data){
	
	def rank = 1
	def idschools = 1000
	def message = []
	data.each{
		name ->
		println "Retrieving Ranking for $name"
		def json = new JsonBuilder()	

		json{
			id idschools
			uniRank rank
			uniName name 
		}
		message.add(json)
		rank += 1
		idschools +=1
	}
	
	def prettyJson = JsonOutput.prettyPrint(message.toString())
	return prettyJson
}


def outputRankings(){

	def webAddr = "http://www.topuniversities.com/university-rankings/university-subject-rankings/2012/computer-science-and-information-systems"

	def data = outputJson(getRankings(getRawDoc(webAddr)))
	

	f = new File("ranking.json")
	f.delete()
	f.append(data)

}

outputRankings()

