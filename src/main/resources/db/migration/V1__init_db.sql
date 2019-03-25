create sequence hibernate_sequence start 1 increment 1;

create table usr (
	id int8 not null, 
	active boolean not null, 
	password varchar(255), 
	registration_date varchar(255), 
	user_pic_name varchar(255), 
	username varchar(255), 
	primary key (id)
);
create table post (
	id int8 not null, 
	creation_date varchar(255),
	filename varchar(255), 
	post_text varchar(255), 
	reposts_count int8, 
	tags varchar(255), 
	user_id int8, 
	grup_id int8, 
	post_id int8, 
	primary key (id)
);
create table comment (
	id int8 not null, 
	comment_pic_name varchar(255), 
	comment_text varchar(255), 
	creation_date varchar(255), 
	user_id int8, 
	post_id int8, 
	primary key (id)
);
create table friend_request (
	id int8 not null, 
	creation_date varchar(255), 
	request_from bytea, 
	request_from_id int8, 
	request_to bytea, 
	request_to_id int8, 
	primary key (id)
);
create table post_likes (
	post_id int8 not null, 
	user_id int8 not null, 
	primary key (post_id, user_id)
);
create table user_friendship (
	first_user_id int8 not null, 
	second_user_id int8 not null, 
	primary key (first_user_id, second_user_id)
);
create table user_role (
	user_id int8 not null, 
	roles varchar(255)
);
create table user_subscriptions (
	subscriber_id int8 not null, 
	channel_id int8 not null, 
	primary key (channel_id, subscriber_id)
);
create table admined_groups (
	group_id int8 not null, 
	user_id int8 not null, 
	primary key (group_id, user_id)
);
create table grup (
	id int8 not null, 
	creation_date varchar(255), 
	group_information varchar(255), 
	group_name varchar(255), 
	group_pic_name varchar(255), 
	group_title varchar(255), 
	user_id int8, 
	primary key (id)
);
create table group_subs (
	group_id int8 not null, 
	user_id int8 not null, 
	primary key (group_id, user_id)
);
alter table if exists comment add constraint FKgcgdcgly6u49hf4g8y2di3g4p foreign key (user_id) references usr ON DELETE CASCADE;
alter table if exists comment add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post ON DELETE CASCADE;
alter table if exists post add constraint FKrm2u0ujvvi9euawhsm1km29m4 foreign key (user_id) references usr ON DELETE CASCADE;
alter table if exists post_likes add constraint FK1xc49w8m0sjl31x564gacfage foreign key (user_id) references usr ON DELETE CASCADE;
alter table if exists post_likes add constraint FKmxmoc9p5ndijnsqtvsjcuoxm3 foreign key (post_id) references post ON DELETE CASCADE;
alter table if exists post_repost add constraint FKcxuj0gujb7pahgecf2yraiy8k foreign key (repost_id) references post ON DELETE CASCADE;
alter table if exists post_repost add constraint FKdg6gefca17ggt17ebptjd8pvb foreign key (reposted_post_id) references post ON DELETE CASCADE;
alter table if exists user_friendship add constraint FKjuft0hq43v4badfqnceabbmwy foreign key (second_user_id) references usr ON DELETE CASCADE;
alter table if exists user_friendship add constraint FKnqpadtuuxeltknx3ggf2i26hq foreign key (first_user_id) references usr ON DELETE CASCADE;
alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr ON DELETE CASCADE; 
alter table if exists user_subscriptions add constraint FK74b7d4i0rhhj8jljvidimewie foreign key (channel_id) references usr ON DELETE CASCADE;
alter table if exists user_subscriptions add constraint FKm69uaasbua17sgdnhsq10yxd5 foreign key (subscriber_id) references usr ON DELETE CASCADE;
alter table if exists admined_groups add constraint FKjatjm0gc8i7yg4n5t9fikrtry foreign key (user_id) references usr ON DELETE CASCADE;
alter table if exists admined_groups add constraint FK4l91pqpg8nmxldu93tu6y0kml foreign key (group_id) references grup ON DELETE CASCADE;
alter table if exists group_subs add constraint FK7h2lvxcqotqeqnvgjhghou2wo foreign key (user_id) references usr ON DELETE CASCADE;
alter table if exists group_subs add constraint FK95ren9mwh6no3fh7kk7kuvhfc foreign key (group_id) references grup ON DELETE CASCADE;
alter table if exists grup add constraint FKsa66k0ilohpmxs4t3w3g9ee25 foreign key (user_id) references usr ON DELETE CASCADE;

insert into usr (id, username, password, registration_date, active) VALUES
  (1, '1', '1', 'test1', true),
  (2, '2', '1', 'test2', true),
  (3, '3', '1', 'test3', true);