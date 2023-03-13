# JogTracker App
This is a web application designed to track and store jogging data for users. The app has two roles: admin and user. Users can create an account and log in to track their jogging data, including location, image, and calories burnt. The admin has access to additional features such as updating, deleting, or creating users.

# Technologies Used
- Springboot
- MySQL
- JWT for authentication
- Swagger

# Getting Started
- Clone the repo using `git clone https://github.com/Comaecod/JogTracker-REST.git`.
- Make sure you have MySQL installed on your machine.
- Download or create JAR based on the steps mentioned below.
- Run the JAR file using `java -jar JogTracker-0.0.1-SNAPSHOT.jar` in the terminal where the jar is located.
- Head to `localhost:8080/swagger-ui/index.html` on your browser once the app has started.
- Test the APIs mentioned in the Swagger Documentation.

- Follow these instructions for JAR creation
    - If your MySQL credentials match in [application-dev.properties](src/main/resources/application-dev.properties)
        - Download the JAR from [JogTracker-0.0.1-SNAPSHOT.jar](__JOG__METADATA/JogTracker-0.0.1-SNAPSHOT.jar).
    - If your MySQL credentials don't match in [application-dev.properties](src/main/resources/application-dev.properties)
        - Change the configuration of the [application-dev.properties](src/main/resources/application-dev.properties) based on your MySQL credentials.
        - Re-create the JAR using the `mvn package` cmd in root directory.
        - The new JAR file would be created in the target folder with name `JogTracker-0.0.1-SNAPSHOT.jar`.

# Features
- User authentication and authorization using JWT
- Users can create an account and log in to track their jogging data
- Admin can update, delete or create users
- Users can store jogging data, location, image, calories burnt etc.
