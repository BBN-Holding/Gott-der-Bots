package core;

import net.dv8tion.jda.core.entities.Game;
import util.SECRETS;

import java.sql.*;

import static core.Main.pst;
import static core.Main.rs;
import static core.Main.con;
import static core.Main.urlempty;
import static util.SECRETS.PREFIX;
import static util.SECRETS.VERSION;

public class GameAnimator {

    private static Thread t;
    private static boolean running = false;
    private static int currentGame;
    private static long last = 0;

    private static final String[] gameAnimations = {
            "Schreit Bots an ", "Sortiert Datenbanken", "Wartet auf Befehle", "Trinkt Tee", "Lädt..."

    };

    public static synchronized void start() {
        try {
            if (!running) {
                    con = DriverManager.getConnection(urlempty + "temp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                    pst = con.prepareStatement("SELECT * FROM `tempt` WHERE `Temp1` LIKE 'Game'");
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        currentGame=rs.getInt(2);
                    }

                    running = true;
            }


            t = new Thread(() -> {
                while (running) {
                    if (System.currentTimeMillis() >= last + 60000) {
                        Main.builder.setGame(Game.playing(gameAnimations[currentGame] + " | " + PREFIX + "help | " + VERSION));
                        if (currentGame == gameAnimations.length - 1) {
                            currentGame = 0;
                        } else {
                            currentGame += 1;
                        }
                        try {
                            sync();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        last = System.currentTimeMillis();
                    }

                }
            });

            t.setName("GameAnimator");
            t.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void sync()throws SQLException {
        Main.con = DriverManager.getConnection(urlempty + "temp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
        pst = Main.con.prepareStatement("UPDATE `tempt` SET `Temp2`='" + currentGame + "' WHERE `Temp1`='Game' ");
        pst.execute();
    }
}