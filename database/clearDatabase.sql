-- script to reset the database (mysql)

-- clear the tables
truncate BORDERPOINT;
delete from LOCATION;
delete from FEATURE;
delete from FEATURESMETADATA;
delete from SHAPEFILEMETADATA;

-- reset the auto increments
alter table BORDERPOINT AUTO_INCREMENT=1000;
alter table LOCATION AUTO_INCREMENT=1000;
alter table FEATURE AUTO_INCREMENT=1000;
alter table FEATURESMETADATA AUTO_INCREMENT=1000;
alter table SHAPEFILEMETADATA AUTO_INCREMENT=1000;