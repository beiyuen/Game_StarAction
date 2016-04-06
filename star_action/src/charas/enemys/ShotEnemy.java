package charas.enemys;

import charas.Enemy;

public class ShotEnemy extends Enemy {
	int count;
	public ShotEnemy(int x, int y) {
		super(x,y,30,"image/enemy3.png");
		count = 0;
	}

}
