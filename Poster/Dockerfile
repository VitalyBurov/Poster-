FROM tomcat:8.5.81-jdk11-openjdk

COPY docker-additional/tomcat-users.xml /usr/local/tomcat/conf
COPY target/poster-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/poster.war

CMD ["catalina.sh", "run"]