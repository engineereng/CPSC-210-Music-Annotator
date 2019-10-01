package ui.sound;

public class CantPlayFileException extends Exception {
    public CantPlayFileException(String message) {
        super(message);
    }
}
