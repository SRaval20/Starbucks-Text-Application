/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Passcode Screen Component */
public class Passcode implements ITouchEventHandler, IDisplayComponent, IKeyPadObserver, IPinAuthObserver
{
    private int count = 0;
    final static int ZERO = 0, ONE = 1, TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6;
    private boolean invalidPin = false;

    ITouchEventHandler nextHandler ;

    /**
     * Touch Event Ignored by Passcode
     * @param x Touch X
     * @param y Touch Y
     */
    public void touch(int x, int y) 
    {
        if ( y==TWO )
        {
            System.err.println( "Passcode Touched at (" + x + ", " + y + ")" ) ; 
        }
        else
        {
            if ( nextHandler != null )
                nextHandler.touch(x,y) ;
        }
    }
    
    /**
     * Set Next Touch Handler
     * @param next Touch Event Handler
     */
    public void setNext( ITouchEventHandler next) 
    { 
        nextHandler = next ;
    }
    
    
    /**
     * Display "Echo Feedback" on Pins enterred so far
     * @return Passcode String for Display
     */
    public String display() 
    {
        String msg = "  Invalid Pin \n" ;
        StringBuffer val_buf = new StringBuffer();
        Device device = Device.getInstance();

        if(invalidPin)
            val_buf.append(msg) ;
        if(device.getPinOption()==FOUR){
            switch ( count )
            {
                case ZERO:  val_buf.append("  [_][_][_][_]  ") ; break ;
                case ONE:   val_buf.append("  [*][_][_][_]  ") ; break ;
                case TWO:   val_buf.append("  [*][*][_][_]  ") ; break ;
                case THREE: val_buf.append("  [*][*][*][_]  ") ; break ;
                case FOUR:  val_buf.append("  [*][*][*][*]  ") ; break ;
                default: return null ;
            }
        }
        else if( device.getPinOption()==SIX){
            switch ( count )
            {
                case ZERO:  val_buf.append("  _ _ _ _ _ _  ") ; break ;
                case ONE:   val_buf.append("  * _ _ _ _ _  ") ; break ;
                case TWO:   val_buf.append("  * * _ _ _ _  ") ; break ;
                case THREE: val_buf.append("  * * * _ _ _  ") ; break ;
                case FOUR:  val_buf.append("  * * * * _ _  ") ; break ;
                case FIVE:  val_buf.append("  * * * * * _  ") ; break ;
                case SIX:   val_buf.append("  * * * * * *  ") ; break ;
                default: return null ;

            }
        }

        String val = val_buf.toString();
        return val;
    }
    
    /**
     * Add Sub Component (Not used)
     * @param c Sub Component to Add
     */
    public void addSubComponent( IDisplayComponent c ) 
    {
        
    }   
    
    /**
     * Key Event Update
     * @param c   Count of Keys So Far
     * @param key Last key Pressed
     */
    public void keyEventUpdate( int c, String key ) 
    {
        System.err.println( "Key: " + key ) ;
        count = c ;
        this.invalidPin = false;
    }

    @Override
    public void validPin() {
    }

    @Override
    public void invalidPin() {
        count = 0;
        this.invalidPin = true;
    }
}
