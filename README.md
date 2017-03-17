# Belajar Continuous Integration dan Deployment #

[![Build Status](https://travis-ci.org/endymuhardin/belajar-ci.svg?branch=master)](https://travis-ci.org/endymuhardin/belajar-ci)
[![Coverage Status](https://coveralls.io/repos/github/endymuhardin/belajar-ci/badge.svg?branch=master)](https://coveralls.io/github/endymuhardin/belajar-ci?branch=master)

Teknologi yang digunakan

* Maven
* Spring Boot

  * Hibernate
  * Spring Data JPA
  * Spring Test
  * Spring MVC

* MySQL
* Travis CI
* Jenkins


## Build Docker Image ##

1. Siapkan dulu docker-machine. Pada contoh ini kita akan menggunakan Digital Ocean supaya hemat bandwidth.

        docker-machine create --driver digitalocean --digitalocean-size 1gb --digitalocean-access-token yaddayaddayadda docker-ocean

2. Build docker engine

        mvn clean package docker:build -DskipTests 

3. Jalankan aplikasi dengan docker-compose

        docker-compose up

4. Lihat IP address docker engine kita dengan perintah berikut

        docker-machine env docker-ocean
    
    Outputnya sebagai berikut
    
        export DOCKER_TLS_VERIFY="1"
        export DOCKER_HOST="tcp://159.203.84.26:2376"
        export DOCKER_CERT_PATH="/Users/endymuhardin/.docker/machine/machines/docker-ocean"
        export DOCKER_MACHINE_NAME="docker-ocean"

5. Test dengan cara browse ke `http://159.203.84.26/api/product/`. Outputnya seharusnya seperti ini

        {
          "content" : [ {
            "id" : "p001",
            "code" : "P-001",
            "name" : "Product 001",
            "price" : 101001.01
          } ],
          "totalElements" : 1,
          "totalPages" : 1,
          "last" : true,
          "sort" : null,
          "first" : true,
          "numberOfElements" : 1,
          "size" : 20,
          "number" : 0
        }
