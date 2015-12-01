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
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;

public class LoginPage extends Activity {

    private EditText emailaddressloginView;
    private EditText passwordloginView;
    private Button loginView;
    private Button forgotpasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);
        Parse.initialize(this, "XheqgiM5I4lMos47gnjaPiaJ86k56E4dW7QkXyWD", "KvEeTStjv0nlL2a6gjOAGtmJjLIk2z6tzUoOoJIL");

        //initialise variables

        emailaddressloginView = (EditText)findViewById(R.id.emailAddress);
        passwordloginView = (EditText)findViewById(R.id.password);
        loginView= (Button)findViewById(R.id.btnLoginScreen);
        forgotpasswordView=(Button)findViewById(R.id.btnForgotPassword);



        //listen to when the loginview button clicked
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Emailaddress= emailaddressloginView.getText().toString().trim();
                String Password= passwordloginView.getText().toString().trim();


                if (isEmpty(emailaddressloginView))
                {
                    Toast.makeText(LoginPage.this, "Please Enter Email Address", Toast.LENGTH_LONG).show();
                }
                else if (isEmpty(passwordloginView))
                {
                    Toast.makeText(LoginPage.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                }



                else
                {
                //get the user inputs covert into string


                ParseUser.logInInBackground(Emailaddress, Password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {

                        if (e == null) {
                            //success, user sign up#
                            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_LONG).show();

                            //take user to home page
                            Intent TakeUserHomePage = new Intent(LoginPage.this, MainHomePage.class);
                            startActivity(TakeUserHomePage);

                        }

                        else
                        {
                            //sorry there was a problem, advice user for error


                           Toast.makeText(LoginPage.this, "Incorrect Details!", Toast.LENGTH_LONG).show();
                         //   LoginPage.this.finish();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });


                        }

                    }





                });

            }

            }


        });

        forgotpasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TakeUserHomePage=new Intent(LoginPage.this, ForgotPasswordPage.class);
                startActivity(TakeUserHomePage);
                LoginPage.this.finish();
            }
        });


        //login the user using parse sdk



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
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id)
        {



            case R.id.SignuP:
                //logout the user
                Intent NewHistory=new Intent(LoginPage.this, SignUpPage.class);
                startActivity(NewHistory);
               LoginPage.this.finish();
                break;


            case R.id.ForgotPassword:
                //take user to settings page
                Intent intent=new Intent(LoginPage.this, ForgotPasswordPage.class);
                startActivity(intent);
                break;




        }
        return super.onOptionsItemSelected(item);
    }
}
