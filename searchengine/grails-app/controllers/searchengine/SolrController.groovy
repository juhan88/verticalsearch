package searchengine

class SolrController {

	def add = {

		String urlString = "http://localhost:8983/solr/"
		def solr = new org.apache.solr.client.solrj.impl.HttpSolrServer(urlString)
		def document = new org.apache.solr.common.SolrInputDocument()
		document.addField("id", "552199")
		document.addField("name", "Gouda cheese wheel")
		document.addField("price", "49.99")
		def response = solr.add(document)

		solr.commit();
	}
}
