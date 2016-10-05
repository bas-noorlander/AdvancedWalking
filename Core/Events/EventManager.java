package scripts.AdvancedWalking.Core.Events;

import scripts.AdvancedWalking.Core.Events.Events.InitializationError;
import scripts.AdvancedWalking.Core.Events.Events.MeshUpdated;
import scripts.AdvancedWalking.Core.Events.Events.PathFound;
import scripts.AdvancedWalking.Game.Path.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Laniax
 */
public final class EventManager {

    private static List<Object> eventListeners = Collections.synchronizedList(new ArrayList<>());

    /**
     * Prevent instantiation
     */
    private EventManager() {}

    public static void listen(Object listener) {

        eventListeners.add(listener);
    }

    public static void triggerPathFound(Path path) {
        for (Object listener : getListenersByEvent(Event.PATH_FOUND)) {
            if (listener instanceof PathFound) {
                ((PathFound)listener).onPathFound(path);
            }
        }
    }

    public static void triggerInitializationError() {
        for (Object listener : getListenersByEvent(Event.INITIALIZED_ERROR)) {
            if (listener instanceof InitializationError) {
                ((InitializationError)listener).onInitilizationError();
            }
        }
    }

    public static void triggerMeshUpdated() {
        for (Object listener : getListenersByEvent(Event.MESH_UPDATED)) {
            if (listener instanceof MeshUpdated) {
                ((MeshUpdated)listener).onMeshUpdated();
            }
        }
    }

    public static ArrayList<Object> getListenersByEvent(Event event) {

        ArrayList<Object> result = new ArrayList<>();

        for (Object eventListener : eventListeners) {

            if (event.getInterface().isAssignableFrom(eventListener.getClass()) ) {
                result.add(eventListener);
            }

        }
        return result;
    }
}
