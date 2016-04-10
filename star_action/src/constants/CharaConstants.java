package constants;

public class CharaConstants {

	// ブロック
	public static final int BLOCK_NOMAL			=	1;
	public static final int BLOCK_HARD			=	2;
	public static final int BLOCK_CLEAR			=	3;
	public static final int BLOCK_FAKE			=	4;
	public static final int GOAL				=	5;

	
	// トゲ
	public static final int NEEDLE_RIGHT		=	20;
	public static final int NEEDLE_LEFT			=	21;
	public static final int NEEDLE_UP			=	22;
	public static final int NEEDLE_DOWN			=	23;
	public static final int NEEDLE_BLOCK_RIGHT	=	24;
	public static final int NEEDLE_BLOCK_LEFT	=	25;
	public static final int NEEDLE_BLOCK_UP		=	26;
	public static final int NEEDLE_BLOCK_DOWN	=	27;

	
	// 敵
	public static final int ENEMY_SLIME			=	100;
	public static final int ENEMY_GHOST			=	101;
	public static final int ENEMY_SHOT			=	102;
	public static final int ENEMY_WALK			=	103;
	public static final int ENEMY_JUMP			=	104;
	public static final int ENEMY_MOVE			=	105;
	public static final int ENEMY_KING			=	106;

	
	// 当たり判定
	public static final int HIT_TREAD			= 	0;
	public static final int HIT_MISS			=	1;
	public static final int HIT_NOT				=	2;

	
	// 画像
	public static final int IMAGE_ENEMY_MAX		= 	7;
	public static final int IMAGE_BLOCK_MAX		= 	3;
	
	public static final int IMAGE_ENEMY_SLIME	=	0;
	public static final int IMAGE_ENEMY_GHOST	=	1;
	public static final int IMAGE_ENEMY_SHOT	=	2;
	public static final int IMAGE_ENEMY_WALK	=	3;
	public static final int IMAGE_ENEMY_MOVE	=	4;
	public static final int IMAGE_ENEMY_KING	=	5;
	public static final int IMAGE_ENEMY_BULLET	=	6;
	
	public static final int IMAGE_BLOCK_NOMAL	=	0;
	
	private CharaConstants(){
	}
}
