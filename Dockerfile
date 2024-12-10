FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package

# Use an official Tomcat runtime as a parent image
FROM tomcat:9-jdk21-openjdk

# Copy the war file to the webapps directory of Tomcat
COPY --from=build target/BookShopWeb-1.0-SNAPSHOT/. /usr/local/tomcat/webapps/ROOT/

# Expose port 8080 to the outside world
EXPOSE 8080

# Run Tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]