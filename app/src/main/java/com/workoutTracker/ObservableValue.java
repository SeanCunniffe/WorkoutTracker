package com.workoutTracker;

import java.util.Observable;

public class ObservableValue<T> extends Observable {
    T value;
    public ObservableValue(){
        super();
    }

    public ObservableValue(T value){
        super();
        this.value = value;
    }

    public void setValue(T value){
        this.value = value;
        notifyObservers();
    }

    public T getValue(){
        return value;
    }

    @Override
    public synchronized boolean hasChanged() {
        return true;
    }
}
