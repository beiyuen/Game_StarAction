package charas.enemys;

import java.awt.Graphics;
import java.util.ArrayList;

import charas.Enemy;
import charas.PlayerChara;
import charas.Shot;

public class ShootEnemy extends Enemy {
	public ArrayList<Shot> bullet = new ArrayList<Shot>();
	int count;
	public ShootEnemy(int x, int y) {
		super(x,y,30,"image/enemy3.png");
		count = 0;
	}

	public void calcAcceleration() {
		calcYAcceleration();
		count++;
	//弾を出す
		/*if( count %150 == 100 &&xPosition>0 && xPosition < Model.getPlay.XMAX-40){
			for(int i =0;i<6;i++)
				bullet.add(new Shot( (int)
						(xPosition+1), (int)(yPosition-13),3.0,
						Math.atan2(yPosition-Model.getPlayerChara().getyPosition(),xPosition-Model.getPlay.m.xpos)+(i*60)*Math.PI/180));
			//Mario.sound("shoot.wav",0.4);
		}
		Iterator<Shot> iter = NPCshoot.bullet.iterator();
		while(iter.hasNext()){ // 次の要素がある限りループ
			Shot val = iter.next();// 次の要素を取得
			val.sim();
			val.hit(Model.getPlay.m);
			if(val.xPosition > Model.getPlay.XMAX || val.xPosition < 0 || val.yPosition > Model.getPlay.YMAX || val.yPosition < 0){
				iter.remove(); // イテレータが指す要素を削除
			}
		}*/
	}

	public int hit(PlayerChara c){
		for(Shot s: bullet)
			if(s.hit(c)) return true;
		return super.hit(c);
	}

	public void draw(Graphics g) {
		g.drawImage(img, (int) (xPosition - width / 2), (int) (yPosition - height / 2),
				(int) width, (int) height, this);
		for(Shot b : bullet){
			b.draw(g);
		}
	}

}
