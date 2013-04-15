// Scraping for Teacher's information on cs.sfu.ca
// Written in Groovy 
// outputs JSON response, then sent into Solr for indexing

import groovy.json.*


// wrapper for outpputing JSON
def outputJson(data,links){
	def messages = []	
	def names = data[0]	
	def edu = data[1]	
	def recentlyTaughtCourses = data[2]	
	def masterSchoolList = []
	def papers = data[3]
	int i = 0
	
	
	//loop thru all the names found
	names.each{
		name ->
		
		if(edu != null)
			eduList = edu[i]
		if(recentlyTaughtCourses != null)
			courseList = recentlyTaughtCourses[i]
		if(papers != null)
			paperList = papers[i]

		def json = new JsonBuilder()
		json{

			id i
			professor name[0] + " " + name[1]
			if(name[2] != null)
				position name[2]
			if(edu != null)
				schools eduList

			int num = 0			
			def degreeslst = []
			def facultylst = []
			def courseslst =[]
			def schoolslst = []
			def countrylst = []
			def yearCompletelst = []
			def paperlst = []
			
			if(edu != null){
				eduList.each{
					def eduInfo = it.split(",")		
					// println eduInfo.size()		
								
					if(eduInfo.size() == 4){					
						degreeslst.add(eduInfo[0].trim())
						schoolslst.add(eduInfo[1].trim())
						countrylst.add(eduInfo[2].trim())
						yearCompletelst.add(eduInfo[3].trim())
						
					}
					else if(eduInfo.size() > 4){					
						degreeslst.add(eduInfo[0].trim())
						facultylst.add(eduInfo[1].trim())
						schoolslst.add(eduInfo[2].trim())
						countrylst.add(eduInfo[3].trim())
						yearCompletelst.add(eduInfo[4].trim())
						
					}
					else if(eduInfo.size() == 3)
					{					
						degreeslst.add(eduInfo[0].trim())
						schoolslst.add(eduInfo[1].trim())
						countrylst.add(eduInfo[2].trim())
					}
						
					num+=1
				}
			}

			

			website links[i]
			degrees degreeslst.unique()
			schools schoolslst.unique()
			country countrylst.unique()			
			if(!facultylst.isEmpty())
				faculty facultylst.unique()
			yearCompleted yearCompletelst.unique()			

			if(edu != null)
				education eduList.findAll{ it.size() > 5 }
			if(recentlyTaughtCourses != null)
				courses courseList.unique()
			if(paperList != null){
				publications paperList
			}

			masterSchoolList.add(schoolslst)
		}
		
		i+= 1

		messages.add(json)

	}
	
	// def results = []
	// results.add(messages)
	// results.add(masterSchoolList.flatten().unique())	
	// println messages.getClass()
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
	
	// println prettyJson  //dumps output to screen
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
	def results = []

	links.each{
		link ->
		println "Getting this doc ${link}"
		def d = getRawDoc(link)
		docs.add(d)
	} 

	results.add(links)
	results.add(docs)
	return results
}

//wrapper for all logic
def getInfo(docs){
	println("Retrieving Info...")
	def names = []
	def educations = []
	def courses = []
	def publications = []
	
	docs.each{
		page ->		
		
		def nameBlock = page.depthFirst().DIV.findAll { it.@class == 'title section'}	
		def position = page.depthFirst().DIV.findAll { it.@class == 'text '}
		def eduBlock = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section' }
		def courseBlock = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section'}
		def publicationBlock = page.depthFirst().DIV.findAll{ it.@class == 'text listed' }
		def publicationBlock1 = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section' }

		if(!nameBlock.isEmpty() && !position.isEmpty())
			names.add(getName(nameBlock) + getTitle(position))		
		if(!eduBlock.isEmpty())
		    educations.add(getEducInfo(eduBlock))
		if(!courseBlock.isEmpty())
			courses.add(getCourses(courseBlock))
		if(!publicationBlock.isEmpty())
			publications.add(getPublication(publicationBlock))
		else if(!publicationBlock1.isEmpty())
			publications.add(getPublication(publicationBlock1))
		
	}
	
	def data = []
	data.add(names)
	println names.size()
	data.add(educations)
	println educations.size()
	data.add(courses)	
	println courses.size()
	data.add(publications)
	println publications.size()
	return data
}


