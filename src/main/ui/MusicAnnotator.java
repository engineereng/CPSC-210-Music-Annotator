package ui;

import model.MusicAnnotatorModel;
import model.observers.MusicAnnotatorObserver;
import ui.sound.CantPlayFileException;
import ui.sound.FilePlayer;
import ui.sound.FilePlayerObserver;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MusicAnnotator extends JFrame implements MusicAnnotatorObserver {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private String musicName = "";
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenuItem openWaveFileMenuItem;
    private JMenuItem songNameMenuItem;
    private PlayPauseButton playButton;

    private SourceOverlay sourceOverlay;
    private MusicAnnotatorModel model;
    private WaveformRenderer waveformRenderer;
    private Drawing currentDrawing;

    // TODO implement filePlayer
    private FilePlayer filePlayer;

    public MusicAnnotator() {
        // inspired by SimpleDrawingPlayer
        super("Music Annotator");
        initializeFields();
        initializeGraphics();
        initializeInteraction();
    }

    public Drawing getCurrentDrawing() {
        return currentDrawing;
    }

    @Override
    public void update(MusicAnnotatorModel model, String message) {
        //TODO refactor
    }

    // MODIFIES: this
    // EFFECTS: initializes values of fields
    private void initializeFields() {
        model = new MusicAnnotatorModel();
        model.addObserver(this);
        musicName = null;
        waveformRenderer = null;
        filePlayer = null;
    }

    private void initializeWaveform() {
        File file = promptForMusicFile();
        if (file != null) {
            try {
                model.loadWaveformFile(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,"The file was not found!");
            } catch (UnsupportedAudioFileException e) {
                JOptionPane.showMessageDialog(this,"This file's audio file is not supported!");
            }
            initializeFilePlayer(file);
            playButton.setFileChanged();
            waveformRenderer = new WaveformRenderer(model.getWaveform(), currentDrawing);
            repaint();
        }
    }

    private void initializeFilePlayer(File file) {
        try {
            filePlayer = new FilePlayer(file);
        } catch (CantPlayFileException e) {
            JOptionPane.showMessageDialog(this,"This program can only play 8bit and 16bit music.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"There was an error trying to play the file.");
        }
        if (filePlayer.isCanPlay()) {
            playButton.setEnabled(true);
        }
    }

    // EFFECTS: prompts the user for the name of the music file and returns it
    //          returns null if the user didn'timer choose anything
    private File promptForMusicFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("Opening: " + file.getName() + ".\n");
            return file;
        } else {
            System.out.println("Open command cancelled by user.\n");
            return null;
        }
    }

    private void promptForMusicName() {
        String input = "";
        input = JOptionPane.showInputDialog(this,"What's the name of this song?");
        if (input == null) {
            //do nothing
            return;
        }
        if ("".equals(input)) {
            JOptionPane.showMessageDialog(this, "Names cannot be blank.");
        } else {
            musicName = input;
            System.out.println("The song's name is " + musicName);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this DrawingEditor will operate
    private void initializeGraphics() {
        setLayout(null);
        setMinimumSize(new Dimension(WIDTH,HEIGHT));
        refreshUiElements();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: draws all Ui elements
    private void refreshUiElements() {         //TODO fix this after the demo
        addPlayButton();
        initializeMenu();
        addNewDrawing();
    }

    // MODIFIES: this
    // EFFECTS: declares and instantiates a Drawing (newDrawing) and adds it to drawings
    private void addNewDrawing() {
        Drawing newDrawing = new Drawing();
        newDrawing.setLocation(10, getHeight() - 400 - 200);
        newDrawing.setSize(getWidth() * 2 / 3,400);
        currentDrawing = newDrawing;
        add(newDrawing);
    }

    private void addPlayButton() {
        playButton = new PlayPauseButton("Play");
        playButton.setBounds(WIDTH - 100, 100, 100, 40);
        playButton.setEnabled(false);
        add(playButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes the fileMenu
    private void initializeMenu() {
        JMenuBar mb = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        openWaveFileMenuItem = new JMenuItem("Open wave file");
        fileMenu.add(openWaveFileMenuItem);
        songNameMenuItem = new JMenuItem("Song name...");
        editMenu.add(songNameMenuItem);
        mb.add(fileMenu);
        mb.add(editMenu);
        setJMenuBar(mb);
    }

    private void initializeMenuInteraction() {
        openWaveFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeWaveform();
            }
        });
        songNameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptForMusicName();
            }
        });
    }

    //TODO stub
    // MODIFIES: this
    // EFFECTS: initializes interaction (e.g. mouse clicks, typing)
    private void initializeInteraction() {
        initializeMenuInteraction();
        //MouseListener ml = new MouseListener();
        //addMouseListener(ml);
        //addMouseMotionListener(ml);
    }

    //EFFECTS: Shows the entire jokes overlay.
    public void showLabels() {
        sourceOverlay.setVisible(true);
    }


    public static void main(String[] args) {
        new MusicAnnotator();
    }

    class PlayPauseButton extends JButton implements FilePlayerObserver {
        private Timer timer;
        private DrawingPlayer drawingPlayer;
        private boolean fileChanged = true;

        public void setFileChanged() {
            fileChanged = true;
        }

        private void initializeDrawingPlayer() {
            if (filePlayer != null) {
                double delayInSeconds = 1.0 / waveformRenderer.getPixelsPerSecond();
                timer = new Timer((int) (delayInSeconds * 1000), null);
                drawingPlayer = new DrawingPlayer(getCurrentDrawing(), timer);
                timer.addActionListener(drawingPlayer);
                timer.setInitialDelay(0);
                timer.start();
            }
        }

        @Override
        public void update(FilePlayer filePlayer) {
            if (filePlayer.getClip().getMicrosecondLength() == filePlayer.getClip().getMicrosecondPosition()) {
                setText("Play");
                resetPlayers();
            }
        }

        public PlayPauseButton(String text) {
            super(text);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (filePlayer != null) {
                        if (drawingPlayer == null || fileChanged) {
                            initializeDrawingPlayer();
                            fileChanged = false;
                        }
                        filePlayer.addObserver(PlayPauseButton.this);
                        if (filePlayer.isPlaying()) {
                            setText("Play");
                        } else {
                            setText("Pause");
                        }
                        togglePlay();
                    }
                }
            });
        }

        private void resetPlayers() {
            if (filePlayer != null) {
                filePlayer.reset();
                timer.stop();
            }
            if (drawingPlayer != null) {
                drawingPlayer.setPlayingColumn(0);
            }
        }

        private void togglePlay() {
            if (filePlayer != null) {
                if (filePlayer.isPlaying()) {
                    filePlayer.stop();
                    if (timer != null) {
                        timer.stop();
                    }
                } else {
                    filePlayer.play();
                    if (timer != null) {
                        timer.start();
                    }
                }
            }
        }
    }
}
