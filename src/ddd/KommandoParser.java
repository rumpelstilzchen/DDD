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
package ddd;

import java.util.Scanner;
import java.util.regex.Pattern;
import simpleIO.KbdInput;

/**
 *
 * @author namor
 */
public class KommandoParser {

    public String parse() throws Exception {
        String ending = "\t\t[" + kommando.trim() + "]";
        if (musterT.matcher(kommando).matches()) {
            return funktionT() + ending;
        }
        if (musterP_.matcher(kommando).matches()) {
            return funktionP_() + ending;
        }
        if (musterP__.matcher(kommando).matches()) {
            return funktionP__() + ending;
        }
        if (musterP___.matcher(kommando).matches()) {
            return funktionP___() + ending;
        }
        if (musterTW__.matcher(kommando).matches()) {
            return funktionTW__() + ending;
        }
        if (musterW_.matcher(kommando).matches()) {
            return funktionW_() + ending;
        }
        if (musterINI_ADD___.matcher(kommando).matches()) {
            return funktionINI_ADD___() + ending;
        }
        if (musterINI_REM_.matcher(kommando).matches()) {
            return funktionINI_REM_() + ending;
        }
        if (musterINI_LIST.matcher(kommando).matches()) {
            return funktionINI_LIST() + ending;
        }
        if (musterINI_NEXT.matcher(kommando).matches()) {
            return funktionINI_NEXT() + ending;
        }
        if (musterINI_MOD__.matcher(kommando).matches()) {
            return funktionINI_MOD__() + ending;
        }
        if (musterINI_SET__.matcher(kommando).matches()) {
            return funktionINI_SET__() + ending;
        }
        return "Unbekannter Befehl";
    }

    private String funktionT() throws Exception {
        return "Trefferzone: " + Wuerfel.trefferzone();
    }

    private String funktionP_() throws Exception {
        Scanner s = new Scanner(kommando);
        int PW = Integer.parseInt(s.findInLine(mZahl));
        return "Probenergebnis: " + Wuerfel.probe(PW);
    }

    private String funktionW_() throws Exception {
        Scanner s = new Scanner(kommando);
        int W = Integer.parseInt(s.findInLine(mZahl));
        return "Würfelwurf: " + Wuerfel.w(W);
    }

    private String funktionP__() throws Exception {
        Scanner s = new Scanner(kommando);
        int PWbasis = Integer.parseInt(s.findInLine(mZahl));
        int mod_pro = Integer.parseInt(s.findInLine(mZahl));

        int PWfinal = PWbasis + PWbasis * mod_pro / 100;

        if (PWfinal < 5) {
            PWfinal = 5;
        }
        if (PWfinal > 90) {
            PWfinal = 90;
        }

        return "Probenergebnis: " + Wuerfel.probe(PWfinal);
    }

    private String funktionP___() throws Exception {
        Scanner s = new Scanner(kommando);
        int PWbasis = Integer.parseInt(s.findInLine(mZahl));
        int mod_pro = Integer.parseInt(s.findInLine(mZahl));
        int mod_abs = Integer.parseInt(s.findInLine(mZahl));

        int PWfinal = PWbasis + mod_abs + PWbasis * mod_pro / 100;

        if (PWfinal < 5) {
            PWfinal = 5;
        }
        if (PWfinal > 90) {
            PWfinal = 90;
        }

        return "Probenergebnis: " + Wuerfel.probe(PWfinal);
    }

    private String funktionINI_ADD___() throws Exception {
        Scanner s = new Scanner(kommando);
        s.findInLine(mName); //INI
        s.findInLine(mName); //ADD

        String Name = s.findInLine(mName); //Name
        int pIni = Integer.parseInt(s.findInLine(mZahl));
        int wIni = Integer.parseInt(s.findInLine(mZahl));

        Initiative.Partizipant p = new Initiative.Partizipant(Name, pIni, wIni);

        InitiativeSingleton.getInstance().initiative.addPartizipant(p);

        return "Partizipant \"" + Name + "\" zur Initiativeliste hinzugefügt,\n" +
                "nächste Initiativephase bei: " + p.naechsteInitiativePhase();
    }

    private String funktionINI_REM_() throws Exception {
        Scanner s = new Scanner(kommando);
        s.findInLine(mName); //INI
        s.findInLine(mName); //REM

        String Name = s.findInLine(mName); //Name

        try {
            InitiativeSingleton.getInstance().initiative.remPartizipant(Name);
        } catch (Initiative.PartizipantNichtGefundenException e) {
            return "Partizipant nicht gefunden.";
        }


        return "Partizipant \"" + Name + "\" von der Initiativeliste entfernt.";
    }

    private String funktionTW__() throws Exception {
        Scanner s = new Scanner(kommando);
        int waffenstaerke = Integer.parseInt(s.findInLine(mZahl));
        double staerkebonus_fac = 0.01 * Integer.parseInt(s.findInLine(mZahl));

        int trefferwucht = (Wuerfel.waffenWucht(waffenstaerke) +
                (int) (staerkebonus_fac * waffenstaerke));

        return "Trefferwucht: " + trefferwucht;
    }

