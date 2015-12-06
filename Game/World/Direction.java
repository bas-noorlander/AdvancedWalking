package scripts.AdvancedWalking.Game.World;

import java.io.Serializable;

/**
 * @author Laniax
 */
public enum Direction implements Serializable {
	NORTH(0, 1),
	NORTHEAST(1, 1),
	EAST(1, 0),
	SOUTHEAST(1, -1),
	SOUTH(0, -1),
	SOUTHWEST(-1, -1),
	WEST(-1, 0),
	NORTHWEST(-1, 1);
	
	private int offsetX;
	private int offsetY;

    private static Direction[] cardinals = new Direction[]{NORTH, EAST, SOUTH, WEST};
    private static Direction[] ordinals = new Direction[]{NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};

	public int getOffsetX() {
		return this.offsetX;
	}
	public int getOffsetY() {
		return this.offsetY;
	}

	private Direction(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public static Direction[] getAllCardinal() {
		return cardinals;
	}
	
	public static Direction[] getAllOrdinal() {
		return ordinals;
	}
	
}