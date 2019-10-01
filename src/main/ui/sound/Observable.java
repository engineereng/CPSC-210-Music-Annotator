package ui.sound;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<FilePlayerObserver> observers = new ArrayList<>();

    void notifyObservers(FilePlayer filePlayer) {
        for (FilePlayerObserver observer : observers) {
            observer.update(filePlayer);
        }
    }

    public void addObserver(FilePlayerObserver o) {
        observers.add(o);
    }
}
