package Baseball;

public class Pitcher extends Players {
	
	private double era;

	public Pitcher(String name, double era) {
		super(name);
		this.era = era;
	}
	
	public double getEra() {
		return era;
	}

}
