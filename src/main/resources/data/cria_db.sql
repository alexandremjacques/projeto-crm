create database if not exists projetocrm;

use projetocrm;

create table if not exists cliente
(
    id       int primary key auto_increment,
    nome     varchar(200) not null,
    endereco varchar(255) null,
    telefone varchar(11)  null,
    salario  double default 0,
    nr_filhos int default 0,
    data_cadastro date
)
