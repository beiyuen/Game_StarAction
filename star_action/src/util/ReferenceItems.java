package util;

import static constants.CharaConstants.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

public class ReferenceItems {
	// 画像データ用変数
	private static Image[]	enemyImage;
	private static Image[]	blockImage;
	private static Image	needleImage;
	private static Image	playerCharaImage;

	// 効果音データ用変数
	private static AudioInputStream[] soundSe;


	public static void Load() throws IOException{
		enemyImage = new Image[IMAGE_ENEMY_MAX];
		blockImage = new Image[IMAGE_BLOCK_MAX];
		enemyImage[IMAGE_ENEMY_SLIME] = Toolkit.getDefaultToolkit().createImage("image/enemy2.png");
		enemyImage[IMAGE_ENEMY_GHOST] = Toolkit.getDefaultToolkit().createImage("image/enemy.png");
		enemyImage[IMAGE_ENEMY_KING] = Toolkit.getDefaultToolkit().createImage("image/boss.png");
		enemyImage[IMAGE_ENEMY_WALK] = Toolkit.getDefaultToolkit().createImage("image/enemy4.png");
		enemyImage[IMAGE_ENEMY_SHOT] = Toolkit.getDefaultToolkit().createImage("image/enemy3.png");
		enemyImage[IMAGE_ENEMY_MOVE] = Toolkit.getDefaultToolkit().createImage("image/enemy5.png");
		enemyImage[IMAGE_ENEMY_BULLET] = Toolkit.getDefaultToolkit().createImage("image/bullet0.png");

		blockImage[IMAGE_BLOCK_NOMAL] = Toolkit.getDefaultToolkit().createImage("image/block.png");
		needleImage = Toolkit.getDefaultToolkit().createImage("image/needle.png");
		playerCharaImage = Toolkit.getDefaultToolkit().createImage("image/otamesi.png");
		System.out.println("references loaded!");
		if(enemyImage[IMAGE_ENEMY_SLIME] == null){
			System.err.println("slime is null!!!");
		}
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

	public static AudioInputStream getSoundSe(int i){
		return soundSe[i];
	}

}
