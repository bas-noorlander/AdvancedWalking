package scripts.AdvancedWalking.Generator;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.script.ScriptManifest;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Generator.NavMesh.Factories.PolytopeFactory;
import scripts.AdvancedWalking.Generator.NavMesh.NavMesh;
import scripts.AdvancedWalking.Generator.Tiles.Collector.Collectors.RegionCollector;
import scripts.AdvancedWalking.Generator.Tiles.Collector.ITileCollector;

@ScriptManifest(authors = { "Laniax" }, category = "Tools", name = "SampleGeneratorScript")
public class SampleGeneratorScript /*extends Script*/ {

    LogProxy log = new LogProxy("SampleGeneratorScript");

    private boolean scanningTiles = true;

    public void run() {

        while (Login.getLoginState() != Login.STATE.INGAME)
            General.sleep(500);

        // Use a different tile collector in order to gather tiles in your own way
        // This opens up the possibility to read from the RS cache etc and all you have to do is replace this collector.
        ITileCollector collector = new RegionCollector();

        while (scanningTiles){

            collector.collect();

        }

        // Create a generator, defining the shape we want to generate the navmesh with
        // currently only the polytope (polygons) is written, but it could also become a rectangular/circled/rainbow mesh if you want :-)
        Generator generator = new Generator(new PolytopeFactory(), collector.getTiles());

        // Let the generator do its work and get a fully able NavMesh object in return!
        // You can do whatever with this object, use it directly to pathfind on, or serialize it to a file and use it later..
        NavMesh mesh = generator.run();

        log.info("Mesh contains %d shapes!", mesh.getShapeCount());
    }
}
