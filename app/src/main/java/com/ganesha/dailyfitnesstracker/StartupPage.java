package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.flurry.android.FlurryAgent;

public class StartupPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);


    // Log in button click handler
        ((Button) findViewById(R.id.btnLoginHomeScreen)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(StartupPage.this, LoginPage.class));
            }
        });

        // Sign up button click handler
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(StartupPage.this, SignUpPage.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {

            case R.id.Login:
                //logout the user

                Intent TakeUserLoginPage = new Intent(StartupPage.this, LoginPage.class);
                startActivity(TakeUserLoginPage);

                break;


            case R.id.SignUp:
                //logout the user
                Intent NewActivity = new Intent(StartupPage.this, SignUpPage.class);
                startActivity(NewActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


/*   public void buttonOnClick(View view) {

        String button_text;

        button_text = ((Button) view).getText().toString();

        if (button_text.equals("Login")) {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);

        } else if (button_text.equals("Sign Up")) {

            Intent intent = new Intent(this, SignUpPage.class);
            startActivity(intent);
        }*/

/* public void buttonOnClick(View v)
  {
      {
          Button btnLoginHomeScreen = (Button) v;
          startActivity(new Intent(getApplicationContext(), LoginPage.class));
      }
  }*/