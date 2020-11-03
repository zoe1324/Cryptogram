package uk.co.team3.cryptogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.exit;

public class Game {

	private Players players = new Players();
	private Player currentPlayer;
	private Scanner scanner = new Scanner(System.in);
	private final String gameFile;
	private Cryptogram crypt;
	private List<String> encryptedPhrase;
	private String phrase;
	private int phraseNo;
	private int numberPhraseLetters;
	private int hintsGiven;
	String[] leaderboardStats;
	String userName;
	int completeCryptograms;
	int correctLetters = 0;
	boolean showSolution = false;

	/**
	 * Constructs a new Game object and sets up the game for the user to play. Asks
	 * the user for their username in order to load their stats into the game.
	 */
	public Game() {
		players.loadPlayers();

		System.out.println("\nWelcome to Team 3's Cryptogram Game, please enter your username:");
		assignCurrentUser();
		gameFile = currentPlayer.getUsername() + "-" + "SavedGame.txt";
	}

	/**
	 * Reads the players username from the input and loads their profile as the
	 * current user. If no player with that username exists a new one is created.
	 */
	private void assignCurrentUser() {
		String username = scanner.next();
		currentPlayer = players.findPlayer(username);
		if (currentPlayer == null) {
			System.out.println("Creating a new user with the username: " + username);
			currentPlayer = new Player(username);
			players.addPlayer(currentPlayer);
		}
	}

	/**
	 * Runs the loop for the main menu of the game.
	 * 
	 * @throws IOException
	 */
	void gameLoop() throws IOException {
		boolean quit = false;

		while (!quit) {
			System.out.println("\nPlease type 'l' for letter cryptogram, 'n' for number cryptogram, "
					+ "'stats' to see current players stats, 'load' to load saved game, 'board' to see leaderboard or 'quit' to quit the game:");

			String input = scanner.next().toLowerCase();
			Random random = new Random();

			switch (input) {
			case "l":
				crypt = new LetterCryptogram();
				phraseNo = random.nextInt(10);
				generateCryptogram(phraseNo);
				currentPlayer.incrementCryptogramsPlayed();
				playGame();
				break;
			case "n":
				crypt = new NumberCryptogram();
				phraseNo = random.nextInt(10);
				generateCryptogram(phraseNo);
				currentPlayer.incrementCryptogramsPlayed();
				playGame();
				break;
			case "stats":
				printPlayerStats();
				break;
			case "load":
				loadGame();
				break;
			case "board":
				showLeaderboard();
				break;
			case "quit":
				quit = true;
				break;
			default:
				System.out.println("Sorry, wrong input. Try again");
				break;
			}
		}

		quit();
	}

	/**
	 * Prints to the console the stats stored for the current player.
	 */
	private void printPlayerStats() {
		System.out.println("Stats for " + currentPlayer.getUsername());
		System.out.println("Cryptograms played: " + currentPlayer.getCryptogramsPlayed());
		System.out.println("Cryptograms completed: " + currentPlayer.getCryptogramsCompleted());
		System.out.println("Total guesses made: " + currentPlayer.getTotalGuesses());
		System.out.println("Correct guesses: " + currentPlayer.getCorrectGuesses());
		System.out.println("Accuracy of guesses: " + currentPlayer.getAccuracy());
	}

	/**
	 * Allows the user to play the cryptogram. loops until the cryptogram has been
	 * completed.
	 */
	private void playGame() {
		boolean complete = false;
		showSolution = false;
		correctLetters = 0;
		hintsGiven = 0;
		while (!complete) {
			System.out.println(
					"Please guess a letter or type: '#' to undo a letter, '*' to save the current game, '/' to view frequencies of letters, '^' to get a hint, '&' to show solution or '!' to quit");
			System.out.println(encryptedPhrase);
			enterLetter();
			System.out.println(currentPlayer.getAccuracy() + "% of your guesses have been correct\n");

			if (correctLetters == numberPhraseLetters) {
				complete = true;
				System.out.println(
						"Congratulations! You finished the cryptogram. You had " + currentPlayer.getTotalGuesses()
								+ " guesses, with an accuracy of " + currentPlayer.getAccuracy() + "%");
				System.out.println("The phrase was: " + phrase);
				currentPlayer.incrementCryptogramsCompleted();
			} else if (showSolution) {
				complete = true;
				System.out.println("The phrase was: " + phrase);
			}
		}
	}

