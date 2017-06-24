package thehunter;

import java.util.ArrayList;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;

public class Main extends PluginBase implements Listener {
	public static ArrayList<Player> onlinePlayers = new ArrayList<>();
	public static boolean gaming = false;
	public static Player hunter;
	public Player last;

	public static int gameTime;
	public static final int PLAYER_WIN = 1;
	public static final int HUNTER_WIN = 2;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getCommandMap().register("hunter", new HunterJoinPlugin());
		this.getServer().getScheduler().scheduleDelayedTask(new Task() {

			@Override
			public void onRun(int currentTick) {
				Main.start();
				

			}
		}, 20 * 60 * 2);
	}

	public static void join(Player player) {
		onlinePlayers.add(player);
	}

	public static boolean canJoin() {
		if (onlinePlayers.size() > 12 || gaming) {
			return false;
		}
		return true;
	}

	public static void start() {
		Main.gameTime = 60 * onlinePlayers.size();
		Main.gaming = true;
		hunter = onlinePlayers.get(new Random().nextInt(onlinePlayers.size()));
		hunter.getInventory().addItem(Item.get(Item.BOW));
		hunter.getInventory().addItem(Item.get(Item.ARROW, 1, 64));
		hunter.sendMessage("당신은 헌터입니다");

		onlinePlayers.forEach((p) -> {
			p.setTitleAnimationTimes(50, 200, 50);
			p.sendTitle("§6게임 제한시간 = " + 60 * onlinePlayers.size());
		});

	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
			EntityDamageByEntityEvent event1 = (EntityDamageByEntityEvent) event;
			if (event1.getDamager() instanceof EntityArrow) {
				last = (Player) event.getEntity();

			}

		}
	}

	public void end(int type) {
		switch (type) {
		case Main.HUNTER_WIN:
			this.getServer().getOnlinePlayers().values().forEach((p) -> {
				p.setTitleAnimationTimes(50, 200, 50);
				p.sendTitle("§c헌터가 승리하였습니다");
			});
			break;
		case Main.PLAYER_WIN:
			this.getServer().getOnlinePlayers().values().forEach((p) -> {
				p.setTitleAnimationTimes(50, 200, 50);
				p.sendTitle("§b플레이어가 승리하였습니다");
			});
			break;
		}
		this.getServer().getScheduler().scheduleDelayedTask(new Task() {

			@Override
			public void onRun(int currentTick) {
				Main.start();
				// TODO Auto-generated method stub

			}
		}, 20 * 60 * 5);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity().equals(last)) {
			onlinePlayers.remove(event.getEntity());
			if (onlinePlayers.size() <= 1) {
				end(Main.HUNTER_WIN);
			}
		} else {
			event.setCancelled();
		}
	}
}