    private String funktionINI_LIST() {
        String list = "";
        for (Initiative.Partizipant p :
                InitiativeSingleton.getInstance().initiative.partizipanten) {
            list += "INI: " + p.naechsteInitiativePhase() + "  " + p.name + "\n";
        }
        return list;
    }

    private String funktionINI_NEXT() throws Exception {
        Initiative.Partizipant p =
                InitiativeSingleton.getInstance().initiative.naechsterPartizipierender();
        return "#INI: " + p.letzterZug + "  Next: " + p.naechsteInitiativePhase() + " Name: " + p.name;
    }

    private String funktionINI_MOD__() {
        Scanner s = new Scanner(kommando);
        s.findInLine(mName); //INI
        s.findInLine(mName); //MOD

        String Name = s.findInLine(mName);
        int mod = Integer.parseInt(s.findInLine(mZahl));

        try {
            Initiative.Partizipant p =
                    InitiativeSingleton.getInstance().initiative.getParatizipant(Name);

            p.letzterZug += mod;
            return "Initiative von \"" + Name + "\" verschoben um " + mod + " zu " +
                    p.naechsteInitiativePhase();
        } catch (Initiative.PartizipantNichtGefundenException e) {
            return "Partizipant nicht gefunden";
        }

    }

    private String funktionINI_SET__() {
        Scanner s = new Scanner(kommando);
        s.findInLine(mName); //INI
        s.findInLine(mName); //SET

        String Name = s.findInLine(mName);
        int waffeninitiative = Integer.parseInt(s.findInLine(mZahl));

        try {
            Initiative.Partizipant p =
                    InitiativeSingleton.getInstance().initiative.getParatizipant(Name);

            p.waffeninitiative = waffeninitiative;

            return "Waffeninitiative von \"" + Name +
                    "\" ist jetzt: " + waffeninitiative + " ;nächster Zug: "+
                    p.naechsteInitiativePhase();
        } catch (Initiative.PartizipantNichtGefundenException e) {
            return "Partizipant nicht gefunden";
        }

    }
    private String _ = "\\s*";
    private String zahl = "[+-]?\\d+";
    private String zahlP = zahl + "%";
    private String name = "[_\\-\\(\\)\\[\\]\\{\\}a-zA-Z]+";
    private String _zahl = _ + zahl;
    private String _zahlP = _ + zahlP;
    private String _name = _ + name;
    private Pattern mZahl = Pattern.compile(zahl);
    private Pattern mName = Pattern.compile(name);
    private Pattern musterT = Pattern.compile(_ + "[Tt]" + _);
    /**
     * P Probenwert
     */
    private Pattern musterP_ = Pattern.compile(_ + "[Pp]" + _zahl + _);
    /**
     * W Würfel
     */
    private Pattern musterW_ = Pattern.compile(_ + "[WwDd]" + _zahl + _);
    /**
     * P Probenwert Modifikator%
     */
    private Pattern musterP__ = Pattern.compile(_ + "[Pp]" + _zahl + _zahlP + _);
    /**
     * P Probenwert Modifikator% Modifikator_Absolut
     */
    private Pattern musterP___ = Pattern.compile(_ + "[Pp]" + _zahl + _zahlP + _zahl + _);
    /**
     * TW Waffenstaerke Staerkemodifikator%
     */
    private Pattern musterTW__ = Pattern.compile(_ + "[Tt][Ww]" + _zahl + _zahlP + _);
    /**
     * INI ADD Name PersönlicheInitiative Waffeninitiative
     */
    private Pattern musterINI_ADD___ = Pattern.compile(
            _ + "[Ii][Nn][Ii]" + _ + "[Aa][Dd][Dd]" + _name + _zahl + _zahl + _);
    /**
     * INI REM Name
     */
    private Pattern musterINI_REM_ = Pattern.compile(
            _ + "[Ii][Nn][Ii]" + _ + "[Rr][Ee][Mm]" + _name + _);
    /**
     * INI LIST
     */
    private Pattern musterINI_LIST = Pattern.compile(
            _ + "[Ii][Nn][Ii]" + _ + "[Ll][Ii][Ss][Tt]" + _);
    /**
     * INI NEXT
     */
    private Pattern musterINI_NEXT = Pattern.compile(
            _ + "[Ii][Nn][Ii]" + _ + "[Nn][Ee][Xx][Tt]" + _);
    /**
     * INI MOD Name Modifikator
     */
    private Pattern musterINI_MOD__ = Pattern.compile(
            _ + "[Ii][Nn][Ii]" + _ + "[Mm][Oo][Dd]" + _name + _zahl + _);
    /**
     * INI SET Name Waffeninitiative
     */
    private Pattern musterINI_SET__ = Pattern.compile(
            _ + "[Ii][Nn][Ii]" + _ + "[Ss][Ee][Tt]" + _name + _zahl + _);

    public KommandoParser(String kommando) {
        this.kommando = kommando;
    }
    final String kommando;

    public static void main(String[] args) throws Exception {
        new KommandoParser("ini add roman 5 5").parse();
        new KommandoParser("ini add janus 5 5").parse();
        new KommandoParser("ini add _anus 5 5").parse();

        while (true) {
            String input = KbdInput.readString();
            String[] a = input.split(";");
            for (String s : a) {
                System.out.println(new KommandoParser(s).parse());
            }
        }
    }
}
