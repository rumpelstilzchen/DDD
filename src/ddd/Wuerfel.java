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

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author namor
 */
public class Wuerfel {

    public static int w(int x) {
        return (int) (new Random().nextDouble() * x) + 1;
    }

    public static TREFFERZONE trefferzone() throws Exception {
        switch (w(20)) {
            case 1:
                switch (w(6)) {
                    case 1:
                        return TREFFERZONE.STIRN;
                    case 2:
                        switch (w(6)) {
                            case 1:
                                return TREFFERZONE.LINKES_AUGE;
                            case 2:
                                return TREFFERZONE.RECHTES_AUGE;
                            case 3:
                            case 4:
                            case 5:
                                return TREFFERZONE.STIRN;
                            case 6:
                                return TREFFERZONE.BEIDE_AUGEN;
                        }
                    case 3:
                        return TREFFERZONE.GESICHTSMITTE;
                    case 4:
                        return TREFFERZONE.KOPF_LINKS;
                    case 5:
                        return TREFFERZONE.KOPF_RECHTS;
                    case 6:
                        return TREFFERZONE.UNTERKIEFER;
                }
            case 2:
                switch (w(6)) {
                    case 1:
                        return TREFFERZONE.KEHLE;
                    case 2:
                    case 3:
                        return TREFFERZONE.HALS_LINKS;
                    case 4:
                    case 5:
                        return TREFFERZONE.HALS_RECHTS;
                    case 6:
                        return TREFFERZONE.NACKEN;
                }
            case 3:
            case 4:
            case 5:
                switch (w(6)) {
                    case 1:
                        return TREFFERZONE.LINKE_SCHULTER;
                    case 2:
                        return TREFFERZONE.LINKER_OBERARM;
                    case 3:
                        return TREFFERZONE.LINKER_ELLBOGEN;
                    case 4:
                    case 5:
                        return TREFFERZONE.LINKER_UNTERARM;
                    case 6:
                        return TREFFERZONE.LINKE_HAND;
                }
            case 6:
            case 7:
            case 8:
                switch (w(6)) {
                    case 1:
                        return TREFFERZONE.RECHTE_SCHULTER;
                    case 2:
                        return TREFFERZONE.RECHTER_OBERARM;
                    case 3:
                        return TREFFERZONE.RECHTER_ELLBOGEN;
                    case 4:
                    case 5:
                        return TREFFERZONE.RECHTER_UNTERARM;
                    case 6:
                        return TREFFERZONE.RECHTE_HAND;
                }
            case 9:
            case 10:
            case 11:
            case 12:
                switch (w(6)) {
                    case 1:
                    case 2:
                        return TREFFERZONE.OBERE_BRUST;
                    case 3:
                        return TREFFERZONE.HERZ;
                    case 4:
                    case 5:
                    case 6:
                        return TREFFERZONE.OBERBAUCH;
                }
            case 13:
            case 14:
                switch (w(6)) {
                    case 1:
                    case 2:
                    case 3:
                        return TREFFERZONE.BAUCH;
                    case 4:
                    case 5:
                        return TREFFERZONE.UNTERBAUCH;
                    case 6:
                        return TREFFERZONE.GENITALIEN;
                }
            case 15:
            case 16:
            case 17:
                switch (w(6)) {
                    case 1:
                        return TREFFERZONE.LINKE_LEISTE;
                    case 2:
                        return TREFFERZONE.LINKER_OBERSCHENKEL;
                    case 3:
                        return TREFFERZONE.LINKES_KNIE;
                    case 4:
                    case 5:
                        return TREFFERZONE.LINKER_UNTERSCHENKEL;
                    case 6:
                        return TREFFERZONE.LINKER_FUSS;
                }
            case 18:
            case 19:
            case 20:
                switch (w(6)) {
                    case 1:
                        return TREFFERZONE.RECHTE_LEISTE;
                    case 2:
                        return TREFFERZONE.RECHTER_OBERSCHENKEL;
                    case 3:
                        return TREFFERZONE.RECHTES_KNIE;
                    case 4:
                    case 5:
                        return TREFFERZONE.RECHTER_UNTERSCHENKEL;
                    case 6:
                        return TREFFERZONE.RECHTER_FUSS;
                }
        }
        throw new Exception("Invalid control flow location");
    }

