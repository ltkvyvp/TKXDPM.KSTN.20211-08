create database if not exists bike_rental;
use bike_rental;
SET time_zone='+07:00';

drop table if exists bike_rental.transactionhistory;
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
    imagePath varchar(255),
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

create table if not exists bike_rental.transactionhistory(
	transactionId int not null auto_increment,
    totalCost float,
    content varchar(255),
    rentedDuration float,
    createAt timestamp,
    userId int not null,
    bikeId int not null,
    constraint pk_transaction primary key(transactionId),
    constraint fk_transaction_user foreign key (userId) references bike_rental.user(userId),
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

insert into bike_rental.dock(dockName, address, dockArea, availableBikes, emptyDockingPoints, distance, walkingTime, imagePath)
values  (n'Bách Khoa', n'Hai Bà Trưng - Hà Nội', 400, 4, 15, 5.5, 10.5, n'dock_1.jpg'),
        (n'Bạch Mai', n'Thanh Xuân - Hà Nội', 550, 5, 20, 6.0, 10.0, n'dock_2.jpg'),
        (n'Vincom', n'Cầu Giấy - Hà Nội', 1000, 5, 60, 4.0, 20.5, n'dock_3.jpg'),
        (n'Vinpearl', n'Thạch Hải - Hà Tĩnh', 650, 5, 16, 5.25, 13.5, n'dock_4.jpg'),
        (n'Quang Vinh', n'Thành phố Hà Tĩnh - Hà Tĩnh', 500, 4, 10, 5.75, 2.55, n'dock_5.jpg'),
        (n'Lê Trọng Tấn', n'Thanh Xuân - Hà Nội', 700, 1, 15, 5.6, 10.7, n'dock_6.jpg'),
        (n'Xã đoàn', n'Huyện Nghi Xuân - Hà Tĩnh', 525, 1, 10, 8.75, 9.25, n'dock_7.jpg'),
        (n'Vịnh Xuân Quyền', n'huyện Đức Thọ - Hà Tĩnh', 150, 2, 6, 8.75, 10.55, n'dock_8.jpg'),
        (n'Vịnh Cam Ranh', n'Nghệ An', 250, 2, 10, 8.25, 6.25, n'dock_9.jpg'),
        (n'Vinh La', n'Quảng Bình', 360, 1, 10, 10.75, 7.65, n'dock_10.jpg');


insert into bike_rental.bike(bikeName, licensePlate, pin, status, initCost, costPerQuarterHour, dockId, categoryId, imagePath)
values  ('No_001', '38MH_001', null, 1, 10, 3, 1, 1, 'assets/images/bike/bike01.jpg'),
        ('No_002', '38MH_002', null, 1, 10, 3, 2, 1, 'assets/images/bike/bike02.jpg'),
        ('No_003', '38MH_003', null, 1, 10, 3, 3, 1, 'assets/images/bike/bike03.jpg'),
        ('No_004', '38MH_004', null, 0, 10, 3, 4, 1, 'assets/images/bike/bike04.jpg'),
        ('No_005', '38MH_005', null, 0, 10, 3, 5, 1, 'assets/images/bike/bike05.jpg'),
        ('No_006', '38MH_006', null, 1, 10, 3, 6, 1, 'assets/images/bike/bike06.jpg'),
        ('No_007', '38MH_007', null, 1, 10, 3, 7, 1, 'assets/images/bike/bike07.jpg'),
        ('No_008', '38MH_008', null, 0, 10, 3, 8, 1, 'assets/images/bike/bike08.jpg'),
        ('No_009', '38MH_009', null, 0, 10, 3, 9, 1, 'assets/images/bike/bike09.jpg'),
        ('No_010', '38MH_010', null, 0, 10, 3, 10, 1, 'assets/images/bike/bike10.jpg'),
        ('No_011', '38MH_011', null, 0, 10, 3, 1, 2, 'assets/images/bike/bike11.jpg'),
        ('No_012', '38MH_012', null, 0, 10, 3, 2, 2, 'assets/images/bike/bike12.jpg'),
        ('No_013', '38MH_013', null, 0, 10, 3, 3, 2, 'assets/images/bike/bike13.jpg'),
        ('No_014', '38MH_014', null, 0, 10, 3, 4, 2, 'assets/images/bike/bike14.jpg'),
        ('No_015', '38MH_015', null, 0, 10, 3, 5, 2, 'assets/images/bike/bike15.jpg'),
        ('No_016', '38MH_016', null, 0, 10, 3, 6, 2, 'assets/images/bike/bike16.jpg'),
        ('No_017', '38MH_017', null, 0, 10, 3, 7, 2, 'assets/images/bike/bike17.jpg'),
        ('No_018', '38MH_018', null, 0, 10, 3, 8, 2, 'assets/images/bike/bike18.jpg'),
        ('No_019', '38MH_019', null, 0, 10, 3, 9, 2, 'assets/images/bike/bike19.jpg'),
        ('No_020', '38MH_020', null, 0, 10, 3, 1, 2, 'assets/images/bike/bike20.jpg'),
        ('No_021', '38MH_021', 2.5, 0, 10, 3, 2, 3, 'assets/images/bike/bike21.jpg'),
        ('No_022', '38MH_022', 3.5, 0, 10, 3, 3, 3, 'assets/images/bike/bike22.jpg'),
        ('No_023', '38MH_023', 4.5, 0, 10, 3, 4, 3, 'assets/images/bike/bike23.jpg'),
        ('No_024', '38MH_024', 5.5, 0, 10, 3, 5, 3, 'assets/images/bike/bike24.jpg'),
        ('No_025', '38MH_025', 6.5, 0, 10, 3, 1, 3, 'assets/images/bike/bike25.jpg'),
        ('No_026', '38MH_026', 7.5, 0, 10, 3, 2, 3, 'assets/images/bike/bike26.jpg'),
        ('No_027', '38MH_027', 8.5, 0, 10, 3, 3, 3, 'assets/images/bike/bike27.jpg'),
        ('No_028', '38MH_028', 9.5, 0, 10, 3, 4, 3, 'assets/images/bike/bike28.jpg'),
        ('No_029', '38MH_029', 10.5, 0, 10, 3, 1, 3, 'assets/images/bike/bike29.jpg'),
        ('No_030', '38MH_030', 3.5, 0, 10, 3, 2, 3, 'assets/images/bike/bike30.jpg'),
        ('No_031', '38MH_031', 4.5, 0, 10, 3, 3, 3, 'assets/images/bike/bike31.jpg'),
        ('No_032', '38MH_032', 5.5, 0, 10, 3, 2, 3, 'assets/images/bike/bike32.jpg'),
        ('No_033', '38MH_033', 6.5, 0, 10, 3, 3, 3, 'assets/images/bike/bike33.jpg'),
        ('No_034', '38MH_034', 7.5, 0, 10, 3, 4, 3, 'assets/images/bike/bike34.jpg'),
        ('No_035', '38MH_035', 8.5, 0, 10, 3, 5, 3, 'assets/images/bike/bike35.jpg');

insert into bike_rental.rent(startTime, endTime, debit, userId, bikeId)
values  (now(), null, 400, 1, 1),
	    (now(), null, 400, 1, 2),
        (now(), null, 400, 1, 3),
        (now(), null, 550, 1, 6),
        (now(), null, 700, 1, 7);