package commands;

import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.sound.sampled.LineEvent;
import java.awt.*;
import java.sql.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static core.Main.urlempty;
import static util.SECRETS.VERSION;

public class profile implements Command {
    String Nick;
    String Game;
    Member user;
    String useruser;
    String Cookies;
    String Cpc;
    String Punkte;
    String Level;
    String Progress;
    String ProgressMax;
    int TempProgress;
    String TempPuntke;
    String TempPunkte2;
    int viertel;
    String LevelPlus;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
                useruser = args[0].replace("<", "").replace("@", "").replace(">", "").replace("!","");
                user = event.getGuild().getMemberById(useruser);
            if (useruser.equals(event.getMember().getUser().getId())) {
                event.getTextChannel().sendMessage("Was bringt es sich selbst zu hinzuschreiben?? egal... mach es nächstes mal einfach mit -profile :wink: ").queue();
            }

        }catch ( ArrayIndexOutOfBoundsException e) {
            user = event.getMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getGame() == null) Game = "Es gibt kein Aktuell gespieltes Spiel";
            else Game  = ""+user.getGame().getName();
        if (user.getNickname() == null) Nick = "Es gibt keinen Nicknamen";
            else Nick = user.getNickname();
            int i=0;
            String out="";
            int end = user.getRoles().size()-1;
            while (i<user.getRoles().size()) {
                if (i<end) {
                    out += user.getRoles().get(i).getName() + ", ";
                    i++;
                } else {
                    out += user.getRoles().get(i).getName();
                    i++;
                }
            }
            try {
                //Cookies
                Connection con = DriverManager.getConnection(urlempty + "cookiebot" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE ID='"+event.getAuthor().getId()+"'");
                ResultSet rs = pst.executeQuery();
                if (!rs.next()) {
                    Cookies= "Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
                    Cpc="Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
                } else {
                    Cookies=rs.getInt(2)+"";
                    Cpc=rs.getInt(3)+"";
                }
            } catch (SQLException e) {}
            //Level//Punkte//Progress
        try {
            Connection con = DriverManager.getConnection(urlempty + "lvl" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
            PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE ID='"+event.getAuthor().getId()+"'");
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                Punkte="Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
               Level= "Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
               LevelPlus= "Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
               Progress="Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
                ProgressMax="Da musst du dich wohl mit -register noch bei dem Bot registrieren!";
            } else {
                Punkte=rs.getInt(2)+"";
                Level=rs.getInt(3)+"";
                TempProgress=rs.getInt(3)+1;
                con = DriverManager.getConnection(urlempty + "lvl" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                pst = con.prepareStatement("SELECT * FROM `lvl` WHERE lvl='"+TempProgress+"'");
                rs = pst.executeQuery();
                if (!rs.next()) {
                    System.out.println("DESFASFD");
                }
                else {
                    System.out.println("DEBUUUG");
                    viertel=rs.getInt(2)/4;
                    ProgressMax=rs.getInt(2)+"";
                    LevelPlus=(Integer.parseInt(Level)+1)+"";
                    //unter 25% viertel=2
                    System.out.println(Punkte);
                    System.out.println(viertel);
                    if (viertel>Integer.parseInt(Punkte)) {
                        System.out.println("DEBUG 1");
                        Progress=event.getGuild().getEmotesByName("progbar_start_empty",true).get(0).getAsMention()+" " +
                                event.getGuild().getEmotesByName("progbar_mid_empty",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_mid_empty",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_end_empty",true).get(0).getAsMention();
                    } else if (((viertel*2)>Integer.parseInt(Punkte))&&(viertel<=Integer.parseInt(Punkte))) {
                        System.out.println("DEBUG 2");
                        Progress=event.getGuild().getEmotesByName("progbar_start_full",true).get(0).getAsMention()+" " +
                                event.getGuild().getEmotesByName("progbar_mid_empty",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_mid_empty",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_end_empty",true).get(0).getAsMention();
                    } else if (((viertel*3)>Integer.parseInt(Punkte))&&((viertel*2)<=Integer.parseInt(Punkte))) {
                        System.out.println("DEBUG 3");
                        Progress=event.getGuild().getEmotesByName("progbar_start_full",true).get(0).getAsMention()+" " +
                                event.getGuild().getEmotesByName("progbar_mid_full",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_mid_empty",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_end_empty",true).get(0).getAsMention();
                    } else if (((viertel*4)>Integer.parseInt(Punkte))&&((viertel*3)<=Integer.parseInt(Punkte))) {
                        System.out.println("DEBUG 4");
                        Progress=event.getGuild().getEmotesByName("progbar_start_full",true).get(0).getAsMention()+
                                event.getGuild().getEmotesByName("progbar_mid_full",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_mid_full",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_end_empty",true).get(0).getAsMention();
                    } else if (rs.getInt(2)<Integer.parseInt(Punkte)) {
                        System.out.println("DEBUG 5");
                        Progress=event.getGuild().getEmotesByName("progbar_start_full",true).get(0).getAsMention()+" " +
                                event.getGuild().getEmotesByName("progbar_mid_full",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_mid_full",true).get(0).getAsMention()+" "+
                                event.getGuild().getEmotesByName("progbar_end_full",true).get(0).getAsMention();
                    }
                }
            }
        } catch (SQLException e) {}
        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.GREEN).setTitle("Profile").setDescription("" +
                "__**User Statistiken**__:\n"+
                "\n**Name**: ***" + user.getUser().getName() + "***\n" +
                "**Nick**: ***" + Nick + "***\n" +
                "**Game**: ***" + Game + "***\n" +
                "**Rollen**: ***" + out + "***\n" +
                "**Server betreten**: ***"+ user.getJoinDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).withZone(ZoneId.of("GMT")))+"***\n" +
                "**Status**: ***" + user.getOnlineStatus()+"***\n" +
                "\n__**Netzwerk Statistiken**__:\n"+
                "**Cookies**: ***" + Cookies + "***\n"+
                "**Cookies pro Nachricht**: ***" + Cpc + "***\n\n"+
                "**Level**: ***" + Level + "***\n" +
                "**Punkte**: ***" + Punkte + "***\n" +
                "**Zu Level " +LevelPlus +"**: ***" +ProgressMax + " Punkte***\n" +
                "**Fortschritt**: ***"+ Progress + "***\n")
                .setThumbnail(user.getUser().getAvatarUrl()).build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
