create database logistics_network;

create table network (
    id integer not null auto_increment,
    name varchar(50) not null,
    primary key (id),
    index idx1 (name)
) engine=innodb;

create table network_edge (
    id integer not null auto_increment,
    network_id integer not null,
    source_node varchar(10) not null,
    target_node varchar(10) not null,
    distance decimal(6, 2) not null,
    primary key (id),
    foreign key (network_id) references network(id),
    index idx1 (network_id)
) engine=innodb;