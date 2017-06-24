import java.util.ArrayList;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {
	public static ArrayList<Player> onlinePlayers = new ArrayList<>();
	public static boolean gaming = false;
	public static Player hunter;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getCommandMap().register("헌터", new HunterJoinPlugin());
	}

	public static void join(Player player) {
		
	}

	public static boolean canJoin() {
		if (onlinePlayers.size() > 12 || gaming) {
			return false;
		}
		return true;
	}

	public static void start() {
		hunter = onlinePlayers.get(new Random().nextInt(onlinePlayers.size()));
		hunter.getInventory().addItem(Item.get(Item.BOW));
		hunter.getInventory().addItem(Item.get(Item.ARROW, 1, 64));
	}
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if (event.getCause() == ) {
			
		}
	}

}
