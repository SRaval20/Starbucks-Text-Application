package starbucks;

public class CardEntry implements ITouchEventHandler,IKeyPadObserver,IDisplayComponent {

    private CardNew cardObj;

    ITouchEventHandler nextHandler;

    public CardEntry(){ cardObj = CardNew.getInstance();}

    public CardNew getCardObj(){ return cardObj;}

    @Override
    public String display() {
        return cardObj.display();
    }

    @Override
    public void addSubComponent(IDisplayComponent c) {

    }

    @Override
    public void keyEventUpdate(int numKeys, String key) {
            System.err.println("Key: "+ key );
    }

    @Override
    public void touch(int x, int y) {
        if(nextHandler!=null)
            nextHandler.touch(x,y);

    }

    @Override
    public void setNext(ITouchEventHandler next) {
      nextHandler=next;
    }
}
