package scripts.AdvancedWalking.Network;

import java.io.File;

/**
 * @author Laniax
 */
public class CommonFiles {

    public static final File localMeshFile = new File(System.getenv("APPDATA") + "\\.tribot\\advancedwalking\\mesh.dat");
    public static final File localHashFile = new File(System.getenv("APPDATA") + "\\.tribot\\scripts\\scripts\\laniax.github.io\\NavMesh\\SHA1.txt");
    public static final File localGraphFile = new File(System.getenv("APPDATA") + "\\.tribot\\scripts\\scripts\\laniax.github.io\\NavMesh\\graph.dat");

    public static final String remoteHashURL = "https://raw.githubusercontent.com/Laniax/laniax.github.io/master/NavMesh/SHA1.txt";
    public static final String remoteMeshURL = "https://raw.githubusercontent.com/Laniax/laniax.github.io/master/NavMesh/mesh.dat";

}
