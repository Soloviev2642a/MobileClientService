package com.dmitri.mobileclientservice.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.dmitri.mobileclientservice.R;
import com.dmitri.mobileclientservice.api.response.UserLoginResponse;
import com.dmitri.mobileclientservice.data.model.User;
import com.dmitri.mobileclientservice.databinding.FragmentAuthBinding;
import com.dmitri.mobileclientservice.ui.adapters.UserSpinnerAdapter;
import com.dmitri.mobileclientservice.ui.viewmodels.AuthViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class AuthFragment extends DaggerFragment {
    private static final String TAG = "AuthFragment";

    private FragmentAuthBinding binding;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private AuthViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentAuthBinding.bind(view);
        viewModel = viewModelFactory.create(AuthViewModel.class);

        viewModel.getUsers().observe(getViewLifecycleOwner(), this::onUserListUpdate);
        viewModel.requestUsers();
        binding.loadingLabel.setVisibility(View.VISIBLE);

        binding.loginButton.setOnClickListener(v -> onLoginButtonClicked());
        viewModel.getLoginStatus().observe(getViewLifecycleOwner(), this::onLoginStatusChanged);
    }

    private void onUserListUpdate(List<User> users) {
        binding.loadingLabel.setVisibility(View.INVISIBLE);
        if (users == null) {
            Toast.makeText(getContext(), "Users request unsuccessful", Toast.LENGTH_SHORT).show();
        }
        UserSpinnerAdapter userSpinnerAdapter = new UserSpinnerAdapter(
                getContext(), android.R.layout.simple_spinner_dropdown_item, users);
        binding.userSpinner.setAdapter(userSpinnerAdapter);
    }

    private void onLoginButtonClicked() {
        String password = binding.userPassword.getText().toString();

        if (!password.trim().isEmpty()) {
            if (viewModel.getUsers().getValue() == null) {
                Toast.makeText(getContext(), "No users loaded", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.userLogin(
                    getSpinnerSelectedUser().getUid(),
                    password
            );
        } else {
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
        }
    }

    private User getSpinnerSelectedUser() {
        return viewModel.getUsers().getValue().get(binding.userSpinner.getSelectedItemPosition());
    }

    private void onLoginStatusChanged(Boolean status) {
        if (status) {
            navigateToUserFragment(
                    getSpinnerSelectedUser(),
                    viewModel.userLoginResponse
            );
        } else {
            Toast.makeText(getContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToUserFragment(User user, UserLoginResponse userLoginResponse) {
        NavDirections action = AuthFragmentDirections.toUserFragment(user, userLoginResponse);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) {
            binding.userPassword.setText("");
        }
    }
}










