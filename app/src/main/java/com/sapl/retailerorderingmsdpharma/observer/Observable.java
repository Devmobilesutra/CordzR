package com.sapl.retailerorderingmsdpharma.observer;

/**
 * Created by JARVIS on 19-Feb-18.
 */

public interface Observable <T>  {
    void register(T observer);

    void unregister(T observer);

}
