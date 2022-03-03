/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import java.util.ArrayList;

/** My Card Options Screen */
public class MyCardsOptions extends Screen {

    private static final int ONE = 1, TWO = 2, THREE = 3, SEVEN = 7;

    public MyCardsOptions()
    {
        // do nothing
    }

    public void touch(int x, int y) {

        boolean val = (x==ONE || x==TWO || x==THREE) ;
        AppController app = AppController.getInstance();

        if (y == SEVEN) {
            if (val) {
                app.setScreen(AppController.SCREENS.MY_CARDS_MORE_OPTIONS);
            }
        }
    }

    public String display()
    {
        String out = "Reload\nRefresh\nMore Options\nCancel\n" ;
        return out ;
    }

}
