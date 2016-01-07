CREATE TABLE IF NOT EXISTS users(
	userId 		varchar(12)	not null,
	password	varchar(12)	not null,
	name		varchar(20)	not null,
	email		varchar(50)	,
	primary key (userid)
);

--insert into users values('astraltear','password','�꾩뒪�몃엫�곗뼱','astral@tear.com');