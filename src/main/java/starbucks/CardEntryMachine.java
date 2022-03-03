package starbucks;

public class CardEntryMachine implements IKeyPadObserver

{

  private CardNew card;

  public  CardEntryMachine(CardNew cardObj)
  {
  this.card=cardObj;
  }

  public void keyEventUpdate(int c, String key)
  {
    card.keyPressed(key);
  }
}
