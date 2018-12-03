package Baseball;

public class Bases {
	
	private boolean loaded;
	private Players playerOnBase;

	public Bases(boolean baseLoaded, Players player) {
		loaded = baseLoaded;
		playerOnBase = player;
	}
	
	public void setArgs(boolean baseLoaded, Players player) {
		loaded = baseLoaded;
		playerOnBase = player;
	}
	
	public boolean getLoaded() {
		return loaded;
	}
	
	public Players getPlayerOnBase() {
		return playerOnBase;
	}
}