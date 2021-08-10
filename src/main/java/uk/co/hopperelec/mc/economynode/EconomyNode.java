package uk.co.hopperelec.mc.economynode;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class EconomyNode extends JavaPlugin implements Listener, PluginMessageListener {

    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "stockblock-economy:node");
        getServer().getMessenger().registerIncomingPluginChannel(this, "stockblock-economy:node", this);
        getServer().getPluginManager().registerEvents(this,this);
    }

    public void sendMessage(String message, Player player) {
        try {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            new DataOutputStream(stream).writeUTF(message);
            if (player == null || !player.isOnline()) {
                try {
                    player = (Player) getServer().getOnlinePlayers().toArray()[0];
                } catch (IndexOutOfBoundsException e) {
                    getLogger().warning("Could not send message `"+message+"` because there is no player online who's connection it can be sent through");
                    return;
                }
            }
            player.sendPluginMessage(this, "stockblockjda:node", stream.toByteArray());
        } catch (IOException e) {
            getLogger().warning("Could not send message `"+message+"` due to an IO error");
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("stockblock-economy:node")) return;
    }
}
