package core;

public class HelpMenu {

    public static String Message;
    public static String Title;

    public static String[] Emoji;

    public static void Help(String id) {

        if (id.equals("\uD83D\uDE04")) {
            Emoji=new String[2];
            Emoji[1]="\uD83D\uDD19";
            Title="Help zum CookieBot";
            Message="``-stats`` - zeigt dir deine Stats\n``-shop`` - damit kannst du dir sachen kaufen";
        }
        if (id.equals("\uD83D\uDC7E")) {
            Emoji = new String[2];
            Emoji[1]="\uD83D\uDD19";
            Title = "Help zum GottderBots";
            Message="``-help`` - macht das Help Menü auf\n" +
                    "``-bots`` - Listet alle Bots auf\n" +
                    "``-miner`` - zeigt dir deine Miner stats\n"+
                    "``-profile`` - zeigt dir dein Profil\n"+
                    "``-register`` - Registriert dich bei einem Bot";
        }
        if (id.equals("\uD83D\uDD19")) {
            Emoji = new String[3];
            Emoji[1]="\uD83D\uDE04";
            Emoji[2]="\uD83D\uDC7E";
            Title="Help - Hilfe";
            Message="Hier eine Übersicht über alle Bot Commands die von <@261083609148948488> und <@!362270177712275491> " +
                    " sind: \nWenn du das entsprechende Menü öffnen willst reagiere mit dem Smiley hinter dem Bot\n" +
                    "``GottderBots`` - :space_invader: \n" +
                    "``CookieBot`` - :smile:";
        }

    }

}
