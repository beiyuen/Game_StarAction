package charas.blocks;
import static constants.ImageConstants.*;

import java.awt.Graphics;
/**
 * 通常ゲームでは普通のブロック。プレイヤーが触れても、敵が触れても消えることはない。また、このブロックは右クリックで消すことはできない。
 * @author kitahara
 *
 */
public class HardBlock extends AbstractBlock {


	public HardBlock(int x, int y) {
		super(x, y, IMAGE_BLOCK_HARD);
	}

	

	// 描画
	public void draw(Graphics g) {
		if(!death){
			g.drawImage(image, (int) (xPosition - width / 2), (int) (yPosition - height / 2), (int) width, (int) height, this);
		}

	}

}