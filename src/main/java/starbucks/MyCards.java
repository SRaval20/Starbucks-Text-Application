/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** My Cards Screen */
public class MyCards extends Screen
{
    final static int TWO = 2, THREE = 3, FOUR = 4;
    Card card ;

    public MyCards()
    {
        card = Card.getNewInstance() ;
    }

    /**
     * Get Screen Display Contents
     * @return Screen Contents
     */
    public String display() {
        return StrategyHandler.padSpaces(card.getBalance()) ;
    }

    /**
     * Touch (X,Y) Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {

        AppController app = AppController.getInstance() ;
        if ( x == THREE && y == THREE) {
            app.setScreen( AppController.SCREENS.MY_CARDS_PAY ) ;
        }
        else if( x == TWO && y == FOUR ){
            app.setScreen( AppController.SCREENS.MY_CARDS_OPTIONS);
        }


    }    
   
}