	/**
	 * Generates a cryptogram from the chosen phrase.
	 *
	 * @param chosenPhrase - The number of the phrase which is to be turned into a
	 *                     cryptogram.
	 */
	void generateCryptogram(int chosenPhrase) {
		try {
			phrase = Files.readAllLines(Paths.get("phrases.txt")).get(chosenPhrase);
			numberPhraseLetters = 0;
			for (char c : phrase.toCharArray()) {
				if (Character.isLetter(c)) {
					numberPhraseLetters++;
				}
			}
			encryptedPhrase = crypt.encryptPhrase(phrase);
		} catch (Exception e) {
			System.out.println("Error reading in phrase from file: " + e);
		}
	}

	/**
	 * Reads the character entered and deals with it. If it is a guess it checks to
	 * see if it was correct then adds the correct guess to the output.
	 */
	private void enterLetter() {
		char guess = scanner.next().toLowerCase().charAt(0);

		switch (guess) {
		case '&':
			showSolution = true;
			break;
		case '#':
			if (currentPlayer.getTotalGuesses() > 0) {
				 undoLetter();
			}
			break;
		case '*':
			saveGame();
			break;
		case '/':
			viewFrequencies();
			break;
		case '^':
			if (hintsGiven <= 3) {
				System.out.println("Which letter would you like a hint for?");
				String input = scanner.next().toLowerCase();
				if (encryptedPhrase.contains(input)) {
					giveHint(input);
					hintsGiven++;
				} else {
					System.out.println("Sorry, that letter isn't in the cryptogram");
				}
			} else {
				System.out.println("Sorry, you have used up all your hints");
			}
			break;
		case '!':
			quit();
			break;
		default:
			currentPlayer.incrementTotalGuesses();
			char[] phraseCharArray = phrase.toLowerCase().toCharArray();
			boolean correct = false;

			for (int i = 0; i < phraseCharArray.length; i++) {

				if (phraseCharArray[i] == guess) {
					correct = true;
					encryptedPhrase.remove(i);
					encryptedPhrase.add(i, "[" + guess + "]");
					correctLetters++;
				}
			}

			if (correct) {
				currentPlayer.incrementCorrectGuesses();
				System.out.println("You got one!");
			}
		}
	}

	/**
	 * WIP - When implemented will allow the user to undo an entered letter.
	 */
	private void undoLetter() {
		System.out.println("Which letter would you like to undo?, please enter # to cancel");
		char choice = scanner.next().toLowerCase().charAt(0);

		currentPlayer.decrementTotalGuesses();
	}

	/**
	 * When implemented will display the frequencies of the letters in the
	 * cryptogram.
	 */
	private void viewFrequencies() {
		System.out.println("The english frequencies of the letters are: ");
		System.out.println(crypt.getFrequencies());
		System.out.println("The crypto frequencies of the letters are: ");
		System.out.println(crypt.getCryptoFrequencies());
	}

	/**
	 * Checks if a save game already exists for that player and if so it will ask
	 * the player if they want it to be overwritten.
	 */
	private void saveGame() {
		File file = new File(gameFile);

		if (file.exists()) {
			boolean chosen = false;
			System.out
					.println("Save file already exists. Do you wish to overwrite? If yes type 'y' or type 'n' for no");

			while (!chosen) {
				char choice = scanner.next().toLowerCase().charAt(0);
				switch (choice) {
				case 'y':
					chosen = true;
					saveToFile(file);
					break;
				case 'n':
					chosen = true;
					System.out.println("Operation cancelled, game not saved.");
					break;
				default:
					System.out.println("Please confirm whether to overwrite or not");
				}
			}
		} else {
			saveToFile(file);
		}
	}

