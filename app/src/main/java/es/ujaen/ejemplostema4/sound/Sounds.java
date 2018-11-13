package es.ujaen.ejemplostema4.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import es.ujaen.ejemplostema4.R;

public class Sounds {
	private static SoundPool mSoundPool;
	private static HashMap<Integer,Integer> mSoundPoolMap;


	public static final int S1 = R.raw.s1;
	public static final int S2 = R.raw.s2;
	public static final int S3 = R.raw.s3;
	public static final int S4 = R.raw.audio_platillos;
	public static final int S5 = R.raw.audio_gong;

	/** Populate the SoundPool */

	public static void initSounds(Context context) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			AudioAttributes audioAttrib = new AudioAttributes.Builder()
					.setUsage(AudioAttributes.USAGE_GAME)
					.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
					.build();
			mSoundPool = new SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(2).build();
		}
		else {
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		}


		mSoundPoolMap = new HashMap<Integer,Integer>(3);

		mSoundPoolMap.put(S1, mSoundPool.load(context, R.raw.s1, 1));
		mSoundPoolMap.put(S2, mSoundPool.load(context, R.raw.s2, 2));
		mSoundPoolMap.put(S3, mSoundPool.load(context, R.raw.s3, 3));
        mSoundPoolMap.put(S4, mSoundPool.load(context, R.raw.audio_gong, 4));
        mSoundPoolMap.put(S5, mSoundPool.load(context, R.raw.audio_platillos, 5));

	}

	
	public static void playSound(Context context, int soundID) {
		if (mSoundPool == null || mSoundPoolMap == null) {
			initSounds(context);
		}
		float volume = 1.0f;// whatever in the range = 0.0 to 1.0

		// play sound with same right and left volume, with a priority of 1,
		// zero repeats (i.e play once), and a playback rate of 1f
		mSoundPool.play(mSoundPoolMap.get(soundID), volume, volume, 0,
				0, 1f);
	}

	public static void playSoundLoop(Context context, int soundID) {
		if (mSoundPool == null || mSoundPoolMap == null) {
			initSounds(context);
		}
		float volume = 1.0f;// whatever in the range = 0.0 to 1.0

		// play sound with same right and left volume, with a priority of 1,
		// zero repeats (i.e play once), and a playback rate of 1f
		mSoundPool.play(mSoundPoolMap.get(soundID), volume, volume, 1,
				-1, 1f);
	}
	
	public static void stopLoop()
	{
		mSoundPool.stop(S1);
	}

	public static void pause()
	{
		mSoundPool.autoPause();
	}

	public static void resume()
	{
		mSoundPool.autoResume();
	}

}
