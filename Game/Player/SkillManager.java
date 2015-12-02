package scripts.AdvancedWalking.Game.Player;

import org.tribot.api2007.Skills;
import scripts.AdvancedWalking.Core.Dynamic.Bag;

/**
 * Small class that will cache the level of a skill the first time it is requested.
 * @author Laniax
 */
public class SkillManager {

    private static Bag _bag = new Bag();

    public static int getLevel(Skills.SKILLS skill) {

        Object result;
        if ((result = _bag.get(skill.name())) != null) {
            return (int) result;
        } else {
            int level = Skills.getActualLevel(skill);
            _bag.add(skill.name(), level);
            return level;
        }
    }
}
