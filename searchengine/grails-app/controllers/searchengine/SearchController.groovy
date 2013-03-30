package searchengine

class SearchController {
	// logic and methods
		
	def index() {
		redirect (action: current)
	}

  def current = {
		def allSearch = Search.list()		// query data from search model db
		[allSearch:allSearch] // send queried data to view
	}
	
}
