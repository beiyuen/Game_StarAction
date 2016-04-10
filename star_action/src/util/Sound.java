package util;

import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	/**
	 * 指定した効果音を 0～1 の音量の範囲で鳴らす。0だと無音。1だと100%の音量。
	 * @param se
	 * @param vol
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public static void soundSE(int se, double vol) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		Clip clip = ReferenceItems.getClip(se);
		FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		setVolume(control, vol);
		clip.setFramePosition(0);
        clip.start();
	}

	static void setVolume(FloatControl control, double vol) {
		control.setValue((float)Math.log10(vol) * 20);
	}
}
