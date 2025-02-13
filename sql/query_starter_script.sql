-- Drop user first if they exist
DROP USER if exists 'bitly'@'%' ;

-- Now create user with prop privileges
CREATE USER 'bitly'@'%' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON * . * TO 'bitly'@'%';

-- Create schema
CREATE SCHEMA `bitlyclone` ;