//HELPER FUNCTIONS
def getPublication(block){
	def papers = []
	block.each{

		def content = it.value()
		int i = 0
		content.each{
			def header = it.value()
			
			header.each{
				// println it
				if(it.toString().contains("recent publications")){					
					def li = content[i+1]		 
					li.each{
						p = it.value()[0]
						if(p.getClass() == groovy.util.Node)
						{
							p.each{								
								papers.add(it.trim())
							}
						}
						else{							
							papers.add(p.replaceAll('"', '').trim())
						}
					}
				}
				
			}
			i += 1

		}
	}

	return papers
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
	
		

	// println results
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
		if (link.contains("people/faculty") && !link.contains("/people/faculty.html"))
			filtered.add(link)
		if (link.contains("people/emeriti") && !link.contains("/people/emeriti.html"))
			filtered.add(link)
	}
	return filtered.unique()
}



//MAIN LOOP
def getProfData(){
	
	// def facultyAddr = ["http://www.cs.sfu.ca/people/emeriti.html", "http://www.cs.sfu.ca/people/faculty.html"]
	// def facultyAddr = ["http://www.cs.sfu.ca/people/emeriti.html"]
	def facultyAddr = ["http://www.cs.sfu.ca/people/faculty.html"]
	def allLinks = []
	def allDocs = []
	facultyAddr.each{
		webAddr ->		
		def tmp = getDoc(webAddr)
		allLinks = allLinks + tmp[0]
		allDocs = allDocs + tmp[1]
	}
	
	def data = getInfo(allDocs)

	f = new File("prof.json")
	f.delete()
	def messages = outputJson(data,allLinks)
	// println messages
	
 	f.append(printJson(messages))

	//get all links of all schools
	// f1 = new File("schools.txt")
	// f1.delete()
	// messages[1].each{
	// 	f1.append(it.trim()+"\n")
	// }
}

def smallTest(){
	
	def webAddr5 = "http://www.cs.sfu.ca/people/faculty/anoopsarkar.html"
	// def webAddr5 = "http://www.cs.sfu.ca/people/faculty/markdrew.html"
	def webAddr4 = "http://www.cs.sfu.ca/people/faculty/cenkssahinalp.html"
	// def webAddr1 = "http://www.cs.sfu.ca/people/faculty/mikeevans.html"
	// def webAddr1 = "http://www.cs.sfu.ca/people/faculty/ryandarcy.html"
	// def webAddr2 = "http://www.cs.sfu.ca/people/faculty/RameshKrishnamurti.html"
	// def webAddr3 = "http://www.cs.sfu.ca/people/faculty/uweglasser.html"	
	// def webAddr2 = "http://www.cs.sfu.ca/people/faculty/stevenpearce.html"
	// def webAddr1 = "http://www.cs.sfu.ca/people/faculty/valentinekabanets.html"
	// def output1 = getInfo(getRawDoc(webAddr1))
	// def output2 = getInfo(getRawDoc(webAddr2))	
	// def output3 = getInfo(getRawDoc(webAddr3))
	def output4 = getInfo(getRawDoc(webAddr4))
    def messages = outputJson(output4, webAddr4)
    println printJson(messages)
    def output5 = getInfo(getRawDoc(webAddr5))
    def messages1 = outputJson(output5, webAddr5)
    println printJson(messages1)
    
	// f = new File("output.json")
	// f.delete()
	// // f.append(printJson(outputJson(output1)))
	// // f.append(printJson(outputJson(output2)))
	// // f.append(printJson(outputJson(output3)))
	// f.append(printJson(outputJson(output4)))
}


 // smallTest()
getProfData()



// def map = [name:"Mori", likes:"CMPT470", id:0]
// def map2 = [name:"Baker", likes:"CMPT470", id:1]
// def map3 = [name:"Bart", likes:"CMPT300", id:1]

// // println map.get('likes')
// // println map2.get('likes')

// def bmap = []
// bmap.add(map)
// bmap.add(map2)
// bmap.add(map3)

// def b = bmap.findAll{
// 	it.get('likes')
// }

// println b

// def c = bmap.findAll{ it.get('id') }
// println c
