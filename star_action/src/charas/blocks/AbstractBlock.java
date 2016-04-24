package charas.blocks;

import static constants.MathConstants.*;

import java.awt.Dimension;
import java.awt.Graphics;

import charas.AbstractChara;
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

	// 接触判定
	public boolean isHit(AbstractChara c) {
		if (c instanceof WalkEnemy) {
			return Math.abs(c.getxPosition() - xPosition) <= c.getWidth() / 2 + width / 2
					&& Math.abs(c.getyPosition() - yPosition) <= c.getHeight() / 2 + height / 2;
		} else {
			return !death && Math.abs(c.getxPosition() - xPosition) <= c.getWidth() / 2 + width / 2
					&& Math.abs(c.getyPosition() - yPosition) <= c.getHeight() / 2 + height / 2;
		}
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
		if (x) {
			double precos = Math
					.cos(Math.atan2(cy - cysp - yPosition, cx - cxsp - xPosition));
			double cos = Math.cos(Math.atan2(cy - yPosition, cx - xPosition));
			if (cos >= Math.cos(30 * Math.PI / 180.0)) {
				hitl = 1;
				isHitx = false;
				c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2));
				//cx = xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2);
			}

			else if (prex > (c.getWidth() / 2 + BLOCK_SIZE / 2) && precos >= Math.cos(60 * Math.PI / 180.0)
					&& cxsp < 0) {
				if (!c.ishitLeg() && !c.isHitHead()) {
					hitl = 1;
					isHitx = true;
					c.setxPosition(xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2));
					//cx = xPosition + (c.getWidth() / 2 + BLOCK_SIZE / 2);
				}

			} else if (cos <= Math.cos(150 * Math.PI / 180.0)) {
				hitr = 1;
				isHitx = false;
				c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2));
			} else if (prex < -(c.getWidth() / 2 + BLOCK_SIZE / 2) && precos <= Math.cos(120 * Math.PI / 180.0)
					&& cxsp > 0) {
				if (!c.ishitLeg() && !c.isHitHead()) {
					hitr = 1;
					isHitx = true;
					c.setxPosition(xPosition - (c.getWidth() / 2 + BLOCK_SIZE / 2));
				}
			} else {
				isHitx = false;
			}
		} else {
			isHitx = false;
		}
		hitX.setSize(hitl, hitr);
		return hitX;

	}

	public Dimension hity(AbstractChara c) {
		boolean x = isHit(c);
		int hith = 0;
		int hitl = 0;
		double cx = c.getxPosition();
		double cy = c.getyPosition();
		double cxsp = c.getxSpeed();
		double cysp = c.getySpeed();
		double prey = cy - cysp - yPosition;
		double curr = cy - yPosition;
		if (x) {

			
			double sin = Math.sin(Math.atan2(cy - yPosition, cx - xPosition));
			double presin = Math
					.sin(Math.atan2(cy - cysp - yPosition, cx - cxsp - xPosition));
			if (sin <= Math.sin(-60 * Math.PI / 180.0)) {
				hitl = 1;
				c.setyPosition(yPosition - (c.getHeight() / 2 + BLOCK_SIZE / 2));
			} else if (prey <= -(c.getHeight() / 2 + BLOCK_SIZE / 2) && presin <= Math.sin(-40 * Math.PI / 180.0)
					&& cysp >= 2) {
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