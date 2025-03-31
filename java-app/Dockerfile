FROM openjdk:11-jre-slim
COPY SendValue.java /app/SendValue.java
WORKDIR /app
RUN javac SendValue.java
CMD ["java", "SendValue"]