    public static PROBENQUALITAET probe(int PW) throws Exception {
        if (PW > 90) {
            PW = 90;
        }
        int wurf = w(100);

        if (wurf == 1) {
            return PROBENQUALITAET.EXZELLENT;
        }
        if (wurf == 100) {
            return PROBENQUALITAET.SCHWERER_PATZER;
        }

        if (wurf >= (PW < 50 ? 98 : 99)) {
            return PROBENQUALITAET.MITTLERER_PATZER;
        }
        if (wurf >= (PW < 50 ? 96 : 98)) {
            return PROBENQUALITAET.LEICHTER_PATZER;
        }
        if (wurf * 10 <= PW) {
            return PROBENQUALITAET.MEISTERLICH;
        }
        if (wurf * 2 <= PW) {
            return PROBENQUALITAET.GUT;
        }
        if (wurf <= PW) {
            return PROBENQUALITAET.GELUNGEN;
        }
        if (PW + 1 <= wurf && wurf <= PW + 0.1 * (100 - PW) && wurf <= 91) {
            return PROBENQUALITAET.SCHLECHT;
        }
        if ((100 - PW) * 0.1 + PW + 1 <= wurf) {
            return PROBENQUALITAET.MISSLUNGEN;
        }
        throw new Exception("control flow error");
    }

    public static TREFFERQUALITAET trefferqualitaet(PROBENQUALITAET angriff, PROBENQUALITAET verteidigung) throws Exception{
        if (angriff == PROBENQUALITAET.MISSLUNGEN ||
                angriff == PROBENQUALITAET.LEICHTER_PATZER ||
                angriff == PROBENQUALITAET.MITTLERER_PATZER ||
                angriff == PROBENQUALITAET.SCHWERER_PATZER) {
            return TREFFERQUALITAET.KEIN_TREFFER;
        }

        switch(angriff){
            case SCHLECHT:
                switch(verteidigung){
                    case SCHWERER_PATZER:
                    case MITTLERER_PATZER:
                    case LEICHTER_PATZER:
                    case MISSLUNGEN:
                        return TREFFERQUALITAET.KRATZER;
                    case SCHLECHT:
                    case GELUNGEN:
                        return TREFFERQUALITAET.PARADE;
                    case GUT:
                        return TREFFERQUALITAET.KONTER;
                    case MEISTERLICH:
                        return TREFFERQUALITAET.GUTER_KONTER;
                    case EXZELLENT:
                        return TREFFERQUALITAET.MEISTERLICHER_KONTER;
                }
            case GELUNGEN:
                switch(verteidigung){
                    case SCHWERER_PATZER:
                    case MITTLERER_PATZER:
                    case LEICHTER_PATZER:
                    case MISSLUNGEN:
                        return TREFFERQUALITAET.TREFFER;
                    case SCHLECHT:
                        return TREFFERQUALITAET.KRATZER;
                    case GELUNGEN:
                        return TREFFERQUALITAET.PARADE;
                    case GUT:
                        return TREFFERQUALITAET.KONTER;
                    case MEISTERLICH:
                        return TREFFERQUALITAET.GUTER_KONTER;
                    case EXZELLENT:
                        return TREFFERQUALITAET.MEISTERLICHER_KONTER;
                }
            case GUT:
                switch(verteidigung){
                    case SCHWERER_PATZER:
                    case MITTLERER_PATZER:
                    case LEICHTER_PATZER:
                    case MISSLUNGEN:
                        return TREFFERQUALITAET.GUTER_TREFFER;
                    case SCHLECHT:
                        return TREFFERQUALITAET.TREFFER;
                    case GELUNGEN:
                        return TREFFERQUALITAET.TREFFER;
                    case GUT:
                        return TREFFERQUALITAET.PARADE;
                    case MEISTERLICH:
                        return TREFFERQUALITAET.KONTER;
                    case EXZELLENT:
                        return TREFFERQUALITAET.GUTER_KONTER;
                }
            case MEISTERLICH:
                switch(verteidigung){
                    case SCHWERER_PATZER:
                    case MITTLERER_PATZER:
                    case LEICHTER_PATZER:
                    case MISSLUNGEN:
                        return TREFFERQUALITAET.MEISTERLICHER_TREFFER;
                    case SCHLECHT:
                        return TREFFERQUALITAET.GUTER_TREFFER;
                    case GELUNGEN:
                        return TREFFERQUALITAET.GUTER_TREFFER;
                    case GUT:
                        return TREFFERQUALITAET.TREFFER;
                    case MEISTERLICH:
                        return TREFFERQUALITAET.PARADE;
                    case EXZELLENT:
                        return TREFFERQUALITAET.KONTER;
                }
            case EXZELLENT:
                switch(verteidigung){
                    case SCHWERER_PATZER:
                    case MITTLERER_PATZER:
                    case LEICHTER_PATZER:
                    case MISSLUNGEN:
                        return TREFFERQUALITAET.EXZELLENTER_TREFFER;
                    case SCHLECHT:
                        return TREFFERQUALITAET.MEISTERLICHER_TREFFER;
                    case GELUNGEN:
                        return TREFFERQUALITAET.MEISTERLICHER_TREFFER;
                    case GUT:
                        return TREFFERQUALITAET.GUTER_TREFFER;
                    case MEISTERLICH:
                        return TREFFERQUALITAET.TREFFER;
                    case EXZELLENT:
                        return TREFFERQUALITAET.PARADE;
                }
        }

        throw new Exception("Invalid contol flow position");
    }

