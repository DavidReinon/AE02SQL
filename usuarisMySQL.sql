CREATE USER 'admin' IDENTIFIED BY 'admin';
CREATE USER 'client' IDENTIFIED BY 'client';
GRANT ALL PRIVILEGES ON books.* TO 'admin';
GRANT SELECT on books.* TO 'client';
