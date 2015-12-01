package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.parse.Parse;

public class History extends Activity {
   // TextView txStepcounts;
    String Stepcounts;

   // TextView txCalories;
    String Calories;


   // TextView txSDist;
    String Distanc;

   // TextView txDur;
    String duration1;

    TextView txTim;
    String Ctime;

    String STtime;

    String ETtime;
   private Button Save;
    private Button Delete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);

        Parse.initialize(this, "XheqgiM5I4lMos47gnjaPiaJ86k56E4dW7QkXyWD", "KvEeTStjv0nlL2a6gjOAGtmJjLIk2z6tzUoOoJIL");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Save = (Button) findViewById(R.id.btnSave);
        Delete = (Button) findViewById(R.id.btnDelete);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(History.this, "Your Records Has been Saved.", Toast.LENGTH_LONG).show();
                Intent TakeUserActivityPage = new Intent(History.this, StartNewActivity.class);
                startActivity(TakeUserActivityPage);
                History.this.finish();
            }

        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(History.this, "Your Records Has been Deleted.", Toast.LENGTH_LONG).show();
                Intent TakeUserActivityPage = new Intent(History.this, StartNewActivity.class);
                startActivity(TakeUserActivityPage);

                History.this.finish();
            }

        });

        Intent newIntent = getIntent();
        Bundle bundle = newIntent.getExtras();

        //  txStepcounts= (TextView) findViewById(R.id.txtStepsCounts);
        // String Scounts= txStepcounts.getText().toString().trim();
        TextView tvsc = (TextView) findViewById((R.id.txtDisplayStepCounts));
        Stepcounts = bundle.getString("StepCounts");
        tvsc.setText(Stepcounts);


        TextView tvcb = (TextView) findViewById((R.id.txtDisplayCaloriesBurn));
        Calories = bundle.getString("CaloriesBurn");
        tvcb.setText(Calories);


        TextView txSDist = (TextView) findViewById((R.id.txtDisplayDistance));
        Distanc = bundle.getString("Distance");
        txSDist.setText(Distanc);


        TextView txDur = (TextView) findViewById((R.id.txtDisplayDuration));
        duration1 = bundle.getString("Duration");
        txDur.setText(duration1);


        TextView txTim = (TextView) findViewById((R.id.txtDisplayCurrentTime));
        Ctime = bundle.getString("CurrentTime");
        txTim.setText(Ctime);


        TextView xtStime=(TextView)findViewById(R.id.txtDisplayStartTime);
        STtime=bundle.getString("StartTime");
        xtStime.setText(STtime);

        TextView txEtime=(TextView)findViewById(R.id.txtDisplayElapsedTime);
        ETtime=bundle.getString("ElapsedTime");
        txEtime.setText(ETtime);


        // do some thing which you want in try block

        // NewMethod();
       /* Intent i= getIntent();
        Stepcounts=i.getStringExtra("CaloriesBurn");
        Calories=i.getStringExtra("StepCounts");
        txStepcounts=(TextView)findViewById(R.id.txtStepsCounts);
        txCalories=(TextView)findViewById(R.id.txtCaloriesBurn);
        txStepcounts.setText(Stepcounts);
        txCalories.setText(Calories);

    }




 /* public void NewMethod () {
      TextView tv = (TextView) findViewById(R.id.TextDisplay);
      Intent newIntent = getIntent();
      Bundle bundle = newIntent.getExtras();
      String duration1 = bundle.getString("Duration");
      tv.setText(duration1);
  }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
