package ar.edu.itba.example.gymateapp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.ActivityLoginBinding;
import ar.edu.itba.example.gymateapp.databinding.FragmentLoginBinding;
import ar.edu.itba.example.gymateapp.model.UserInfo;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.viewModel.UserViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginFragment extends Fragment {
    private EditText username, password;
    private UserViewModel viewModel;
    private LiveData<UserInfo> userInfo;
    private String arg1;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle a = getArguments();
        if(a != null ){
            arg1 = a.getString("RoutineId");
        }else {
            arg1 = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentLoginBinding binding = FragmentLoginBinding.inflate(getLayoutInflater());
        username = binding.username;
        password = binding.password;

        View view = binding.getRoot();
        Button loginBtn = view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v -> tryLogin());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void tryLogin() {
        if (!validateUsername() | !validatePassword()) {
            return;
        }
        viewModel.tryLogin(username.getText().toString().toUpperCase(), password.getText().toString());

        viewModel.getLoginError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                switch (error.getCode()) {
                    case 4:
                        password.setError(" ");
                        username.setError(" ");
                        new Handler().postDelayed(() -> {
                            password.setError(null);
                            username.setError(null);
                        }, 2000);
                        viewModel.setLoginErrorCode(null);
                        break;
                    default:
                        break;
                }
            }
        });

        viewModel.getToken().observe(getViewLifecycleOwner(), authToken -> {
            if (authToken != null) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                Bundle aux = getArguments();
                if(aux != null){
                    if (aux.get("RoutineId") != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("routineId", Integer.parseInt(aux.getString("RoutineId")));
                        new NavDeepLinkBuilder(requireActivity())
                                .setComponentName(MainActivity.class)
                                .setGraph(R.navigation.mobile_navigation)
                                .setDestination(R.id.routine_detail)
                                .setArguments(bundle).createTaskStackBuilder().startActivities();
                    }
                } else {
                    startActivity(intent);
                }
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                requireActivity().finish();
            }
        });

    }

    private boolean validateUsername() {
        String val = username.getText().toString().trim();
        String checkspaces = "^[a-zA-Z0-9\\-_]{1,20}$"; //No white spaces, must contain at most 20 characters.

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No white spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getText().toString().trim();
        String checkPassword = "^[a-zA-Z0-9\\-_]{8,}$"; //No whitespaces, must contain more than 8 characters.

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain at least 8 characters!");
            return false;
        } else {
            password.setError(null);
            password.setEnabled(false);
            return true;
        }
    }


}

