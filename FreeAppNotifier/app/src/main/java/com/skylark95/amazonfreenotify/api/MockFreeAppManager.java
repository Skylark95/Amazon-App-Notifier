package com.skylark95.amazonfreenotify.api;

import android.content.Context;

public class MockFreeAppManager extends FreeAppManager {

    @Override
    public FreeApp getFreeApp(Context context) {
        return new MockFreeApp(context);
    }

}
