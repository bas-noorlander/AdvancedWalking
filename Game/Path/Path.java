package scripts.AdvancedWalking.Game.Path;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Laniax
 */
public class Path {

    private List<PathStep> _path = new ArrayList<>();

    public void append(PathStep step) {
        _path.add(step);
    }

    public PathStep getLast() {
        return _path.get(_path.size() - 1);
    }

    public int getLength() {
        return _path.size();
    }

    public PathStep getStep(int i) {
        return _path.get(i);
    }

    public List<PathStep> getAll() {
        return _path;
    }
}
