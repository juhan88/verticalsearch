package searchengine

class SearchController {

	// logic and methods
		
	def index() {
    }

	/* Global Variables */
	String urlString = "http://localhost:8983/solr/"
	def solr = new org.apache.solr.client.solrj.impl.HttpSolrServer(urlString)
	def uQ
	def response
	def TOTAL_UNIVERSITIES = 852	// const for scrape total, used for rank calculation
    def startPageNum = 0


	/* Initial Query */
	def mainQuery() {		
		
		/* Local Variables */
		def solrparams = new org.apache.solr.client.solrj.SolrQuery()

        if (!uQ)
            uQ = params.address				// user query input value from gsp
		solrparams.setQuery(uQ)
		solrparams.set("qf","""
										professor
										position
										courses
										education
										country
										""")										 // searchable fields
		solrparams.set("q.op", "OR")						 // allow in-exact matches
		solrparams.set("defType", "edismax")		 // set solr to run as edismax
        solrparams.set("start",startPageNum)
		
		response = solr.query(solrparams)				 // main query raw header and results
						 
		def doclist = response.getResults()			 // main query results
		
		/* Unsuccessful search */
		if (doclist.getNumFound() == 0)
			render "Sorry, I could not find any matches for <b>" + uQ + "</b><br /><br />"
        else{
            render "Results found ${doclist.getNumFound()}"
            render "<br />"
            render "<br />"
        }

		def allResults = []		// collection for concatenation of all prof maps

		/* Loop every result doc in main results */		
		for (org.apache.solr.common.SolrDocument doc : doclist) {
			
			def id = doc.getFieldValue("id")								// single unique value
			def name = doc.getFieldValue("professor")				// single value
			def plink = doc.getFieldValue("website")				// single unique value
			def position = doc.getFieldValue("position")		// single value
			def courses = doc.getFieldValues("courses")			// multivalue
			def schools = doc.getFieldValues("schools")			// multivalue
			def education = doc.getFieldValues("education")	// multivalue
			def result = [name:"",
									 plink:"",
								position:"",
									course:"",
									school:"",
									 slink:"",
									 ranks:"",
									 prank:"",
							 education:""]		// map data for each prof
			
			/* Professor name and profile website	*/
			result.name = name	// set name data
			result.plink = plink	// set plink data
			
			/* Professor position, if exists	*/
			if (position != null) {
				result.position = position	// set position data
			}
			
			/* Courses taught, if any */
			result.course = courses
			
			/* Schools Attended */
			def schoollist				// nested query results used to find school rank
			def linklist					// nested query results used to find school link
			def uniName						// school name key for sub query									[single unique value]
			def uniRank						// school rank match															[single value]
			def uniLink						// school site key for sub query									[single unique value]
			def actualUnis = []		// original school names from prof profiles				[multivalued]
			def pooledRanks = []	// school rank matches for later retrieval				[multivalued]
			def pooledLinks = []	// school link matches for later retrieval				[multivalued]
			def uniqueUnis = []		// used to remove duplicate schools for a prof		[multivalued]
			
			/* Match each school to rank data and website link */			
			schools.each {
								
				def cut = it.replaceAll('Computer','')	// clean the school query values
				cut = cut.replaceAll('Canada','')
				cut = cut.replaceAll('Science','')
				cut = cut.replaceAll('\\-',' ')				
												
				if (cut.matches('.*\\w.*')) {
					actualUnis.add(cut)														// store actual school names
				}
				
				/* School Ranks Subquery */
				def schoolparams = new org.apache.solr.client.solrj.SolrQuery() // subquery params
				schoolparams.setQuery(cut)							// cleaned values for query
				schoolparams.set("q.op", "AND")					// exact match
				schoolparams.set("rows",1)							// return first match only
				schoolparams.set("defType", "edismax")
				schoolparams.set("qf", "uniName")				// query school name->rank keys only
				schoollist = solr.query(schoolparams).getResults()	// subquery results			
				
				/* Loop each school in results */				
				for (org.apache.solr.common.SolrDocument school : schoollist) {
					
					uniName = school.getFieldValue("uniName")		// school name->rank key
					uniqueUnis.add(uniName)											// append school name keys
					uniqueUnis = uniqueUnis.unique()						// remove duplicate schools
					
					uniRank = school.getFieldValue("uniRank")		// school rank match
					pooledRanks.add(uniRank)										// append ranks
					pooledRanks = pooledRanks.unique()					// remove duplicate ranks
					result.ranks = pooledRanks									// set school rank data
					
					/* School Links Subquery */
					def linkparams = new org.apache.solr.client.solrj.SolrQuery()	// subquery params
					linkparams.setQuery(cut)										// cleaned values for query
					linkparams.set("q.op", "AND")								// exact match
					linkparams.set("rows",1)										// return first match only
					linkparams.set("defType", "edismax")
					linkparams.set("qf", "university")					// query school name->link keys only
					linklist = solr.query(linkparams).getResults()	// subquery results
					
					/* Loop each link in results */
					for (org.apache.solr.common.SolrDocument link : linklist) {						
												
						uniLink = link.getFieldValue("uniLink")		// school link
						pooledLinks.add(uniLink)									// append school links
						pooledLinks = pooledLinks.unique()				// remove duplicates
													
					} // end for link:linklist
					
					result.slink = pooledLinks									// set school link data
					
				}	// end for school:schoollist
			}	// end schools.each
			
			result.school = uniqueUnis
			
			/* Education */
			result.education = education
			
			/* Calculate Ranks */
			result.prank = calculateRanks(pooledRanks)
			
			/* Add to Results */
			allResults.add(result)

		} // end for (doc : doclist)
		
		/* to View */
		show(allResults)
		
		/* for Relationships */
		findSimilarities(allResults)

        multiPage(doclist.getNumFound())

	} // end mainQuery



