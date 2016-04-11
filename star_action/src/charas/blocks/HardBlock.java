package charas.blocks;
import static constants.ImageConstants.*;
/**
 * 通常ゲームでは普通のブロック。プレイヤーが触れても、敵が触れても消えることはない。また、このブロックは右クリックで消すことはできない。
 * @author kitahara
 *
 */
public class HardBlock extends AbstractBlock {


	public HardBlock(int x, int y) {
		super(x, y, IMAGE_BLOCK_HARD);
	}


}