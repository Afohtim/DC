package org.afohtim;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShootMouseAdapter extends MouseAdapter {
    private DuckHuntGame game;

    ShootMouseAdapter(DuckHuntGame game) {
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        game.runForeachDuckActionIf(
                (duck -> duck.isShot(x, y)),
                (Duck::kill)
        );
    }
}
