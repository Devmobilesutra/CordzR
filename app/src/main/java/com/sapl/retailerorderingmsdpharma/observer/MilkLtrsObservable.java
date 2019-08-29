package com.sapl.retailerorderingmsdpharma.observer;

import java.util.ArrayList;
import java.util.List;


public class MilkLtrsObservable implements Observable<MilkLtrsObserver> {

    static List<MilkLtrsObserver> milkLtrsObservers = new ArrayList<>();

    @Override
    public void register(MilkLtrsObserver observer) {
        if (!milkLtrsObservers.contains(observer)) {
            milkLtrsObservers.add(observer);
        }
    }

    @Override
    public void unregister(MilkLtrsObserver observer) {
        if (milkLtrsObservers.contains(observer)) {
            milkLtrsObservers.remove(observer);
        }
    }

    public void notifyMilkLtrs(String cowLtrs, String buffellowLtrs) {
        for (MilkLtrsObserver observer : milkLtrsObservers) {
            observer.setMilkLtrs(cowLtrs, buffellowLtrs);
        }
    }

    public void notifysetValues() {
        for (MilkLtrsObserver observer : milkLtrsObservers) {
            observer.setValues();
        }
    }
}
