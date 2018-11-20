package es.ujaen.ejemplostema4;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import es.ujaen.ejemplostema4.sound.Sounds;

public class FragmentoMusic extends Fragment {
    public static final int REQUEST_PERMISSION_READEXTERNAL = 1;

    //Activity open file chooser
    private static final int READ_REQUEST_CODE = 42;

    private static final String TAG = "FragmentoMusic";


    private ImageView mPlayRaw = null;
    private ImageView mPlayExternal = null;
    private ImageView mRecord = null;
    private ImageView mPlayRecord = null;

    private MediaPlayer mMPlayer = null;
    private MediaPlayer mMPlayerEx = null;
    private MediaPlayer mPlayerRecorded = null;

    private View mMainView = null;

    File music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    File audio = null;


    String mExternalPath = music.getPath() + "/" + "invierno.mp3";
    String mRecordPath = "";
    /*
     * Grabación
     * */
    private MediaRecorder mRecorder = null;

    private boolean mRecording = false;
    private boolean mPlaying = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView= inflater.inflate(R.layout.activity_music, container, false);


        audio = getActivity().getFilesDir();
        mRecordPath = audio.getPath() + "/audioEjemplos2.3gpp";

        Sounds.initSounds(getContext());

        mPlayRaw = mMainView.findViewById(R.id.main_play);

        mPlayExternal = mMainView.findViewById(R.id.main_play2);


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

        mMainView.findViewById(R.id.music_gong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(v);
            }
        });
        mMainView.findViewById(R.id.music_platillos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(v);
            }
        });
        return mMainView;
    }


    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Se pregunta si debemos poner una explicación
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Se muestra una breve explicación de por qué solicitar el permiso
                Snackbar message = Snackbar.make(mMainView, R.string.permission_readexternal,
                        Snackbar.LENGTH_LONG);
                message.show();
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READEXTERNAL);
            } else {

                // No se necesita explicación, se pasa a solicitar el permiso.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READEXTERNAL);

                // REQUEST_PERMISSION_READEXTERNAL es una constante para identificar
                // la petición de permisos concreta
            }
        } else {
            playMusicExternal();
        }

    }



    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("audio/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());
                try {
                    iniExternalAudio(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

                    playMusicExternal();
                    //playMusicExternal(mExternalPath);
                } else {

                    // Permiso denegado por el usuario
                    Snackbar.make(mMainView, R.string.permission_readexternal_denied,
                            Snackbar.LENGTH_LONG).show();
                }
                break;

        }
    }

    public void onPlay(View view) {
        switch (view.getId()) {
            case R.id.music_gong:
                Sounds.playSound(getContext(), Sounds.S4);
                break;
            case R.id.music_platillos:
                Sounds.playSound(getContext(), Sounds.S5);
                break;
        }
    }

    protected void playMusic() {
        if (mMPlayer != null)
            try {
                if (mMPlayer.isPlaying()) {
                    mMPlayer.pause();
                    mPlayRaw.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
                } else {

                    mMPlayer.start();
                    mPlayRaw.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_pause));
                }
            } catch (IllegalStateException ex) {
                Log.d("MusicActivity", "IllegalStateException");
            }

    }

    protected void playMusicExternal() {
        if (mMPlayerEx != null) {
            if (mMPlayerEx.isPlaying()) {
                mMPlayerEx.pause();
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
            } else {
                mMPlayerEx.start();
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_pause));
            }
        } else {
                       performFileSearch();

        }

    }

    private void iniExternalAudio(Uri path) throws IOException {
        mMPlayerEx = new MediaPlayer();
        mMPlayerEx.setDataSource(getContext(),path);
        mMPlayerEx.prepare();
        mMPlayerEx.start();
        mPlayExternal.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_pause));
        mMPlayerEx.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mMPlayer = MediaPlayer.create(getContext(), R.raw.audio_vivaldi);
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
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
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
                mPlayRaw.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
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

}
