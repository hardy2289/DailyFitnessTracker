package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class Settings extends Activity {



    private EditText firstnameViewNew;
    private EditText lastnameViewNew;
    private EditText emailaddressViewNew;
    private EditText dobViewNew;
    private EditText ageViewNew;
    private EditText genderViewNew;
    private EditText heightViewNew;
    private EditText weightViewNew;

    private Button   UpdateDetailsViewNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Configure Flurry
        FlurryAgent.setLogEnabled(false);

        //init flurry
        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");






        Parse.initialize(this, "XheqgiM5I4lMos47gnjaPiaJ86k56E4dW7QkXyWD", "KvEeTStjv0nlL2a6gjOAGtmJjLIk2z6tzUoOoJIL");


        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("User",true);
        installation.saveInBackground();


        firstnameViewNew = (EditText) findViewById(R.id.txtFirstNameSettings);
        lastnameViewNew = (EditText) findViewById(R.id.txtLastNameSettings);
        emailaddressViewNew = (EditText) findViewById(R.id.txtEmailAddressSettings);
        dobViewNew = (EditText) findViewById(R.id.editText2Settings);
        genderViewNew = (EditText) findViewById(R.id.txtGenderSettings);
        ageViewNew = (EditText) findViewById(R.id.textAgeSettings);
        heightViewNew = (EditText) findViewById(R.id.txtHeightSettings);
        weightViewNew = (EditText) findViewById(R.id.txtWeightSettings);

        UpdateDetailsViewNew = (Button) findViewById(R.id.btnUpadateStatus);


        firstnameViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("firstname"));
        lastnameViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("lastname"));
        dobViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("dob"));
        genderViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("gender"));
        ageViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("age"));
        heightViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("height"));
        weightViewNew.setText((CharSequence)ParseUser.getCurrentUser().get("weight"));
        emailaddressViewNew.setText(ParseUser.getCurrentUser().getEmail());


       UpdateDetailsViewNew.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               final String DateOfBirth =   dobViewNew.getText().toString();
               final String FirstName    =   firstnameViewNew.getText().toString();
               final String LastName      =   lastnameViewNew.getText().toString();
               final String Gender    =   genderViewNew.getText().toString();
               final String Email     =   emailaddressViewNew.getText().toString();
               final String Age      =   ageViewNew.getText().toString();
               final String Height    =   heightViewNew.getText().toString();
               final String Weight     =   weightViewNew.getText().toString();



               ParseUser user = ParseUser.getCurrentUser();
               user.setUsername(Email);
               user.setEmail(Email);
               user.put("lastname", LastName);
               user.put("dob", DateOfBirth);
               user.put("firstname", FirstName);
               user.put("gender", Gender);
               user.put("age", Age);
               user.put("height", Height);
               user.put("weight", Weight);

               user.saveInBackground(new SaveCallback() {
                   @Override
                   public void done(ParseException e) {
                       setProgressBarIndeterminateVisibility(false);

                       if (e == null) {
                           //Success!
                           Toast.makeText(Settings.this, " Your Details updated successfully ", Toast.LENGTH_LONG).show();
                           Intent intent = new Intent(Settings.this, MainHomePage.class);
                           startActivity(intent);

                       } else {

                           AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                           builder.setMessage(e.getMessage())
                                   //.setTitle(R.string.sign_up_error_title)
                                   .setPositiveButton(android.R.string.ok, null);
                           AlertDialog dialog = builder.create();
                           dialog.show();
                       }
                   }
               });

           }
       });











            }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {


            case R.id.SettingsLogout:
                ParseUser.logOut();
                Intent TakeUserLoginPage=new Intent(Settings.this, LoginPage.class);
                startActivity(TakeUserLoginPage);
                Settings.this.finish();
                break;

            case R.id.SettingsHistory:
                //logout the user
                Intent NewHistory=new Intent(Settings.this, History1.class);
                startActivity(NewHistory);
                Settings.this.finish();
                break;


            case R.id.SettingsActivity:
                //take user to settings page
                Intent intent=new Intent(this, Settings.class);
                startActivity(intent);
                Settings.this.finish();
                break;

        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
