package org.afohtim;

import org.afohtim.assets.Textures;
import org.afohtim.misc.DuckAction;
import org.afohtim.misc.DuckChecker;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class DuckHuntGame extends JPanel {
    private ImageIcon background = new ImageIcon(Textures.BACKGROUND);
    private static final int maxDucks = DuckHuntConstants.maxGameDucks;
    private final Queue<Duck> ducks = new LinkedList<>();
    private Hunter hunter;
    GameControl control;


    DuckHuntGame(GameWindow gameWindow) {
        this.setSize(gameWindow.getSize());
        setBackground(Color.WHITE);

        setLayout(null);
        setSize(getWidth(), getHeight());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Textures.CROSS);
        setCursor(toolkit.createCustomCursor(image, new Point(), "cross"));
        addMouseListener(new ShootMouseAdapter(this));

        control = new GameControl(this);
        hunter = new Hunter(this);

        control.start();
    }

    public Hunter hunter() {
        return hunter;
    }



    public void addDuck(Duck duck) {
        synchronized (ducks) {
            ducks.add(duck);
        }
    }

    public boolean ducksNeeded() {
        return ducks.size() < maxDucks;
    }

    public void kill(Duck duck) {
        synchronized (ducks) {
            ducks.remove(duck);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(getWidth(), getHeight());
    }

    public void runForeachDuckActionIf(DuckChecker duckChecker, DuckAction action) {
        synchronized (ducks) {
            for (Duck duck : ducks) {
                if (duckChecker.check(duck)) {
                    action.run(duck);
                }
            }
        }
    }





}
