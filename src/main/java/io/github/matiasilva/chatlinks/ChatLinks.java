package io.github.matiasilva.chatlinks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ChatLinks extends JavaPlugin {

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("slack")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
					sender.sendMessage(ChatColor.RED.toString() + "Configuration has been reloaded for ChatLinks");
					return true;
				}
			} else {
				if (sender instanceof Player) {
					TextComponent root = new TextComponent(this.getConfig().getString("intro") + " ");
					root.setColor(ChatColor.YELLOW);
					TextComponent link = new TextComponent(this.getConfig().getString("clickable"));
					link.setItalic(true);
					link.setBold(true);
					link.setColor(ChatColor.DARK_GREEN);
					link.setUnderlined(true);
					link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.getConfig().getString("url")));
					link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder(this.getConfig().getString("hover")).color(ChatColor.AQUA).italic(true)
									.create()));
					root.addExtra(link);
					Player p = (Player) sender;
					p.spigot().sendMessage(root);
					return true;
				} else {
					sender.sendMessage("You cannot run this command from the console!");
					return true;
				}
			}
		}
		return false;
	}
}
