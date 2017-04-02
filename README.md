# Belajar Continuous Integration dan Deployment #

[![Build Status](https://travis-ci.org/endymuhardin/belajar-ci.svg?branch=master)](https://travis-ci.org/endymuhardin/belajar-ci)
[![Coverage Status](https://coveralls.io/repos/github/endymuhardin/belajar-ci/badge.svg?branch=master)](https://coveralls.io/github/endymuhardin/belajar-ci?branch=master)

Teknologi yang digunakan

* Maven
* MySQL
* Spring Boot

  * Hibernate
  * Spring Data JPA
  * Spring Test
  * Spring MVC

* Continuous Integration

    * Travis CI
    * Jenkins
    * Gitlab CI


## Build Docker Image ##

Agar bisa menggunakan docker, kita install dulu `docker` dan `docker-machine`. Instalasinya bisa dibaca di [artikel ini](https://docs.docker.com/engine/installation/).

1. Siapkan dulu docker-machine. Pada contoh ini kita akan menggunakan Digital Ocean supaya hemat bandwidth.

        docker-machine create --driver digitalocean --digitalocean-size 1gb --digitalocean-access-token yaddayaddayadda docker-ocean

2. Update environment variable supaya perintah docker kita diarahkan ke droplet DO kita tadi

        eval $(docker-machine env docker-ocean)

3. Build docker container untuk aplikasi kita

        docker build -t endymuhardin/belajar-ci . 

4. Jalankan aplikasi dengan docker-compose

        docker-compose up

5. Lihat IP address docker engine kita dengan perintah berikut

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
