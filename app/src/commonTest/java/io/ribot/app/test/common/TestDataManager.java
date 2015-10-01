package io.ribot.app.test.common;

import android.content.Context;

import io.ribot.app.RibotApplication;
import io.ribot.app.data.DataManager;
import io.ribot.app.data.local.DatabaseHelper;
import io.ribot.app.data.remote.GoogleAuthHelper;
import io.ribot.app.data.remote.RibotService;
import io.ribot.app.test.common.injection.component.DaggerDataManagerTestComponent;
import io.ribot.app.test.common.injection.component.TestComponent;
import io.ribot.app.test.common.injection.module.DataManagerTestModule;

/**
 * Extension of DataManager to be used on a testing environment.
 * It uses DataManagerTestComponent to inject dependencies that are different to the
 * normal runtime ones. e.g. mock objects etc.
 * It also exposes some helpers like the DatabaseHelper or the Retrofit service that are helpful
 * during testing.
 */
public class TestDataManager extends DataManager {

    public TestDataManager(Context context) {
        super(context);
    }

    @Override
    protected void injectDependencies(Context context) {
        TestComponent testComponent = (TestComponent)
                RibotApplication.get(context).getComponent();
        DaggerDataManagerTestComponent.builder()
                .testComponent(testComponent)
                .dataManagerTestModule(new DataManagerTestModule(context))
                .build()
                .inject(this);
    }

    public RibotService getRibotService() {
        return mRibotService;
    }

    public DatabaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
    }

    public GoogleAuthHelper getGoogleAuthHelper() {
        return mGoogleAuthHelper;
    }
}