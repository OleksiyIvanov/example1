In order to run MongoDB collections join example please do:

1) Import data:
mongoimport -d bank -c accounts < accounts.json
mongoimport -d bank -c trades < trades.json 

2) Create index on date field of collection trades issuing command below to mongo command line client:
db.trades.ensureIndex( { date: 1 } )

3) Build project using maven or import into IDE (Eclipse or Intellij IDEA).
