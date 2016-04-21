package util;

import static constants.ImageConstants.*;
import static constants.SoundCnstants.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ReferenceItems {
	// 画像データ用変数
	private static Image[]	enemyImage;
	private static Image[]	blockImage;
	private static Image	needleImage;
	private static Image	playerCharaImage;
	private static Image[]	textImage;
	private static Image	openingImage;
	private static Image	endingImage;
	private static Image[]	signboardImage;
	private static Image	gameoverImage;
	
	// 効果音データ用変数
	private static File[] soundSe;
	private static Clip[]    clip;
	private static AudioInputStream[] audioInputStream;
	private static AudioFormat[]     audioFormat;
	private static DataLine.Info[]   info;
	




	public static void Load() throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		enemyImage = new Image[IMAGE_ENEMY_MAX];
		blockImage = new Image[IMAGE_BLOCK_MAX];
		textImage = new Image[IMAGE_TEXT_MAX];
		signboardImage = new Image[IMAGE_SIGNBOARD_MAX];

		enemyImage[IMAGE_ENEMY_SLIME] = Toolkit.getDefaultToolkit().createImage("image/enemy2.png");
		enemyImage[IMAGE_ENEMY_GHOST] = Toolkit.getDefaultToolkit().createImage("image/enemy.png");
		enemyImage[IMAGE_ENEMY_KING] = Toolkit.getDefaultToolkit().createImage("image/boss.png");
		enemyImage[IMAGE_ENEMY_WALK] = Toolkit.getDefaultToolkit().createImage("image/enemy4.png");
		enemyImage[IMAGE_ENEMY_SHOT] = Toolkit.getDefaultToolkit().createImage("image/enemy3.png");
		enemyImage[IMAGE_ENEMY_MOVE] = Toolkit.getDefaultToolkit().createImage("image/enemy5.png");
		enemyImage[IMAGE_ENEMY_BULLET] = Toolkit.getDefaultToolkit().createImage("image/bullet0.png");

		blockImage[IMAGE_BLOCK_HARD] = Toolkit.getDefaultToolkit().createImage("image/hardBlock.png");
		blockImage[IMAGE_BLOCK_NOMAL] = Toolkit.getDefaultToolkit().createImage("image/block.png");
		blockImage[IMAGE_BLOCK_GOAL] = Toolkit.getDefaultToolkit().createImage("image/goal.png");
		blockImage[IMAGE_BLOCK_FLOORCLEAR] = Toolkit.getDefaultToolkit().createImage("image/worldClearBlock.png");
		needleImage = Toolkit.getDefaultToolkit().createImage("image/needle.png");
		playerCharaImage = Toolkit.getDefaultToolkit().createImage("image/otamesi.png");
		
		textImage[IMAGE_TEXT_WORLD1] = Toolkit.getDefaultToolkit().createImage("image/textWorld1.png");
		textImage[IMAGE_TEXT_CLEAR] = Toolkit.getDefaultToolkit().createImage("image/textClear.png");
		
		signboardImage[IMAGE_SIGNBOARD_1] = Toolkit.getDefaultToolkit().createImage("image/signboard1.png");
		signboardImage[IMAGE_SIGNBOARD_2] = Toolkit.getDefaultToolkit().createImage("image/signboard2.png");
		signboardImage[IMAGE_SIGNBOARD_3] = Toolkit.getDefaultToolkit().createImage("image/signboard3.png");
		signboardImage[IMAGE_SIGNBOARD_4] = Toolkit.getDefaultToolkit().createImage("image/signboard4.png");
		signboardImage[IMAGE_SIGNBOARD_5] = Toolkit.getDefaultToolkit().createImage("image/signboard5.png");
		//signboardImage[IMAGE_SIGNBOARD_6] = Toolkit.getDefaultToolkit().createImage("image/signboard6.png");

		openingImage = Toolkit.getDefaultToolkit().createImage("image/Title.png");
		endingImage = Toolkit.getDefaultToolkit().createImage("image/ending.png");
		gameoverImage = Toolkit.getDefaultToolkit().createImage("image/gameover.png");

		soundSe = new File[SOUND_SE_MAX];
		clip = new Clip[SOUND_SE_MAX];
		audioInputStream = new AudioInputStream[SOUND_SE_MAX];
		audioFormat = new AudioFormat[SOUND_SE_MAX];
		info = new DataLine.Info[SOUND_SE_MAX];


		soundSe[SOUND_SE_SHOT] = new File("sound/shoot.wav");
		soundSe[SOUND_SE_SURPRISE] = new File("sound/surprise.wav");
		soundSe[SOUND_SE_TREAD] = new File("sound/tread.wav");
		for(int i = 0; i < SOUND_SE_MAX; i++){
			audioInputStream[i] = AudioSystem.getAudioInputStream(soundSe[i]);
			audioFormat[i] = audioInputStream[i].getFormat();
			info[i] = new DataLine.Info(Clip.class, audioFormat[i]);
			clip[i] = (Clip)AudioSystem.getLine(info[i]);
			clip[i].open(audioInputStream[i]);
		}
		System.out.println("file loaded");
	}

	public static Image getEnemyImage(int i){
		return enemyImage[i];
	}

	public static Image getBlockImage(int i){
		return blockImage[i];
	}
	public static Image getNeedleImage(){
		return needleImage;
	}

	public static Image getPlayerCharaImage(){
		return playerCharaImage;
	}
	
	public static Image getTexteImage(int i){
		return textImage[i];
	}

	public static Image getOpeningImage() {
		return openingImage;
	}

	public static Image getEndingImage() {
		return endingImage;
	}

	public static Image getGameoverImage() {
		return gameoverImage;
	}

	public static Image getSignboardImage(int i) {
		return signboardImage[i];
	}

	public static File getSoundSe(int i){
		return soundSe[i];
	}

	public static Clip getClip(int i){
		return clip[i];
	}

}
