package tev.tevusql;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;


public class MysqlSetterGetter {
    Main plugin = Main.getPlugin(Main.class);

    @EventHandler

    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String pl = player.getName();

        if(!(Main.getPlugin(Main.class)).sql.playerExists(player.getUniqueId())){

           //Do something on join if player dont exist getServer().dispatchCommand(getServer().getConsoleSender(), "bungee btab player "+pl + " customtabname " +pl);
            player.setDisplayName(pl);
            return;
        }


        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String nicker = resultSet.getString("NICK");
                // do something with new variable utility.nick_player(player, nicker);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }



    }
    public boolean playerExists(UUID uuid)  {
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }
    public void createPlayer(final UUID uuid, Player player, String nick){
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if(playerExists(uuid) != true){
                PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.table + " (UUID,NAME,NICK) VALUE (?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2,player.getName());
                insert.setString(3, nick);
                insert.executeUpdate();

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deletePlayer(final UUID uuid){
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            // utility.prefix_player(player, prefix);

            PreparedStatement insert = plugin.getConnection().prepareStatement("DELETE FROM " + plugin.table + " WHERE UUID = ?");
            insert.setString(1, uuid.toString());
            insert.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static boolean isDbConnected() {
        Connection db = Main.getPlugin(Main.class).getConnection();
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = false;
        try {
            final PreparedStatement statement = db.prepareStatement(CHECK_SQL_QUERY);
            isConnected = true;
        } catch (SQLException | NullPointerException e) {
            // handle SQL error here!
        }
        return isConnected;
    }
}
