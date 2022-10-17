package com.dmitri.mobileclientservice.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.dmitri.mobileclientservice.R;
import com.dmitri.mobileclientservice.data.model.LoginEntry;
import com.dmitri.mobileclientservice.databinding.FragmentUserBinding;
import com.dmitri.mobileclientservice.ui.viewmodels.UserViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserFragment extends DaggerFragment {
    private static final String TAG = "UserFragment";

    private FragmentUserBinding binding;
    private UserFragmentArgs args;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private UserViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentUserBinding.bind(view);
        args = UserFragmentArgs.fromBundle(getArguments());
        viewModel = viewModelFactory.create(UserViewModel.class);

        binding.userCode.setText(String.format("Code: %s", args.getUserLoginResponse().code));

        viewModel.getLoginEntries();
        viewModel.loginEntries.observe(getViewLifecycleOwner(), this::onLoginEntriesUpdate);

        Completable.fromRunnable(() ->
                viewModel.saveLoginEntry(args.getUser(), args.getUserLoginResponse()))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void onLoginEntriesUpdate(List<LoginEntry> entries) {
        Log.d(TAG, "Login entries: ");
        for (LoginEntry entry : entries) {
            Log.d(TAG, "\nUsername: " + entry.getUsername() + " \nCode : " + entry.getCode());
        }
    }
}
