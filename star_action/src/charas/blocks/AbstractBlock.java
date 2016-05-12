package charas.blocks;

import static constants.MathConstants.*;

import java.awt.Dimension;
import java.awt.Graphics;

import charas.AbstractChara;
import charas.PlayerChara;
import charas.bosses.Boss1;
import charas.enemys.WalkEnemy;
import util.ReferenceItems;

/**
 * 通常ゲームでは普通のブロック。プレイヤーが触れても、敵が触れても消えることはない。また、このブロックは右クリックで消すことはできない。
 * 
 * @author kitahara
 *
 */
public class AbstractBlock extends AbstractChara {
	private static final long serialVersionUID = 1L;
	public boolean removable = false;
	public Dimension hitX, hitY;
	public boolean isHitx;
	public int imageNum = 0;

	public AbstractBlock(int x, int y, int i) {
		width = BLOCK_SIZE;
		height = BLOCK_SIZE;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
		imageNum = i;
		image = ReferenceItems.getBlockImage(i);
		hitX = new Dimension(0, 0);
		hitY = new Dimension(0, 0);
		isHitx = false;
		death = false;
	}

	public AbstractBlock(int x, int y, int w, int h, int i) {
		width = w;
		height = h;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
		imageNum = i;
		image = ReferenceItems.getBlockImage(i);
		death = false;
	}

