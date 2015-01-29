package idle.land.app.logic.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Representation of the logged in player
 */
public class Player {

    String name;

    Date registrationDate;
    String professionName;
    String gender;

    ValuePair hp;
    ValuePair mp;
    ValuePair special;
    ValuePair level;
    ValuePair xp;
    ValuePair gold;

    String map;
    String oldRegion;
    String mapRegion;

    String partyName;

    List<Event> recentEvents;

    List<Archievment> achievements;
    HashMap<String, Double> _baseStats;
    List<Collectible> collectibles;

    public HashMap<String, Double> getBaseStats() {
        return _baseStats;
    }

    public Double getBaseStat(String stat)
    {
        return getBaseStats().get(stat);
    }

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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public ValuePair getXp() {
        return xp;
    }

    public ValuePair getGold() {
        return gold;
    }

    public ValuePair getHp() {
        return hp;
    }

    public ValuePair getMp() {
        return mp;
    }

    public ValuePair getSpecial() {
        return special;
    }

    public ValuePair getLevel() {
        return level;
    }

    public String getMap() {
        return map;
    }

    public String getOldRegion() {
        return oldRegion;
    }

    public String getMapRegion() {
        return mapRegion;
    }

    public String getPartyName() {
        return partyName;
    }

    public List<Collectible> getCollectibles() {
        return collectibles;
    }
}
