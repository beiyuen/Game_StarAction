package constants;
/**
 * 計算に使われる定数の定義
 * @author kitahara
 *
 */
public class MathConstants {
	// 画面サイズ
	public static final int WINDOW_WIDTH 			= 	900;
	public static final int WINDOW_HEIGHT 			= 	630;
	
	// 描画範囲
	public static final int GAME_WIDTH 				= 	900;
	public static final int GAME_HEIGHT 			= 	600;

	// ブロックサイズ
	public static final int BLOCK_SIZE				=	50;
	
	// フレーム間の時間(ms)
	public static final int DELAY 					=	20;
	
	// x方向、y方向の速さの最大
	public static final double YSPEED_MAX			=	19.0;
	public static final double XSPEED_MAX			=	14.0;
	
	// 最小の速さ。これ以下の速さになると速度が0になるようにする
	public static final double DELTA_SPEED			=	0.7;
	

	private MathConstants(){
	}
}
