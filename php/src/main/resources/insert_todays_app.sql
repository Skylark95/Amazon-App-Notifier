INSERT INTO free_app_of_day (
					app_date,
					app_url,
					app_title,
					app_developer,
					app_list_price,
					app_category,
					app_description)
				VALUES (
					CURDATE(),
					'$appUrl',
					'$appTitle',
					'$appDeveloper',
					'$appListPrice',
					'$appCategory',
					'$appDescription'
					);