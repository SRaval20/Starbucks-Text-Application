/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main Entry Point.
 */
final class Main {

    /**
     * Prevent Construction.
     */
    private Main() {
        // Utility Class
        return ;
    }

    private static final int ONE = 1, TWO = 2, THREE = 3, FIVE = 5, SIX = 6, SEVEN = 7;

    /**
     * Main App Entry Point.
     * @param args No args expected.
     */
    public static void main(final String[] args) {
//        System.err.println( "Args: " + args ) ;
        Device d = Device.getInstance() ;
        IApp app = (IApp) d ;
        Scanner sc = new Scanner(System.in);
        String msg = "" ;
        ArrayList<String> laters = new ArrayList<>( Arrays.asList("a", "b", "c", "d", "e")) ;

        for (;;) {
            System.out.print("\033[H\033[2J") ; // clear the screen
            System.out.flush() ;
            System.out.println(app.screenContents()) ;
            System.out.println( msg ) ;
            System.out.println("=> ") ;
            String ch = sc.nextLine(); ;
            String cmd = ch.toLowerCase() ;  // convert to lower case
            cmd = cmd.replaceAll("\\s","") ; // remove all whitespaces
                   
            /* process commands */
            msg = cmd ;
            if ( cmd.startsWith("touch") ) {
                String parms = cmd.replaceFirst("touch", "") ;
                parms = parms.substring(1) ;
                parms = parms.substring(0, parms.length() - 1) ;
                String[] values = parms.split(",") ;
//                System.err.println( "Value: " + values ) ;
                String x = values[0] ;
                String y = values[1] ;
                msg = "touch: x="+x + " y="+y ; 
                app.touch( Integer.parseInt(x), Integer.parseInt(y) ) ;

            } else if ( laters.contains( cmd ) ) {
                String selection = cmd.toUpperCase() ;
                msg = "selected: " + selection ;
                app.execute( selection ) ;

            } else if ( cmd.startsWith("prev") ) {
                msg = "cmd: previous" ;
                app.prev() ;
            } else if ( cmd.startsWith("next") ) {
                msg = "cmd: next" ;
                app.next() ;
            } else if (cmd.equalsIgnoreCase( "portrait" )) {
                app.portrait() ;
            } else if (cmd.equalsIgnoreCase( "landscape" )) {
                app.landscape() ;
            } else if ( cmd.startsWith("login") ) {
                app.touch(ONE,FIVE) ;    // 1
                app.touch(TWO,FIVE) ;    // 2
                app.touch(THREE,FIVE) ;  // 3
                app.touch(ONE,SIX) ;     // 4
            } else if (cmd.startsWith("addcard")) {
                app.touch(ONE,FIVE) ;    // 1
                app.touch(TWO,FIVE) ;    // 2
                app.touch(THREE,FIVE) ;  // 3
                app.touch(ONE,SIX) ;     // 4
                app.touch(TWO,SIX) ;     // 5
                app.touch(THREE,SIX) ;   // 6
                app.touch(ONE,SEVEN) ;   // 7
                app.touch(TWO,SEVEN) ;   // 8
                app.touch(THREE,SEVEN) ; // 9
                app.touch(TWO,THREE);    // FOCUS
                app.touch(THREE,SEVEN);  // 9
                app.touch(THREE,SEVEN);  // 9
                app.touch(THREE,SEVEN);  // 9
            } else {
                msg = "";
            }
        }
    }
}

