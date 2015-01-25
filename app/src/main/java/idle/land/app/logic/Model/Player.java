package idle.land.app.logic.Model;

import java.util.List;

/**
 * Representation of the logged in player
 */
public class Player {

    String name;
    List<Event> recentEvents;

    public List<Event> getRecentEvents() {
        return recentEvents;
    }
}
