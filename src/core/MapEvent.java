package core;

public abstract class MapEvent {
    private int x;
    private int y;
    private boolean triggered = false;

    private Renderer renderer;
    private Map map;
    private Player player;
    private Sound sound;

    public MapEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean check(int x, int y) {
        return this.x == x && this.y == y && !triggered;
    }

    public abstract void trigger(long currentMillis);

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
}
