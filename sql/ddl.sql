create database logistics_network;

create table network (
    id integer not null auto_increment,
    name varchar(50) not null,
    processed_mark boolean not null,
    primary key (id),
    index idx1 (name),
    index idx2 (processed_mark)
) engine=innodb;

create table network_edge (
    id integer not null auto_increment,
    network_id integer not null,
    source_node varchar(10) not null,
    target_node varchar(10) not null,
    distance decimal(6, 2) not null,
    primary key (id),
    foreign key (network_id) references network(id),
    index idx1 (network_id),
    index idx2 (source_node)
) engine=innodb;

create table network_path (
    id bigint not null auto_increment,
    network_id integer not null,
    source_node varchar(10) not null,
    target_node varchar(10) not null,
    distance decimal(7, 2) not null,
    description varchar(100) not null,
    primary key (id),
    foreign key (network_id) references network (id),
    index idx1 (network_id),
    index idx2 (network_id, source_node, target_node)
) engine=innodb;