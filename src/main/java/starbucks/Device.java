/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

import java.util.HashMap;


/**
 * Authentication Proxy for App Controller
 */
public class Device implements IApp, IPinAuthObserver {

    private static Device theDevice = null;
    private static final int ZERO = 0, FOUR = 4, SIX = 6 ;
    private String pin = "" ;
    private boolean fourPin = true ;
    private boolean sixPin = false ;

    private HashMap<String, String> props = new HashMap<String, String>() ;

    private IApp app ;
    private KeyPad keyObj;
    private Passcode pass;
    private PinScreen pinScreen;
    private Spacer spacer;
    private PinEntryMachine pinMachine;
    private boolean authenticated = false ;

    public enum ORIENTATION_MODE {
        PORTRAIT, LANDSCAPE
    }

    private ORIENTATION_MODE device_orientation_state ;

    public ORIENTATION_MODE getDeviceOrientation() {
        return this.device_orientation_state ;
    }

    public void setPortraitOrientation() {
        this.device_orientation_state = ORIENTATION_MODE.PORTRAIT ;
    }

    public void setLandscapeOrientation() {
        this.device_orientation_state = ORIENTATION_MODE.LANDSCAPE ;
    }

    private Device() { }

    /** Debug Device State */
    public static void debug()
    {
        Device d = Device.getInstance() ;
        System.err.println( "============================================" ) ;
        System.err.println( "--------- D E V I C E  S T A T E  ----------" ) ;
        System.err.println( "============================================" ) ;
        System.err.println( "Pin Option    = " + d.getPinOption() ) ;
        System.err.println( "Stored Pin    = " + d.getPin() ) ;
        System.err.println( "Authenticated = " + d.isAuthenticated() ) ;
        System.err.println( "Orientation   = " + d.getDeviceOrientation() ) ;
        System.err.println( "============================================" ) ;
    }

    /** Get/Set Device Secured Enclave for Apps **/
    public String getProps(String key) {
        return props.get(key) ;
    }

    public void setProps(String key, String value) {
        props.put(key, value) ;
    }

    /**
     * Get Current Auth State
     * @return Auth T/F
     */
    public String isAuthenticated() {
        return Boolean.toString( authenticated ) ;
    }    

    /**
     * Return the current Pin Option:
     *  0 = User Chosed No Pin
     *  4 = User Chosed 4-digit Pin
     *  6 = User Chosed 6-digit Pin
     * @return Pin Option
     */
    public int getPinOption() {
        if ( fourPin )
            return FOUR ;
        else if ( sixPin )
            return SIX ;
        else
            return ZERO ;
    }

    /**
     * Get Current Pin
     * @return Pin
     */
    public String getPin() {
        return pin ;
    }


    /**
     * Set Pin
     * @param p New Pin
     */
    private void setPin( String p ) {
        pin = p ;
        int len = p.length() ;
        switch ( len ) {
            case FOUR:
                //fourPin = true ;
                //sixPin = false ;
                break ;
            case SIX:
                fourPin = false ;
                sixPin = true ;
                break ;
            default:
                fourPin = false ;
                //sixPin = false ;
                authenticated = true ;
                break;
        }
    }

    /**
     * Device Reset Pin  
     */
    private void clearPin()
    {
        this.pin = "" ;
    }

    /**
     * Get Singleton Instance
     * @return Reference to Current Device Config (Create if none exists)
     */
    public synchronized static Device getInstance() {
        if (theDevice == null) {
            return getNewInstance( "1234" ) ;
        }
        else
            return theDevice;
    }

    /**
     * Get New Instance 
     * @return Reference to Device (Create New Singleton)
     */
    public synchronized static Device getNewInstance() {
        return getNewInstance( "1234" ) ;
    }

    public synchronized static Device getNewInstance( String pin ) {
        theDevice = new Device() ;
        
        theDevice.setPin( pin ) ;
        theDevice.setProps( "apiurl", "http://localhost:3000" ) ;
        theDevice.setProps( "apikey", "2742a237475c4703841a2bf906531eb0" ) ;
        theDevice.setProps( "register", "5012349" ) ;
        theDevice.startUp() ;

        return theDevice ;
    }

    public synchronized static Device getNewInstance( String pin, String url ) {
        if (theDevice == null) {
            theDevice = getNewInstance( "1234" ) ;
            theDevice.setProps( "apiurl", url ) ;
        }
        debug() ;
        return theDevice ;
    }

    /**
     * Device Starup Process.  
     * Starts Up with Default 4-Pin Option
     */
    public void startUp()
    {
        keyObj = new KeyPad() ;
        pass = new Passcode() ;
        spacer = new Spacer() ;
        pinScreen = new PinScreen() ;
        pinMachine = new PinEntryMachine() ;

        // setup the composite pattern
        pinScreen.addSubComponent(pass) ;
        pinScreen.addSubComponent(spacer) ;
        pinScreen.addSubComponent(keyObj) ;

        // setup the observer pattern
        ((IKeyPadSubject) keyObj).attach(pass) ;
        ((IKeyPadSubject) keyObj).attach(pinMachine) ;
        ((IPinAuthSubject) pinMachine).registerObserver(this) ;
        ((IPinAuthSubject) pinMachine).registerObserver(pass) ;
        ((IPinAuthSubject) pinMachine).registerObserver(keyObj) ;

        // get app controller reference
        app = AppController.getNewInstance() ;   

        // startup in portrait
        this.device_orientation_state = ORIENTATION_MODE.PORTRAIT ;
    }

    /**
    * Switch to Landscape View
    */
    public void landscape() {
        if ( authenticated )
            app.landscape() ;
    }

    /**
     * Switch to Portait View
     */
    public void portrait() {
        if ( authenticated )
            app.portrait() ;
    }

    /**
     * User Touch at X,Y Coordinates
     * @param x X Coordinate
     * @param y Y Coordinate
     */
    public void touch(int x, int y) {
        if ( authenticated )
            app.touch(x, y) ;
        else
            pinScreen.touch(x, y) ;
    }

    /**
     * Display Screen Contents to Terminal
     */
    public void display() {
        System.out.println( screenContents() ) ;
    }

    /**
     * Get Class Name of Screen
     * @return Class Name of Current Screen
     */
    public String screen() {
        if ( authenticated )
            return app.screen() ;
        else
            return pinScreen.name() ;
    }

    /**
     * Get Screen Contents as a String
     * @return Screen Contents of Current Screen
     */
    public String screenContents() {
        if ( authenticated ) {
            return app.screenContents() ;
        } else {
            String out = "" ;
            out = "----------------\n" ;
            out += "   " + pinScreen.name() + "  \n" ;
            out += "----------------\n\n\n" ;
            out += pinScreen.display() ;
            out += "\n\n\n----------------\n" ;
            return out ;
        }
    }


    /**
     * Select a Menu Command
     * @param c Menu Option (A, B, C, E, or E)
     */
    public void execute( String c ) {
        if ( authenticated )
            app.execute( c ) ;
    }

    /**
     * Navigate to Previous Screen
     */
    public void prev() {
        if ( authenticated )
            app.prev() ;
    }

    /**
     * Navigate to Next Screen
     */
    public void next() {
        if ( authenticated )
            app.next() ;
    }

    @Override
    public void validPin() {
        this.authenticated = true;
    }

    @Override
    public void invalidPin() {
        /* do nothing*/
    }

}
