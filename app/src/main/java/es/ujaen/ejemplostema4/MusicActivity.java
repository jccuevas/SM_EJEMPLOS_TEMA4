package es.ujaen.ejemplostema4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import es.ujaen.ejemplostema4.sound.Sounds;

public class MusicActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_READEXTERNAL = 1;
    public static final int REQUEST_PERMISSION_RECORDAUDIO = 2;

    private ImageView mPlayRaw = null;
    private ImageView mPlayExternal = null;
    private ImageView mRecord = null;
    private ImageView mPlayRecord=null;

    private MediaPlayer mMPlayer = null;
    private MediaPlayer mMPlayerEx = null;
    private MediaPlayer mPlayerRecorded = null;

    private View mMainView = null;

    File music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    File audio = null;


    String mExternalPath = music.getPath() + "/" + "invierno.mp3";
    String mRecordPath ="";
    /*
    * Grabación
    * */
    private MediaRecorder mRecorder = null;

    private boolean mRecording = false;
    private boolean mPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mMainView = findViewById(R.id.content_main);
        audio = getFilesDir();
        mRecordPath =audio.getPath() + "/audioEjemplos2.3gpp";

        Sounds.initSounds(this);

        mPlayRaw = (ImageView) findViewById(R.id.main_play);

        mPlayExternal = (ImageView) findViewById(R.id.main_play2);


        mPlayRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();

            }
        });
        mPlayExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (podemosLeer()) {
                    checkPermissions();
                } else {
                    Snackbar.make(mMainView, R.string.externalstorage_error,
                            Snackbar.LENGTH_LONG).show();

                }

            }
        });

        mRecord = (ImageView) findViewById(R.id.music_record);
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(!mRecording);


            }
        });

        mPlayRecord = (ImageView) findViewById(R.id.music_play_recorded);
        mPlayRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay();
            }
        });
    }

    private void onRecord(boolean start) {
        if (start) {
            Snackbar.make(mMainView, R.string.music_startrecording,
                    Snackbar.LENGTH_LONG).show();

            checkPermissionsRecord();
        } else {
            Snackbar.make(mMainView, R.string.music_stoprecording,
                    Snackbar.LENGTH_LONG).show();

            stopRecording();
        }
    }

        private void onPlay() {
        if (!mPlaying) {
            startPlayingRecorded();
        } else {
            stopPlayingRecorded();
        }
    }

    private void startPlayingRecorded() {
        mPlayerRecorded = new MediaPlayer();
        try {
            mPlayerRecorded.setDataSource(mRecordPath);
            mPlayerRecorded.prepare();
            mPlayerRecorded.start();
            mPlaying=true;
            mPlayRecord.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, R.drawable.ic_stop));
        } catch (IOException e) {
            Log.e("MusicActivity", "prepare() failed");
        }
    }

    private void stopPlayingRecorded() {
        mPlayerRecorded.release();
        mPlayerRecorded = null;
        mPlaying=false;
        mPlayRecord.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this,android.R.drawable.ic_media_play));

    }
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mRecordPath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mRecording = !mRecording;
            mRecord.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        } catch (IOException e) {
            Log.e("MusicActivity", "prepare() failed "+e.getMessage());
        }


    }

    private void stopRecording() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            mRecording = !mRecording;
            mRecord.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MusicActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Se pregunta si debemos poner una explicación
            if (ActivityCompat.shouldShowRequestPermissionRationale(MusicActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Se muestra una breve explicación de por qué solicitar el permiso
                Snackbar message = Snackbar.make(mMainView, R.string.permission_readexternal,
                        Snackbar.LENGTH_LONG);
                message.show();
                ActivityCompat.requestPermissions(MusicActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READEXTERNAL);
            } else {

                // No se necesita explicación, se pasa a solicitar el permiso.
                ActivityCompat.requestPermissions(MusicActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READEXTERNAL);

                // REQUEST_PERMISSION_READEXTERNAL es una constante para identificar
                // la petición de permisos concreta
            }
        } else {
            playMusicExternal(mExternalPath);
        }

    }

    public void checkPermissionsRecord() {
        if (ContextCompat.checkSelfPermission(MusicActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Se pregunta si debemos poner una explicación
            if (ActivityCompat.shouldShowRequestPermissionRationale(MusicActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Se muestra una breve explicación de por qué solicitar el permiso
                Snackbar message = Snackbar.make(mMainView, R.string.fragment_record_permission,
                        Snackbar.LENGTH_LONG);
                message.show();
                ActivityCompat.requestPermissions(MusicActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION_RECORDAUDIO);
            } else {

                // No se necesita explicación, se pasa a solicitar el permiso.
                ActivityCompat.requestPermissions(MusicActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION_RECORDAUDIO);

                // REQUEST_PERMISSION_READEXTERNAL es una constante para identificar
                // la petición de permisos concreta
            }
        } else {
            startRecording();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READEXTERNAL:
                // Si la petición es cancelada se devuelve un vector vacío
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido
                    playMusicExternal(mExternalPath);
                } else {

                    // Permiso denegado por el usuario
                    Snackbar.make(mMainView, R.string.permission_readexternal_denied,
                            Snackbar.LENGTH_LONG).show();
                }
                break;
            case REQUEST_PERMISSION_RECORDAUDIO:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido
                    Snackbar.make(mMainView, R.string.music_startrecording,
                            Snackbar.LENGTH_LONG).show();
                    startRecording();
                } else {

                    // Permiso denegado por el usuario
                    Snackbar.make(mMainView, R.string.permission_record_denied,
                            Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void onPlay(View view) {
        switch (view.getId()) {
            case R.id.music_gong:
                Sounds.playSound(this, Sounds.S4);
                break;
            case R.id.music_platillos:
                Sounds.playSound(this, Sounds.S5);
                break;
        }
    }

    protected void playMusic() {
        if (mMPlayer != null)
            try {
                if (mMPlayer.isPlaying()) {
                    mMPlayer.pause();
                    mPlayRaw.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_play));
                } else {

                    mMPlayer.start();
                    mPlayRaw.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_pause));
                }
            } catch (IllegalStateException ex) {
                Log.d("MusicActivity", "IllegalStateException");
            }

    }

    protected void playMusicExternal(String path) {
        if (mMPlayerEx != null) {
            if (mMPlayerEx.isPlaying()) {
                mMPlayerEx.pause();
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_play));
            } else {
                mMPlayerEx.start();
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_pause));
            }
        } else {
            try {
                iniExternalAudio(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void iniExternalAudio(String path) throws IOException {
        mMPlayerEx = new MediaPlayer();
        mMPlayerEx.setDataSource(path);
        mMPlayerEx.prepare();
        mMPlayerEx.start();
        mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_pause));
    }

    @Override
    public void onResume() {
        super.onResume();

        mMPlayer = MediaPlayer.create(MusicActivity.this, R.raw.audio_vivaldi);
        mMPlayerEx = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMPlayerEx != null) {
            try {
                if (mMPlayer.isPlaying())
                    mMPlayer.stop();
                mMPlayer.release();
            } catch (IllegalStateException ex) {
                Log.d("MusicActivity", "IllegalStateException");
            } finally {
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_play));
            }
        }
        if (mMPlayerEx != null) {
            try {
                if (mMPlayerEx.isPlaying())
                    mMPlayerEx.stop();
                mMPlayerEx.release();
            } catch (IllegalStateException ex) {
                Log.d("MusicActivity", "IllegalStateException");
            } finally {
                mPlayRaw.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_play));
            }
        }
    }

    public boolean podemosLeer() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else return Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
