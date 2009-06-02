/*
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
package simpleIO;

/**
 * SimpleIO.KbInput1_1_1 provides the following methods for reading from 
 * a keyboard:
 * It differs from kbdInput because under JDK 1.1.1 the method readLine
 * is deprecated for the class DataInputStream and should be replaced by
 * BufferedReader.readLine()
 *
 * public static byte readByte() 
 * public static byte readByte(String prompt) 
 * public static double readDouble() 
 * public static double readDouble(String prompt) 
 * public static float readFloat() 
 * public static float readFloat(String prompt) 
 * public static int readInt() 
 * public static int readInt(String prompt) 
 * public static long readLong() 
 * public static long readLong(String prompt) 
 * public static String readString() 
 * public static String readString(String prompt) 
 *
 * New Methods: 02-12-96
 * public static char getc()
 * public static void help()
 *
 * Version JDK 1.1.* and higher
 * adapted from F.Mattern / TH Darmstadt , 3-99

 */



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class KbdInput  {

  private static String linebuf;
  private static int lb_index = 0;
  private static int lb_fill_degree=0;

  public static void main(String argv[]) 
  {
  }

  public static void help() 
  {
    System.out.println(" * gdi1.kbdInput1_1_1 provides the following methods for reading from");
    System.out.println(" * a keyboard under JDK 1.1:");
    System.out.println(" *");
    System.out.println(" * public static void help(): prints this message");
    System.out.println(" *");
    System.out.println(" * public static byte readByte()");
    System.out.println(" * public static byte readByte(String prompt)");
    System.out.println(" * public static double readDouble()");
    System.out.println(" * public static double readDouble(String prompt)");
    System.out.println(" * public static float readFloat()");
    System.out.println(" * public static float readFloat(String prompt)");
    System.out.println(" * public static int readInt()");
    System.out.println(" * public static int readInt(String prompt)");
    System.out.println(" * public static long readLong()");
    System.out.println(" * public static long readLong(String prompt)");
    System.out.println(" * public static String readString()");
    System.out.println(" * public static String readString(String prompt)");
    System.out.println(" *");
    System.out.println(" * New Methods: 02-12-96");
    System.out.println(" * public static char getc()");
    System.out.println(" * Read a char from a buffered input line");
    System.out.println(" * ATTENTION: before calling any other method from this class, the");
    System.out.println(" *            buffer should be emptied by getc to avoid unintended results");
    System.out.println(" *            when getc is next called (Access of old buffer)");
    System.out.println(" *");
    System.out.println(" * getc() returns (char) (-1) when an empty line is read");
    System.out.println(" * and '\\n' after the last char in the buffer has been reached.");
    System.out.println("");
  }

/**
 * Read a single byte
 */

  public static byte readByte() 
  {
    byte b = ' ';
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String s = new String();

    try {
      s = inp.readLine();
      if (s.length() > 0) {
        b = (byte) s.charAt(0);
      }
      else {
        System.out.println(s + " was no byte, assigning ' '");
      }
    }
    catch(IOException e) {
      System.out.println("readByte: Input error");
      System.exit(1);
    }
    catch(NumberFormatException e) {
      System.out.println(s + " was no byte, assigning '0'");
    }
    catch(NullPointerException e) {
      System.out.println("No input available, assigning '0'");
    }

    return b;
  }

/**
 * Read a single byte providing a prompt and using readByte()
 */

  public static byte readByte(String prompt) 
  {
    System.out.print(prompt);
    System.out.flush();

    return readByte();
  }

/**
 * Read a char from a buffered input line
 * ATTENTION: before calling any other method from this class, the
 *	      buffer should be emptied by getc to avoid unintended results
 * 	      when getc is next called (Access of old buffer)
 *
 * getc() returns (char) (-1) when an empty line is read.
 */

  public static char getc()
  {
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));

    if (lb_index >= lb_fill_degree) {
      if (lb_index != 0) {
        lb_index = 0;
        lb_fill_degree = 0;
        return ('\n');
      } else {
        try {
          linebuf = inp.readLine();
          lb_fill_degree = linebuf.length();
        }
        catch(NumberFormatException e) {
          System.out.println("c was no Char, assigning '0'");
        }
        catch(IOException e) {
          System.out.println("readChar: Input error");
          System.exit(1);
        }
      }
    }
    if (linebuf.length() == 0) {
      return (char) (-1);
    } else {
      return(linebuf.charAt(lb_index++));
    }
  }

/**
  * Convenience method for compliance with examples in lecture notes
  */ 

  public static char get()
  {
	System.err.println("ERROR: Please use method getc()");
	return (char) (-1);
  }


/**
 * Read a double value
 */

  public static double readDouble() 
  {
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String s = new String();
    Double D;
    double d = 0.0;

    try {
      s = inp.readLine();
      D = Double.valueOf(s);
      d = D.doubleValue();
    }
    catch(NumberFormatException e) {
      System.out.println(s + " was no double, assigning '0.0'");
    }
    catch(IOException e) {
      System.out.println("readDouble: Input error");
      System.exit(1);
    }

    return d;
  }

/**
 * Read a double value providing a prompt and using readDouble()
 */

  public static double readDouble(String prompt) 
  {
    System.out.print(prompt);
    System.out.flush();

    return readDouble();
  }

/**
 * Read a float value
 */

  public static float readFloat() 
  {
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String s = new String();
    Float F;
    float f = (float) 0.0;

    try {
      s = inp.readLine();
      F = Float.valueOf(s);
      f = F.floatValue();
    }
    catch(NumberFormatException e) {
      System.out.println(s + " was no float, assigning '0.0'");
    }
    catch(IOException e) {
      System.out.println("readFloat: Input error");
      System.exit(1);
    }

    return f;
  }

/**
 * Read a float value providing a prompt and using readFloat()
 */

  public static float readFloat(String prompt) 
  {
    System.out.print(prompt);
    System.out.flush();

    return readFloat();
  }

/**
 * Read an int value
 */

  public static int readInt() 
  {
    int i = 0;
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String s = new String();

    try {
      s = inp.readLine();
      i = Integer.parseInt(s);
    }
    catch(NumberFormatException e) {
      System.out.println(s + " was no int, assigning '0'");
    }
    catch(IOException e) {
      System.out.println("readInt: Input error");
      System.exit(1);
    }

    return i;
  }

/**
 * Read an int value providing a prompt and using readInt()
 */

  public static int readInt(String prompt) 
  {
    System.out.print(prompt);
    System.out.flush();

    return readInt();
  }

/**
 * Read a long value
 */

  public static long readLong() 
  {
    long l = 0;
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String s = new String();

    try {
      s = inp.readLine();
      l = Long.parseLong(s);
    }
    catch(NumberFormatException e) {
      System.out.println(s + " was no long, assigning '0'");
    }
    catch(IOException e) {
      System.out.println("readLong: Input error");
      System.exit(1);
    }

    return l;
  }

/**
 * Read a long value providing a prompt and using readLong()
 */

  public static long readLong(String prompt) 
  {
    System.out.print(prompt);
    System.out.flush();

    return readLong();
  }

/**
 * Read a string value
 */

  public static String readString() 
  {
    BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    String s = new String();

    try {
      s = inp.readLine();
    }
    catch(IOException e) {
      System.out.println("readString: Input error");
      System.exit(1);
    }

    return s;
  }

/**
 * Read a string value providing a prompt and using readString()
 */

  public static String readString(String prompt) 
  {
    System.out.print(prompt);
    System.out.flush();

    return readString();
  }
}
