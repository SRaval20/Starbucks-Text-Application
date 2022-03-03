/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks ;

/** Pin Auth Observer Interface */
public interface IPinAuthObserver
{
    void validPin();
    void invalidPin();
}
