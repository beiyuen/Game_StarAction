package constants;
/**
 * 計算に使われる定数の定義
 * @author kitahara
 *
 */
public class MathConstants {

	public static final int WINDOW_WIDTH 			= 	900;
	public static final int WINDOW_HEIGHT 			= 	630;
	public static final int GAME_WIDTH 				= 	900;
	public static final int GAME_HEIGHT 			= 	600;

	public static final int GAMESTATUS_OPENING 		= 	0;
	public static final int GAMESTATUS_PLAYING 		= 	1;
	public static final int GAMESTATUS_DIE 			= 	2;
	public static final int GAMESTATUS_ENDING		= 	3;
	public static final int GAMESTATUS_STAGECHANGE	= 	4;
	public static final int GAMESTATUS_WORLDCHANGE	= 	5;

	public static final int BLOCK_SIZE				=	50;
	public static final int DELAY 					=	20;
	
	public static final double YSPEED_MAX			=	19.0;
	public static final double XSPEED_MAX			=	16.0;
	

	private MathConstants(){
	}
}
