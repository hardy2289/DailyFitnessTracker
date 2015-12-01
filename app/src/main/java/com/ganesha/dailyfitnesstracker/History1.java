package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;


public class History1 extends Activity {


    private Button Save1;
    private Button Delete1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history1);

        FlurryAgent.init(this, "GXZ2JQFK5XZD67XY6PNV");
        FlurryAgent.setLogEnabled(false);


        Save1 = (Button) findViewById(R.id.btnSave1);
        Delete1 = (Button) findViewById(R.id.btnDelete1);

        Save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(History1.this, "No Records To Save!", Toast.LENGTH_LONG).show();
                Intent TakeUserActivityPage = new Intent(History1.this, StartNewActivity.class);
                startActivity(TakeUserActivityPage);
               History1.this.finish();
            }
        });

        Delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(History1.this, "No Records To Delete!", Toast.LENGTH_LONG).show();
                Intent TakeUserActivity1Page = new Intent(History1.this, StartNewActivity.class);
                startActivity(TakeUserActivity1Page);
                History1.this.finish();

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history1, menu);
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
