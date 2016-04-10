package charas.bosses;

import static constants.CharaConstants.*;
import static constants.ImageConstants.*;
import static constants.MathConstants.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import charas.PlayerChara;
import charas.Shot;
import charas.blocks.AbstractBlock;
import charas.enemys.AbstractEnemy;
import star_action.Model;

public class AbstractBoss extends AbstractEnemy {
	private static final long serialVersionUID = 1L;

	public static ArrayList<Shot> bullet = new ArrayList<Shot>();
	int count; //踏まれ数カウンター
	int i=0,
		imagekind=0,
		tate = 2,
		yoko = 4,
		state = 0,//ボスの動きを決める変数
		st = 0,//踏まれた時の一時変数,１なら踏んだとき
		cnt=0; //動きのタイミングを決める
	BossAct action = new BossAct();

	public AbstractBoss(int x,int y){
		super(x,y-1,75,75,IMAGE_ENEMY_KING); //マップ単位より大きく、はみ出してしまうため少し持ち上げ
		xSpeed=-6;
		count = 1;
		width = 300;
		height = 150;
	}

	//enemyからこのオブジェクトを除去
	public void death() {
		//Mario.s.block.add(new GameclearBlock(9,5));
		//Mario.sound("surprise.wav",0.6);
		//Mario.iterator.remove();
	}

	public int isHitPlayer(PlayerChara c){
			for(Shot s: bullet){
				if(s.isHit(c)) {
					return HIT_MISS;
				}
			}

		if(count<5 //やられる回数を定義
				&& Math.abs(c.xPosition + c.xSpeed - xPosition) < c.width / 2 + width / 2
				&& Math.abs(c.yPosition + c.ySpeed - yPosition) < c.height / 2 + height / 2
				&& Math.sin((Math.atan2(c.yPosition-yPosition, c.xPosition-xPosition))) <= -1/Math.sqrt(2.0)
				){

			if(xPosition<500){
				xSpeed=20;
			}

			else {
				xSpeed=-20;
			}
			if(st==0){
				state++;
				count++;
				//Mario.sound("stamp.wav", 0.6);
				if(state==4||state==3)
					AbstractBoss.bullet.clear();
			}
			st = 1;//走って壁に逃げる時
			cnt=0;
			return HIT_TREAD;
		}
		else return super.isHitPlayerChara(c);
	}

	// 移動定義
	public void calcXAcceleration(double a) {
			//ブロックにあたったら反転

		/////////////設定///////////////////
		if(st==0){
			switch(state){
			case 0:
				action.pattern0(this);
				break;
			case 1:
				action.pattern1(this);
				break;
			case 2:
				action.pattern2(this);
				break;
			case 3:
				action.pattern3(this);
				break;
			case 4:
				action.pattern4(this);
				break;
			}
			cnt++;
		}
		else if(st==1)
			action.pattern0(this);
		//壁に当たった時の反応など
		switch(state){
		case 0:
		case 1:
			if (xSpeed == 0)
				xSpeed++;
				if (xPosition+xSpeed<70 || xPosition+xSpeed>GAME_WIDTH-70){
					if(st == 0)
						xSpeed *= -1;
					else if(st == 1){
						xSpeed=0;
						st=0;
					}
				}
			break;
		case 2:
		case 3:
			if(xPosition+xSpeed<70){
				xPosition=80;
				xSpeed*=-1;
				if(st==1){
					if(state == 3){
						xSpeed = r.nextInt(4)+1;
						ySpeed = -5;
					}
					st=0;
				}
			}
			else if(xPosition+xSpeed>GAME_WIDTH-70){
				xPosition=GAME_WIDTH-80;
				xSpeed*=-1;
				if(st==1){
					if(state == 3){
						xSpeed = r.nextInt(4)-9;
						ySpeed = -5;
					}
					st=0;
				}
			}
			Iterator<Shot> iter2 = AbstractBoss.bullet.iterator();//ショットを移動させる
			while(iter2.hasNext()){ // 次の要素がある限りループ
				Shot val = iter2.next();// 次の要素を取得
				val.calcAcceleration();
				val.move();
			}
			break;
		case 4:
			if(st == 1 && (xPosition < 70 || xPosition > GAME_WIDTH-70)){
				for (AbstractBlock b : Model.getBlockList()) {
					b.setDeath(true);
				}
				//Mario.sound("surprise.wav", 0.6);
			}
			break;
		}
	}
	//xsimここまで
	public void calcYAcceleration(){//重力関係
		if(st==0){
			switch(state){
			case 0:
			case 1:
				super.calcYAcceleration();
				break;
			case 2:
				if(yPosition>GAME_HEIGHT-84){
					yPosition = GAME_HEIGHT-87;
					ySpeed=0;
					cnt=-40;
					action.flag=2;
				}
				break;
			case 3:
			case 4:
				if(yPosition>GAME_HEIGHT-84){
					yPosition = GAME_HEIGHT-87;
					ySpeed*=-1;
				}
				else if(yPosition<50){
					yPosition = 55;
					ySpeed*=-1;
				}
				break;
			}
		}
		else if(st==1){
			super.calcYAcceleration();
			if(yPosition>GAME_HEIGHT-84)
				yPosition = GAME_HEIGHT-87;
		}
	}

	public void draw(Graphics g){
		double px = Model.getPlayerChara().getxPosition();
		if(xPosition>px){
			i ++;
		imagekind=(i%8)/2;//0,1,2,3番目の画像
		}
		else if(xPosition<=px){
			i ++;
		imagekind=(i%8)/2+4; //4,5,6,7番目の画像
		}
		double sx, sy;
		int pwidth = width/yoko;
		int pheight = height/tate;
        sx = (imagekind % yoko) * pwidth;
        sy = (imagekind / yoko) * pheight;

		g.drawImage(image,(int)(xPosition- width / 2),(int)(yPosition- height / 2),
				(int)(xPosition+width/2),(int)(yPosition+height/2),
				(int)(sx),(int)(sy), (int)(sx+pwidth), (int)(sy+pheight),this);
			for(Shot b : bullet)
				b.draw(g);

	}
}
