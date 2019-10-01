package model.observers;

import model.MusicAnnotatorModel;

public interface MusicAnnotatorObserver {
    void update(MusicAnnotatorModel model, String message);
}
