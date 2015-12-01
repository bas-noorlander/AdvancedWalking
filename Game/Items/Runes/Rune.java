package scripts.AdvancedWalking.Game.Items.Runes;

/**
 * @author Laniax
 */
public enum Rune {

    AIR(0),
    MIND(0),
    WATER(0),
    EARTH(0),
    FIRE(0),
    BODY(0),
    COSMIC(0),
    CHAOS(0),
    NATURE(0),
    LAW(0),
    DEATH(0),

    ASTRAL(0),
    BLOOD(0),
    SOUL(0),

    DUST(0),
    LAVA(0),
    MIST(0),
    MUD(0),
    SMOKE(0),
    STEAM(0);

    private int _id;

    Rune(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }
}
