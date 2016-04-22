package charas.blocks;

import static constants.ImageConstants.*;

/**
 * このゲームでの普通のブロック。右クリックで消すことができる。消えていないうちは、プレイヤー、敵、どちらが触れても消えることはない。
 * 
 * @author kitahara
 *
 */
public class NomalBlock extends AbstractBlock {

	public NomalBlock(int x, int y) {
		super(x, y, IMAGE_BLOCK_NOMAL);
		removable = true;
	}

}
