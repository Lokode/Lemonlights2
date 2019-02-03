package com.lemonlights.lemon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lemonlights.lemon.Common.Common;
import com.lemonlights.lemon.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Singing in...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //check if user exists or not in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                            //Get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                //if user enters correct login, take me to main menu..
                                {
                                    Intent homeIntent = new Intent(SignIn.this,Home.class);
                                    Common.currentUser = user; //called from common/common.java
                                    startActivity(homeIntent);
                                    finish(); //next,went to home

                                }
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
