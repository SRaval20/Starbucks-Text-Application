/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Store Screen */
public class Store extends Screen
{
    private String storestoreRegId;
    private static final int ONE = 1 , TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6, SEVEN = 7;

    /**
     * Constructor
     */
    public Store() {
        storestoreRegId = "";
    }

    /**
     * Return Screen Contents
     * @return Map Screen Sample Content
     */
    public String display() {
        String out = "         X\n   X\n       X\n      X\n  X\n           X\n  X" ;
        return out ;
    }

    /**
     * Handle Touch Event -- Not Used.
     * @param x Touch X
     * @param y Touch Y
     */
    public void touch(int x, int y) {

        AppController app = AppController.getInstance();
        Device theDevice = Device.getInstance();

        if( x == ONE){
            if( y== THREE)
                this.storestoreRegId = "6498234";
            else if(y == SIX)
                this.storestoreRegId = "9621043";
            else if(y == SEVEN)
                this.storestoreRegId = "1393478";
        }
        else if( x == TWO ){
            if( y == FOUR )
                this.storestoreRegId = "7812386";
            else if( y == FIVE)
                this.storestoreRegId = "8723098";
        }
        else if( x== THREE){
            if(y == TWO)
                this.storestoreRegId = "5012349";
            else if( y== SEVEN)
                this.storestoreRegId = "1287612";
        }

        theDevice.setProps("register", this.storestoreRegId);
        app.setScreen(AppController.SCREENS.MY_CARDS_PAY);

    }
}
