package scripts.AdvancedWalking.Core.Events;

import scripts.AdvancedWalking.Core.Events.Events.InitializationError;
import scripts.AdvancedWalking.Core.Events.Events.MeshUpdated;
import scripts.AdvancedWalking.Core.Events.Events.PathFound;

/**
 * @author Laniax
 */
public enum Event {

    PATH_FOUND(PathFound.class),
    MESH_UPDATED(MeshUpdated.class),
    INITIALIZED_ERROR(InitializationError.class);

    private Class _c;
    Event(Class c) {
        this._c = c;
    }

    public Class getInterface() {
        return this._c;
    }
}
