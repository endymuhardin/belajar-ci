#!/bin/bash

set -e

service mysql start

dbname=belajar
dbusername=belajar
dbpassword=java

if [ "$MYSQL_DATABASE" ]; then
    dbname="$MYSQL_DATABASE"
fi


if [ "$MYSQL_USERNAME" ]; then
    dbusername="$MYSQL_USERNAME"
fi

if [ "$MYSQL_PASSWORD" ]; then
    dbpassword="$MYSQL_PASSWORD"
fi

echo "GRANT ALL ON \`$dbname\`.* to '"$dbusername"'@'localhost' IDENTIFIED BY '"$dbpassword"'" | mysql -uroot
echo "CREATE DATABASE IF NOT EXISTS \`$dbname\`" | mysql -uroot

exec "$@"
