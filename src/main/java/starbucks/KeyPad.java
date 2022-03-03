/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.util.ArrayList;

/** Key Pad */
public class KeyPad implements ITouchEventHandler, IDisplayComponent, IKeyPadSubject, IPinAuthObserver
{
    int countPinDigits = 0 ;
    final static int ONE = 1, TWO = 2, THREE = 3, FOUR = 4, EIGHT = 8 ;
    String lastKey = "" ;

    ITouchEventHandler nextHandler ;
    private ArrayList<IKeyPadObserver> observers ;

    public KeyPad()
    {
        observers = new ArrayList<IKeyPadObserver>() ;
    }

    /**
     * Touch Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {

        boolean val_1 = ( x==THREE && y==EIGHT ), val_2 = ( y<EIGHT ), val_3 = (x==TWO && y==EIGHT) ;

        if ( y > FOUR )
        {
            this.lastKey = getKey( x, y ) ;
            if ( val_1 )
            {
                countPinDigits-- ;
            }
            else if ( val_2 || val_3)
            {
                countPinDigits++ ;
            }
            notifyObservers() ;            
        }
        else
        {
            if ( nextHandler != null )
                nextHandler.touch(x,y) ;
        }
    }

    /**
     *  Get Last Key Pressed 
     * @return Lasy Key
     */
    public String lastKey() { 
        System.err.println( "Key Pressed: " + this.lastKey ) ;
        return this.lastKey ; 
    }

    /**
     * Get Key Number from (X,Y) Touch Coord's
     * @param  x [description]
     * @param  y [description]
     * @return   [description]
     */
    private String getKey( int x, int y )
    {
        int kx = 0, ky = 0, extra = 0, last = 0  ;
        kx = x;
        ky = y-FOUR ;
        extra = THREE * ( ky - ONE ) ;
        last = kx + extra ;

        if ( kx == THREE && ky == FOUR )
            return "X" ;
        else if ( kx == TWO && ky == FOUR )
            return "0" ;
        else if ( kx == ONE && ky == FOUR )
            return " " ;
        else
            return Integer.toString(last) ;
    }

    /**
     * Set Next Touch Event Handler
     * @param next Event Handler
     */
    public void setNext( ITouchEventHandler next) { 
        nextHandler = next ;
    }

    /**
     * Get Key Pad Display
     * @return Key Pad View Contents
     */
    public String display() 
    {
        //return " [1] [2] [3]\n [4] [5] [6]\n [7] [8] [9]\n [_] [0] [X]"  ;

        StringBuffer out_buf = new StringBuffer() ;
        out_buf.append("  [1] [2] [3]\n");
        out_buf.append("  [4] [5] [6]\n");
        out_buf.append("  [7] [8] [9]\n");
        out_buf.append("  [_] [0] [X]\n");

        String output = out_buf.toString() ;
        return output ;
    }

    /**
     * Add Sub Component (Not used)
     * @param c Display Component
     */
    public void addSubComponent( IDisplayComponent c ) 
    {
    }

    /**
     * Attach a Key Pad Observer
     * @param obj Observer
     */
    public void attach( IKeyPadObserver obj ) 
    {
        observers.add( obj ) ;
    }

    /**
     * Remove Key Pad Observer
     * @param obj Observer
     */
    public void removeObserver( IKeyPadObserver obj )
    {
        int i = observers.indexOf(obj) ;
        if ( i >= 0 )
            observers.remove(i) ;
    }

    /**
     * Notify all Observers of Update Event
     */
    public void notifyObservers( )
    {
        for (int i=0; i<observers.size(); i++)
        {
            IKeyPadObserver observer = observers.get(i) ;
            observer.keyEventUpdate( countPinDigits, lastKey ) ;
        }
    }

    @Override
    public void validPin() {
    }

    @Override
    public void invalidPin() {
        this.countPinDigits =0;
    }
}
