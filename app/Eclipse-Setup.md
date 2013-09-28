Eclipse Setup
-------------

### Steps ###
1. Install Required Eclipse plugins
2. Import ActionBarSherlock
3. Import Amazon-App-Notifier
4. Running Unit Tests in Eclipse

#### Required Eclipse Plugins ####
* [ADT Plugin](https://developer.android.com/tools/sdk/eclipse-adt.html)
* [m2e-android](http://rgladwell.github.io/m2e-android/)

#### Import ActionBarSherlock ####
* Checkout latest version of [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock)
* Import the `actionbarsherlock` directory into eclipse using File > Import > Android > Existing Android Code Into Workspace
* Delete the existing android-support-v4.jar under the directory `actionbarsherlock/lib`
* Right click on the imported actionbarsherlock project and select AndroidTools > Add Support Library

#### Import Amazon-App-Notifier ####
* Import the `app` directory into eclipse using File > Import > Maven > Existing Maven Projects
* If you get a an error about a missing actionbarsherlock dependency, you can delete the error

#### Running Unit Tests in Eclipse ####
* Right click on the `src/test/java` directory and select Run As > Run Configurations
* Create a new JUnit run configuration 
* In the new run configuration add the folder `amazonfreenotify-app/bin/classes` to the classpath
* In the pom.xml, change the scope for android from provided to test 
  (this will need changed back in order to build the project again)
