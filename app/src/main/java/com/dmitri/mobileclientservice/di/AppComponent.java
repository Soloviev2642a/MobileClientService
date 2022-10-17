package com.dmitri.mobileclientservice.di;

import com.dmitri.mobileclientservice.App;
import com.dmitri.mobileclientservice.di.modules.ActivityBuildersModule;
import com.dmitri.mobileclientservice.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
    modules = {
        AppModule.class,
        ActivityBuildersModule.class,
        AndroidSupportInjectionModule.class
    }
)
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }
}