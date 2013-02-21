CREATE TABLE free_app_of_day
(
app_date 		date 			NOT NULL PRIMARY KEY,
app_url 		varchar(120)	NOT NULL,
app_title 		varchar(80)		NOT NULL,
app_developer 	varchar(80)		NOT NULL,
app_list_price 	varchar(7)		NOT NULL,
app_category 	varchar(30)		NOT NULL,
app_description varchar(4000)	NOT NULL
);