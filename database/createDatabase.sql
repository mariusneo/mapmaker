CREATE USER 'mapmaker'@'localhost' IDENTIFIED BY 'password';
CREATE DATABASE MAPMAKER;
USE MAPMAKER;
GRANT ALL PRIVILEGES ON MAPMAKER TO 'mapmaker'@'localhost' IDENTIFIED BY 'password' WITH GRANT OPTION;
grant alter, select, insert, update, delete, create, drop on mapmaker.* to 'mapmaker'@'localhost';