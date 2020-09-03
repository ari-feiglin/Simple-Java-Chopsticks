package games;

import java.util.Scanner;

enum Position {TOP, BOTTOM};

class player {
	char hands[] = {1, 1};
	String player_name;
	
	player(String player_name) {
		this.player_name = player_name;
	}
	
	void print_hands(Position position) {
		if(Position.TOP == position) {
			System.out.println(String.format("%s:", player_name));
		}
		
		System.out.print("Left: ");
		for(int i=0; i<hands[0]; i++) {
			System.out.print("|");
		}
		System.out.print(" Right: ");
		for(int i=0; i<hands[1]; i++) {
			System.out.print("|");
		}
		
		System.out.println();
		if(Position.BOTTOM == position) {
			System.out.println(String.format("%s:", player_name));
		}
		System.out.println();
	}
	
	boolean attack(player defending_player, int defending_hand, int attacking_hand) {
		boolean success = false;
		
		if(defending_player.hands[defending_hand] > 0 && hands[attacking_hand] > 0) {
			defending_player.hands[defending_hand] =  (char) ((defending_player.hands[defending_hand] + hands[attacking_hand]) % 5);
			success = true;
		}
		
		return success;
	}
	
	boolean split(int splitting_hand) {
		boolean success = false;
		int gaining_hand = (splitting_hand + 1) % 2;
		
		if(hands[splitting_hand] > 0 && 0 == hands[gaining_hand] && 0== hands[splitting_hand] % 2) {
			hands[gaining_hand] = (char) (hands[splitting_hand] / 2);
			hands[splitting_hand] /= 2;
			success = true;
		}
		
		return success;
	}
	
	boolean dead() {
		boolean game_over = false;
		
		if(0 == hands[0] && 0 == hands[1]) {
			game_over = true;
		}
		
		return game_over;
	}
}

public class Chopsticks {
	static int get_int_input(String prompt) {
		int input = 0;
		Scanner scanner_obj = new Scanner(System.in);
		
		System.out.println(prompt);
		input = scanner_obj.nextInt();
		
		return input;
	}
	
	static char get_char_input(String prompt) {
		char input = 0;
		Scanner scanner_obj = new Scanner(System.in);
		
		System.out.println(prompt);
		input = scanner_obj.next().charAt(0);
		
		return input;
	}
	
	
	
	public static void main(String[] args) {
		player players[] = new player[2];
		players[0] = new player("Player 1");
		players[1] = new player("Player 2");
		char move = 0;
		int major_hand = 0;
		int minor_hand = 0;
		boolean success = false;
		
		System.out.println("============\n CHOPSTICKS\n============\n");
		System.out.println("=============\n| Options:  |\n| --------  |\n| Attack: a |\n| Split: s  |\n=============\n");
		
		for(int i=0; !players[0].dead() && !players[1].dead(); i++) {
			players[1].print_hands(Position.TOP);
			players[0].print_hands(Position.BOTTOM);
			
			while(!success) {
				move = get_char_input(String.format("%s, what would you like to do?", players[i%2].player_name));
				switch(move) {
				case 'a':
					minor_hand = get_int_input("You have chosen to attack. Which hand would you like to attack with?");
					major_hand = get_int_input("Which hand would you like to attack?");
					success = players[i%2].attack(players[(i+1) %2], major_hand, minor_hand);
					if(!success) {
						System.out.println("That is not a valid move.");
					}
					break;
				case 's':
					major_hand = get_int_input("You have chosen to split. Which hand are you splitting?");
					success = players[i%2].split(major_hand);
					if(!success) {
						System.out.println("That is not a valid move.");
					}
					break;
				default: 
					System.out.println("That is not a valid move. Choose Attack (a) or Split (s).");
					success = false;
					break;
				}
			}
			success = false;
		}
		if(players[0].dead()) {
			System.out.println(String.format("%s Wins!", players[1].player_name));
		}
		else{
			System.out.println(String.format("%s Wins!", players[0].player_name));
		}
	}
}
