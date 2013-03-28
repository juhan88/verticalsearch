
echo "Deleting All...\n"

curl http://localhost:8983/solr/update/json?commit=true --data-binary @prof.json -H 'Content-type:application/json'