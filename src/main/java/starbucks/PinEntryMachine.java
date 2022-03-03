/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.util.ArrayList;

/** Pin Entry Machine - Context for State Pattern */
public class PinEntryMachine implements IPinStateMachine, IKeyPadObserver, IPinAuthSubject
{

    /**
     * Get name of current state for unit testing
     * @return Class Name of Current State
     */
    public String getCurrentState()
    {
        return state.getClass().getName() ;
    }

    // Pin Domain Object
//    private String pin = "1234" ;
    private boolean invalidPin = true;
//    private int pinCount=0 ;
//    private static final int TWO = 2, THREE = 3, FOUR = 4, FIVE = 5, SIX = 6;
    private static final int FOUR = 4;
    private ArrayList<IPinAuthObserver> authObservers;

    // pin machine states
    private NoPinDigits pin0 ;
    private OnePinDigit pin1 ;
    private TwoPinDigits pin2 ;
    private ThreePinDigits pin3 ;
    private FourPinDigits pin4 ;
    private FivePinDigits pin5 ;
    private SixPinDigits pin6 ;

    // current state
    private IPinState state ;

    // pin captured so far
    private String d1, d2, d3, d4, d5, d6 ;
    public String d1() { return d1 ; }
    public String d2() { return d2 ; }
    public String d3() { return d3 ; }
    public String d4() { return d4 ; }
    public String d5() { return d5 ; }
    public String d6() { return d6 ; }

    /**
     * Constructor - Set Up State Objects
     * and Set Initial State to "PIN 0"
     */
    public PinEntryMachine( )
    {
        authObservers = new ArrayList<>();
        pin0 = new NoPinDigits( this ) ;
        pin1 = new OnePinDigit( this ) ;
        pin2 = new TwoPinDigits( this ) ;
        pin3 = new ThreePinDigits( this ) ;
        pin4 = new FourPinDigits( this ) ;
        pin5 = new FivePinDigits( this ) ;
        pin6 = new SixPinDigits( this ) ;
        this.d1 = "" ;
        this.d2 = "" ;
        this.d3 = "" ;
        this.d4 = "" ;
        this.d5 = "" ;
        this.d6 = "" ;
        this.state = pin0 ;
    }

    /** Backspace Event */
    public void backspace() {
        this.state.backspace() ;
    }

    /**
     * Number Event
     * @param digit Digit Pressed
     */
    public void number( String digit ) {
        this.state.number( digit ) ;
    }

    /** Valid Pin Event */
    public void validPin() {
        this.state.validPin() ;
    }

    /** Invalid Pin Event */
    public void invalidPin() {
        notifyObserver();
        this.setStateNoPinDigits();
    }

    /** Change the State to No Pin State */
    public void setStateNoPinDigits()
    {
//        this.pinCount = 0 ;
        this.state = pin0 ;
        this.d1 = "" ;
        this.d2 = "" ;
        this.d3 = "" ;
        this.d4 = "" ;
        this.d5 = "" ;
        this.d6 = "" ;
        debug() ;
    }

    /**
     * Change the State to One Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateOnePinDigit( String digit )
    {
//        this.pinCount = 1 ;
        this.state = pin1 ;
        if ( digit != null )
            this.d1 = digit ;
        else {
            this.d2 = "" ;
            this.d3 = "" ;
            this.d4 = "" ;
        }
        debug() ;
    }

    /**
     * Change the State to Two Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateTwoPinDigits( String digit )
    {
//        this.pinCount = TWO ;
        this.state = pin2 ;
        if ( digit != null )
            this.d2 = digit ;
        else {
            this.d3 = "" ;
            this.d4 = "" ;
        }
        debug() ;
    }

    /**
     * Change the State to Three Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateThreePinDigits( String digit )
    {
//        this.pinCount = THREE ;
        this.state = pin3 ;
        if ( digit != null )
            this.d3 = digit ;
        else {
            this.d4 = "" ;
        }
        debug() ;
    }

    /**
     * Change the State to Four Pin State
     * @param digit Digit/Number Enterred
     */
    public void setStateFourPinDigits( String digit )
    {
//        this.pinCount = FOUR ;
        this.state = pin4 ;
        if ( digit != null )
        {
            this.d4 = digit ;
            debug() ;
            Device theDevice = Device.getInstance();
            if(theDevice.getPinOption() == FOUR){
                if ( theDevice.getPin().equals( d1+d2+d3+d4 ) )
                {
                    System.err.println( "Successful Login!" ) ;
                    invalidPin = false;
                    notifyObserver() ;
                }
                else
                {
                    System.err.println( "Login Failed!" ) ;
                    this.state.invalidPin();
                }
            }
            else{
                this.d5 = "";
            }
        }
    }

    @Override
    public void setStateFivePinDigits(String digit) {
//        this.pinCount = FIVE ;
        this.state = pin5 ;
        if ( digit != null )
            this.d5 = digit ;
        else {
            this.d6 = "" ;
        }
        debug() ;
    }

    @Override
    public void setStateSixPinDigits(String digit) {
//        this.pinCount = SIX ;
        this.state = pin6 ;
        if ( digit != null )
        {
            this.d6 = digit ;
            debug() ;
            Device theDevice = Device.getInstance();
                if ( theDevice.getPin().equals( d1+d2+d3+d4+d5+d6 ) )
                {
                    System.err.println( "Successful Login!" ) ;
                    invalidPin = false;
                    notifyObserver();
                }
                else
                {
                    System.err.println( "Login Failed!" ) ;
                    this.state.invalidPin();
                }
        }

    }

    /**
     * Observer of Key Events
     * @param c   Num Keys So Far
     * @param key Last Key Enterred
     */
    public void keyEventUpdate( int c, String key ) 
    {
        System.err.println( "Key: " + key + " Count: " + c ) ;
        if ( key.equals(" ") )
        /* nothing */ ;
        else if ( key.equals("X") )
            backspace() ;
        else
            number( key ) ;        
    }    

    /**
     * Register Observers for Pin Authentication
     * @param obj Object Observing Pin Auth
     */
    public void registerObserver( IPinAuthObserver obj ) 
    {
        authObservers.add(obj);
    }

    /**
     * Remove Pin Auth Observer
     * @param obj Object No Longer Observing Pin Auth
     */
    public void removeObserver( IPinAuthObserver obj ) 
    {
        authObservers.remove(obj);
    }

    /**
     * Notify Pin Auth Observers
     */
    public void notifyObserver( ) 
    {
        for(IPinAuthObserver iPinAuthObserver : authObservers){
            if(!invalidPin)
                iPinAuthObserver.validPin();
            else
                iPinAuthObserver.invalidPin();
        }
    }

    /** Debug Dump to Stderr State Machine Changes */
    private void debug()
    {
        System.err.println( "Current State: " + state.getClass().getName() ) ;
        System.err.println( "D1 = " + d1 ) ;
        System.err.println( "D2 = " + d2 ) ;
        System.err.println( "D3 = " + d3 ) ;
        System.err.println( "D4 = " + d4 ) ;
        System.err.println( "D5 = " + d5 ) ;
        System.err.println( "D6 = " + d6 ) ;
    }

}
