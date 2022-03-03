/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/**
 * Add New Card Screen
 */
public class AddCard extends Screen
{
    private KeyPad keyObj;
    private Spacer spaceObj;
    private CardEntry cardEntryObj;
    private CardEntryMachine cardMachineObj;

    public AddCard()
    {
        keyObj = new KeyPad();
        spaceObj = new Spacer();
        cardEntryObj =new CardEntry();
        cardMachineObj =new CardEntryMachine(cardEntryObj.getCardObj());

        this.addSubComponent(cardEntryObj);
        this.addSubComponent(spaceObj);
        this.addSubComponent(keyObj);
            ((IKeyPadSubject) keyObj).attach(cardEntryObj);
            ((IKeyPadSubject) keyObj).attach(cardMachineObj);
            ((ITouchEventHandler) keyObj).setNext(cardEntryObj.getCardObj());

    }

   /**
     * Frame Next Screen
     */
    public void next() {
        CardNew card = cardEntryObj.getCardObj();
        card.addNewCard();
        AppController app = AppController.getInstance();
        app.setScreen(AppController.SCREENS.MY_CARDS_MAIN);
    }
    public void prev() {
        cardEntryObj.getCardObj().clearCard();
        AppController appController = AppController.getInstance();
        appController.setScreen(AppController.SCREENS.SETTINGS);
    }


}
