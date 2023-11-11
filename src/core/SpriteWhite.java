package core;

public class SpriteWhite extends Sprite {
    public SpriteWhite(Point<Double> pos) {
        super(pos, 1.0, 1.0, "/resources/texture/momo_ghost.png");
        String[] ghostLists = {
                "/resources/texture/momo_ghost.png",
                "/resources/texture/ghost_chicken.png",
                "/resources/texture/ghost_nun.png",
        };

        setImage(ghostLists[(int) (Math.random() * ghostLists.length)]);
    }
}
