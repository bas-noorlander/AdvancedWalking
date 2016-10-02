package scripts.AdvancedWalking.Game.Items.Runes;

/**
 * @author Laniax
 */
public enum Rune {

    AIR(556),
    MIND(558),
    WATER(555),
    EARTH(557),
    FIRE(554),
    BODY(559),
    COSMIC(564),
    CHAOS(562),
    NATURE(561),
    LAW(563),
    DEATH(560),

    ASTRAL(9075),
    BLOOD(565),
    SOUL(566),

    DUST(4696),
    LAVA(4699),
    MIST(4695),
    MUD(4698),
    SMOKE(4697),
    STEAM(4694);

    private int _id;

    Rune(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }
}
