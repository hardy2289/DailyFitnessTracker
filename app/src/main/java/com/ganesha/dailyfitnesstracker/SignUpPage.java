package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.parse.SignUpCallback;

public class SignUpPage extends Activity {


    private EditText firstnameView;
    private EditText lastnameView;
    private EditText emailaddressView;
    private EditText dobView;
    private EditText ageView;
    private EditText genderView;
    private EditText heightView;
    private EditText weightView;
    private EditText passwordView;
    private Button signupbuttonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);

        Parse.initialize(this, "XheqgiM5I4lMos47gnjaPiaJ86k56E4dW7QkXyWD", "KvEeTStjv0nlL2a6gjOAGtmJjLIk2z6tzUoOoJIL");

        firstnameView = (EditText) findViewById(R.id.txtFirstName);
        lastnameView = (EditText) findViewById(R.id.txtLastName);
        emailaddressView = (EditText) findViewById(R.id.txtEmailAddress);
        dobView = (EditText) findViewById(R.id.editText2);
        genderView = (EditText) findViewById(R.id.txtGender);
        ageView = (EditText) findViewById(R.id.textAge);
        heightView = (EditText) findViewById(R.id.txtHeight);
        weightView = (EditText) findViewById(R.id.txtWeight);
        passwordView = (EditText) findViewById(R.id.txtPassword);
        signupbuttonView = (Button) findViewById(R.id.btnSignUpPage);

        signupbuttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = firstnameView.getText().toString().trim();
                String lastname = lastnameView.getText().toString().trim();
                String email = emailaddressView.getText().toString().trim();
                String dob = dobView.getText().toString().trim();
                String gender = genderView.getText().toString().trim();
                String age = ageView.getText().toString().trim();
                String height = heightView.getText().toString().trim();
                String weight = weightView.getText().toString().trim();
                String password = passwordView.getText().toString().trim();



                if (isEmpty(firstnameView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter First Name", Toast.LENGTH_LONG).show();
                }
                else if (isEmpty(lastnameView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter Last Name", Toast.LENGTH_LONG).show();
                }
                else if (isEmpty(emailaddressView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter Email Address", Toast.LENGTH_LONG).show();
                }
                else if (isEmpty(dobView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter Date of Birth", Toast.LENGTH_LONG).show();
                }
               else if (isEmpty(genderView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter Gender", Toast.LENGTH_LONG).show();
                }
                else if (isEmpty(ageView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter Age", Toast.LENGTH_LONG).show();
                }
                else if (isEmpty(heightView))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter Height", Toast.LENGTH_LONG).show();
                }
               else {
                    if (isEmpty(weightView)) {
                        Toast.makeText(SignUpPage.this, "Please Enter Weight", Toast.LENGTH_LONG).show();
                    } else {
                        if (isEmpty(passwordView)) {
                            Toast.makeText(SignUpPage.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                        } else {


                            ParseUser user = new ParseUser();

                            user.setUsername(email);
                            user.setEmail(email);
                            user.setPassword(password);
                            user.put("firstName", firstname);
                            user.put("lastName", lastname);
                            user.put("dob", dob);
                            user.put("gender", gender);
                            user.put("age", age);
                            user.put("height", height);
                            user.put("weight", weight);
                            //user.saveInBackground();


                            // Toast.makeText(SignUpPage.this, "Welcome, Sign Up Successful!", Toast.LENGTH_LONG).show();
                            // other fields can be set just like with ParseObject


                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        // Hooray! Let them use the app now.
                                        Toast.makeText(SignUpPage.this, "Welcome, Sign Up Successful!", Toast.LENGTH_LONG).show();
    /*Intent intent = new Intent(SignUpPage.this, StartupPage.class);
    intent.putExtra("user_id", user.getObjectId());
    startActivity(intent);*/

                                        Intent TakeUserLoginPage = new Intent(SignUpPage.this, LoginPage.class);
                                        startActivity(TakeUserLoginPage);
                                        SignUpPage.this.finish();
                                        //take user to login page again

                                    } else {
                                        // Sign up didn't succeed. Look at the ParseException
                                        // to figure out what went wrong
                                        // Log.d("", "User update error: " + e);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage.this);
                                        builder.setMessage(e.getMessage());
                                        builder.setTitle("Sorry!");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        });

                                        SignUpPage.this.finish();
                                    }
                                }
                            });
                        }
                    }
                }
            }


        });

        }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_page, menu);
        return true;
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
