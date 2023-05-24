mvn clean install -U
docker start discounts_mongodb_1
sleep 5
docker exec -it discounts_mongodb_1 bash -c "mongo discounts --eval 'db.roles.drop()'"
docker stop discounts_mongodb_1
sudo docker-compose up --build --force-recreate