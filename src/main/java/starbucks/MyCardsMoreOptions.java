/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import java.util.ArrayList;

/** My Card More Options Screen */
public class MyCardsMoreOptions extends Screen
{
  
    public MyCardsMoreOptions()
    {
        // do nothing
    }

    public void touch(int x, int y)
    {
        //no implementation
    }

    public String display()
    {
        String out = "Refresh\nReload\nAuto Reload\nTransactions\n" ;
        return out ;
    }

}
