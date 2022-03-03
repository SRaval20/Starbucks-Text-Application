/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** My Card Pay Screen */
public class MyCardsPay extends Screen
{
    private final static int TWO =2;
    private final static int THREE =3;

    Card card ;

    public MyCardsPay()
    {
        card = Card.getInstance() ;
    }

    /**
     * Get Screen Display Contents
     * @return Screen Contents
     */
    public String display() {

        String s1 = "[" + card.getCardId() + "]";
        String s2 = "\n";
        String s3 = "Scan Now";

        StringBuffer out_buf = new StringBuffer() ;

        out_buf.append(StrategyHandler.padSpaces( s1 )) ;
        out_buf.append(StrategyHandler.padSpaces( s2 )) ;
        out_buf.append(StrategyHandler.padSpaces( s3 )) ;

        String result =  out_buf.toString();
        return result ;

    }

    /**
     * Touch (X,Y) Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {

        boolean val = ( x == TWO && y == TWO) || (x == THREE && y== TWO);

        AppController app = AppController.getInstance() ;

        if ( x == THREE && y == THREE) {
            app.setScreen( AppController.SCREENS.MY_CARDS_MAIN ) ;
        }            
        if (val) {
            card.pay() ; 
        }

    } 

}


