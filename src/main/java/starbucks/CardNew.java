package starbucks;

public class CardNew implements ITouchEventHandler {
    private static CardNew cardNew = null;

//    ITouchEventHandler nextHandler;
    private String code;
    private String id;
    private static final int idLength = 9;
    private static final int codeLength = 3;
    private boolean idFocus;
    private boolean codeFocus;
    private final static double balance = 20;

    private static final int ONE = 1, TWO = 2, THREE = 3, FIFTEEN = 15;

    public CardNew()
    {

        id ="";
        code ="";
        idFocus =true;
        codeFocus =false;
    }

    public void clearCard()

    {
        id ="";
        code ="";
        idFocus =true;
        codeFocus =false;
    }

    public String display(){

        StringBuffer val_bf = new StringBuffer();
        val_bf.append("\n");

        String str1 = "["+ id +"]";
        String str2 = "["+ code +"]";

        int count = (FIFTEEN - str1.length())/TWO;
        int rem = (FIFTEEN- str1.length())%TWO;

        val_bf.append(padSpaces( count ));
        val_bf.append(padSpaces( rem ));
        val_bf.append(str1);
        val_bf.append(padSpaces( count ));
        val_bf.append("\n");

        count = (FIFTEEN - str2.length())/TWO;
        rem = (FIFTEEN- str2.length())%TWO;

        val_bf.append(padSpaces( count ));
        val_bf.append(padSpaces( rem ));
        val_bf.append(str2);
        val_bf.append(padSpaces(count));
        val_bf.append("\n");

        String val = val_bf.toString();
        return val;
    }

    private String padSpaces(int num) {
        StringBuffer space_bf = new StringBuffer() ;
        String space ;
        for ( int i = 0; i<num; i++ )
            space_bf.append(" ") ;
        space = space_bf.toString() ;
        return space ;
    }

    public void keyPressed(String key)
    {
        String space = " " ;
        String cross = "X" ;

        if( key.equals(space)){
        }
        else if(key.equals(cross)){
            if(idFocus){
                if(id.length()!=0){
                    id = id.substring(0, id.length()-1);
                }
            }
            else if(codeFocus){
                if(code.length()!=0){
                    code = code.substring(0, code.length()-1);
                }
            }
        }
        else{
            if(idFocus){
                if(id.length() < idLength){
                    id += key ;
                }
            }
            else if(codeFocus){
                if(code.length() < codeLength){
                    code += key;
                }
            }
        }
    }

    public static CardNew getInstance(){return new CardNew();}


    @Override
    public void setNext(ITouchEventHandler next) {

    }

    public void addNewCard()
    {
        boolean val = ( id.length() == idLength && code.length() == codeLength ) ;
//        boolean val0 = (ID.equals("000000000"));
        boolean val1 = (id.equals("222222222") && code.equals("000"));
        if( val )
        {
            if(val1)
            {
                AppController appc = AppController.getInstance();
                appc.setScreen(AppController.SCREENS.MY_CARDS_MAIN);
            }
            else
            {
                Card card = Card.getInstance();
                card.setCard(id, code);
                card.setCard(balance);
            }
        }
        clearCard();
    }

    @Override
    public void touch(int x, int y)
    {
        boolean val = (x == ONE|| x== TWO|| x== THREE) ;

        if( y== TWO)
        {
            if( val )
            {
                idFocus = true;
                codeFocus = false;
            }
        }
        else if( y == THREE )
        {
            if( x == TWO)
            {
                idFocus = false;
                codeFocus = true;
            }
        }
    }

}