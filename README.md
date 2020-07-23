This repository contains 2 Spring boot applications:
- connect5: REST api for playing connect5 
    * The following 2 files contain the majority of the server code:
        https://github.com/karrawlinson/connect5/blob/master/connect5/src/main/java/com/genesys/connect5/controller/GameController.java
        https://github.com/karrawlinson/connect5/blob/master/connect5/src/main/java/com/genesys/connect5/service/GameService.java
    * Swagger docs should be available at http://localhost:8080/swagger-ui.html
    * The server can be run from the connect5 folder using: gradlew bootRun 
- client: client to connect to the connect5 server
    * The following 2 files contain the majority of the client code: 
        https://github.com/karrawlinson/connect5/blob/master/client/src/main/java/com/genesys/connect5/client/ClientApplication.java
        https://github.com/karrawlinson/connect5/blob/master/client/src/main/java/com/genesys/connect5/client/Connect5Service.java
    * Each client can be run from the client folder using: gradlew bootRun 