	/**
	 * Saves the current cryptogram to file.
	 *
	 * @param file - The file which the game is to be saved to.
	 */
	private void saveToFile(File file) {
		try (FileWriter savedGame = new FileWriter(file)) {

			savedGame.write(crypt.getClass().getSimpleName() + System.lineSeparator());

			for (Object key : crypt.getCryptogramAlphabet().keySet()) {
				savedGame.write(key.toString() + " " + crypt.getCryptogramAlphabet().get(key).toString());
				savedGame.write(System.lineSeparator());
			}

			savedGame.write(phraseNo + System.lineSeparator());

			StringBuilder stringBuilder = new StringBuilder();
			for (String s : encryptedPhrase) {
				stringBuilder.append(s);
				stringBuilder.append("|");
			}
			savedGame.write(stringBuilder.toString() + System.lineSeparator());

			System.out.println("Current game saved.");
		} catch (Exception e) {
			System.out.println("Error saving the game: " + e);
		}
	}

	/**
	 * Loads a game from the users save.
	 */
	private void loadGame() {
		File file = new File(gameFile);
		try {
			try (Scanner fileScanner = new Scanner(file)) {

				if (fileScanner.nextLine().equals("NumberCryptogram")) {
					crypt = new NumberCryptogram();
					Map<Character, Integer> encryptionMap = new HashMap<>();

					while (!fileScanner.hasNextInt()) {
						String[] mapEntry = fileScanner.nextLine().split(" ");
						encryptionMap.put(mapEntry[0].charAt(0), (int) mapEntry[1].charAt(0));
					}
					crypt.setCryptogramAlphabet(encryptionMap);

				} else {
					crypt = new LetterCryptogram();
					Map<Character, Character> encryptionMap = new HashMap<>();

					while (!fileScanner.hasNextInt()) {
						String[] mapEntry = fileScanner.nextLine().split(" ");
						encryptionMap.put(mapEntry[0].charAt(0), mapEntry[1].charAt(0));
					}
					crypt.setCryptogramAlphabet(encryptionMap);
				}

				phraseNo = Integer.parseInt(fileScanner.nextLine());
				generateCryptogram(phraseNo);
				encryptedPhrase = new LinkedList<>(Arrays.asList(fileScanner.nextLine().split("\\|")));
			}
			playGame();
		} catch (Exception e) {
			System.out.println("Error loading game save: " + e);
		}
	}

		/**
	 * Code for User Hint request.
	 */
	private void giveHint(String target) {
		char[] phraseCharArray = phrase.toLowerCase().toCharArray();
		boolean outputBool = false;
		for (int i = 0; i < phraseCharArray.length; i++) {
			if (encryptedPhrase.get(i).contains(target)) {
				encryptedPhrase.remove(i);
				encryptedPhrase.add(i, "[" + phraseCharArray[i] + "]");
				correctLetters++;
				if (outputBool == false) {
					System.out.println("The letter '" + phraseCharArray[i] + "' was revealed in the cryptogram");
					outputBool = true;
				}

			}
		}
	}

	private void showLeaderboard() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("players.txt"));
		ArrayList<Player> playerStats = new ArrayList<Player>();
		String currentLine = reader.readLine();

		while (currentLine != null) {
			leaderboardStats = currentLine.split(" ");
			userName = leaderboardStats[0];
			completeCryptograms = Integer.valueOf(leaderboardStats[4]);

			playerStats.add(new Player(userName, completeCryptograms));
			currentLine = reader.readLine();
		}
		playerStats.sort(Comparator.comparingInt(Player::getCryptogramsCompleted).reversed());
		BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"));
		int counter = 0;
		System.out.println("Top 10 players:");
		for (Player player : playerStats) {
			counter++;
			if (counter > 10) {
				break;
			}
			writer.write(userName);
			writer.write(completeCryptograms);
			System.out.println(player.getUsername() + " " + player.getCryptogramsCompleted());
		}
		reader.close();
		writer.close();
	}

	/**
	 * Calls the savePlayers method of Players class then prints a message and exits
	 * the program.
	 */
	private void quit() {
		players.savePlayers();
		System.out.println("\nThanks for playing! Goodbye :)");
		exit(0);
	}
}