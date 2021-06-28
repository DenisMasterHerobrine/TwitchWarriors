package dev.denismasterherobrine.twitchwarriors;

import dev.denismasterherobrine.twitchwarriors.commands.CommandDisablePlugin;
import dev.denismasterherobrine.twitchwarriors.commands.CommandEnablePlugin;
import dev.denismasterherobrine.twitchwarriors.game.InGameItemController;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TwitchWarriorsCore extends JavaPlugin {
    FileConfiguration config = getConfig();
    List<String> WarriorsList = new ArrayList<String>();
    ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();
    public Connection connection;
    public String host, database, username, password;
    public int port;

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }

    @Override
    public void onEnable() {
        this.getCommand("tw_enable").setExecutor(new CommandEnablePlugin());
        this.getCommand("tw_disable").setExecutor(new CommandDisablePlugin());

        getServer().getPluginManager().registerEvents(new InGameItemController(), this);

        config.addDefault("isPluginEnabled", true);

        // MySQL DB parameters
        config.addDefault("SQLUsername"," ");
        config.addDefault("SQLPassword"," ");
        config.addDefault("SQLDatabaseName"," ");
        config.addDefault("SQLHost"," ");
        config.addDefault("SQLPort", 3306);

        config.options().copyDefaults(true);
        saveConfig();

        host = config.getString("SQLHost");
        port = config.getInt("SQLPort");
        database = config.getString("SQLDatabaseName");
        username = config.getString("SQLUsername");;
        password = config.getString("SQLPassword");;

        // Whitelister module
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    openConnection();
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT * FROM event;");
                    while (result.next()) {
                        String name = result.getString("mcnick");
                        if (name != null)
                            new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.dispatchCommand(consoleSender, "whitelist add " + name);
                            }
                            }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                    }
                } catch(ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.runTaskAsynchronously(this);

        Runnable r = new BukkitRunnable() {
            @Override
            public void run() {
                int i = 0;
                String nick;
                i = WarriorsList.size();
                while (i != 0) {
                    i = i - 1;
                    nick = WarriorsList.get(i);
                    Bukkit.dispatchCommand(consoleSender, "/whitelist add " + nick);
                }
            }
        };
        Bukkit.getScheduler().runTask(this, r);
    }

    @Override
    public void onDisable() {
        try {
            if (connection != null && !connection.isClosed()){
                connection.close(); // Close main thread connection to stop all modules.
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

