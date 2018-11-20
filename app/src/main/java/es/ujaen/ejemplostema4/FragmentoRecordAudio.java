package es.ujaen.ejemplostema4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class FragmentoRecordAudio extends Fragment {
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

    String mRecordPath ="";
    /*
    * Grabación
    * */
    private MediaRecorder mRecorder = null;

    private boolean mRecording = false;
    private boolean mPlaying = false;

    private View mFragmentView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView= inflater.inflate(R.layout.activity_recordaudio, container, false);

        audio = getActivity().getFilesDir();
        mRecordPath =audio.getPath() + "/audioEjemplos2.3gpp";


        mRecord = mMainView.findViewById(R.id.music_record);
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(!mRecording);


            }
        });

        mPlayRecord = mMainView.findViewById(R.id.music_play_recorded);
        mPlayRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay();
            }
        });

        return mMainView;
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
            mPlayRecord.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_stop));
            mPlayerRecorded.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayingRecorded();
                }
            });
        } catch (IOException e) {
            Log.e("MusicActivity", "prepare() failed");
        }
    }

    private void stopPlayingRecorded() {
        if(mPlayerRecorded!=null) {
            mPlayerRecorded.release();
            mPlayerRecorded = null;
            mPlaying = false;
            mPlayRecord.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_media_play));
        }

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



    public void checkPermissionsRecord() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Se pregunta si debemos poner una explicación
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.RECORD_AUDIO)) {

                // Se muestra una breve explicación de por qué solicitar el permiso
                Snackbar message = Snackbar.make(mMainView, R.string.fragment_record_permission,
                        Snackbar.LENGTH_LONG);
                message.show();
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSION_RECORDAUDIO);
            } else {

                // No se necesita explicación, se pasa a solicitar el permiso.
                ActivityCompat.requestPermissions(getActivity(),
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


}
