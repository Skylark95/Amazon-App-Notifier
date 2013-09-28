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
* Right click on the imported actionbarsherlock project and select Android Tools > Add Support Library

#### Import Amazon-App-Notifier ####
* Import the `app` directory into eclipse using File > Import > Maven > Existing Maven Projects
* If you get a an error about a missing actionbarsherlock dependency, you can delete the error
* If the project cannot find ActionBarSherlock, you may need to remove and add it again as an Anrdoid library
  * This can be done by performing a right click on the project and going to Properties > Android
  * In the Library section, select actionbarsherlock and press 'Remove'
  * In the Library section, click 'Add...' and select the actionbarsherlock project

#### Running Unit Tests in Eclipse ####
* In the pom.xml, change the scope for android from provided to 'test'
    * NOTE: This will need changed back to 'provided' in order to build the project again
* Right click on the `src/test/java` directory and select Run As > Run Configurations
* Create a new JUnit run configuration 
* In the new run configuration add the folder `amazonfreenotify-app/bin/classes` to the classpath
* Run the JUnit tests uning the new run configuration

