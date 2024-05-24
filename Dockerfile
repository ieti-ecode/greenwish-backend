FROM openjdk:23-ea-17-jdk-oraclelinux8

WORKDIR /usrapp/bin

ENV PORT 46000

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","edu.eci.ieti.greenwish.EcoredApplication"]