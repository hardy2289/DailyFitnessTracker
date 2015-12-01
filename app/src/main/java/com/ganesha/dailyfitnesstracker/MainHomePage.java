package com.ganesha.dailyfitnesstracker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainHomePage extends Activity implements  MediaPlayerControl{


    private Button startnewactivityView;
    private Button settingsView;
    private Button historyView;



    //Variables for the Media Player
    private MusicController controller;
    private ArrayList<Song> songList;
    private ListView songView;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private boolean paused=false, playbackPaused=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_page);

        songView = (ListView)findViewById(R.id.song_list);
        songList = new ArrayList<Song>();
        getSongList();

        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle1().compareTo(b.getTitle1());
            }
        });

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);
        setController();



        Parse.initialize(this, "XheqgiM5I4lMos47gnjaPiaJ86k56E4dW7QkXyWD", "KvEeTStjv0nlL2a6gjOAGtmJjLIk2z6tzUoOoJIL");
        startnewactivityView = (Button) findViewById(R.id.btnStartNewActivity);
        settingsView=(Button)findViewById(R.id.btnSettings);
        historyView=(Button)findViewById(R.id.btnHistory);


        //********************************************************************//
        //Navigation to other pages*******************************************//
        //*****************************************************************I**//


        startnewactivityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TakeUserStartActivityPage = new Intent(MainHomePage.this, StartNewActivity.class);
                startActivity(TakeUserStartActivityPage);
                //LoginPage.this.finish();
            }
        });

        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TakeUserSettingPage = new Intent(MainHomePage.this, Settings.class);
                startActivity(TakeUserSettingPage);
            }
        });


        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TakeUserHistoryPage = new Intent(MainHomePage.this, History1.class);
                startActivity(TakeUserHistoryPage);
            }
        });
    }







    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_home_page, menu);
        return true;
    }

    //******************************************************************//
    //For the menu option of the application which on the top
    //***********************************************************//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {

            case R.id.Logout:
                //logout the user
                ParseUser.logOut();
                Intent TakeUserLoginPage=new Intent(MainHomePage.this, LoginPage.class);
                startActivity(TakeUserLoginPage);
                MainHomePage.this.finish();
                break;


            case R.id.NewActivity:
                //logout the user
                Intent NewActivity=new Intent(MainHomePage.this, StartNewActivity.class);
                startActivity(NewActivity);
                break;

            case R.id.History:
                //logout the user
                Intent NewHistory=new Intent(MainHomePage.this, History1.class);
                startActivity(NewHistory);
                break;


            case R.id.Settings:
                //take user to settings page
                Intent intent=new Intent(this, Settings.class);
                startActivity(intent);
                break;




        }

        return super.onOptionsItemSelected(item);
    }

    //*******************************************************//
    //All the codes for the music player of the Main Home Page//
    //********************************************************//

    private void setController(){
        //set the controller up
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);

    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        //retrieve song info

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }

    }

    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }


    private void playNext(){
        musicSrv.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    //play previous
    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }



  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item

        switch (item.getItemId()) {
            case R.id.action_shuffle:
                musicSrv.setShuffle();
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;


        }
        return super.onOptionsItemSelected(item);*/




    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicSrv!=null&& musicBound && musicSrv.isPng())
            return musicSrv.getDur();
        else return 0;
    }


    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null && musicBound&& musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }
    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);}

    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
            return musicSrv.isPng();
        return false;
    }


    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }
    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }



}
