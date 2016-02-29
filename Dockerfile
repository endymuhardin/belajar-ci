FROM maven:latest

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get install -y mysql-server \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*  

VOLUME ~/.m2 /var/lib/mysql /tmp
COPY setup-database.sh /setup-database.sh
ENTRYPOINT ["/setup-database.sh"]
CMD ["/bin/bash"]
