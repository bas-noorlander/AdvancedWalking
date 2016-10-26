package scripts.advancedwalking.network;

import org.tribot.util.Util;

import java.io.File;

/**
 * @author Laniax
 */
public class CommonFiles {

    public static final File local_root_dir = new File(Util.getWorkingDirectory().getAbsolutePath() + "\\advancedwalking");
    public static final File localMeshFile = new File(local_root_dir.getAbsolutePath() + "\\mesh.dat");
    public static final File localHashFile = new File(local_root_dir.getAbsolutePath() + "\\SHA1.txt");
    public static final File localGraphFile = new File(local_root_dir.getAbsolutePath() + "\\graph.dat");

    public static final String remoteHashURL = "https://raw.githubusercontent.com/Laniax/laniax.github.io/master/NavMesh/SHA1.txt";
    public static final String remoteMeshURL = "https://raw.githubusercontent.com/Laniax/laniax.github.io/master/NavMesh/mesh.dat";

}
