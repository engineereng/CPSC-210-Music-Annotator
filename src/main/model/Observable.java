package model;

import model.observers.MusicAnnotatorObserver;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<MusicAnnotatorObserver> observers = new ArrayList<>();

    void notifyObservers(MusicAnnotatorModel model, String message) {
        for (MusicAnnotatorObserver observer : observers) {
            observer.update(model, message);
        }
    }

    public void addObserver(MusicAnnotatorObserver o) {
        observers.add(o);
    }
}