	public AbstractBlock(int x, int y) {
		width = BLOCK_SIZE;
		height = BLOCK_SIZE;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
		image = null;
		hitX = new Dimension(0, 0);
		hitY = new Dimension(0, 0);
		isHitx = false;
		death = false;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void calcAcceleration() {
	}

	/**
	 *  現在の位置でキャラとブロックが衝突しているか判定する
	 */
	public boolean isHit(AbstractChara c) {
		if (c instanceof WalkEnemy) {
			return Math.abs(c.getxPosition() - xPosition) <= c.getWidth() / 2 + width / 2
					&& Math.abs(c.getyPosition() - yPosition) <= c.getHeight() / 2 + height / 2;
		} else {
			return !death && Math.abs(c.getxPosition() - xPosition) <= c.getWidth() / 2 + width / 2
					&& Math.abs(c.getyPosition() - yPosition) <= c.getHeight() / 2 + height / 2;
		}
	}
	/**
	 * 現在の位置から速度分だけ移動したときにブロックと衝突しているか判定する
	 * @param c
	 * @return ブロックとキャラが衝突するならtrue,そうでなければfalse
	 */
	public boolean isHitMove(AbstractChara c){
		return !death && Math.abs(c.getxPosition() + c.getxSpeed() - xPosition) <= c.getWidth() / 2 + width / 2
				&& Math.abs(c.getyPosition() + c.getySpeed() - yPosition) <= c.getHeight() / 2 + height / 2;
	}

	// 接触かつ直前にx方向に接触していない場合に位置、速度を調整
	public Dimension hitx(AbstractChara c) {
		boolean x = isHit(c);
		int hitr = 0;
		int hitl = 0;
		double cx = c.getxPosition();
		double cy = c.getyPosition();
		double cxsp = c.getxSpeed();
		double cysp = c.getySpeed();
		double prex = cx - cxsp - xPosition;
		double nextMaxX = cx + c.getWidth()/2 +  cxsp;
		double nextMinX = cx - c.getWidth()/2 +  cxsp;
		double cos = Math.cos(Math.atan2(cy - yPosition, cx - xPosition));
		double saleft = (xPosition + width/2) - (cx - c.getWidth()/2);
		double saright = (cx + c.getWidth()/2) - (xPosition - width/2);
		double sahead = Math.abs((yPosition - height/2) - (cy + c.getHeight()/2));
		double saleg = Math.abs(cy - c.getHeight()/2 - (yPosition + height/2));
		// 対象キャラと離れすぎているとき,当たり判定を計算しない
		if(Math.abs( cx + cxsp - xPosition ) > 100 || Math.abs(cy + cysp - yPosition) > 100 ){
			hitX.setSize(hitl, hitr);
			return hitX;
		}
		// 近いときは当たり判定を計算
		// 現在のフレームでぶつかっていないとき
		// 次のフレームで壁とぶつかっているとき
		if (isHitMove(c)){
			if (cos >= Math.cos(30 * Math.PI / 180.0) || cos <= Math.cos(150 * Math.PI / 180.0)) {
				// 現在どの壁にもぶつかっていないとき
			//	if(!c.isHitLeft() && !c.isHitRight()){
					// 右方向に移動時(右側の壁にぶつかる)
					if(cxsp > 1 && nextMaxX - (xPosition - width/2) <= XSPEED_MAX){
						hitr = 1;
						c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2));
						//c.changeXSpeed();
						if(c instanceof PlayerChara)System.out.println("hitRight  " + c.getxPosition() + "  " + c.getxSpeed());
						
					}
					// 左方向に移動時
					else if(cxsp < 0 && (xPosition + width/2) - nextMinX < XSPEED_MAX){
						hitl = 1;
						c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2));
						//c.changeXSpeed();
						if(c instanceof PlayerChara)System.out.println("hitLeft  " + c.getxPosition() + "  " + c.getxSpeed());
					}
			//	}
				//else System.out.println("else  " + ((int)cx - c.getWidth()/2 - ((int)xPosition + width/2)));
			}
			
			// 現在右側の壁にぶつかっているとき
			if(c.isHitRight() && /*(saright <sahead || saright <saleg )*/cxsp < 3 && (int)cy + c.getHeight()/2 != (int)yPosition - BLOCK_SIZE/2){
				if((int)cx + c.getWidth()/2 == (int)xPosition - width/2){
					hitr = 1;
					c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2));
					if(c instanceof PlayerChara)System.out.println("hitRight2  " + c.getxPosition() + "  " + c.getxSpeed());
				}
				else if(c instanceof PlayerChara)System.out.println("hitriguht222  " + ((int)cx + c.getWidth()/2 - ((int)xPosition - width/2)));
			}
			else if(c.isHitRight() && cxsp < 3 && cy + c.getHeight()/2 + cysp > yPosition - BLOCK_SIZE/2){
				if((int)cx + c.getWidth()/2 == (int)xPosition - width/2){
					hitr = 1;
					c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2));
					if(c instanceof PlayerChara)System.out.println("hitRight33 " + c.getxPosition() + "  " + c.getxSpeed());
				}
			}
			// 現在左側の壁にぶつかっているとき
			else if( c.isHitLeft() && /*(saleft < sahead || saleft < saleg)*/ cxsp > -3 && (int)cy + c.getHeight()/2 != (int)yPosition - BLOCK_SIZE/2){
				if((int)cx - c.getWidth()/2 == (int)xPosition + width/2){
					hitl = 1;
					c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2));
					if(c instanceof PlayerChara)System.out.println("hitLeft2  " + c.getxPosition() + "  " + c.getxSpeed());
				}
				else if(c instanceof PlayerChara)System.out.println("hitleft222  " + ((int)cx - c.getWidth()/2 - ((int)xPosition + width/2)));
			}
			else if( c.isHitLeft() &&  cxsp > -3 &&  cy + c.getHeight()/2 + cysp > yPosition - BLOCK_SIZE/2){
				if((int)cx - c.getWidth()/2 == (int)xPosition + width/2){
					hitl = 1;
					c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2));
					if(c instanceof PlayerChara)System.out.println("hitLeft33 " + c.getxPosition() + "  " + c.getxSpeed());
				}
				else if(c instanceof PlayerChara)System.out.println("hitleft222  " + ((int)cx - c.getWidth()/2 - ((int)xPosition + width/2)));
			}
			else if(c instanceof PlayerChara)System.out.println("hitelse  " + ((int)cx - c.getWidth()/2 - ((int)xPosition + width/2)));
			

		}
		/*if (isHitMove(c) && (cos >= Math.cos(30 * Math.PI / 180.0) || cos <= Math.cos(150 * Math.PI / 180.0))) {
			// 現在どの壁にもぶつかっていないとき
			if(!c.isHitLeft() && !c.isHitRight()){
				// 右方向に移動時(右側の壁にぶつかる)
				if(cxsp > 1 && nextMaxX - (xPosition - width/2) <= XSPEED_MAX){
					hitr = 1;
					c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2) -1);
					//c.changeXSpeed();
					if(c instanceof PlayerChara)System.out.println("hitRight  " + c.getxPosition() + "  " + c.getxSpeed());
					
				}
				// 左方向に移動時
				else if(cxsp < 0 && (xPosition + width/2) - nextMinX < XSPEED_MAX){
					hitl = 1;
					c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2) +1);
					//c.changeXSpeed();
					if(c instanceof PlayerChara)System.out.println("hitLeft  " + c.getxPosition() + "  " + c.getxSpeed());
				}
			}
			// 現在右側の壁にぶつかっているとき
			else if(c.isHitRight()){
				if(cx + c.getWidth()/2 == xPosition - width/2-1.0){
					hitr = 1;
					c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2) -1);
					if(c instanceof PlayerChara)System.out.println("hitRight2  " + c.getxPosition() + "  " + c.getxSpeed());
				}
			}
			// 現在左側の壁にぶつかっているとき
			else if( c.isHitLeft()){
				if(cx - c.getWidth()/2 == xPosition + width/2+1.0){
					hitl = 1;
					c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2)+1);
					if(c instanceof PlayerChara)System.out.println("hitLeft2  " + c.getxPosition() + "  " + c.getxSpeed());
				}
			}

		}
		else if(isHitMove(c) && cy + c.getHeight()/2 != yPosition - BLOCK_SIZE/2){
			
		}*/
		// 現在のフレームでぶつかっているとき
		/*else if(c instanceof PlayerChara){
			if(((PlayerChara)c).isMoveRight() && c.isHitRight()&& isHitMove(c)){
				hitr = 1;
				c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2) -1);
				if(c instanceof PlayerChara)System.out.println("hitRight3  " + c.getxPosition() + "  " + c.getxSpeed());
			}
			else if(((PlayerChara)c).isMoveLeft() && c.isHitLeft() && isHitMove(c)){
				hitl = 1;
				c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2) +1);
				if(c instanceof PlayerChara)System.out.println("hitLeft3  " + c.getxPosition() + "  " + c.getxSpeed());
			}
		}*/
		
		else if(isHitMove(c)){
			if(c instanceof PlayerChara)System.out.println("hitsonota  x:" + c.getxPosition() + " , y:" + c.getyPosition() + "  , hitleg:" + c.ishitLeg() + "  " + c.getxSpeed());
		}
		
		/*if (x) {
			double precos = Math
					.cos(Math.atan2(cy - cysp - yPosition, cx - cxsp - xPosition));
			if (cos >= Math.cos(30 * Math.PI / 180.0)) {
				hitl = 1;
				isHitx = false;
				c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2) + 1);
			}

			else if (prex > (c.getWidth() / 2 + BLOCK_SIZE / 2) && precos >= Math.cos(60 * Math.PI / 180.0)
					&& cxsp < 0) {
				if (!c.ishitLeg() && !c.isHitHead()) {
					hitl = 1;
					isHitx = true;
					c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2) + 1);
				}

			} else if (cos <= Math.cos(150 * Math.PI / 180.0)) {
				hitr = 1;
				isHitx = false;
				c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2) - 1);
			} else if (prex < -(c.getWidth() / 2 + BLOCK_SIZE / 2) && precos <= Math.cos(120 * Math.PI / 180.0)
					&& cxsp > 0) {
				if (!c.ishitLeg() && !c.isHitHead()) {
					hitr = 1;
					isHitx = true;
					c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2) - 1);
				}
			} else {
				isHitx = false;
			}
		}
		else if(isHitMove(c)){
			
		}
		else {
			isHitx = false;
		}*/
		
		hitX.setSize(hitl, hitr);
		return hitX;

	}

	public Dimension hity(AbstractChara c) {
		boolean nowHit = isHit(c);
		int hith = 0;
		int hitl = 0;
		double cx = c.getxPosition();
		double cy = c.getyPosition();
		double cxsp = c.getxSpeed();
		double cysp = c.getySpeed();
		double prey = cy - cysp - yPosition;
		double curr = cy - yPosition;
		double nextMaxY = cy + c.getHeight()/2 +  cysp;
		double nextMinY = cy - c.getHeight()/2 +  cysp;
		// 対象キャラと離れすぎているとき,当たり判定を計算しない
		if(Math.abs( cx + cxsp - xPosition ) > 100 || Math.abs(cy + cysp - yPosition) > 100){
			hitY.setSize(hith, hitl);
			return hitY;
		}
		// 近いときは当たり判定を計算
		// 現フレームで壁とぶつかっているとき
		if(c.ishitLeg() && nowHit){
			if(cy + c.getHeight()/2 == yPosition - height/2){
				hitl = 1;
				c.setyPosition(yPosition - (c.getHeight() / 2 + BLOCK_SIZE / 2));
				if(c instanceof Boss1)System.out.println("leg");
			}
			else if(c instanceof Boss1){
				hitl = 1;
				if(c instanceof Boss1)System.out.println("leg2" + c.getyPosition() + "  " + cysp);
			}
			
			
		}
		// 現フレームで壁とぶつかっていないとき
		else if(!c.ishitLeg() && !nowHit && !isHitLeft() && !isHitRight()){
			if(isHitMove(c)){
				if(cysp > 1 && nextMaxY - (yPosition - height/2) <= YSPEED_MAX && (cx + c.getWidth()/2 != xPosition - width/2) && (cx - c.getWidth()/2 != xPosition + width/2) && cy+c.getHeight()/2 < yPosition- BLOCK_SIZE/2){
					hitl = 1;
					c.setyPosition(yPosition - (c.getHeight() / 2 + BLOCK_SIZE / 2));
					c.changeYSpeed();
					if(c instanceof Boss1)System.out.println("hitleg  " + c.getyPosition() + "  " + c.getySpeed());
					
				}
				else if(cysp < 0 && (yPosition + height/2) - nextMinY < 22 && (cx + c.getWidth()/2 != xPosition - width/2) && (cx - c.getWidth()/2 != xPosition + width/2)){
					hith = 1;
					c.setyPosition(yPosition + (c.getHeight() / 2 + BLOCK_SIZE / 2));
					if(c instanceof Boss1)System.out.println("head");
				}
			}
		}
		else if( c instanceof Boss1 ){
			if(!c.ishitLeg() && nowHit){
				hitl = 1;
			}
		}
		else if(c instanceof Boss1)System.out.println("hitleg: " + c.ishitLeg() + "  nowHit: " + nowHit);
		/*if(cysp < 0 && (yPosition + height/2) - nextMinY < 25 && isHitMove(c)){
			hith = 1;
			c.setyPosition(yPosition + (c.getHeight() / 2 + BLOCK_SIZE / 2));
			if(c instanceof PlayerChara)System.out.println("head2");
	 }*/
		/*if (x) {

			
			double sin = Math.sin(Math.atan2(cy - yPosition, cx - xPosition));
			double presin = Math
					.sin(Math.atan2(cy - cysp - yPosition, cx - cxsp - xPosition));
			if (sin <= Math.sin(-50 * Math.PI / 180.0)) {
				hitl = 1;
				c.setyPosition(yPosition - (c.getHeight() / 2 + BLOCK_SIZE / 2));
			} else if (prey <= -(c.getHeight() / 2 + BLOCK_SIZE / 2) && presin <= Math.sin(-40 * Math.PI / 180.0)
					&& cysp > 2) {
				if (!c.isHitLeft() && !c.isHitRight() && !c.ishitLeg() && !isHitx) {
					hitl = 1;
					c.setyPosition(yPosition - (c.getHeight() / 2 + BLOCK_SIZE / 2));
				}

			} else if (sin >= Math.sin(60 * Math.PI / 180.0)) {
				hith = 1;
				c.setyPosition(yPosition + (c.getHeight() / 2 + BLOCK_SIZE / 2));
			} else if (prey > (c.getHeight() / 2 + BLOCK_SIZE / 2) && presin >= Math.sin(35 * Math.PI / 180.0)
					&& cysp < 0) {
				if (!c.isHitLeft() && !c.isHitRight() && !isHitx) {
					hith = 1;
					c.setyPosition(yPosition + (c.getHeight() / 2 + BLOCK_SIZE / 2));
				}
			}
		} else {
		}
		*/
		hitY.setSize(hith, hitl);
		return hitY;
	}

	// 描画
	public void draw(Graphics g) {
		if (!death) {
			g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) width, (int) height,
					this);
		}

	}

	@Override
	public void changeXSpeed() {
	}

	@Override
	public void changeYSpeed() {
	}

}