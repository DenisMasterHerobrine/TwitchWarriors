package dev.denismasterherobrine.twitchwarriors.game;

import dev.denismasterherobrine.twitchwarriors.TwitchWarriorsCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.Objects;

public class InGameItemController implements Listener {
    public String host, database, username, password;
    public int port;
    public Connection connection;

    public void openConnection() throws SQLException, ClassNotFoundException {
        host = TwitchWarriorsCore.getPlugin(TwitchWarriorsCore.class).host;
        port = TwitchWarriorsCore.getPlugin(TwitchWarriorsCore.class).port;
        database = TwitchWarriorsCore.getPlugin(TwitchWarriorsCore.class).database;
        username = TwitchWarriorsCore.getPlugin(TwitchWarriorsCore.class).username;;
        password = TwitchWarriorsCore.getPlugin(TwitchWarriorsCore.class).password;;

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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Runnable SQL table checker
        BukkitRunnable itemRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    openConnection(); // Open parallel connection to sql for armor/sword/etc checking
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT * FROM event;");
                    while (result.next()) {
                        String name = result.getString("mcnick");
                        int sword = result.getInt("sword");
                        int armor = result.getInt("armor");
                        if (Objects.equals(player.getName(), name)){
                            // Swords ItemGiver module
                            System.out.println("If this message prints more than 1 time per player, then MySQL got screwed.");
                            ItemStack WoodenSword = new ItemStack(Material.WOODEN_SWORD);
                            ItemStack StoneSword = new ItemStack(Material.STONE_SWORD);
                            ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
                            ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);

                            if (sword == 1){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().addItem(WoodenSword);
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Wooden Sword!");
                            }
                            if (sword == 2){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().addItem(StoneSword);
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Stone Sword!");
                            }
                            if (sword == 3){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().addItem(IronSword);
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Iron Sword!");
                            }
                            if (sword == 4){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().addItem(DiamondSword);
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Diamond Sword!");
                            }
                            // Armor ItemGiver module
                            ItemStack LeatherHelmet = new ItemStack(Material.LEATHER_HELMET);
                            ItemStack LeatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                            ItemStack LeatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
                            ItemStack LeatherBoots = new ItemStack(Material.LEATHER_BOOTS);

                            ItemStack ChainHelmet = new ItemStack(Material.CHAINMAIL_HELMET);
                            ItemStack ChainChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                            ItemStack ChainLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                            ItemStack ChainBoots = new ItemStack(Material.CHAINMAIL_BOOTS);

                            ItemStack IronHelmet = new ItemStack(Material.IRON_HELMET);
                            ItemStack IronChestplate = new ItemStack(Material.IRON_CHESTPLATE);
                            ItemStack IronLeggings = new ItemStack(Material.IRON_LEGGINGS);
                            ItemStack IronBoots = new ItemStack(Material.IRON_BOOTS);

                            ItemStack DiamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
                            ItemStack DiamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            ItemStack DiamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                            ItemStack DiamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                            if(armor == 1){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().setArmorContents(new ItemStack[]{LeatherBoots, LeatherLeggings, LeatherChestplate, LeatherHelmet});
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Leather armor!");
                            }
                            if(armor == 2){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().setArmorContents(new ItemStack[]{ChainBoots, ChainLeggings, ChainChestplate, ChainHelmet});
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Chain armor!");
                            }
                            if(armor == 3){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().setArmorContents(new ItemStack[]{IronBoots, IronLeggings, IronChestplate, IronHelmet});
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Iron armor!");
                            }
                            if(armor == 4){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.getInventory().setArmorContents(new ItemStack[]{DiamondBoots, DiamondLeggings, DiamondChestplate, DiamondHelmet});
                                    }
                                }.runTask(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
                                System.out.println(name + "just got Diamond armor!");
                            }
                        }
                    }
                    connection.close(); // Close parallel connection to stop memory leak
                } catch(ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        itemRunnable.runTaskAsynchronously(JavaPlugin.getPlugin(TwitchWarriorsCore.class));
        }
    }