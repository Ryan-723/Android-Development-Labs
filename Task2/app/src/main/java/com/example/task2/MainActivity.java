package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    EditText Name,Email,Address,Password,conPassword;
    Button Register,Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Address = findViewById(R.id.address);
        Password = findViewById(R.id.password);
        conPassword = findViewById(R.id.confirm_password);
        Register = findViewById(R.id.register);
        Login = findViewById(R.id.login);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registerdata = Name.getText().toString()+  Email.getText().toString() + Address.getText().toString();
                Toast.makeText(getApplicationContext(), registerdata, Toast.LENGTH_LONG).show();
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this,"Validating the credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }


}