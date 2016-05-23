FROM maven:3.3.3-jdk-8
RUN apt-get update && apt-get install -y \
    libxml2-utils \
    git \
 && rm -rf /var/lib/apt/lists/* && mkdir -p /usr/src/app
