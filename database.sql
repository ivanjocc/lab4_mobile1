create database lab4_mobile1;

create table utilisateur (
	id int primary key AUTO_INCREMENT,
    name varchar(50),
    last_name varchar(50),
	usr varchar(50),
	pwd varchar(50),
    dateEntry date
);

insert into utilisateur values 
	(1, 'ivan', 'jose', 'ivanj', '123', '2019-12-12'),
	(2, 'prueba', 'test', 'test', 'test', '2020-12-12')
;