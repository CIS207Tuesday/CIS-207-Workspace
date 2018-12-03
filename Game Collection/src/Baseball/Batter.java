package Baseball;

public class Batter extends Players {
	
	private double battingAvg;

	public Batter(String name, double battingAvg) {
		super(name);
		this.battingAvg = battingAvg;
	}
	
	public double getBattingAvg() {
		return battingAvg;
	}

}
