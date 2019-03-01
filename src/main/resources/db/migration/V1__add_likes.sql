<!-- create sequence hibernate_sequence start 1 increment 1; -->
<!-- create table app_groups ( -->
<!-- 		id int8 not null,  -->
<!-- 		creation_date varchar(255),  -->
<!-- 		group_information varchar(255),  -->
<!-- 		group_name varchar(255),  -->
<!-- 		primary key (id) -->
<!-- ); -->
<!-- create table comment ( -->
<!-- 		id int8 not null,  -->
<!-- 		comment_pic_name varchar(255), -->
<!-- 		comment_text varchar(255),  -->
<!-- 		creation_date varchar(255),  -->
<!-- 		user_id int8,  -->
<!-- 		post_id int8,  -->
<!-- 		primary key (id) -->
<!-- ); -->
<!-- create table post ( -->
<!-- 		id int8 not null,  -->
<!-- 		creation_date varchar(255),  -->
<!-- 		filename varchar(255),  -->
<!-- 		post_text varchar(255),  -->
<!-- 		tags varchar(255),  -->
<!-- 		user_id int8,  -->
<!-- 		primary key (id) -->
<!-- ); -->
<!-- create table user_role ( -->
<!-- 		user_id int8 not null,  -->
<!-- 		roles varchar(255) -->
<!-- ); -->
<!-- create table usr ( -->
<!-- 		id int8 not null,  -->
<!-- 		active boolean not null,  -->
<!-- 		password varchar(255),  -->
<!-- 		registration_date varchar(255),  -->
<!-- 		user_pic_name varchar(255),  -->
<!-- 		username varchar(255),  -->
<!-- 		primary key (id) -->
<!-- ); -->
<!-- create table post_likes ( -->
<!-- 		 user_id bigint not null references usr,  -->
<!-- 		 post_id bigint not null references post, -->
<!-- 		 primary key (user_id, post_id) -->
<!-- ); -->
<!-- alter table if exists comment add constraint FKgcgdcgly6u49hf4g8y2di3g4p foreign key (user_id) references usr; -->
<!-- alter table if exists comment add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post; -->
<!-- alter table if exists post add constraint FKrm2u0ujvvi9euawhsm1km29m4 foreign key (user_id) references usr; -->
<!-- alter table if exists post_likes add constraint FK1xc49w8m0sjl31x564gacfage foreign key (user_id) references usr; -->
<!-- alter table if exists post_likes add constraint FKmxmoc9p5ndijnsqtvsjcuoxm3 foreign key (post_id) references post; -->
<!-- alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr; -->