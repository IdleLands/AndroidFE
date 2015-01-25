package idle.land.app.logic.Model;

import java.util.Date;

/**
 * Represents a player event
 */
public class Event {

    Date createdAt;
    String player;
    String message;
    EventType type;

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return 31 * ((player + message).hashCode() + createdAt.hashCode() + type.ordinal()) / 27;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Event && o.hashCode() == hashCode();
    }
}
