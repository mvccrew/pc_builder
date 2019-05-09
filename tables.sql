create table parts (
    id identity,
    name varchar(50) not null,
    price numeric(6,2) not null,
    type varchar(15) not null,
    check(price< 5000)
);

create table computers (
    id identity,
    name varchar(50) not null,
    price numeric(6,2) not null,
    type varchar(15) not null,
    cpuId int not null,
    gpuId int not null,
    ramId int not null,
    hddId int not null,
    coolingId int not null,
    foreign key (cpuId) references parts(id),
    foreign key (gpuId) references parts(id),
    foreign key (ramId) references parts(id),
    foreign key (hddId) references parts(id),
    foreign key (coolingId) references parts(id),
    check(price< 25000)
);