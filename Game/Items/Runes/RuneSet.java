package scripts.AdvancedWalking.Game.Items.Runes;

import scripts.AdvancedWalking.Core.Collections.Pair;

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
