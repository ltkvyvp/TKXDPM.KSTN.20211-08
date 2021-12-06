create database if not exists bike_rental;
use bike_rental;
SET time_zone='+07:00';

drop table if exists bike_rental.transaction;
drop table if exists bike_rental.rent;
drop table if exists bike_rental.user;
drop table if exists bike_rental.bike;
drop table if exists bike_rental.category;
drop table if exists bike_rental.dock;

create table if not exists bike_rental.user(
	userId int not null auto_increment,
    userName varchar(255) not null,
    constraint pk_user primary key(userId)
);

create table if not exists bike_rental.dock (
	dockId int not null auto_increment,
    dockName varchar(255) not null,
    address varchar(255) not null,
    dockArea float not null,
    availableBikes int default 0,
    emptyDockingPoints int default 0,
    distance float,
    walkingTime float,
    imagePath varchar(512),
    constraint pk_dock primary key(dockId)
);

create table if not exists bike_rental.category(
	categoryId int not null auto_increment,
	categoryName varchar(255) not null,
	categoryDescription varchar(255) not null,
    bikeValue float not null,
    costRatio float not null,
    constraint pk_category primary key(categoryId)
);

create table if not exists bike_rental.bike(
	bikeId int not null auto_increment,
    bikeName varchar(255) not null,
    licensePlate varchar(255) not null,
    pin float,
    status boolean,
    initCost float default 10,
    costPerQuarterHour float default 3,
    dockId int not null,
    categoryId int not null,
    constraint pk_bike primary key(bikeId),
    constraint fk_bike_dock foreign key (dockId) references bike_rental.dock(dockId),
    constraint fk_bike_category foreign key (categoryId) references bike_rental.category(categoryId)
);

create table if not exists bike_rental.rent(
	rentId int not null auto_increment,
    startTime timestamp default current_timestamp,
    endTime timestamp,
    debit int,
    userId int not null,
    bikeId int not null,
    constraint pk_rent primary key(rentId),
    foreign key (userId) references bike_rental.user(userId),
    foreign key (bikeId) references bike_rental.bike(bikeId)
);

create table if not exists bike_rental.transaction(
	transactionId int not null auto_increment,
    totalCost float,
    content varchar(255),
    rentedDuration float,
    modifyAt timestamp default current_timestamp,
    userId int not null,
    rentId int not null,
    bikeId int not null,
    constraint pk_transaction primary key(transactionId),
    constraint fk_transaction_user foreign key (userId) references bike_rental.user(userId),
    constraint fk_transaction_rent foreign key (rentId) references bike_rental.rent(rentId),
    constraint fk_transaction_bike foreign key (bikeId) references bike_rental.bike(bikeId)
);

--
--    seed data
--
insert into bike_rental.user(userName)
values  ('admin');

insert into bike_rental.category(categoryName, categoryDescription, bikeValue, costRatio)
values  (n'Xe đạp đơn thường', n'1 yên/bàn đạp và 1 ghế ngồi phía sau', 400, 1.0),
	    (n'Xe đạp đôi', n'có 2 yên/bàn đạp và 1 ghế ngồi phía sau', 550, 1.5),
        (n'Xe đạp đơn điện', n'1 yên/bàn đạp và 1 ghế ngồi phía sau, có motor điện giúp đạp xe nhanh hơn', 700, 1.5);

insert into bike_rental.dock(dockName, address, dockArea, availableBikes, emptyDockingPoints)
values  (n'Bách Khoa', n'Hai Bà Trưng - Hà Nội', 400, 10, 15),
        (n'Bạch Mai', n'Thanh Xuân - Hà Nội', 550, 12, 20),
        (n'Vincom', n'Cầu Giấy - Hà Nội', 1000, 50, 60),
        (n'Vinpearl', n'Thạch Hải - Hà Tĩnh', 650, 15, 16),
        (n'Quang Vinh', n'Thành phố Hà Tĩnh - Hà Tĩnh', 500, 13, 10),
        (n'Lê Trọng Tấn', n'Thanh Xuân - Hà Nội', 700, 18, 15);

insert into bike_rental.bike(bikeName, licensePlate, initCost, costPerQuarterHour, dockId, categoryId)
values  ('No_001', '38MH_001', 10, 3, 1, 1),
        ('No_002', '38MH_002', 10, 3, 2, 1),
        ('No_003', '38MH_003', 10, 3, 3, 1),
        ('No_004', '38MH_004', 10, 3, 4, 1),
        ('No_005', '38MH_005', 10, 3, 5, 1),
        ('No_006', '38MH_006', 10, 3, 1, 2),
        ('No_007', '38MH_007', 10, 3, 1, 3),
        ('No_008', '38MH_008', 10, 3, 2, 2),
        ('No_009', '38MH_009', 10, 3, 3, 2),
        ('No_010', '38MH_010', 10, 3, 3, 3);

insert into bike_rental.rent(startTime, endTime, debit, userId, bikeId)
values  (now(), '2022-10-01 07:00:00', 5, 1, 1),
	    (now(), '2022-11-01 07:00:00', 5, 1, 2),
        (now(), '2022-12-01 07:00:00', 5, 1, 3),
        (now(), '2022-09-01 07:00:00', 5, 1, 4),
        (now(), '2022-08-01 07:00:00', 5, 1, 5);