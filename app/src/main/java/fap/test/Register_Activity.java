package fap.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_Activity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private EditText Email;
    private Button Login;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Email = findViewById(R.id.etEmail);
        Login = findViewById(R.id.btnLogin);
        Register = findViewById(R.id.btnRegister);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateRegister(Name.getText().toString(), Password.getText().toString(), Email.getText().toString());
            }
        });
    }

    private void validateRegister(String userName, String userPassword, String userEmail){
        if((!userName.isEmpty()) && (!userPassword.isEmpty()) && (!userEmail.isEmpty())){
            Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
            startActivity(intent);
        }
    }

}
