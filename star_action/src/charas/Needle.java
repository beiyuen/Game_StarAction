package charas;

import static constants.MathConstants.*;

import charas.enemys.WalkEnemy;
import util.ReferenceItems;

/**
 * プレイヤーが当たるとゲームオーバーになるトゲです
 * 
 * @author kitahara
 *
 */
public class Needle extends AbstractChara {

	public Needle(int x, int y, int i) {
		width = BLOCK_SIZE;
		height = BLOCK_SIZE;
		xPosition = x * BLOCK_SIZE + BLOCK_SIZE / 2;
		yPosition = y * BLOCK_SIZE + BLOCK_SIZE / 2;
		initX = xPosition;
		initY = yPosition;
		xSpeed = 0;
		ySpeed = 0;
		image = ReferenceItems.getNeedleImage();
		death = false;
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

	@Override
	public void changeXSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void changeYSpeed() {
		// TODO 自動生成されたメソッド・スタブ

	}

}