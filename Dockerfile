# Use a Java image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the built jar (chúng ta build jar trước)
COPY target/*.jar app.jar

# Cổng do Render cung cấp qua biến môi trường $PORT
ENV PORT=8080

# Expose the port
EXPOSE 8080

# Run the jar file
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
