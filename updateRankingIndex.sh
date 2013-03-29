
echo "Updating Ranking's Index All...\n"

curl http://localhost:8983/solr/update/json?commit=true --data-binary @ranking.json -H 'Content-type:application/json'