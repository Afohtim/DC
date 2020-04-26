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
        Coordinate coordinate = new Coordinate(e.getX(), e.getY());

        game.bulletShot(coordinate);
    }
}
