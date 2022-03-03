/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

import java.text.NumberFormat ;
import java.util.Locale;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/* 

    REST CLIENT:  

    http://unirest.io/java.html 
    https://www.baeldung.com/unirest
    http://stleary.github.io/JSON-java/index.html
    https://www.programcreek.com/java-api-examples/?api=com.mashape.unirest.http.Unirest

*/


/**
 * Card Class for Managing Card Balance & Transactions
 */
public class Card {

    private double balance = 0.00f ; // default
    private String cardId = "000000000" ;
    private String cardCode = "000" ;
    private static final int successCode = 200 ;
    
    private static Card theCard = null ;
    
    public synchronized static Card getNewInstance() {
        theCard = new Card();
        theCard.setCard();
        return theCard;
    }
    
    public synchronized static Card getInstance() {
        if (theCard == null) {
            theCard = new Card();
            theCard.setCard();
            return theCard;
        }
        else
            return theCard;
    }

    private Card() { }

    // << Change Me!  Hard Coded to Get a Random Card for Now >>
    public void setCard()
    {
  
        Device theDevice = Device.getInstance() ;
        String apiurl = theDevice.getProps("apiurl") ;
        String apikey = theDevice.getProps("apikey") ;
        //String register = theDevice.getProps("register") ;

        try
        {
            System.out.println("CardId : "+ cardId);
            HttpResponse<JsonNode> response2 = Unirest.post( apiurl + "/card/activate/" + cardId + "/" + cardCode )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code2 = response2.getStatus() ;
            String status_text2 = response2.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code2) + " " + status_text2 ) ;
            JSONObject json2 = response2.getBody().getObject() ;
            System.err.println( json2.toString() ) ;
            if( status_code2 == successCode )
            {
                AppController app = AppController.getInstance();
                app.setScreen(AppController.SCREENS.MY_CARDS_MAIN);
            }
        } catch (Exception e) {

            System.err.println( e ) ;
            
        }
    }

    public void setCard(String cardID, String cardCode){
        this.cardId = cardID;
        this.cardCode = cardCode;
        setCard();
    }

    public void setCard(Double balance){
        this.balance = balance;
        setCard();
    }

    public String getCardId() {
        return cardId ;
    }
    
    public String getCardCode() {
        return cardCode ;
    }
    
    public String getBalance() {
        Locale.setDefault(Locale.US);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(balance) ;        
    }

    public void pay() {

        Device theDevice = Device.getInstance() ;
        String apiurl = theDevice.getProps("apiurl") ;
        String apikey = theDevice.getProps("apikey") ;
        String register = theDevice.getProps("register") ;
        System.err.println("Regsiter : "+ register);
        System.err.println("CARDID : "+ cardId);
        try {

            HttpResponse<JsonNode> response = 
                Unirest.post( apiurl + "/order/register/"+register+"/pay/"+cardId)
                    .header( "apikey", apikey )
                    .asJson() ;
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;

            double new_bal = json.getDouble( "Balance" )  ;  
            this.balance = new_bal ;    

        } catch (Exception e) {

            System.err.println( e ) ;
            
        }

    }

    public void display()
    {
        System.err.format( "Card ID: %s%n", cardId ) ;
        System.err.format( "Card Balance: $%4.2f%n", balance ) ;
    }
}
 

