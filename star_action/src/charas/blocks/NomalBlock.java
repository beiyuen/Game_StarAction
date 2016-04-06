package charas.blocks;

import charas.Block;
/**
 * このゲームでの普通のブロック。右クリックで消すことができる。消えていないうちは、プレイヤー、敵、どちらが触れても消えることはない。
 * @author kitahara
 *
 */
public class NomalBlock extends Block {

	public NomalBlock(int x, int y) {
		super(x, y);
		removable = true;
	}

}
