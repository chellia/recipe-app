# Start with a base image containing Java runtime
FROM openjdk:11


# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8081 available to the world outside this container
EXPOSE 8000

# The application's jar file
ARG JAR_FILE=target/home-made-recipe-api-1.0.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} home-made-recipe-api.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/home-made-recipe-api.jar"]
