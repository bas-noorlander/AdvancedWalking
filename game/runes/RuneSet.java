package scripts.advancedwalking.game.runes;


import scripts.advancedwalking.core.collections.Pair;

/**
 * @author Laniax
 */
public class RuneSet {

    private Pair<Rune, Integer>[] pairs;

    public RuneSet(Pair<Rune, Integer>... pairs) {

        this.pairs = pairs;
    }

    public Pair<Rune, Integer>[] get() {
        return this.pairs;
    }
}
