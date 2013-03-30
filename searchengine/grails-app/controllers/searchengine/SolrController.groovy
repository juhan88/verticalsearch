package searchengine

class SolrController {

	String urlString = "http://localhost:8983/solr/"
	def solr = new org.apache.solr.client.solrj.impl.HttpSolrServer(urlString)

 	def index() {
		//def j = [scrape: Scrape.get(webAddr)]
		//render (view: "index", model: j)
	}

	def add() {
		def document = new org.apache.solr.common.SolrInputDocument()
		document.addField("id", "552199")
		document.addField("name", "Gouda cheese wheel")
		document.addField("price", "49.99")
		def response = solr.add(document)
		solr.commit();
	}
	
	def query() {
		def params = new org.apache.solr.client.solrj.SolrQuery()
		params.set("q", "monitor")
		def response = solr.query(params)
		render response
	}
}
