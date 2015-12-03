package scripts.AdvancedWalking.Generator;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Projection;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Generator.NavMesh.Factories.PolytopeFactory;
import scripts.AdvancedWalking.Generator.NavMesh.NavMesh;
import scripts.AdvancedWalking.Generator.Tiles.Collector.Collectors.RegionCollector;
import scripts.AdvancedWalking.Generator.Tiles.Collector.ITileCollector;

import java.awt.*;

@ScriptManifest(authors = {"Laniax"}, category = "Tools", name = "SampleGeneratorScript")
public class SampleGeneratorScript extends Script implements Painting, MouseActions {

    LogProxy log = new LogProxy("SampleGeneratorScript");

    private boolean scanningTiles = true;

    ITileCollector collector;

    NavMesh mesh;

    public void run() {

        while (Login.getLoginState() != Login.STATE.INGAME)
            General.sleep(500);

        // Use a different tile collector in order to gather tiles in your own way
        // This opens up the possibility to read from the RS cache etc and all you have to do is replace this collector.
        collector = new RegionCollector();

        while (scanningTiles) {

            collector.collect();
        }

        // Create a generator, defining the shape we want to generate the navmesh with
        // currently only the polytope (polygons) is written, but it could also become a rectangular/circled/rainbow mesh if you want :-)
        Generator generator = new Generator(new PolytopeFactory(), collector.getTiles());

        // Let the generator do its work and get a fully able NavMesh object in return!
        // You can do whatever with this object, use it directly to pathfind on, or serialize it to a file and use it later..
        mesh = generator.run();

        log.info("Mesh contains %d shapes!", mesh.getShapeCount());

        while(true) {
            // prevent script from stopping
        }
    }

    Color blackTransparent = new Color(0, 0, 0, 100);

    Rectangle stopButton = new Rectangle(380, 300, 130, 30);

    @Override
    public void onPaint(Graphics g) {

        if (scanningTiles) {
            g.setColor(blackTransparent);
            g.fillRect(stopButton.x, stopButton.y, stopButton.width, stopButton.height);
            g.setColor(Color.BLACK);
            g.drawRect(stopButton.x, stopButton.y, stopButton.width, stopButton.height);
            g.setColor(Color.WHITE);
            g.drawString("Generate", (stopButton.width / 3) + stopButton.x, (stopButton.height / 2) + stopButton.y + 5);
            g.drawString("Number of tiles: " + collector.getTiles().size(), stopButton.x, stopButton.y - 10);
        }
    }

    @Override
    public void mouseClicked(Point point, int button, boolean isBot) {

        if (stopButton.contains(point.x, point.y)) {
            scanningTiles = false;
        }

    }

    public void mouseMoved(Point point, boolean b) {
    }

    public void mouseDragged(Point point, int i, boolean b) {
    }

    public void mouseReleased(Point point, int i, boolean b) {
    }
}
