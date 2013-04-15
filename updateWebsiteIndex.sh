
echo "Updating Universities websites Index All...\n"

curl http://localhost:8983/solr/update/json?commit=true --data-binary @university_links.json -H 'Content-type:application/json'