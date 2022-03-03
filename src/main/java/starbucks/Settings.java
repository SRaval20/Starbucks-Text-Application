/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** Settings Screen */
public class Settings extends Screen
{
    private final static int ONE = 1, TWO = 2, THREE = 3;

    public Settings()
    {
        // do nothing
    }
    public String display(){

        StringBuffer out_bf = new StringBuffer();
        out_bf.append(StrategyHandler.padSpaces("Add Card"));
        out_bf.append(StrategyHandler.padSpaces("Delete Card"));
        out_bf.append(StrategyHandler.padSpaces("Billing"));
        out_bf.append(StrategyHandler.padSpaces("Passcode"));
        out_bf.append(StrategyHandler.padSpaces(""));
        out_bf.append(StrategyHandler.padSpaces("About|Terms"));
        out_bf.append(StrategyHandler.padSpaces("Help"));

        return out_bf.toString();

   }
   public void touch(int x, int y)
   {
        boolean val = (x == ONE) || (x == TWO) || (x == THREE);
        AppController app = AppController.getInstance();

        if (val)
        {
            if ( y == ONE)
            {
               app.setScreen(AppController.SCREENS.ADD_CARD);
            }
        }
   }
}
   

