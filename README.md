#Bring up DB
docker compose up -d

#start web application on port 8080
./gradlew bootRun

#things left to do
better exception handling, multithreading locks, more separation of DTOs and mapping between them, enhance auth to conform to OAuth2.0, and better input validation
