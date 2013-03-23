// Scraping for Teacher's information on cs.sfu.ca
// Written in Groovy 
// outputs JSON response, then sent into Solr for indexing

import groovy.json.*


// wrapper for outpputing JSON
def outputJson(data){
	def messages = []
	def names = data[0]
	def edu = data[1]	
	int i = 0

	names.each{
		name ->
		list = edu[i]
		def json = new JsonBuilder()
		json.message{
			header{
				firstname name[0]
				lastname name[1]
			}
			int num = 0
			println list
			list.each{
				eduInfo = it.split(",")

				education{
					"edu$num" it
				}
				countries{
					"country$num" eduInfo[3]
				}
				num+=1
			}
		}
		// println it
		// println edu[i]
		i+= 1
		messages.add(json)
	}
	return messages
}

def printJson(message){
	def prettyJson = JsonOutput.prettyPrint(message.toString())
	println prettyJson
}

// wrapper function for scraping data
def getData(webAddr){
	def raw_html = getRawDoc(webAddr)
	return getLinks(raw_html)    	
}


// gets html page of webaddress provided
def getRawDoc(webAddr){
	// println "Address scraping -> $webAddr"
	@Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.18')
	def doc = new XmlParser(new org.cyberneko.html.parsers.SAXParser()).parse(webAddr)
    return doc
}

// searches an html page for links
def getLinks(doc){
	def htmllinks = []
    if (doc != null) {
        def hyperlinks = doc.depthFirst().A['@href'].findAll {
            link ->
            // println("current link $link")
            if (link != null && link.length() > 1) {      //href can be null or just '/'
                //simply containing the cs.sfu.ca/People
                if (link.contains('cs.sfu.ca/People') && link.contains('http') && !link.contains('.pdf')) {
                    htmllinks.add(link)
                }
                else {
                    // '/' means extension to cs.sfu.ca, '//' new link on different address
                    if (link.charAt(0) == '/' && link.charAt(1) != '/')
                        htmllinks.add("http://www.cs.sfu.ca$link")
                }
            }
    	}
    	sleep(1000)  //politeness
	}
	return htmllinks
}

// gets html body of each link
def getDoc(webAddr){
	def links = filterPeopleOnly(getData(webAddr))
	def docs = []

	links.each{
		link ->
		println "Getting this doc ${link}"
		def d = getRawDoc(link)
		docs.add(d)
	} 
	return docs
}

def getInfo(docs){
	println("Getting Education Info...")
	def names = []
	def educations = []
	docs.each{
		page ->		
		def nameBlock = page.depthFirst().DIV.findAll { it.@class == 'title section'}	
		def eduBlock = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section' }
		
		if(!nameBlock.isEmpty()) 
			names.add(getName(nameBlock))
		if(!eduBlock.isEmpty())
		    educations.add(getEducInfo(eduBlock))
	}

	def data = []
	data.add(names)
	data.add(educations)
	return data
}

def getName(block){	
	def name = []
	block.each{
		def content = it.value()

		content.each{
			def header = it.value()				
			header.each{
				it.value().each{ 
				  def fullname = it.split(" ") 
				  fstname = fullname[0]
				  lstname = fullname[fullname.size()-1]
				  name.add(fstname)
				  name.add(lstname)
				}					
			}
		}
	}
	return name
}

def getEducInfo(block){
	def education = []			
	block.each{
		def content = it.value()
		content.each{
			def header = it.value()
			int i = 0
			header.each{
				if(it.name() == "H2" && it.value().contains("Education"))
				{
					def schools = header[i+1].value()
					// println schools
					schools.each{
						tag ->
						// println tag
						if(tag.getClass() != groovy.util.Node)
						{
							education.add(tag)
						}
						else if(tag.getClass() == groovy.util.Node && tag.name() == "LI")
						{
							tag.each{					
								li ->									
								education.add(li)
							}
						}
					}
				}
				i+=1
			}
		}
	}

	//refilter only want school names not BR[tags
	def edu = []
	education.collect{ 
		if(it.getClass() != groovy.util.Node)
			edu.add(it)
	}
	return edu
}

def filterPeopleOnly(links){
	def filtered = []
	links.collect{
		link ->
		if (link.contains("people/faculty"))
			filtered.add(link)
	}
	return filtered.unique()
}

def facultyAddr = ["http://www.cs.sfu.ca/people/emeriti.html", "http://www.cs.sfu.ca/people/faculty.html"]


// def webAddr = "http://www.cs.sfu.ca/people/faculty/uweglasser.html"

def bigTest(){
	def webAddr = "http://www.cs.sfu.ca/people/faculty.html"
	printJson(outputJson(getInfo(getDoc(webAddr))))	
}

def smallTest(){
	def webAddr1 = "http://www.cs.sfu.ca/people/faculty/dianacukierman.html"
	def webAddr2 = "http://www.cs.sfu.ca/people/faculty/uweglasser.html"	
	def output1 = getInfo(getRawDoc(webAddr1))
	def output2 = getInfo(getRawDoc(webAddr2))
	printJson(outputJson(output1))
	printJson(outputJson(output2))
}


smallTest()
// bigTest()