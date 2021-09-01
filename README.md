# mobilehandset

This project deals with searching mobile handsets based on various criteria.
It has one search API with following url and search criteria can be constructed using different field from handset json
http://localhost:8080/mobile/search?announceDate=1999&priceEur=200

Steps involved in search API
1.	Reading data from json file as a list(json data : https://run.mocky.io/v3/b755c334-9627-4b09-84f2-548cb1918184)
2.	Create list of predicates based on the criteria provided in request
3.	Apply predicates to filter data list and return search result as list

