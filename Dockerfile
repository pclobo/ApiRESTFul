FROM java:8

WORKDIR /app/ApiRESTFul/
# Copy the jar
COPY target/ApiRESTFul.jar ./
# Copy input data
COPY src/test/java/* /tmp/docker/input/
# Volumes
VOLUME ["/tmp/docker/input", "/tmp/docker/output", "/tmp/docker/data/logs"]
EXPOSE 8080
# Run the jar (CMD)
CMD ["java", "-jar", "ApiRESTFul.jar"]



#TRYING TO ADD ELASTICSEARCH TO THIS API
#ENV VERSION 6.2.4
#ENV FILE elasticsearch-$VERSION.tar.gz
#RUN wget -O /tmp/$FILE https://artifacts.elastic.co/downloads/elasticsearch/$FILE
#RUN tar -xzf /tmp/$FILE -C /tmp
#RUN mv /tmp/elasticsearch-$VERSION /elasticsearch
#ADD config/elasticsearch.yml /usr/share/elasticsearch/config/elasticsearch.yml
#ADD elasticsearch.yml /usr/share/elasticsearch/config/
# Mountable directory
#VOLUME ["/data"]
# Working directory
#WORKDIR /data
# Expose ports (9200: HTTP, 9300: Transport)
#EXPOSE 9200 9300
# RUN
#CMD ["/elasticsearch/bin/elasticsearch"]
