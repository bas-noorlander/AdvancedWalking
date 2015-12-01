package scripts.AdvancedWalking.Game.World;

import org.tribot.api2007.types.RSTile;

/**
 * Collision data for {@link RSTile}s.
 * @author Laniax
 */
public class CollisionFlags {

	public static final int OPEN = 0;
	public static final int CLOSED = 0xFFFFFF;
	public static final int UNINITIALIZED = 0x1000000;
	public static final int OCCUPIED = 0x100;
	public static final int SOLID = 0x20000;
	public static final int BLOCKED = 0x200000;

	public static final int NORTH = 0x2; //fences etc
	public static final int EAST = 0x8;
	public static final int SOUTH = 0x20;
	public static final int WEST = 0x80;

	public static final int NORTHEAST = 0x4;
	public static final int SOUTHEAST = 0x10;
	public static final int SOUTHWEST = 0x40;
	public static final int NORTHWEST = 0x1;

	public static final int EAST_NORTH = EAST | NORTH;
	public static final int EAST_SOUTH = EAST | SOUTH;
	public static final int WEST_SOUTH = WEST | SOUTH;
	public static final int WEST_NORTH = WEST | NORTH;

	public static final int BLOCKED_NORTH = 0x400; // blocked == walls
	public static final int BLOCKED_EAST = 0x1000;
	public static final int BLOCKED_SOUTH = 0x4000;
	public static final int BLOCKED_WEST = 0x10000;

	public static final int BLOCKED_NORTHEAST = 0x800;
	public static final int BLOCKED_SOUTHEAST = 0x2000;
	public static final int BLOCKED_NORTHWEST = 0x200;
	public static final int BLOCKED_SOUTHWEST = 0x8000;

	public static final int BLOCKED_EAST_NORTH = BLOCKED_EAST | BLOCKED_NORTH;
	public static final int BLOCKED_EAST_SOUTH = BLOCKED_EAST | BLOCKED_SOUTH;
	public static final int BLOCKED_WEST_SOUTH = BLOCKED_WEST | BLOCKED_SOUTH;
	public static final int BLOCKED_WEST_NORTH = BLOCKED_WEST | BLOCKED_NORTH;

}