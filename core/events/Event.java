package scripts.advancedwalking.core.events;

import scripts.advancedwalking.core.events.events.*;

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
