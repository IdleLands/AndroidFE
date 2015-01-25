package idle.land.app.logic.api;

import idle.land.app.logic.Model.Player;

/**
 * Heartbeat events get sent by the heartbeat service and can be received via {@see idle.land.app.logic.BusProvider}
 */
public class HeartbeatEvent {

    public enum EventType
    {
        LOGGED_IN,
        HEARTBEAT,
        LOGGED_OUT,
        ERROR,
    }

    public EventType type;
    public Player player;

    public HeartbeatEvent(EventType type)
    {
        this.type = type;
    }
    public HeartbeatEvent(EventType type, Player player) {
        this.type = type;
        this.player = player;
    }

}
