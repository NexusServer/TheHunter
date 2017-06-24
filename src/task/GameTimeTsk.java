package thehunter.task;

import cn.nukkit.scheduler.PluginTask;
import thehunter.Main;

public class GameTimeTsk extends PluginTask<Main> {
	public GameTimeTsk(Main owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onRun(int currentTick) {
		// TODO Auto-generated method stub
		if (Main.gaming) {
			Main.gameTime--;
			Main.onlinePlayers.forEach(p->{
				p.setTitleAnimationTimes(50, 200, 50);
				p.sendActionBarTitle("§6남은 제한시간 = "+Main.gameTime);
			});
		}

	}
}
