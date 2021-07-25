package tev.tevusql;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class testevent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().getName().equals("Tevuori")){
             //Main.getPlugin(Main.class).sql.createPlayer(e.getPlayer().getUniqueId(), e.getPlayer(), e.getPlayer().getName());
        }
    }
}
