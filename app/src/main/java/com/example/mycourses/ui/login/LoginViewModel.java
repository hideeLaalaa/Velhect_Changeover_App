package com.example.mycourses.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.example.mycourses.data.LoginRepository;
import com.example.mycourses.data.Result;
import com.example.mycourses.data.model.LoggedInUser;
import com.example.mycourses.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("ChangeOver");

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String users = dataSnapshot.child("wifiName").getValue(String.class);
                String pass = dataSnapshot.child("wifiPassword").getValue(String.class);

                Result<LoggedInUser> result = loginRepository.login(username, password,users,pass);

                if (result instanceof Result.Success) {

                    LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                    loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                } else {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        };
        myRef.addListenerForSingleValueEvent(postListener);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }else {
//            return username.toLowerCase().equals("change");
            return  true;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null || password.toLowerCase().equals("over");
    }
}