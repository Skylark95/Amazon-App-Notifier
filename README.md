Amazon App Notifier
===================

Download
--------
The app is available on [Google Play](https://play.google.com/store/apps/details?id=com.skylark95.amazonfreenotify).

Will also be available to download from [F-Droid](https://f-droid.org/) once out of beta status


About
-----
**Version**: 2.0-BETA

Amazon App Notifier is a free and open source notification manager for providing notifications for the free app of the day on the [Amazon Appstore](http://www.amazon.com/mobile-apps/b?ie=UTF8&node=2350149011).

Developer: Derek (Skylark95)<br/>
Contact: [derek@skylark95.com](mailto:derek@skylark95.com)<br/>
Website: [http://www.skylark95.com/](http://www.skylark95.com/)

Permissions
-----------
This app requires the following permissions.  Permissions labeled with *"optional"* can be changed in settings to no longer be used.

* **Full Network Access**: *Optional*, Used for downloading app data for the free app of the day
* **Google Play Billing Service**: Will be used in the future for accepting donations
* **View Network Connections**: *Optional*, Needed to see if device is offline before downloading app data.
* **Run At Startup**: *Optional*, will show notification at startup
* **Control Vibration**: *Optional*, vibrate when notification fires
* **Prevent Phone from Sleeping**: Allows the notification to display even if the device is sleeping

Donations
---------
Donations are always appreciated for supporting development of this app.

Donations are currently not enabled in the app but you can donate $1 now by purchasing the old version of this app below:<br/>
[https://play.google.com/store/apps/details?id=com.skylark95.amazonnotify](https://play.google.com/store/apps/details?id=com.skylark95.amazonnotify)

Help & How To
-------------
Consider this section on my TODO list.

Code Statistics
---------------
See page at [Ohloh.net](http://www.ohloh.net/p/Amazon-App-Notifier)


Open Source Tools
-----------------
Special thanks to the following for making this huge update possible:

* [ActionBarSherlock](http://actionbarsherlock.com/)
* [CommonsWare Android Components](http://commonsware.com/cwac)
* [Jackson JSON Processor](http://wiki.fasterxml.com/JacksonHome)
* [PHP Simple HTML DOM Parser](http://simplehtmldom.sourceforge.net/)
* [Maven Android Plugin](https://code.google.com/p/maven-android-plugin/)
* [Maven Android SDK Deployer](https://github.com/mosabua/maven-android-sdk-deployer)
* [Robolectric](http://pivotal.github.io/robolectric/)
* [Mockito](https://code.google.com/p/mockito/)

Bug Reports
-----------
I'll be honest and say this beta release was put together somewhat quickly due to the previous version breaking...

If you find bugs that my current scope of testing didn't seem to catch<br/>
Please email me ([derek@skylark95.com](mailto:derek@skylark95.com)) if you find any bugs or report them on the GitHub project page

Planned updates
---------------
These are updates I have planned for the future.

As I work full time and writing Android apps just a hobby of mine I try my best to<br/>
add more features but make no guarantees on the amount of time it will take for updates

* If device is offline try notification again a few times (Planned fix for showing device offline when starting the phone)
* Options to filter by more categories
* Some potential suggestions I receive from users


Changelog
---------
<b>Version 2.0-BETA - 4/8/2013</b><br/>
	Early beta release of newly rewritten app due to recent changes on Amazon breaking notifications for previous versions<br/>
	<br/>
	Completely rewritten with new UI and open source<br/>
	<br/>
	Expandable notifications for Android Jellybean<br/>
	<br/>
	Option to filter for apps with category "Games" (more category filters are planned)<br/>
	<br/>
	Currently known bugs:<br/>
	App developer section of notification sometimes shows private policy after the developer name<br/>
	<br/>
	Please email me (derek@skylark95.com) if you find any bugs or report them on the GitHub project page<br/>
	<br/>
<b>Version 1.0.4 - 9/22/2012</b><br/>
	Small bug fix affecting free version<br/>
	<br/>
<b>Version 1.0.3 - 9/21/2012</b><br/>
	If Appstore not found on phone, clicking notification redirects to download page<br/>
	<br/>
	Fix startup crash on some phones<br/>
	<br/>
<b>Version 1.0.2 - 9/21/2012</b><br/>
	Added Option to disable vibrate<br/>
	<br/>
<b>Version 1.0.1 - 08/13/2012</b><br/>	
	Fixed notifications not appearing on some phones (4.0 ICS, 4.1 Jelly Bean)<br/>
	<br/>
	Increased timeout for reading app name and price to 30 seconds (Less error downloading data messages on a slow connection)<br/>
	<br/>
	Fixed notification so when clicked on it launches the appstore on the main store page instead of the last open view (Get to downloading your free app faster!)<br/>
	<br/>
	 Works great on my new Nexus 7! :)<br/>
	<br/>
<b>Version 1.0 - 05/16/2012</b><br/>
	Initial release
	<br/>

