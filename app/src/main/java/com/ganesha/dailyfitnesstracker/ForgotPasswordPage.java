package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordPage extends Activity {

    private EditText emailaddresspasswordscreenView;
    private Button requestView;
    private Button cancelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);

        Parse.initialize(this, "XheqgiM5I4lMos47gnjaPiaJ86k56E4dW7QkXyWD", "KvEeTStjv0nlL2a6gjOAGtmJjLIk2z6tzUoOoJIL");

        emailaddresspasswordscreenView = (EditText) findViewById(R.id.emailAddressForgotPassword);
        requestView = (Button) findViewById(R.id.btnRequest);
        cancelView = (Button) findViewById(R.id.btnCancel);

        requestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailaddresspasswordscreenView.getText().toString().trim();

                if (isEmpty(emailaddresspasswordscreenView))
                {


                    Toast.makeText(ForgotPasswordPage.this, "Please Enter Email Address", Toast.LENGTH_LONG).show();
                }

                else
                {

                    ParseUser.requestPasswordResetInBackground(email,
                            new RequestPasswordResetCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        // An email was successfully sent with reset instructions.
                        Toast.makeText(ForgotPasswordPage.this, "An email was successfully sent with reset instructions.", Toast.LENGTH_LONG).show();

                                        Intent TakeUserLoginPage = new Intent(ForgotPasswordPage.this, LoginPage.class);
                                        startActivity(TakeUserLoginPage);
                                    } else {
                                        // Something went wrong. Look at the ParseException to see what's up.
                      Toast.makeText(ForgotPasswordPage.this, "Email Address is not Valid.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                  ForgotPasswordPage.this.finish();
                }

            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TakeUserLoginPage = new Intent(ForgotPasswordPage.this, LoginPage.class);
                startActivity(TakeUserLoginPage);
                ForgotPasswordPage.this.finish();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_password_page, menu);
        return true;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
