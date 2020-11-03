package uk.co.team3.cryptogram;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Players {

	private List<Player> allPlayers = new ArrayList<>();
	private String playersFile = "players.txt";

	/**
	 * Adds a player to the list of players.
	 * @param player - The player to be added to the list.
	 */
	void addPlayer(Player player) {
		allPlayers.add(player);
	}

	/**
	 * Loads the player list from the players file.
	 */
	void loadPlayers() {
		File file = new File(playersFile);
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String username = scanner.next();
				double correctGuesses = scanner.nextDouble();
				double totalGuesses = scanner.nextDouble();
				int cryptogramsPlayed = scanner.nextInt();
				int cryptogramsCompleted = scanner.nextInt();
				Player player = new Player(username, correctGuesses, totalGuesses, cryptogramsPlayed, cryptogramsCompleted);
				allPlayers.add(player);
			}

		} catch (Exception e) {
			System.out.println("Error occurred while loading player stats: " + e);
		}
	}

	/**
	 * Saves the players list to the players file.
	 */
	void savePlayers() {
			File file = new File(playersFile);
			try (FileWriter savedStats = new FileWriter(file)) {

				for (Player player : allPlayers) {
					savedStats.write(player.getUsername() + " ");
					savedStats.write(player.getCorrectGuesses() + " ");
					savedStats.write(player.getTotalGuesses() + " ");
					savedStats.write(player.getCryptogramsPlayed() + " ");
					savedStats.write(player.getCryptogramsCompleted() + "\n");
				}


			} catch (Exception e) {
				System.out.println("Error saving player stats: " + e);
			}
	}

	/**
	 * Finds the player with the entered username.
	 * @param username - Username of player which is being searched for.
	 * @return - The player with the username handed in or null if a player with that username doesn't exist.
	 */
	Player findPlayer(String username) {
		Player returnPlayer = null;

		for (Player player : allPlayers) {
			if (player.getUsername().equals(username)) {
				returnPlayer = player;
			}
		}

		return returnPlayer;
	}

	/**
	 * Gets the accuracies of all players in the player list.
	 * @return - List of player accuracies.
	 */
	List<Double> getAllPlayersAccuracies() {
		List<Double> accuracies = new ArrayList<>();

		for (Player player : allPlayers) {
			accuracies.add(player.getAccuracy());
		}

		return accuracies;
	}

	/**
	 * Gets the number of cryptograms played for all players.
	 * @return - list of number of cryptograms played.
	 */
	List<Integer> getAllPlayersCryptogramsPlayed() {
		List<Integer> cryptogramsPlayed = new ArrayList<>();

		for (Player player : allPlayers) {
			cryptogramsPlayed.add(player.getCryptogramsPlayed());
		}

		return cryptogramsPlayed;
	}

	/**
	 * Gets the number of completed cryptograms for all players.
	 * @return - list of number of completed cryptograms.
	 */
	List<Integer> getAllPlayersCompletedCryptos() {
		List<Integer> completedCryptos = new ArrayList<>();

		for (Player player : allPlayers) {
			completedCryptos.add(player.getCryptogramsCompleted());
		}

		return completedCryptos;
	}
}