    public static int waffenWucht(int waffenklasse) {
        int sum = 0;
        int a, b;
        do {
            a = w(6);
            b = w(6);
            if (a == 1) {
                a = 6;
            }
            if (b == 1) {
                b = 6;
            }
            sum += a + b;
        } while (a == b);

        double prozentsatz = sum * 0.1;
        int waffenwucht = (int) (prozentsatz * waffenklasse);
        return waffenwucht;
    }

    public static void main(String[] args) throws Exception {
        JOptionPane.showInputDialog("Probenergebnis", probe(90));
    }

    public static enum TREFFERZONE {

        STIRN,
        LINKES_AUGE,
        RECHTES_AUGE,
        BEIDE_AUGEN,
        GESICHTSMITTE,
        KOPF_LINKS,
        KOPF_RECHTS,
        UNTERKIEFER,
        KEHLE,
        HALS_LINKS,
        HALS_RECHTS,
        NACKEN,
        OBERE_BRUST,
        HERZ,
        OBERBAUCH,
        BAUCH,
        UNTERBAUCH,
        GENITALIEN,
        LINKE_SCHULTER,
        LINKER_OBERARM,
        LINKER_ELLBOGEN,
        LINKER_UNTERARM,
        LINKE_HAND,
        RECHTE_SCHULTER,
        RECHTER_OBERARM,
        RECHTER_ELLBOGEN,
        RECHTER_UNTERARM,
        RECHTE_HAND,
        LINKE_LEISTE,
        LINKER_OBERSCHENKEL,
        LINKES_KNIE,
        LINKER_UNTERSCHENKEL,
        LINKER_FUSS,
        RECHTE_LEISTE,
        RECHTER_OBERSCHENKEL,
        RECHTES_KNIE,
        RECHTER_UNTERSCHENKEL,
        RECHTER_FUSS
    }

    public enum PROBENQUALITAET {

        EXZELLENT,
        MEISTERLICH,
        GUT,
        GELUNGEN,
        SCHLECHT,
        MISSLUNGEN,
        LEICHTER_PATZER,
        MITTLERER_PATZER,
        SCHWERER_PATZER
    };

    public enum TREFFERQUALITAET {

        EXZELLENTER_TREFFER,
        MEISTERLICHER_TREFFER,
        GUTER_TREFFER,
        TREFFER,
        KRATZER,
        PARADE,
        KONTER,
        GUTER_KONTER,
        MEISTERLICHER_KONTER,
        KEIN_TREFFER
    }
}
