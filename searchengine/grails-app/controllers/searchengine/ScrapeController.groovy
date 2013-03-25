package searchengine

class ScrapeController {

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
			    	sleep(1000)
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
				def r_education
				def r_name
				def name_educ = []
				docs.each{
					page ->		
					def nameBlock = page.depthFirst().DIV.findAll { it.@class == 'title section'}	
					def eduBlock = page.depthFirst().DIV.findAll{ it.@class == 'text parbase section' }

					if(!nameBlock.isEmpty()) 
					{
						r_name = getName(nameBlock)
					  eduBlock.each{
					 	block -> 
					 	def v = block.value()

					 	v.each{
					 		def info = it.value()
					 		int i = 0
					 		info.each{
					 			// println ">>> $i"
					// 			// println it
					// 			// println "${it.name()} -- ${it.value()}"
					 			if(it.name() == "H2" && it.value().contains("Education"))
					 			{ 	
					 				r_education = getEducInfo(info[i+1].value())
					 			}
					 			//if(it.name() == "P")
					// 			// 	// println it.value()
					// 			// if(it.name() == "UL")
					// 			// 	println it.value()
					 			i+=1
					 		} // end info.each
					 	} // end v.each
					} // end edublock.each
				} // end if
				name_educ += [name:r_name,education:r_education]
			} // end docs.each
			 return name_educ
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
							  def fstname = fullname[0]
							  def lstname = fullname[fullname.size()-1]
							  name = fstname + ' ' + lstname
							}					
						}
					}
				}
				return name
			}

			def getEducInfo(block){
				def education = []
				block.each{
					eduInfo ->
					if(eduInfo.getClass() != groovy.util.Node)
					{
						education.add(eduInfo)
					}
				}
				return education
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

			//def webAddr = "http://www.cs.sfu.ca/people/faculty.html"
			 def webAddr = "http://www.cs.sfu.ca/people/faculty/uweglasser.html"
			// def webAddr1 = "http://www.cs.sfu.ca/people/faculty/gregbaker.html"
			// def webAddr2 = "http://www.cs.sfu.ca/people/faculty/uweglasser.html"

			// println getDoc(webAddr)[1]
			def index = {
				def rdata = getInfo(getDoc(webAddr))
				//[rdata:rdata]
				
				def json = new groovy.json.JsonBuilder()
				json rdata
				[json:json]
				
			// getInfo(getRawDoc(webAddr1))
			// getInfo(getRawDoc(webAddr2))
			}
}