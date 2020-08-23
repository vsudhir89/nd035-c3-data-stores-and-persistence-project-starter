create schema critter;

create user 'sal'@'localhost' identified by 'sal12345';
grant all on critter.* to 'sal'@'localhost';