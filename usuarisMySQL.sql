CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
CREATE USER 'client'@'localhost' IDENTIFIED BY 'client';
GRANT ALL PRIVILEGES ON books.* TO 'admin'@'localhost';
GRANT SELECT ON books.* TO 'client'@'localhost';
