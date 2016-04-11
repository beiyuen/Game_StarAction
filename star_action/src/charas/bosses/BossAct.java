package charas.bosses;

import charas.Shot;
import star_action.Model;

public class BossAct {
	public int flag=0;
	public BossAct(){
	}
	public void pattern0(AbstractBoss c){//横移動
		c.xPosition += c.xSpeed;
	}

	public void pattern1(AbstractBoss c){//跳びはねる
		c.xSpeed = Math.signum(c.xSpeed)*7;
		c.xPosition += c.xSpeed;
		if(c.count%45 == 10)
			c.ySpeed=-11;
	}

	public void pattern2(AbstractBoss c){//自分の近くに移動する
		if(c.count==1){
			c.xSpeed=0;
			c.ySpeed=-4;
			flag=0;
		}
		else if(c.count%140==40){
			c.ySpeed=0;
			if(c.xPosition<Model.getPlayerChara().getxPosition())
				c.xSpeed=2;
			else
				c.xSpeed=-2;
		}
		else if(flag == 0 &&((c.count%140>40 && c.xPosition-75<Model.getPlayerChara().getxPosition() && c.xPosition+75>Model.getPlayerChara().getxPosition() )||
				c.count==140)){
			flag = 1;
			c.xSpeed=0;
		}
		else if(flag==1)
			c.ySpeed+=2.2;
		if(c.count>0 && c.count%40==35){
				AbstractBoss.bullet.add(new Shot( (int)(c.xPosition), (int)(c.yPosition),1.0,
						Math.atan2(Model.getPlayerChara().getyPosition()-c.yPosition,Model.getPlayerChara().getxPosition()-c.xPosition)));
				//Mario.sound("shoot.wav",0.4);
		}

		c.xPosition += c.xSpeed;
		c.yPosition += c.ySpeed;
	}

	public void pattern3(AbstractBoss c){//ショットを打つ
		if(c.count%35==20){
			for(int i =0;i<6;i++)
				AbstractBoss.bullet.add(new Shot( (int)(c.xPosition), (int)(c.yPosition),3.0,(c.count+i*60)*Math.PI/180));
			//Mario.sound("shoot.wav",0.4);
		}
		else if(c.count==180)
			c.count=0;

		c.xPosition += c.xSpeed;
		c.yPosition += c.ySpeed;
	}
	public void pattern4(AbstractBoss c){//縦移動
		if(c.count==0)
			c.ySpeed=-5;
		c.yPosition += c.ySpeed;
	}
}