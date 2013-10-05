package com.skylark95.robolectric;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.runners.model.InitializationError;

import java.io.File;

public class AmazonAppNotifierTestRunner extends RobolectricTestRunner {
    
    private static final String ANDROID_PROJECT_ROOT = RobolectricProperties.getProperty("project");
    
    public AmazonAppNotifierTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, new File(ANDROID_PROJECT_ROOT));
    }

}
