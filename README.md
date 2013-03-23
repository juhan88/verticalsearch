verticalsearch
==============
Vertical Search Engine on http://cs.sfu.ca/people/

authoring team
--------------
Juhan, Ahmed, Derek

what it does
------------
- [] Scrapes the subdomain http://cs.sfu.ca/people/
- [] Saves scraped pages to files
- [] Parse the files for professor name, education, research and teaching interests, courses taught, and research publications
- [] Save the parsings into a json file
- [] Pass the parsed json to Apache Solr for indexing

- [] Serve front-end interface for user query
- [] Take user query and query Solr for matches
- [] Generate links and snippets for top k results
- [] Return top k results in the interface

- [] Pass name query to http://www.name-list.net
- [] Return interesting name-list.net facts
- [] Show interesting correlations in results

- [] Use Google Maps api on education location strings
- [] Generate location data-points on Google Maps

- [] For single queries, return top k, name-list facts, data-points
- [] For multiple queries, return top k, name-list facts, superposition data-points on map with legend
