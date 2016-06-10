package charas.blocks;

import static constants.MathConstants.*;

import java.awt.Dimension;
import java.awt.Graphics;

import charas.AbstractChara;
import charas.bosses.Boss1;
import charas.enemys.WalkEnemy;

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
		image = referenceItems.getBlockImage(i);
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
		image = referenceItems.getBlockImage(i);
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
		int hitr = 0;
		int hitl = 0;
		double cx = c.getxPosition();
		double cy = c.getyPosition();
		double cwh = c.getWidth() / 2;
		double chh = c.getHeight() / 2;
		double cxsp = c.getxSpeed();
		double cysp = c.getySpeed();
		double nextMaxX = cx + cwh +  cxsp;
		double nextMinX = cx - cwh +  cxsp;
		double blockMaxX = xPosition + BLOCK_SIZE / 2; // ブロックの一番右のx座標
		double blockMinX = xPosition - BLOCK_SIZE / 2; // ブロックの一番左のx座標
		double blockMaxY = yPosition + BLOCK_SIZE / 2; // ブロックの一番下のy座標
		double blockMinY = yPosition - BLOCK_SIZE / 2; // ブロックの一番上のy座標
		double cos = Math.cos(Math.atan2(cy - yPosition, cx - xPosition));
		// 対象キャラと離れすぎているとき,当たり判定を計算しない
		if(Math.abs( cx + cxsp - xPosition ) > 100 || Math.abs(cy + cysp - yPosition) > 100 ){
			hitX.setSize(hitl, hitr);
			return hitX;
		}
		// 近いときは当たり判定を計算
		// 現在のフレームでぶつかっておらず、次のフレームで壁とぶつかっているとき
		if (isHitMove(c)){
			// 座標角度と移動角度で判定
			if (cos >= Math.cos(30 * Math.PI / 180.0) || cos <= Math.cos(150 * Math.PI / 180.0) || (Math.abs(Math.cos(c.getMoveAngle())) > Math.cos(35 * Math.PI / 180.0) &&  (int)cy + c.getHeight()/2 != (int)yPosition - BLOCK_SIZE/2 )) {
				// 現在どの壁にもぶつかっていないとき
					// 右方向に移動時(右側の壁にぶつかる)
					if(cxsp > 0 && nextMaxX - (blockMinX) <= XSPEED_MAX){
						hitr = 1;
						c.setxPosition(blockMinX - cwh);
					}
					// 左方向に移動時
					else if(cxsp < 0 && (blockMaxX) - nextMinX < XSPEED_MAX){
						hitl = 1;
						c.setxPosition(blockMaxX + cwh);
					}
				// 空中で真横からあたるとき
					else if(cxsp > 0 && cy + chh > blockMinY && cy - chh < blockMaxY){
						hitr = 1;
						c.setxPosition(xPosition - (cwh + BLOCK_SIZE / 2));
					}
					else if(cxsp < 0 && cy + chh > blockMinY && cy - chh < blockMaxY){
						hitl = 1;
						c.setxPosition(xPosition + (cwh + BLOCK_SIZE / 2));
					}

			}
			//空中でほぼ真横からあたるとき,右
			else if(cxsp > 0 && cy > blockMinY && cy < blockMaxY && nextMaxX - (xPosition - BLOCK_SIZE/2) < XSPEED_MAX){
				hitr = 1;
				c.setxPosition(xPosition - (cwh + BLOCK_SIZE / 2));
			}//空中でほぼ真横からあたるとき,左
			else if(cxsp < 0 && cy > blockMinY && cy < blockMaxY && (xPosition + BLOCK_SIZE/2) - nextMinX < XSPEED_MAX){
				hitl = 1;
				c.setxPosition(xPosition + (cwh + BLOCK_SIZE / 2));
			}

			// 現在右側の壁にぶつかっているとき
			else if(c.isHitRight() && cxsp < 3 && cy + chh + cysp > blockMinY){
				if((int)cx + c.getWidth()/2 == (int)xPosition - width/2  && cy + chh != blockMinY){
					hitr = 1;
					c.setxPosition(xPosition - (cwh + BLOCK_SIZE / 2));
				}
			}
			// 現在左側の壁にぶつかっているとき
			else if( c.isHitLeft() &&  cxsp > -3 &&  cy + chh + cysp > blockMinY){
				if((int)cx - c.getWidth()/2 == (int)xPosition + width/2){
					hitl = 1;
					c.setxPosition(xPosition + (cwh + BLOCK_SIZE / 2));
				}
			}

		}

		hitX.setSize(hitl, hitr);
		return hitX;

	}

	public Dimension hity(AbstractChara c) {
		boolean nowHit = isHit(c);
		int hith = 0;
		int hitl = 0;
		double cx = c.getxPosition();
		double cy = c.getyPosition();
		double cwh = c.getWidth() / 2;
		double chh = c.getHeight() / 2;
		double cxsp = c.getxSpeed();
		double cysp = c.getySpeed();
		double angle = Math.atan2(cy - yPosition, cx - xPosition);
		double nextMaxY = cy + chh +  cysp;
		double nextMinY = cy - chh +  cysp;
		// 対象キャラと離れすぎているとき,当たり判定を計算しない
		if(Math.abs( cx + cxsp - xPosition ) > 100 || Math.abs(cy + cysp - yPosition) > 100){
			hitY.setSize(hith, hitl);
			return hitY;
		}
		
		// 近いときは当たり判定を計算
		// 現フレームで床に触れており、かつ、現在触っている床を踏んでいたら、床の上に立っていることとする①
		if(c.isHitLeg() && nowHit){
			if(cy + chh == yPosition - height/2){
				hitl = 1;
				c.setyPosition(yPosition - (chh + BLOCK_SIZE / 2));
			}
			// Boss1用
			else if(c instanceof Boss1){
				hitl = 1;
			}
		}

		// 現フレームでブロックとぶつかっていないとき
		else if(!c.isHitLeg() && !nowHit){
			if(isHitMove(c)){
				// 空中から着地するとき②
				if(cysp > 1 && nextMaxY - (yPosition - height/2) <= YSPEED_MAX && (cx + cwh != xPosition - width/2) && (cx - cwh != xPosition + width/2) && cy+chh < yPosition- BLOCK_SIZE/2){
					hitl = 1;
					c.setyPosition(yPosition - (chh + BLOCK_SIZE / 2));
					c.changeYSpeed();

				}
				// 空中で上方向に衝突するとき③
				else if(cysp < 0 && (yPosition + height/2) - nextMinY < YSPEED_MAX+3 && (((cx + cwh != xPosition - width/2) && (cx - cwh != xPosition + width/2)) && Math.sin(angle) > Math.sin(55 * Math.PI / 180.0))){
					hith = 1;
					c.setyPosition(yPosition + (chh + BLOCK_SIZE / 2));

				}
			}
		}

		// Boss1用
		else if( c instanceof Boss1 ){
			if(!c.isHitLeg() && nowHit){
				hitl = 1;
			}
		}

		hitY.setSize(hith, hitl);
		return hitY;
	}

	// 描画
	public void draw(Graphics g) {
		if (!death && xPosition < GAME_WIDTH + 100 && xPosition > -100) {
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