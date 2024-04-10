package com.example.mobileno_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class contactDetailsActivity extends AppCompatActivity {

    EditText editTextMobile;
    Button getOTPButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        editTextMobile = findViewById(R.id.editTextMobile);
        getOTPButton = findViewById(R.id.buttonGetOTP);

        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        getOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextMobile.getText().toString().trim().isEmpty()) {
                    if (editTextMobile.getText().toString().trim().length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        getOTPButton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + editTextMobile.getText().toString(), 60, TimeUnit.SECONDS,
                                contactDetailsActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOTPButton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOTPButton.setVisibility(View.VISIBLE);
                                        Toast.makeText(contactDetailsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendotp, forceResendingToken);
                                        progressBar.setVisibility(View.GONE);
                                        getOTPButton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(contactDetailsActivity.this, otpActivity.class);
                                        intent.putExtra("mobile", editTextMobile.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                        super.onCodeAutoRetrievalTimeOut(s);
                                        // Handle timeout here if needed
                                        Toast.makeText(contactDetailsActivity.this, "OTP retrieval timed out", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );


                    } else {
                        Toast.makeText(contactDetailsActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(contactDetailsActivity.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
