package idle.land.app.logic.Model;

/**
 * Represents a player archivment
 */
public class Archievment {

    public String name;
    public String desc;
    public String reward;
    public ArchievmentType type;

    public ArchievmentType getType()
    {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getReward() {
        return reward;
    }

    @Override
    public int hashCode() {
        return ((name + desc + reward).hashCode() + type.hashCode()) / 3;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Archievment && o.hashCode() == hashCode());
    }
}
