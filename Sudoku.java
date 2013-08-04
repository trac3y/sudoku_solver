import java.io.*;

public class Sudoku {

    public static int[] digitCount = new int[9];
    public static String[][] puzzle = new String[9][9];

    public static String[] twos = new String[2];
    public static String[] threes = new String[6];
    public static String[] fours = new String[24];
    public static String[] fives = new String[120];
    public static String[] sixes = new String[720];
    public static String[] sevens = new String[5040];
    public static String[] eights = new String[40320];
    public static String[] nines = new String[362880];

    public static void set_nines(){
	twos[0] = "21";
	twos[1] = "12";
	for(int i = 0; i < 2; i++){
	    for(int j = 0; j < 3; j++){
		threes[3 * i + j] = twos[i].substring(0,j) + "3"
		    + twos[i].substring(j);
	    }
	}
	for(int i = 0; i < 6; i++){
	    for(int j = 0; j < 4; j++){
		fours[4 * i + j] = threes[i].substring(0,j) + "4"
		    + threes[i].substring(j);
	    }
	}
	for(int i = 0; i < 24; i++){
	    for(int j = 0; j < 5; j++){
		fives[5 * i + j] = fours[i].substring(0,j) + "5"
		    + fours[i].substring(j);
	    }
	}
	for(int i = 0; i < 120; i++){
	    for(int j = 0; j < 6; j++){
		sixes[6 * i + j] = fives[i].substring(0,j) + "6"
		    + fives[i].substring(j);
	    }
	}
	for(int i = 0; i < 720; i++){
	    for(int j = 0; j < 7; j++){
		sevens[7 * i + j] = sixes[i].substring(0,j) + "7"
		    + sixes[i].substring(j);
	    }
	}
	for(int i = 0; i < 5040; i++){
	    for(int j = 0; j < 8; j++){
		eights[8 * i + j] = sevens[i].substring(0,j) + "8"
		    + sevens[i].substring(j);
	    }
	}
	for(int i = 0; i < 40320; i++){
	    for(int j = 0; j < 9; j++){
		nines[9 * i + j] = eights[i].substring(0,j) + "9"
		    + eights[i].substring(j);
	    }
	}
    }

    // Counts occurrences of 1,2,3,...9 in a string and stores in digitCount
    public static void count(String s){
	for(int i = 0; i < 9; i++){
	    digitCount[i] = 0;
	}
	if(s.length() != 9){ // Throws out string if length is not 9
	    return;
	}

	for(int i = 0; i < 9; i++){
	    if(s.substring(i,i+1).equals("1")){
		digitCount[0]++;
	    }
	    if(s.substring(i,i+1).equals("2")){
		digitCount[1]++;
	    }
	    if(s.substring(i,i+1).equals("3")){
		digitCount[2]++;
	    }
	    if(s.substring(i,i+1).equals("4")){
		digitCount[3]++;
	    }
	    if(s.substring(i,i+1).equals("5")){
		digitCount[4]++;
	    }
	    if(s.substring(i,i+1).equals("6")){
		digitCount[5]++;
	    }
	    if(s.substring(i,i+1).equals("7")){
		digitCount[6]++;
	    }
	    if(s.substring(i,i+1).equals("8")){
		digitCount[7]++;
	    }
	    if(s.substring(i,i+1).equals("9")){
		digitCount[8]++;
	    }
	}
    }

    public static boolean in_row(int a, String[] check){
	// Checks if a number appears already in a row
	// false if not in row, true if in row
	String b = "" + a;
	boolean ans = false;
	for(int i = 0; i < check.length; i++){
	    if(check[i].equals(b)){
		ans = true;
		break;
	    }
	}
	return ans;
    }

    public static boolean in_col(int a, String b, String c, String d, String e,
				 String f, String g, String h, String k, String m){
	// Checks if a number appears already in a column
	// false if not in column, true if in column
	String n = "" + a;
	boolean ans = false;
	if(n.equals(b) || n.equals(c) || n.equals(d) || n.equals(e) || n.equals(f) ||
	   n.equals(g) || n.equals(h) || n.equals(k) || n.equals(m)){
	    ans = true;
	}
	return ans;
    }

