package util;

import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import resource.ReferenceItems;
/**
 * 効果音を鳴らすための操作を定義したクラス
 *
 * @author kitahara
 *
 */
public class Sound {
	private ReferenceItems referenceItems = ReferenceItems.getReferenceItems();
	private static Sound sound = null;
	private Sound(){
		
	}
	/**
	 * 指定した効果音を 0～1 の音量の範囲で鳴らす。0だと無音。1だと100%の音量。
	 *
	 * @param se 効果音
	 * @param vol 音量
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void soundSE(int se, double vol)
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		Clip clip = referenceItems.getClip(se);
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		setVolume(control, vol);
		clip.setFramePosition(0);
		clip.start();
	}

	private void setVolume(FloatControl control, double vol) {
		control.setValue((float) Math.log10(vol) * 20);
	}
	
	public static Sound getSound(){
		if(sound == null){
			sound = new Sound();
		}
		return sound;
	}
}
