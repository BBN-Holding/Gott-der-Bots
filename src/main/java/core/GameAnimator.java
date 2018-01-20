package core;

import net.dv8tion.jda.core.entities.Game;
import util.SECRETS;

import static util.SECRETS.PREFIX;
import static util.SECRETS.VERSION;

public class GameAnimator {

    private static Thread t;
    private static boolean running = false;
    private static int currentGame = 0;

    private static final String[] gameAnimations = {
            "Schreit Bots an ", "Sortiert Datenbanken", "Wartet auf Befehle", "Trinkt Tee", "Lädt..."

    };

    public static synchronized void start() {
        if (!running) {
            t = new Thread(() -> {
                long last = 0;
                while (running) {
                    if (System.currentTimeMillis() >= last + 60000) {
                                Main.builder.setGame(Game.playing(gameAnimations[currentGame] +" | "+ PREFIX + "help | "+ VERSION ));
                                if (currentGame == gameAnimations.length - 1)
                                    currentGame = 0;
                                else
                                    currentGame += 1;
                                last = System.currentTimeMillis();
                            }

                    }

            });
            t.setName("GameAnimator");
            running = true;
            t.start();
        }
    }

    public static synchronized void stop() {
        if (running) {
            try {
                running = false;
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}