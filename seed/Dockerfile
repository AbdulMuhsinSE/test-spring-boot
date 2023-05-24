FROM mongo:4.2.24
COPY init.json /init.json
CMD mongoimport --host mongodb --db discounts --collection roles --type json /init.json --jsonArray