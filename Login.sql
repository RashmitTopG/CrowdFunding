create database student;

use student;

CREATE TABLE login (
    userid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

-- Set the initial value for the auto-increment column
ALTER TABLE login AUTO_INCREMENT = 1000;

desc login;

insert into login values(1001, "Aditya", "aditya", "aditya123","aditya.gmail.com", 98884843244);

insert into login values(1000,"Rashmit","rashmit","rashmit23","rashmit449@gmail.com ", 7506101281);

select * from login;

delete from login where userid = 1003;

alter table login modify email varchar(255) after name;

select * from login;

