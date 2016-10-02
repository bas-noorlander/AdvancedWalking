package scripts.AdvancedWalking.Generator;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;
import scripts.AdvancedWalking.Core.IO.IOExtensions;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Generator.NavMesh.AbstractShape;
import scripts.AdvancedWalking.Generator.NavMesh.Factories.PolytopeFactory;
import scripts.AdvancedWalking.Generator.NavMesh.NavMesh;
import scripts.AdvancedWalking.Generator.Tiles.Collector.Collectors.RegionCollector;
import scripts.AdvancedWalking.Generator.Tiles.Collector.ITileCollector;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;
import scripts.AdvancedWalking.Network.CommonFiles;

import java.awt.*;

@ScriptManifest(authors = {"Laniax"}, category = "Tools", name = "SampleGeneratorScript")
public class SampleGeneratorScript extends Script implements Painting, MouseActions {

    LogProxy log = new LogProxy("SampleGeneratorScript");

    private boolean scanningTiles = true;

    ITileCollector collector;

    NavMesh mesh = null;

    public void run() {

        while (Login.getLoginState() != Login.STATE.INGAME)
            General.sleep(500);

        // Use a different tile collector in order to gather tiles in your own way
        // This opens up the possibility to read from the RS cache etc and all you have to do is replace this collector.
        collector = new RegionCollector();

        while (scanningTiles) {

            collector.collect();
        }

        log.info("Collected %d tiles!", collector.getTiles().size());

        // Create a generator, defining the shape we want to generate the navmesh with
        // currently only the polytope (polygons) is written, but it could also become a rectangular/circled/rainbow mesh if you want :-)
        Generator generator = new Generator(new PolytopeFactory(), collector.getTiles());

        log.info("Initialized a polytope mesh generator.");

        // Let the generator do its work and get a fully able NavMesh object in return!
        mesh = generator.run();

        // You can do whatever with the mesh object, use it directly to pathfind on, or serialize it to a file and use it later..
        log.info("Mesh contains %d shapes!", mesh.getShapeCount());
        if (IOExtensions.Serialize(mesh, CommonFiles.localMeshFile)) {
            log.info("Mesh was successfully serialized! Path to file: %s", CommonFiles.localMeshFile.toPath());
        }

//        log.info("meshtile X:%d Y:%d is %d tiles away from  X:%d Y:%d", 2961, 3348, new MeshTile(new RSTile(2965, 3352, 0)).distanceTo(new RSTile(2961, 3348)), 2965, 3352);

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

        if (mesh != null) {

            for (AbstractShape shape : mesh.getAllShapes()) {

                if (!shape.contains(Player.getPosition()))
                    continue;

                g.setColor(blackTransparent);
                for(MeshTile t: shape.getAllTiles()) {
                    Point tilePoint = Projection.tileToScreen(t,0);

                    if (Projection.isInViewport(tilePoint)) {
                        Polygon drawPoly = Projection.getTileBoundsPoly(t, 0);
                        g.fillPolygon(drawPoly);
                    }
                }

                g.setColor(Color.RED);

//                Point previous = null, first = null;
//                Point previousMinimap = null, firstMinimap = null;

                int i = 0;
                for (MeshTile t : shape.getBoundaryTiles()) {

                    Point tilePoint = Projection.tileToScreen(t,0);

                    if (Projection.isInViewport(tilePoint)) {
                        Polygon drawPoly = Projection.getTileBoundsPoly(t, 0);
                        g.fillPolygon(drawPoly);
                        g.setColor(Color.WHITE);
                        g.drawString(i+"", tilePoint.x, tilePoint.y);
                    }
                    g.setColor(Color.RED);

                    Point minimap = Projection.tileToMinimap(t);
                    if (minimap != null && Projection.isInMinimap(minimap)) {
                        g.fillOval(minimap.x, minimap.y, 3,3);
                    }
                    i++;

//                    if (previous != null && previousMinimap != null) {
//                        if (Projection.isInViewport(tilePoint))
//                            g.drawLine(previous.x, previous.y, tilePoint.x, tilePoint.y);
//
//                        if (Projection.isInMinimap(minimap))
//                            g.drawLine(previousMinimap.x, previousMinimap.y, minimap.x, minimap.y);
//                    }
//                    if (first == null) {
//                        first = tilePoint;
//                        firstMinimap = minimap;
//                    }
//
//                    previous = tilePoint;
//                    previousMinimap = minimap;
                }
//
//                if (Projection.isInViewport(previous))
//                    g.drawLine(previous.x, previous.y, first.x, first.y);
//
//                if (Projection.isInMinimap(previousMinimap))
//                    g.drawLine(previousMinimap.x, previousMinimap.y, firstMinimap.x, firstMinimap.y);

                break;
            }
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
