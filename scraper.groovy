// Scraping for Teacher's information on cs.sfu.ca
// Written in Groovy 
// outputs JSON response, then sent into Solr for indexing

import groovy.json.*


// wrapper for outpputing JSON
def outputJson(data){
	def messages = []	
	
	def names = data[0]	
	def edu = data[1]	
	def courses = data[2]
	int i = 0
	
	//loop thru all the names found
	names.each{
		name ->
		eduList = edu[i]
		courseList = courses[i]

		def json = new JsonBuilder()
		json.message{
			professor{
				title name[2]
				firstname name[0]
				lastname name[1]
			}
			int num = 0
			
			eduList.each{				
				def eduInfo = it.split(",")		
				// println eduInfo.size()		
				//NEED TO FIX BUG
				//NOT ALL PROFS LIST in the following order 
				//DEGREE, FACULTY, UNIVERSITY, COUNTRY, YEAROFCOMPLETION							
				//SOME HAVE 3 OR 4 OR 5				
				if(eduInfo.size() == 4){
					"education$num"{
						dissertation eduInfo[0]
						school eduInfo[1]
						country eduInfo[2]
						yearcomplete eduInfo[3]
					}
				}
				else if(eduInfo.size() > 3){
					"education$num"{
						dissertation eduInfo[0]
						faculty eduInfo[1]
						school eduInfo[2]
						country eduInfo[3]
						yearcomplete eduInfo[4]	
					}
				}
				else if(eduInfo.size() == 3)
				{
					"education$num"{
						dissertation eduInfo[0]						
						school eduInfo[1]
						country eduInfo[2]
						
					}
				}
				
				num+=1
			}
			
			if(!courseList.isEmpty() && name[1] != "Pearce"){
				num = 0			
				courseList.each{					
					def course = it.split(" ")
					if(course.size() > 2){
						def cname = course[0] + " " + course[1]				
						def topic = joinString(course, 2, course.size()-1)
						
						"course$num"{
							courseName cname
							courseTopic topic
						}
					}
					num+=1
				}
			}
			//special case for Pearce
			else if( name[1] == "Pearce"){
				courseList.each{
					c ->
					num = 0
					if (c.contains("Computing Science"))
					{	
						
						def d = c.split(",")
						d.each{
							def cname  = getStevenPearce(it)
							"course$num"{
								courseName "CMPT " + cname
							}
							num+=1
						}
					}
				}
			}
		}
		// println it
		// println edu[i]
		i+= 1
		messages.add(json)
	}
	return messages
}

def getStevenPearce(string){
	def newString = []
	def add = false
	if(string.contains("(")){
		string.each{
			if(add)
				newString.add(it)
			if(it == '(')
			{
				add = true
			}
		}
		return newString.join()
	}
	if(string.contains(")"))
	{
		return string[0..-2]
	}
	return string
}

def printJson(message){
	def prettyJson = JsonOutput.prettyPrint(message.toString())
	println prettyJson  //dumps output to screen
	return prettyJson	//dumps output for writing to file
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

//wrapper for all logic
def getInfo(docs){
	println("Retrieving Info...")
	def names = []
	def educations = []
	def courses = []	
	docs.each{
		page ->		
		def nameBlock = page.depthFirst().DIV.findAll { it.@class == 'title section'}	
		def position = page.depthFirst().DIV.findAll { it.@class == 'text '}
		def eduBlock = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section' }
		def courseBlock = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section'}

		
		if(!nameBlock.isEmpty() && !position.isEmpty())
			names.add(getName(nameBlock) + getTitle(position))		
		if(!eduBlock.isEmpty())
		    educations.add(getEducInfo(eduBlock))
		if(!courseBlock.isEmpty())
			courses.add(getCourses(courseBlock))


	}

	
	def data = []
	data.add(names)
	data.add(educations)
	data.add(courses)	
	return data
}

def getTitle(block){
	def position
	block.each{
		def content = it.value()
		content.each{			
			if(it.name() == 'P'){
				def header = it.value().text()	
				if(header.contains("School of Computing Science") && !header.contains("DIV")){
					def p = header.split(",")
					position = p[0]
				}
			}
		}
	}
	return position
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

def getCourses(block){
	def courses = []			
	block.each{
		def content = it.value()
		content.each{
			def header = it.value()
			int i = 0
			header.each{
				if(it.name() == "H2" && it.value().contains("Recently taught courses"))
				{
					def schools = header[i+1].value()
					
					schools.each{
						tag ->
						// println tag
						if(tag.getClass() != groovy.util.Node)
						{
							courses.add(tag)
						}
						else if(tag.getClass() == groovy.util.Node && tag.name() == "LI")
						{
							tag.each{					
								li ->									
								courses.add(li)
							}
						}
					}
				}
				i+=1
			}
		}
	}
	
	return ignoreBRTags(courses)
}

def joinString(name, start, end){
	def joined = []
	(start..end).each{ joined.add(name[it]) }
	return joined.join(" ")
}

def getEducInfo(block){
	def education = []			
	block.each{
		def content = it.value()
		content.each{
			def header = it.value()
			int i = 0
			def found = false

			header.each{				
				if(it.name() == "H2" && it.value().contains("Education"))
				{
					found = true
					// println header[i+1]
				}
				if(it.name() == "P" && found){
					school = it.value()	
					// println "+++++++++"			
					// println school.text()
					// println "+++++++++"
					if(school.getClass() != groovy.util.Node)
					{
						education.add(school)
					}

				}
				else if (it.name() == "UL" && found){
					school = it.value()					
					school.each{
						li -> 									
						education.add(li.text())
					}
				}
				i+=1
			}
		}
	}

	//refilter only want school names not BR[tags

	def edu = education
	def results = []
	edu.each{
		degree ->
		
		if(degree.getClass() == groovy.util.NodeList){
			degree.each{
				if(it.getClass() != groovy.util.Node){	
					results.add(it)
				}
			}
		}		
	}


	// println results.size()
	if(results.isEmpty() && !education.isEmpty())
		results = education		
	
		

	println results
	return results
}


def ignoreBRTags(list){
	def filtered = []
	list.collect{ 
		if(it.getClass() != groovy.util.Node)
			filtered.add(it)
	}
	return filtered
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
	def data = getInfo(getDoc(webAddr))	
	
	f = new File("output.txt")
	f.append(printJson(outputJson(data)))

}

def smallTest(){
	// def webAddr4 = "http://www.cs.sfu.ca/people/faculty/dianacukierman.html"
	// def webAddr1 = "http://www.cs.sfu.ca/people/faculty/mikeevans.html"
	// def webAddr1 = "http://www.cs.sfu.ca/people/faculty/ryandarcy.html"
	// def webAddr2 = "http://www.cs.sfu.ca/people/faculty/RameshKrishnamurti.html"
	// def webAddr3 = "http://www.cs.sfu.ca/people/faculty/uweglasser.html"	
	// def webAddr2 = "http://www.cs.sfu.ca/people/faculty/stevenpearce.html"
	def webAddr1 = "http://www.cs.sfu.ca/people/faculty/valentinekabanets.html"
	def output1 = getInfo(getRawDoc(webAddr1))
	// def output2 = getInfo(getRawDoc(webAddr2))	
	// def output3 = getInfo(getRawDoc(webAddr3))
	// def output4 = getInfo(getRawDoc(webAddr4))
	
	printJson(outputJson(output1))
	// printJson(outputJson(output2))
	// printJson(outputJson(output3))
	// printJson(outputJson(output4))
}


smallTest()
// bigTest()