package idle.land.app.logic.Model;

public class Collectible {

    String name;
    String map;
    String region;
    long foundAt;
    String rarity; // TODO enum?

    public String getName() {
        return name;
    }

    public String getMap() {
        return map;
    }

    public String getRegion() {
        return region;
    }

    public long getFoundAt() {
        return foundAt;
    }

    public String getRarity() {
        return rarity;
    }
}
