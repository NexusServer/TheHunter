package thehunter;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class HunterJoinPlugin extends Command {

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("플레이어만 참여 가능합니다");
			return false;
		}
		if (Main.canJoin()) {
			Player player = (Player) sender;
			player.sendMessage(TextFormat.YELLOW + "게임에 참여하였습니다");
			sendTitle(player, TextFormat.YELLOW + "게임에 참여하였습니다");
			Main.join(player);
		}
		return false;
	}

	public HunterJoinPlugin() {
		super("hunter", "헌터게임에 참여합니다", "/헌터");
		this.setPermission("");
		this.commandData.permission = "";
	}

	public void sendTitle(Player player, String message) {
		player.setTitleAnimationTimes(50, 200, 50);
		player.sendTitle(message);

	}
}
