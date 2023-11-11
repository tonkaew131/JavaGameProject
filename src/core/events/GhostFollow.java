package core.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import core.MapEvent;
import core.Point;
import core.Sprite;
import core.SpriteWhite;
import core.path.Node;
import core.path.PathFind;

public class GhostFollow extends MapEvent {
    private double ghostX;
    private double ghostY;
    private long duration = 125;
    private Timer timer;
    private Sprite ghost;

    public GhostFollow(int x, int y) {
        super(x, y);
    }

    @Override
    public void trigger(long currentMillis) {
        System.out.println("[MapEvent]: Ghost follow!");
        ghost = new SpriteWhite(new Point<Double>(ghostX, ghostY));
        getMap().addSprite(ghost);
        getSound().playJumpScareSound();

        int startCol = (int) ghostX;
        int startRow = (int) ghostY;

        PathFind pf = new PathFind(getMap());
        pf.setNode(startCol, startRow, (int) getPlayer().getPosX(), (int) getPlayer().getPosY());

        if (pf.search() == true) {
            ArrayList<Node> p = pf.getPathList();
            timer = new Timer((int) duration, new TimerListener(p));
            timer.start();
        }
    }

    class TimerListener implements ActionListener {
        ArrayList<Node> p;
        int i = 0;

        TimerListener(ArrayList<Node> p) {
            this.p = p;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (i >= p.size()) {
                getMap().removeSprite(ghost);
                timer.stop();
                return;
            }

            int x = p.get(i).col;
            int y = p.get(i).row;
            // System.out.println(x + " " + y);
            ghost.setPos(new Point<Double>(x + 0.5, y + 0.5));

            i++;
        }
    }

    public void setGhostX(double ghostX) {
        this.ghostX = ghostX;
    }

    public void setGhostY(double ghostY) {
        this.ghostY = ghostY;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
