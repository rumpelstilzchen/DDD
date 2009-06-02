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

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author namor
 */
public class Initiative {

    public class NoParticipantsRegisteredException extends Exception {
    }
    public List<Partizipant> partizipanten = new LinkedList<Partizipant>();
    private int initiativePhase = 0;

    public Partizipant getParatizipant(String name) throws PartizipantNichtGefundenException {
        for (Partizipant p : partizipanten)
            if(p.name.equals(name))
                return p;
        throw new PartizipantNichtGefundenException();
    }

    public Partizipant naechsterPartizipierender() throws Exception {
        if (partizipanten.isEmpty()) {
            throw new NoParticipantsRegisteredException();
        }
        Partizipant p = null;
        do {
            System.out.println("Phase: " + initiativePhase);
            for (Partizipant i : partizipanten) {
                if (i.naechsteInitiativePhase() == initiativePhase) {
                    p = i;
                    p.letzterZug = initiativePhase;
                    p.wuerfleInitiative();
                    break;
                }
            }
            for (Partizipant i : partizipanten) {
                if (i.naechsteInitiativePhase() == initiativePhase) {
                    initiativePhase--;
                    break;
                }
            }
            initiativePhase++;
            if (initiativePhase > 100000) {
                throw new Exception("Could not find next Participant");
            }
        } while (p == null);
        return p;
    }

    public void addPartizipant(Partizipant p) throws Exception {
        partizipanten.add(p);
        p.letzterZug = initiativePhase;
        p.wuerfleInitiative();
    }

    public void remPartizipant(String name) throws Exception {
        for (Partizipant p : partizipanten) {
            if (p.name.equals(name)) {
                partizipanten.remove(p);
                return;
            }
        }
        throw new PartizipantNichtGefundenException();
    }

    public static class PartizipantNichtGefundenException extends Exception {
    }

    public static class Partizipant {

        public String name;
        public int persoenlicheInitiative;
        public int waffeninitiative;
        public int wuerfelinitiative;
        public int gewuerfelteInitiative;
        public int letzterZug = 0;

        public Partizipant(String name, int persoenlicheInitiative, int waffeninitiative) throws Exception {
            this.name = name;
            this.waffeninitiative = waffeninitiative;
            setPersoenlicheInitiative(persoenlicheInitiative);
        }

        public void setPersoenlicheInitiative(int persI) throws Exception {
            this.persoenlicheInitiative = persI;
            this.wuerfelinitiative = iniWuerfel(persoenlicheInitiative);
        }

        public void wuerfleInitiative() throws Exception {
            gewuerfelteInitiative = ddd.Wuerfel.w(wuerfelinitiative);
        }

        public int naechsteInitiativePhase() {
            return letzterZug + persoenlicheInitiative + gewuerfelteInitiative + waffeninitiative;
        }
    }

    public static class Einzelereignis extends Partizipant {

        public Einzelereignis(String name, int ereignisphase) throws Exception {
            super(name, 0, 0);
            this.ereignisphase = ereignisphase + 1;
        }
        int ereignisphase;

        @Override
        public int naechsteInitiativePhase() {
            return ereignisphase;
        }

        @Override
        public void wuerfleInitiative() throws Exception {
            ereignisphase--;
        }
    }

    public static int iniWuerfel(int persoenlicheInitiative) throws Exception {
        if (persoenlicheInitiative == 0) {
            return 4;
        }
        if (persoenlicheInitiative <= 4) {
            return 6;
        }
        if (persoenlicheInitiative <= 7) {
            return 8;
        }
        if (persoenlicheInitiative <= 10) {
            return 10;
        }
        throw new Exception("Invalid control flor position");
    }

    public static void main(String[] args) throws Exception {
        Initiative i = new Initiative();
        Partizipant janus = new Partizipant("janus", 7, 4);
        i.addPartizipant(janus);
        Partizipant anus = new Partizipant("_anus", 7, 4);
        i.addPartizipant(anus);
        Einzelereignis boom = new Einzelereignis("##Bombe!##", 25);
        i.addPartizipant(boom);

        for (int n = 0; n < 6; n++) {
            Partizipant p = i.naechsterPartizipierender();
            if (n == 2) {
                i.remPartizipant("janus");
            }
            System.out.println("name: " + p.name + " iniphase: " + p.letzterZug + " neue iniphase: " + p.naechsteInitiativePhase());
        }
    }
}
