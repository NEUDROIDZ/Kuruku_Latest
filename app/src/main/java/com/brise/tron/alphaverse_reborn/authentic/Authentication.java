package com.brise.tron.alphaverse_reborn.authentic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.homepage.BaseActivity;
import com.brise.tron.alphaverse_reborn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.onesignal.OneSignal;

import java.util.concurrent.TimeUnit;

public class Authentication extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    // [END declare_auth]

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks ProcessCallback;
    private LinearLayout verification,resend;
    private EditText num1, num2,num3;
    Button verf1,verf2,resnd;
    private ProgressBar bar1,bar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        OneSignal.startInit(this).init();
        OneSignal.setSubscription(true);
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);



        verification = (LinearLayout)findViewById(R.id.verif);
        resend = (LinearLayout)findViewById(R.id.resend);
        num1 = (EditText)findViewById(R.id.number);
        num2 = (EditText)findViewById(R.id.mcode);
        num3 = (EditText)findViewById(R.id.nphone);
        verf1 = (Button)findViewById(R.id.vbtn);
        verf2 = (Button)findViewById(R.id.verify);
        resnd = (Button)findViewById(R.id.button_resend);
        bar1 = (ProgressBar)findViewById(R.id.pbar);
        bar2 = (ProgressBar)findViewById(R.id.pbar1);
        verf1.setOnClickListener(this);
        verf2.setOnClickListener(this);
        resnd.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            Intent intent = new Intent(Authentication.this, BaseActivity.class);
            startActivity(intent);
            finish();
        }

        ProcessCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                    Toast.makeText(Authentication.this,"problematic phone number verify number length and country code",Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(Authentication.this,"the number of requests for firebase Quota exceeded please contact your admin",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId = s;
                mResendToken = forceResendingToken;

            }
        };

    }

    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+237"+ num1.getText().toString(),60, TimeUnit.SECONDS,Authentication.this,ProcessCallback);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                Authentication.this,               // Activity (for callback binding)
                ProcessCallback,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Authentication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, redirect to mainActivity with the signed-in user's information
                            Intent intent = new Intent(Authentication.this, BaseActivity.class);
                            startActivity(intent);

                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Authentication.this,"invalid verification code please try again",Toast.LENGTH_LONG).show();

                            }

                            Toast.makeText(Authentication.this,"verification failed please Contact your Admin",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vbtn:
                if (!validatePhoneNumber()) {
                    return;
                }
                bar1.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                verification.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
                startPhoneNumberVerification(num1.getText().toString());
                num1.setText(null);
                break;
            case R.id.verify:
                String code = num2.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(Authentication.this,"Cannot be empty.",Toast.LENGTH_LONG).show();
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:
                bar2.setVisibility(View.VISIBLE);
                resendVerificationCode(num3.getText().toString(), mResendToken);
                break;
        }

    }
    private boolean validatePhoneNumber() {
        String phoneNumber ="+237" + num1.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(Authentication.this,"problematic phone number verify number length and country code",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }
}
