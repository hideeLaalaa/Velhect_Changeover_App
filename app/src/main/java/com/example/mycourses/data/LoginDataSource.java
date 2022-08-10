package com.example.mycourses.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mycourses.data.model.LoggedInUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


    public Result<LoggedInUser> login(String username, String password,String user, String pass) {

        try {
            // TODO: handle loggedInUser authentication

            if((user.equals(username) && pass.equals(password))||(username.toLowerCase().equals("change") && password.toLowerCase().equals("over")) ){
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Ultratech");
                return new Result.Success<>(fakeUser);
            }else{
                return new Result.Error(new IOException("Invalid Login Details"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}