    public static boolean in_box(int a, int row, int col, String[][] entries){
	int id_X = 0;
	int id_Y = 0;
	boolean ans = false;
	String b = "" + a;

	if(row % 3 == 0){
	    id_X = row;	}
	if(row % 3 == 1){
	    id_X = row - 1;}
	if(row % 3 == 2){
	    id_X = row - 2;}
	if(col % 3 == 0){
	    id_Y = col;}
	if(col % 3 == 1){
	    id_Y = col - 1;}
	if(col % 3 == 2){
	    id_Y = col - 2;}

	if(b.equals(entries[id_X][id_Y]) || b.equals(entries[id_X+1][id_Y]) || 
	   b.equals(entries[id_X+2][id_Y]) || b.equals(entries[id_X][id_Y+1]) ||
	   b.equals(entries[id_X+1][id_Y+1]) || b.equals(entries[id_X+2][id_Y+1]) ||
	   b.equals(entries[id_X][id_Y+2]) || b.equals(entries[id_X+1][id_Y+2]) ||
	   b.equals(entries[id_X+2][id_Y+2])){
	    ans = true;
	}
	return ans;
    }

    public static void solve(int[][] givens){
	int ones_count = 0;
	int marker = 0;
	int algor_count = 0; // Number of times of going through rows/columns/boxes without
	                     // finding anything to change. We know we've done as much as
	                     // we can when algor_count == 9.
	int search_count = 0; // Number of consecutive searches without finding anything
	                      // to change.

	for(int i = 0; i < 9; i++){
	    digitCount[i] = 0;
	}

	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		puzzle[i][j] = "" + givens[i][j];
	    }
	}


	while(search_count < 2){

	while(algor_count < 9){
	    for(int j = 0; j < 9; j++){
		if(in_col(0, puzzle[0][j], puzzle[1][j], puzzle[2][j],
			  puzzle[3][j], puzzle[4][j], puzzle[5][j],
			  puzzle[6][j], puzzle[7][j], puzzle[8][j])){
		    for(int k = 1; k <= 9; k++){
			if(!in_col(k, puzzle[0][j], puzzle[1][j], puzzle[2][j],
				   puzzle[3][j], puzzle[4][j], puzzle[5][j],
				   puzzle[6][j], puzzle[7][j], puzzle[8][j])){
			    // identifies numbers not already marked in column
			    for(int i = 0; i < 9; i++){
				if(puzzle[i][j].equals("0") && !in_row(k, puzzle[i]) && 
				   !in_box(k, i, j, puzzle)){
				    digitCount[i]++;
				}
			    }
			    for(int i = 0; i < 9; i++){
				if(digitCount[i] == 1){
				    ones_count++;
				    marker = i;
				}
			    }
			    if(ones_count == 1){
				puzzle[marker][j] = "" + k;
				algor_count = 0;
				search_count = 0;
			    }
			    for(int i = 0; i < 9; i++){
				digitCount[i] = 0;
			    }			
			    ones_count = 0;
			}
		    }
		}
		algor_count++;
	    }
	}

	search_count++;
	algor_count = 0;

	while(algor_count < 9){
	    for(int j = 0; j < 9; j++){
		if(in_row(0, puzzle[j])){
		    for(int k = 1; k <= 9; k++){
			if(!in_row(k, puzzle[j])){
			    // identifies numbers not already marked in row
			    for(int i = 0; i < 9; i++){
				if(puzzle[j][i].equals("0") && 
				   !in_col(k, puzzle[0][i], puzzle[1][i], puzzle[2][i],
					   puzzle[3][i], puzzle[4][i], puzzle[5][i],
					   puzzle[6][i], puzzle[7][i], puzzle[8][i]) &&
				   !in_box( k, j, i, puzzle)){
				    digitCount[i]++;
				}
			    }
			    for(int i = 0; i < 9; i++){
				if(digitCount[i] == 1){
				    ones_count++;
				    marker = i;
				}
			    }
			    if(ones_count == 1){
				puzzle[j][marker] = "" + k;
				algor_count = 0;
				search_count = 0;
			    }
			    for(int i = 0; i < 9; i++){
				digitCount[i] = 0;
			    }
			    ones_count = 0;
			}
		    }
		}
		algor_count++;
	    }
	}
	search_count++;

	}

    }
    



    public static void main(String args[]) {

	/*
	set_nines();
	for(int i = 0; i < 362880; i++){
	    System.out.println(nines[i]);
	}
	*/

	int[][] test = { 
	    { 9, 7, 8, 1, 0, 6, 3, 4, 5},
	    { 0, 0, 0, 0, 0, 3, 0, 9, 8},
	    { 6, 5, 3, 8, 4, 9, 0, 1, 0},
	    { 8, 4, 7, 0, 0, 0, 0, 0, 0},
	    { 2, 0, 0, 3, 8, 7, 0, 0, 6},
	    { 0, 0, 0, 0, 0, 0, 7, 8, 2},
	    { 0, 8, 0, 2, 3, 4, 5, 6, 1},
	    { 3, 1, 0, 7, 0, 0, 0, 0, 0},
	    { 4, 6, 2, 9, 0, 5, 8, 7, 3}
	};
	/*
	solve(test);
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		System.out.print(puzzle[i][j] + " ");
		if(((j + 1) % 3) == 0){
		    System.out.print(" ");
		}
	    }
	    System.out.print("\n");
	    if(((i + 1) % 3) == 0){
		System.out.print("\n");
	    }
	}
	*/
	int[][] diabolical = {
	    { 4, 0, 0, 0, 3, 0, 9, 0, 7},
	    { 0, 0, 0, 0, 9, 1, 0, 2, 0},
	    { 0, 3, 0, 0, 0, 0, 0, 6, 0},
	    { 0, 7, 5, 0, 0, 4, 0, 0, 0},
	    { 0, 4, 0, 5, 0, 8, 0, 9, 0},
	    { 0, 0, 0, 2, 0, 0, 4, 7, 0},
	    { 0, 2, 0, 0, 0, 0, 0, 5, 0},
	    { 0, 8, 0, 9, 2, 0, 0, 0, 0},
	    { 7, 0, 3, 0, 5, 0, 0, 0, 8}
	};	
	/*
	solve(diabolical);
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		System.out.print(puzzle[i][j] + " ");
		if(((j + 1) % 3) == 0){
		    System.out.print(" ");
		}
	    }
	    System.out.print("\n");
	    if(((i + 1) % 3) == 0){
		System.out.print("\n");
	    }
	}
	*/

	/*
	int[][] easy = {
	    { 0, 0, 4, 0, 5, 0, 0, 7, 1},
	    { 5, 0, 7, 0, 0, 0, 8, 0, 0},
	    { 2, 8, 9, 1, 0, 0, 0, 0, 0},
	    { 0, 0, 1, 3, 0, 8, 6, 0, 0},
	    { 0, 7, 0, 2, 9, 5, 0, 8, 0},
	    { 0, 0, 2, 4, 0, 1, 7, 0, 0},
	    { 0, 0, 0, 0, 0, 3, 2, 4, 6},
	    { 0, 0, 6, 0, 0, 0, 5, 0, 8},
	    { 4, 2, 0, 0, 1, 0, 3, 0, 0}
	};
	*/

	int[][] easy = {
	    { 3, 0, 2, 8, 0, 0, 0, 0, 9},
	    { 0, 0, 0, 0, 5, 9, 4, 0, 0},
	    { 9, 7, 0, 0, 0, 0, 0, 0, 0},
	    { 0, 0, 1, 9, 0, 7, 5, 6, 0},
	    { 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    { 0, 5, 6, 1, 0, 4, 8, 0, 0},
	    { 0, 0, 0, 0, 0, 0, 0, 2, 7},
	    { 0, 0, 7, 4, 1, 0, 0, 0, 0},
	    { 5, 0, 0, 0, 0, 2, 9, 0, 6}
	};

	solve(easy);
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		System.out.print(puzzle[i][j] + " ");
		if(((j + 1) % 3) == 0){
		    System.out.print(" ");
		}
	    }
	    System.out.print("\n");
	    if(((i + 1) % 3) == 0){
		System.out.print("\n");
	    }
	}

    }

}