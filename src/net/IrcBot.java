/*
 *  Copyright 2009 Roman Naumann <roman_naumann@fastmail.fm>.
 *
 *  This file is part of DDD.
 *
 *  DDD is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with DDD.  If not, see <http://www.gnu.org/licenses/>.
 */
package net;

import ddd.Initiative;
import ddd.KommandoParser;
import org.jibble.pircbot.PircBot;

/**
 *
 * @author namor
 */
public class IrcBot extends PircBot {

    public IrcBot() {
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        super.onMessage(channel, sender, login, hostname, message);
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        super.onPrivateMessage(sender, login, hostname, message);
        System.out.println(message);
        try {
        String[] a = message.split(";");
        for (String s : a) {
            String result = new KommandoParser(s).parse();
            if (result.equals("Unbekannter Befehl")) {
                sendMessage(sender, result);
            } else {
                sendMessage("#x3y", result+" {"+sender+"}");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        IrcBot bot = new IrcBot();
        bot.setName("wddd");
        bot.connect("irc.freenode.net");
        bot.joinChannel("#x3y");
    }
}
