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

/**
 *
 * @author namor
 */
public class InitiativeSingleton {

    private InitiativeSingleton() {
    }

    public static InitiativeSingleton getInstance() {
        if (instance == null) {
            instance = new InitiativeSingleton();
        }
        return instance;
    }
    private static InitiativeSingleton instance = null;

    public Initiative initiative = new Initiative();
}
