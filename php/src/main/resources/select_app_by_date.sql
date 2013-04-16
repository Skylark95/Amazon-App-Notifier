SELECT
	app_date,
	app_url,
	app_title,
	app_developer,
	app_list_price,
	app_category,
	app_description
  FROM free_app_of_day
  WHERE app_date = '$date';