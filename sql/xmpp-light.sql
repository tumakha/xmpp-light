
CREATE DATABASE `xmpp-light` CHARACTER SET UTF8;

# DROP USER IF EXISTS 'xmpp-light'@'localhost';
CREATE USER 'xmpp-light'@'localhost' IDENTIFIED BY 'P@$$word';

GRANT ALL PRIVILEGES ON `xmpp-light`.* TO 'xmpp-light'@'localhost';
FLUSH PRIVILEGES;

SHOW GRANTS FOR 'xmpp-light'@'localhost';
