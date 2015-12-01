package scripts.AdvancedWalking.Core.Events;

import scripts.AdvancedWalking.Core.Events.Events.IOnInitializationError;
import scripts.AdvancedWalking.Core.Events.Events.IOnMeshUpdated;
import scripts.AdvancedWalking.Core.Events.Events.IOnPathFound;

/**
 * @author Laniax
 */
public enum Event {

    PATH_FOUND(IOnPathFound.class),
    MESH_UPDATED(IOnMeshUpdated.class),
    INITIALIZED_ERROR(IOnInitializationError.class);

    private Class _c;
    Event(Class c) {
        this._c = c;
    }

    public Class getInterface() {
        return this._c;
    }
}
