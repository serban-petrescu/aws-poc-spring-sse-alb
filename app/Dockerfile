FROM maven:3-jdk-11

RUN mkdir /code
WORKDIR /code

COPY . /code

RUN mvn package -DskipTests 

CMD java -jar -Djava.security.egd=file:/dev/./urandom /code/target/demo.jar