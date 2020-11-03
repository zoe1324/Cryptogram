package uk.co.team3.cryptogram;

public class Player {

	private String username;
	private double correctGuesses;
	private double totalGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;

	Player(String username) {
		this.username = username;
		correctGuesses = 0;
		totalGuesses = 0;
		cryptogramsPlayed = 0;
		cryptogramsCompleted = 0;
	}
	
	Player(String username, int completeCryptograms) {
		this.username = username;
		correctGuesses = 0;
		totalGuesses = 0;
		cryptogramsPlayed = 0;
		cryptogramsCompleted = completeCryptograms;
	}

	Player(String username, double correctGuesses, double totalGuesses, int cryptogramsPlayed,
			int cryptogramsCompleted) {
		this.username = username;
		this.correctGuesses = correctGuesses;
		this.totalGuesses = totalGuesses;
		this.cryptogramsPlayed = cryptogramsPlayed;
		this.cryptogramsCompleted = cryptogramsCompleted;
	}

	void incrementCorrectGuesses() {
		correctGuesses++;
	}

	void incrementTotalGuesses() {
		totalGuesses++;
	}

	void decrementTotalGuesses() {
		if (totalGuesses > 0) {
			totalGuesses--;
		}
	}

	void incrementCryptogramsPlayed() {
		cryptogramsPlayed++;
	}

	void incrementCryptogramsCompleted() {
		cryptogramsCompleted++;
	}

	double getAccuracy() {
		return (correctGuesses / totalGuesses) * 100;
	}

	public String getUsername() {
		return username;
	}

	public double getCorrectGuesses() {
		return correctGuesses;
	}

	public double getTotalGuesses() {
		return totalGuesses;
	}

	public int getCryptogramsPlayed() {
		return cryptogramsPlayed;
	}

	public int getCryptogramsCompleted() {
		return cryptogramsCompleted;
	}
}