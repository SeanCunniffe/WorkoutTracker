package com.workoutTracker;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class ObservableList<T> extends ArrayList<T> implements Parcelable {

    private ListsObservable ob = new ListsObservable();

    public ObservableList(){

    }

    protected ObservableList(Parcel in) {
        ob = in.readParcelable(ListsObservable.class.getClassLoader());
        if(ob == null){
            Log.i("Exercise","ob is null");
        }
    }

    public static final Creator<ObservableList> CREATOR = new Creator<ObservableList>() {
        @Override
        public ObservableList createFromParcel(Parcel in) {
            ObservableList list = new ObservableList(in);
            return list;
        }

        @Override
        public ObservableList[] newArray(int size) {
            return new ObservableList[size];
        }
    };


    @Override
    public boolean add(T t) {
        boolean b = super.add(t);
        ob.notifyObservers();
        return b;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        boolean bool = super.remove(o);
        ob.notifyObservers();
        return bool;
    }

    public Observable getObservable(){
        return ob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(ob, i);
    }

    public static class ListsObservable extends Observable implements Parcelable {
        public ListsObservable(){
            super();
        }

        protected ListsObservable(Parcel in) {
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ListsObservable> CREATOR = new Creator<ListsObservable>() {
            @Override
            public ListsObservable createFromParcel(Parcel in) {
                return new ListsObservable(in);
            }

            @Override
            public ListsObservable[] newArray(int size) {
                return new ListsObservable[size];
            }
        };

        @Override
        public synchronized boolean hasChanged() {
            return true;
        }
    }
}

