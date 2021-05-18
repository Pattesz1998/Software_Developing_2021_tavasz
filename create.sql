
    create table Database (
       pk bigint not null auto_increment,
        date date,
        numOfRounds integer,
        winner varchar(255),
        primary key (pk)
    ) engine=InnoDB

    create table Results (
       pk bigint not null auto_increment,
        date date,
        numOfRounds integer,
        winner varchar(255),
        primary key (pk)
    ) engine=InnoDB