    /* multi pages */
    def multiPage(numPages){
        int pages = getPages(numPages)
        def queryString = uQ.replaceAll(" ","%20")
        render "<center>"
        render "<div class=rpage>"
        def prevlink = "./search/page?q=${queryString};p=-1"
//        render "<a href=$prevlink>  prev  <a/>"
        if(pages > 1){
            (1..pages).each{
                def link = "./page?q=${queryString};p=$it"
                render "<a href=$link style='text-decoration:none'>  $it  <a/>"
            }
        }
        def nextlink = "./search/page?q=${queryString};p=+1"
//        render "<a href=$nextlink>  next  <a/>"
        render "</div>"
        render "</center>"
    }
	/* Setter for simple string values */
	def setData(a) {
		def b
		a.each {
			if (!it.isEmpty()) {
				if (it == a.first())
					b = it
				else if (it == a.last()) {
					b = b + it
				} else
					b = b + it
			}
		}
		return b
	}
	
	/* Calculate and return the professor's prestige */
	def calculateRanks(ranklist) {
		if (ranklist.size() > 0) {
			def totalRank = ranklist.sum()
			def rank = totalRank / ranklist.size()
			def pct = (1 - (rank / TOTAL_UNIVERSITIES )) * 100
			pct = pct.setScale(1, BigDecimal.ROUND_HALF_UP).toString()
			return pct
		}
	}

	/* Cross-section analyse */	
	def findSimilarities(e) {
		render "<div class='cell'>"
		
		e.eachWithIndex { v1, i1 ->
			render "$i1:$v1.name <br />"
			v1.course.eachWithIndex { v2, i2 -> def r = "$i1$i2"
				render "$r:$v2 <br />"
				render "${v2.tokenize().take(2)} <br />"
			}
			//it.findAll{ !it.course.each.unique() }
		}
		render "</div>"
		
	}
	
	/* to View */
	def show(e) {
		e.each { f ->
			render "<span class='name'><a href='$f.plink'>$f.name</a></span>"
			if (f.position) {
			 render " is a  <b><i> SFU $f.position </i></b>"
			}
			if (f.course) {
				f.course.each {
					if (it == f.course.first())
						render " who teaches: <br /><span class='course'>$it"
					else if (it == f.course.last()) {
						render ", and $it.</span>"
					} else
						render ", $it"
				}
			}
			if (f.school) {
				render "<span class='smindent'>${f.name.takeWhile{ it != ' ' }} attended:</span>"
				if (f.ranks) {
					f.school.eachWithIndex {g, i ->
						render "<span class='indent'><a href='${f.slink[i]}'>$g</a>, with a world ranking of: ${f.ranks[i]}</span>"
					}
				}
				if (f.prank) {
					render "<span class='prestige'>${f.name.takeWhile{ it != ' ' }}'s prestigeness score is: <b><em>$f.prank</em></b></span>"
				}
			}
			render "<br />"
		}
		render "<br />"

	}


    def page(){
        def request = request.getQueryString().split(";")
        def query = request[0].split('=')
        def page = request[1].split('=')
        int num = page[1] as int
        startPageNum = ((num-1)*10)
        uQ = query[1].replaceAll("%20"," ")
        mainQuery()
    }

    //HELPER FUNCTION TO DETERMINE HOW RESULT PAGES
    private getPages(pages){
        int numPages = pages/10
        if(pages%10 > 0)
            numPages += 1
        return numPages
    }
	
}
