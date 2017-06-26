package com.example.qenawi.fbmini;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.qenawi.fbmini.items.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
public class Register extends AppCompatActivity
{
    EditText name_edittxt;
    EditText email_edittxt;
    EditText password_edittxt;
    EditText confirm_password_edittxt;
    EditText mobile_edittxt;
    EditText user_adress;
    Button create_btn;
    Button cancel_btn;
    String emailKey;

    ArrayList <String> follower;

    Firebase connect;
    FirebaseAuth fireauth;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);

        connect = new Firebase("https://fbmini-956d5.firebaseio.com/");

        name_edittxt = (EditText) findViewById(R.id.input_name);
        email_edittxt = (EditText) findViewById(R.id.input_Email);
        password_edittxt = (EditText) findViewById(R.id.input_password);
        confirm_password_edittxt = (EditText) findViewById(R.id.input_confirm_password);
        mobile_edittxt = (EditText) findViewById(R.id.input_mobile);
        user_adress = (EditText) findViewById(R.id.input_address);
        create_btn = (Button) findViewById(R.id.create_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
    }


    public void Create_Account(View view)
    {

        String User_Name = name_edittxt.getText().toString();
        final String User_Email = email_edittxt.getText().toString();
        final String User_Password = password_edittxt.getText().toString();
        String User_Confirm_Password = confirm_password_edittxt.getText().toString();
        String User_Address = user_adress.getText().toString();
        String User_Mobile = mobile_edittxt.getText().toString();



        if (User_Name.isEmpty())
        {
            name_edittxt.setError("Name is Requiered");
        }

        if (User_Email.isEmpty())
        {
            email_edittxt.setError("Email is Requiered");
        }

        else if (!User_Email.contains("@yahoo.com"))
        {
            email_edittxt.setError("Invalid Email Address");
        }
        else if (User_Password.isEmpty())
        {
            password_edittxt.setError("Password is Requiered");
        }
        else if (User_Password.length() < 7)
        {
            password_edittxt.setError("This Password is Too Short");
        }
        else if (User_Confirm_Password.isEmpty())
        {
            confirm_password_edittxt.setError("Password is Requiered");
        }
        else if (User_Confirm_Password.equals(User_Password))
        {
            confirm_password_edittxt.setError("Password Doesn't Match");
        }
        else if (User_Address.isEmpty())
        {
            user_adress.setError("Address is Requiered");
        }
        else if (User_Mobile.isEmpty())
        {
            mobile_edittxt.setError("Mobile is Requiered");
        }

        else
        {
            progress = ProgressDialog.show(this, "", "Please Wait... ", true);
            SignUpTask();
        }
    }

    public void SignUpTask()
    {
        final String name, email, pass, age, adr, mob;
        name = name_edittxt.getText().toString();
        email = email_edittxt.getText().toString();
        pass = password_edittxt.getText().toString();
        age = confirm_password_edittxt.getText().toString();
        adr = user_adress.getText().toString();
        mob =  mobile_edittxt.getText().toString();
        emailKey = email.substring(0, email.indexOf('@'));
        fireauth = FirebaseAuth.getInstance();
        fireauth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {


                            Firebase fireusers = new Firebase("https://fbmini-956d5.firebaseio.com/users/"+emailKey);
                            final User newUser = new User(name, email_edittxt.getText().toString(), pass, age, adr, mob, follower);



                            fireusers.setValue(newUser);
                            progress.dismiss();
                            Toast.makeText(Register.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            fireusers.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                                {
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s)
                                {
                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                        }
                        else
                        {
                            progress.dismiss();
                            Toast.makeText(Register.this, "This Email is Exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void Cancel()
    {
        finish();
    }
}

