package enums;

/**
 * プレイヤーと敵との当たり判定の時に使う列挙型。
 * @author kitahara
 *
 */
public enum HitPlayer {
	Tread("敵を踏んだ"),
	Miss("敵に触れてやられた"),
	Not("敵と接触していない");
	private String message;

	 HitPlayer(String s){
		message = s;
	}

	public String getMessage(){
		return message;
	}
}

