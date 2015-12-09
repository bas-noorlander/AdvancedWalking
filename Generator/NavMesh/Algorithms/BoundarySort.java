package scripts.AdvancedWalking.Generator.NavMesh.Algorithms;

import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Laniax
 */
public class BoundarySort {

    private static class SortSegment {

        private final SortSegment prefix;
        private final MeshTile head;
        private final int size;
        private final double length;

        public SortSegment(SortSegment prefix, MeshTile head) {
            this.prefix = prefix;
            this.head = head;

            if (prefix == null) {
                size = 1;
                length = 0.0;
            } else {
                size = prefix.size +1;

                int distx = head.X - prefix.head.X;
                int disty = head.Y - prefix.head.Y;
                double headLength = Math.sqrt(distx * distx + disty * disty);

                length = prefix.length + headLength;
            }
        }

    }

    public static List<MeshTile> run(List<MeshTile> tiles, Generator generator) {

        final int length = tiles.size();

        List<MeshTile> result = new ArrayList<>(length);

        final Comparator<SortSegment> comparator = new Comparator<SortSegment>() {
            @Override
            public int compare(SortSegment o1, SortSegment o2) {
                return Double.compare(o1.length / o1.size, o2.length / o2.size);
            }
        };

        PriorityQueue<SortSegment> queue = new PriorityQueue<>(length, comparator);
        queue.offer(new SortSegment(null, tiles.get(0)));

        while (!queue.isEmpty()) {

            SortSegment segment = queue.poll();

            if (segment.size == length) {
                // result found!
                while(segment != null) {
                    result.add(0, segment.head);
                    segment = segment.prefix;
                }
                break;
            }

            for (MeshTile t : tiles) {

                boolean found = false;
                for (SortSegment check = segment; check != null; check = check.prefix) {
                    if (t.equals(check.head)) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    continue;

                queue.offer(new SortSegment(segment, t));
            }
        }

        return result;
    }
}
