## Starbucks Mobile App (Text UI) Project

# 4-Pin vs 6-Pin Authentication
 
This feature is implemented using State and observer design patterns.
 
## State Design Pattern
 
**Intent:** Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.
 
- We've implemented his using PinEntryMachine class and different states like NoPinDigits, OnePinDigit, TwoPinDigits, ThreePinDigits, FourPinDigits, FivePinDigits, SixPinDigits.

-  Athentication is also done in respective classes like fourPinState and sixPinState.

-  After that, it notifies its observers about authentication status using observer pattern.
 

## Observer Design Pattern
 
**Intent:** Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
 
- PinEntryMachine acts as IPinAuthSubject, whereas Device, Keypad and Passcode act as observers. Thus, whenever there is an update on authentication, All observers are notified and they update their behavior/values accordingly.
 
- If the pin is valid, The device makes the user authenticated and navigates to MyCards-Main and if pin is invalid, Keypad clears pinCount, Passcode clears its count and displays invalid pin message on the screen. The device make sure the user to remain in authenticated state.

![4-Pin and 6-Pin Authentication](https://user-images.githubusercontent.com/89321718/139566018-a6b4bac3-71d0-452d-87de-2a4ff21195f6.png)


 
# Screen Navigation and Layout Management
 
I've implemented this using Singleton, Chain of Responsibility, Strategy, Command, Composite patterns.
 
## Singleton
 
**Intent:** Ensure a class only has one instance, and provide a global point of access to it.
 
- Only one global class insance has been declared and used for classes like AppController and Device class

## Chain of Responsibility
 
**Intent:** Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.
 
- Implemented this pattern and handled the request by corresponding class for touch command. i.e. used AppController, Frame, Screen for touch function. 
 
 
## Strategy
 
**Intent:** Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.
 
- Used this pattern using Strategyhandler class to change the strategy used for alignment.
- We have used portrait as well as landscape strategies and have inner classes for both of them in Frame class.  


## Command
 
**Intent:** Encapsulate a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.
 
- We have used this pattern for switching in between differen Screens and used different commands for that also.
 
 
## Composite

**Intent:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.
 
- In next/previous step, we have used composite pattern using IScreen objects. 
 

![Screen Layout Management](https://user-images.githubusercontent.com/89321718/139566022-ee1adcfa-10ae-42a5-a6d2-1a9a621dc055.png)



# Adding a New Card
 
New Card implementation uses Singletion, Observer and Command patterns.  

- Singleton pattern is used in card class to ensure that we are storing only one card instance not multiple cards at a time so it's the perfect impementation of singeton pattern.
 
- If user enters the next command, it checks the new card conditions and based on those conditions; user is navigated to the screen. 
- Previous command navigates the users to Settings Screen and Card details are cleared in this step.
 
- Users can enter the new card number using the keypad class and afterthat, Keypad notifies its observers which are AddCardEntryMachine and CardNumber about KeyEventUpdate.

- If Card activation is failed, then user entered card details are cleared and make user to stay on the AddCard page. 

![Adding a New Card](https://user-images.githubusercontent.com/89316778/139557026-b8295d91-e264-444b-a1ae-8bf124f14c27.png)




 # Object collaboration when we Add a New Card
 
 - Collaboration in between objects during the card generation is given below.

 
 ![Collaboration of Adding a New Card](https://user-images.githubusercontent.com/89321718/139566040-0a6cf6d9-e79e-4467-84be-5ba8d1ac4be7.png)

 
 
 
 # Code Refactoring & Clean Code
 
 The code refactoring is the process of restructuring computer code without changing or adding to its external behavior and functionality.
 
 Developer should maintain clean code during the whole time.

 
## Note

- Photos of all diagrams are included in **UMLdiagrams** folder in **starbucks** folder.


