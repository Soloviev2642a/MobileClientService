package com.dmitri.mobileclientservice.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dmitri.mobileclientservice.ui.viewmodels.AuthViewModel;
import com.dmitri.mobileclientservice.ui.viewmodels.UserViewModel;
import com.dmitri.mobileclientservice.ui.viewmodels.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import kotlin.Suppress;

@Suppress(names = "unused")
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}

