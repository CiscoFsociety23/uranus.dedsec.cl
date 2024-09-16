FROM openjdk:21-jdk
COPY ./target/uranus-dedsec-corp.war uranus-dedsec-corp.war
ENTRYPOINT ["java", "-jar", "uranus-dedsec-corp.war"]
