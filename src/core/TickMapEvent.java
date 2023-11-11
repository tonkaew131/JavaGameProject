package core;

public class TickMapEvent extends Tick {
    private Map map;

    public TickMapEvent(Map map, Renderer renderer, Player player, Sound sound) {
        super(renderer, player);
        this.map = map;

        for (MapEvent event : map.getMapEvents()) {
            event.setRenderer(renderer);
            event.setMap(map);
            event.setPlayer(player);
            event.setSound(sound);
        }
    }

    @Override
    public void run() {
        int playerX = (int) Math.floor(player.getPosX());
        int playerY = (int) Math.floor(player.getPosY());

        for (MapEvent event : map.getMapEvents()) {
            if (event.check(playerX, playerY)) {
                event.trigger(getCurrentMillis());
                event.setTriggered(true);
            }
        }
    }
}
