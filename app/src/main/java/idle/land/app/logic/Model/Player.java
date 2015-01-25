package idle.land.app.logic.Model;

import java.util.List;

/**
 * Representation of the logged in player
 */
public class Player {

    String name;
    String professionName;
    String gender;

    List<Event> recentEvents;
    List<Archievment> achievements;

    public List<Event> getRecentEvents() {
        return recentEvents;
    }

    public List<Archievment> getAchievements() {
        return achievements;
    }

    public String getName() {
        return name;
    }

    public String getProfessionName() {
        return professionName;
    }

    public String getGender() {
        return gender;
    }
}
