package charas;

import java.awt.Graphics;

import charas.enemys.WalkEnemy;

/**
 * プレイヤーが当たるとゲームオーバーになるトゲです
 * 
 * @author kitahara
 *
 */
public class Needle extends AbstractChara {

	public Needle(int x, int y, int i) {
		super(x, y, 50, 50, "image/needle.png");
		imageKind = i;
		imageDrawWidth = 50;
		imageDrawHeight = 50;
		imageColumn = 4;
		imageLine = 1;
	}

	public void death() {
	}

	// 接触判定
	public boolean isHit(AbstractChara c) {
		if (c instanceof PlayerChara && (Math.sqrt((xPosition - c.xPosition) * (xPosition - c.xPosition)
				+ (yPosition - c.yPosition) * (yPosition - c.yPosition)) <= 33)) {
			return true;
		} else if (c instanceof WalkEnemy) {
			return !death && Math.abs(c.xPosition - xPosition) <= c.width / 2 + width / 2
					&& Math.abs(c.yPosition - yPosition) <= c.height / 2 + height / 2;
		}
		return false;
	}

	public void draw(Graphics g) {
		double sx, sy;
		sx = (imageKind % imageColumn) * imageDrawWidth;
		sy = (imageKind / imageColumn) * imageDrawHeight;

		g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) (xPosition + width / 2),
				(int) (yPosition + height / 2), (int) (sx), (int) (sy), (int) (sx + imageDrawWidth),
				(int) (sy + imageDrawHeight), this);
	}

	@Override
	public void changeXSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void changeYSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}

}