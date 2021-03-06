import java.io.*;

public class Sudoku {

    public static int[] digitCount = new int[9];
    public static String[][] puzzle = new String[9][9];
    public static int[][][] entries = new int[9][9][9];

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

    public static boolean in_col(int a, String b, String c, String d,
				 String e, String f, String g, String h,
				 String k, String m){
	// Checks if a number appears already in a column
	// false if not in column, true if in column
	String n = "" + a;
	boolean ans = false;
	if(n.equals(b) || n.equals(c) || n.equals(d) || n.equals(e) || 
	   n.equals(f) || n.equals(g) || n.equals(h) || n.equals(k) ||
	   n.equals(m)){
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
	   b.equals(entries[id_X+1][id_Y+1]) ||
	   b.equals(entries[id_X+2][id_Y+1]) || b.equals(entries[id_X][id_Y+2])
	   || b.equals(entries[id_X+1][id_Y+2]) ||
	   b.equals(entries[id_X+2][id_Y+2])){
	    ans = true;
	}
	return ans;
    }

    public static boolean complete(String[][] numbers){
	boolean ans = true;
	A: for(int i = 0; i < 9; i++){
	    B: for(int j = 0; j < numbers[i].length; j++){
		if(numbers[i][j].equals("0")){
		    ans = false;
		    break A;
		}
	    }
	}
	return ans;
    }

    public static void fill_in(int[][] givens){
	int ones_count = 0;
	int marker = 0;
	int algor_count = 0;
	// Number of times of going through rows/columns/boxes without
	// finding anything to change. We know we've done as much as
	// we can when algor_count == 9.
	int search_count = 0;
	// Number of consecutive searches without finding anything to change.
	int box_row = 0;
	int box_col = 0;

	for(int i = 0; i < 9; i++){
	    digitCount[i] = 0;
	}

	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		puzzle[i][j] = "" + givens[i][j];
	    }
	}

	while(search_count < 3){

	    while(algor_count < 9){
		for(int j = 0; j < 9; j++){
		    if(in_col(0, puzzle[0][j], puzzle[1][j], puzzle[2][j],
			      puzzle[3][j], puzzle[4][j], puzzle[5][j],
			      puzzle[6][j], puzzle[7][j], puzzle[8][j])){
			for(int k = 1; k <= 9; k++){
			    if(!in_col(k, puzzle[0][j], puzzle[1][j],
				       puzzle[2][j],puzzle[3][j],
				       puzzle[4][j], puzzle[5][j],
				       puzzle[6][j], puzzle[7][j],
				       puzzle[8][j])){
				// identifies numbers not already marked
				// in column
				for(int i = 0; i < 9; i++){
				    if(puzzle[i][j].equals("0") &&
				       !in_row(k, puzzle[i]) &&
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
				       !in_col(k, puzzle[0][i],
					       puzzle[1][i], puzzle[2][i],
					       puzzle[3][i], puzzle[4][i],
					       puzzle[5][i], puzzle[6][i],
					       puzzle[7][i], puzzle[8][i]) && 
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
	    algor_count = 0;

	    while(algor_count < 9){
		for(int j = 0; j < 3; j++){
		    for(int n = 0; n < 3; n++){
			if(in_box(0, 3*j, 3*n, puzzle)){
			    for(int k = 1; k <= 9; k++){
				if(!in_box(k, 3*j, 3*n, puzzle)){
				    // identifies numbers not
				    // already marked in box
				    for(int r = 0; r < 3; r++){
					for(int s = 0; s < 3; s++){
					    if(puzzle[3*j + r][3*n + s].equals("0") &&
					       !in_row(k, puzzle[3*j + r]) &&
					       !in_col(k, puzzle[0][3*n+s],
						       puzzle[1][3*n+s],
						       puzzle[2][3*n+s],
						       puzzle[3][3*n+s],
						       puzzle[4][3*n+s],
						       puzzle[5][3*n+s],
						       puzzle[6][3*n+s],
						       puzzle[7][3*n+s],
						       puzzle[8][3*n+s])){
						digitCount[3 * r + s]++;
					    }
					}
				    }
				    for(int i = 0; i < 9; i++){
					if(digitCount[i] == 1){
					    ones_count++;
					    marker = i;
					}
				    }
				    if(ones_count == 1){
					box_row = marker / 3;
					box_col = marker % 3;
					puzzle[3*j + box_row][3*n + box_col] =
					    "" + k;
					algor_count = 0;
					search_count = 0;
					box_row = 0;
					box_col = 0;
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
	    }
	    search_count++;
	    algor_count = 0;
	}
    }

    public static boolean possible(int row, int col){
	boolean ans = true;
	int[] counters = new int[9];
	int num_pass = 0;
	for(int i = 0; i < 9; i++){
	    counters[i] = 0;
	}
	if(puzzle[row][col].equals("0")){
	    for(int k = 1; k <= 9; k++){
		if(!in_row(k, puzzle[row]) &&
		   !in_col(k, puzzle[0][col], puzzle[1][col],
			   puzzle[2][col], puzzle[3][col],
			   puzzle[4][col], puzzle[5][col],
			   puzzle[6][col], puzzle[7][col],
			   puzzle[8][col]) &&
		   !in_box(k, row, col, puzzle)){
		    counters[k-1] = 1;
		}
	    }
	    for(int i = 0; i < 9; i++){
		if(counters[i] == 1){
		    num_pass++;
		    break;
		}
	    }
	    if(num_pass == 0){
		ans = false;
	    }
	}
	return ans;
    }

    public static boolean puzzle_possible(){
	boolean ans = true;
	A: for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		if(!possible(i, j)){
		    ans = false;
		    break A;
		}
	    }
	}
	return ans;
    } // Finds out if the entire puzzle is possible


    public static void choices(){
	int marker = 0;
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		for(int k = 0; k < 9; k++){
		    entries[i][j][k] = 0;
		}
	    }
	}
	for(int i = 0; i < 9; i++){
	    for(int j = 0; j < 9; j++){
		marker = 0;
		for(int k = 1; k <= 9; k++){
		    if(!in_row(k, puzzle[i]) &&
		       !in_col(k, puzzle[0][j], puzzle[1][j], puzzle[2][j],
			       puzzle[3][j], puzzle[4][j], puzzle[5][j],
			       puzzle[6][j], puzzle[7][j], puzzle[8][j]) &&
		       !in_box(k, i, j, puzzle) &&
		       puzzle[i][j].equals("0")){
			entries[i][j][marker] = k;
			marker++;
		    }
		}
	    }
	}
    }

    public static void solve(int[][] givens){
	int[] potentials = new int[9];
	int pass_one = 0;
	// If there are two values alone that could be in the spot
	int pass_two = 0;
	int permit = 0;
	// Number of valid numbers in a single box based on fill_in checks

	int counter = 0;
	int update = 0;

	String[][] decoy = new String[9][9];
	// A test array that mimics puzzle[][]
	int[][] puzzleInt = new int[9][9];
	boolean valid_puzzle = true;
	boolean good_sir = true;

	fill_in(givens);

	while(update < 81 && !complete(puzzle)){

	    fill_in(givens);

	    for(int i = 0; i < 9; i++){
		for(int j = 0; j < 9; j++){
		    decoy[i][j] = puzzle[i][j];
		    // Copies puzzle[][] to decoy[][]
		}
	    }

	    if(!complete(puzzle)){
		// if the puzzle still isn't finished
		//choices();
		// Might be more efficient to place choices() HERE HERE HERE
		for(int i = 0; i < 9; i++){
		    for(int j = 0; j < 9; j++){
			choices();
			counter = 0;
			//pass_one = 0;
			//pass_two = 0;
			for(int k = 0; k < 9; k++){
			    if(entries[i][j][k] != 0){
				counter++;
			    }
			    else{
				break;
			    }
			}
			if(counter == 2){
			    puzzle[i][j] = entries[i][j][0] + "";
			    for(int aa = 0; aa < 9; aa++){
				for(int bb = 0; bb < 9; bb++){
				    puzzleInt[aa][bb] =
					Integer.parseInt(puzzle[aa][bb]);
				}
			    }
			    fill_in(puzzleInt);

			    if(complete(puzzle)){
				return;
			    }
			    if(!complete(puzzle)){
				if(!puzzle_possible()){
				    decoy[i][j] = entries[i][j][1] + "";
				    update = 0;
				    for(int aa = 0; aa < 9; aa++){
					for(int bb = 0; bb < 9; bb++){
					    puzzle[aa][bb] = decoy[aa][bb];
					}
				    }
				    for(int aa = 0; aa < 9; aa++){
					for(int bb = 0; bb < 9; bb++){
					    puzzleInt[aa][bb] =
						Integer.parseInt(decoy[aa][bb]);
					}
				    }
				    fill_in(puzzleInt);

				}
				if(puzzle_possible()){
				    for(int aa = 0; aa < 9; aa++){
					for(int bb = 0; bb < 9; bb++){
					    puzzle[aa][bb] = decoy[aa][bb];
					}
				    }
				    puzzle[i][j] = entries[i][j][1] + "";
				    for(int aa = 0; aa < 9; aa++){
					for(int bb = 0; bb < 0; bb++){
					    puzzleInt[aa][bb] =
						Integer.parseInt(puzzle[aa][bb]);
					}
				    }
				    fill_in(puzzleInt);

				    if(complete(puzzle)){
					return;
				    }
				    if(!complete(puzzle)){
					if(!puzzle_possible()){
					    decoy[i][j] = entries[i][j][0] + "";
					    update = 0;
					    for(int aa = 0; aa < 9; aa++){
						for(int bb = 0; bb < 9; bb++){
						    puzzle[aa][bb] =
							decoy[aa][bb];
						}
					    }
					    for(int aa = 0; aa < 9; aa++){
						for(int bb = 0; bb < 9; bb++){
						    puzzleInt[aa][bb] =
							Integer.parseInt(puzzle[aa][bb]);
						}
					    }
					    fill_in(puzzleInt);
					}
					if(puzzle_possible()){
					    for(int aa = 0; aa < 9; aa++){
						for(int bb = 0; bb < 9; bb++){
						    puzzle[aa][bb] =
							decoy[aa][bb];
						}
					    }
					}
				    }
				}
			    }
			}
			update++;
		    }
		}
	    }
	}

    }


    public static void main(String args[]) {

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
	//fill_in(test);
	
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
	//fill_in(diabolical);
	//solve(diabolical);

	int[][] gentle = {
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
	//fill_in(gentle);

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
	//fill_in(easy);

	int[][] test_A = {
	    { 9, 1, 0, 0, 6, 0, 0, 8, 7},
	    { 8, 4, 3, 0, 5, 0, 1, 2, 6},
	    { 0, 0, 5, 0, 0, 0, 4, 0, 0},
	    { 0, 9, 0, 0, 1, 0, 0, 5, 0},
	    { 2, 6, 0, 0, 3, 0, 0, 4, 8},
	    { 5, 0, 0, 2, 9, 4, 0, 0, 1},
	    { 7, 2, 0, 6, 4, 1, 0, 3, 5},
	    { 3, 0, 0, 5, 7, 9, 0, 0, 4},
	    { 1, 5, 4, 3, 2, 8, 6, 7, 9}
	};
	// "gentle"
	//fill_in(test_A);

	int[][] test_B = {
	    { 0, 0, 0, 4, 9, 0, 0, 0, 6},
	    { 0, 4, 7, 3, 0, 1, 8, 9, 0},
	    { 6, 0, 0, 7, 0, 0, 0, 0, 0},
	    { 4, 0, 5, 0, 0, 0, 1, 0, 0},
	    { 3, 9, 8, 6, 1, 4, 2, 5, 7},
	    { 7, 0, 2, 0, 0, 0, 6, 0, 0},
	    { 2, 0, 0, 9, 0, 0, 0, 0, 0},
	    { 0, 8, 6, 1, 0, 3, 4, 2, 0},
	    { 0, 0, 0, 2, 4, 0, 0, 0, 8}
	};
	// "moderate"
	//fill_in(test_B);
	//solve(test_B);

	int[][] test_C = {
	    { 0, 0, 0, 2, 0, 0, 0, 6, 3},
	    { 3, 0, 0, 0, 0, 5, 4, 0, 1},
	    { 0, 0, 1, 0, 0, 3, 9, 8, 0},
	    { 0, 0, 0, 0, 0, 0, 0, 9, 0},
	    { 0, 0, 0, 5, 3, 8, 0, 0, 0},
	    { 0, 3, 0, 0, 0, 0, 0, 0, 0},
	    { 0, 2, 6, 3, 0, 0, 5, 0, 0},
	    { 5, 0, 3, 7, 0, 0, 0, 0, 8},
	    { 4, 7, 0, 0, 0, 1, 0, 0, 0}
	};
	// "hard"
	//fill_in(test_C);
	//solve(test_C);

	int[][] test_D = {
	    { 5, 7, 0, 8, 4, 0, 0, 0, 0},
	    { 0, 9, 0, 7, 0, 0, 0, 2, 0},
	    { 0, 0, 2, 0, 6, 0, 8, 5, 0},
	    { 0, 1, 0, 0, 0, 0, 0, 0, 6},
	    { 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    { 9, 0, 0, 0, 0, 0, 0, 7, 0},
	    { 0, 2, 3, 0, 8, 0, 1, 0, 0},
	    { 0, 4, 0, 0, 0, 9, 0, 8, 0},
	    { 0, 6, 0, 0, 3, 5, 0, 4, 2}
	};
	// "gentle"
	//fill_in(test_D);

	int[][] test_E = {
	    { 0, 8, 0, 0, 0, 7, 2, 0, 0},
	    { 9, 0, 0, 0, 0, 5, 0, 0, 8},
	    { 0, 2, 6, 0, 8, 0, 0, 0, 0},
	    { 0, 7, 0, 8, 9, 0, 1, 3, 0},
	    { 8, 0, 0, 0, 5, 0, 0, 0, 2},
	    { 0, 6, 3, 0, 2, 4, 0, 7, 0},
	    { 0, 0, 0, 0, 1, 0, 3, 2, 0},
	    { 3, 0, 0, 6, 0, 0, 0, 0, 1},
	    { 0, 0, 7, 3, 0, 0, 0, 8, 0}
	};
	// "moderate"
	//fill_in(test_E);

	int[][] test_G = {
	    { 3, 7, 0, 6, 1, 0, 0, 0, 4},
	    { 0, 5, 0, 0, 0, 0, 6, 3, 0},
	    { 6, 0, 4, 0, 0, 0, 1, 0, 0},
	    { 0, 4, 0, 0, 8, 0, 0, 0, 0},
	    { 0, 0, 0, 2, 0, 3, 0, 0, 0},
	    { 0, 0, 0, 0, 5, 0, 0, 8, 0},
	    { 0, 0, 3, 0, 0, 0, 8, 0, 1},
	    { 0, 2, 8, 0, 0, 0, 0, 7, 0},
	    { 1, 0, 0, 0, 9, 4, 0, 2, 5}
	};
	//fill_in(test_G);
	//solve(test_G);

	int[][] test_H = {
	    { 8, 0, 0, 2, 7, 3, 0, 0, 0},
	    { 3, 9, 0, 0, 0, 6, 0, 0, 0},
	    { 0, 0, 4, 0, 8, 0, 0, 0, 0},
	    { 0, 7, 0, 0, 1, 0, 9, 6, 0},
	    { 0, 0, 0, 3, 0, 2, 0, 0, 0},
	    { 0, 1, 8, 0, 9, 0, 0, 3, 0},
	    { 0, 0, 0, 0, 6, 0, 1, 0, 0},
	    { 0, 0, 0, 4, 0, 0, 0, 5, 9},
	    { 0, 0, 0, 1, 3, 9, 0, 0, 8}
	};
	// "super-hard"
	//solve(test_H);

	int[][] test_I = {
	    { 0, 0, 0, 9, 2, 0, 0, 0, 0},
	    { 0, 0, 6, 8, 0, 3, 0, 0, 0},
	    { 1, 9, 0, 0, 7, 0, 0, 0, 6},
	    { 2, 3, 0, 0, 4, 0, 1, 0, 0},
	    { 0, 0, 1, 0, 0, 0, 7, 0, 0},
	    { 0, 0, 8, 0, 3, 0, 0, 2, 9},
	    { 7, 0, 0, 0, 8, 0, 0, 9, 1},
	    { 0, 0, 0, 5, 0, 7, 2, 0, 0},
	    { 0, 0, 0, 0, 6, 4, 0, 0, 0}
	};
	// "diabolical"
	//solve(test_I);

	int[][] test_J = {
	    { 0, 9, 4, 5, 0, 7, 1, 2, 0},
	    { 0, 0, 5, 0, 0, 1, 7, 0, 4},
	    { 1, 0, 0, 0, 8, 0, 5, 0, 9},
	    { 0, 5, 0, 1, 0, 0, 0, 7, 0},
	    { 0, 0, 3, 0, 0, 0, 2, 0, 0},
	    { 0, 2, 0, 0, 0, 6, 0, 1, 0},
	    { 5, 0, 9, 0, 2, 0, 0, 0, 1},
	    { 7, 0, 1, 6, 0, 0, 3, 0, 0},
	    { 0, 3, 6, 8, 0, 5, 9, 4, 0}
	};
	// "easy"
	//solve(test_J);

	int[][] test_K = {
	    { 0, 0, 5, 9, 0, 3, 0, 1, 0},
	    { 0, 0, 7, 0, 1, 0, 8, 0, 0},
	    { 0, 3, 0, 6, 0, 8, 0, 0, 0},
	    { 0, 1, 3, 0, 0, 2, 0, 0, 0},
	    { 2, 0, 0, 0, 6, 0, 0, 0, 9},
	    { 0, 0, 0, 8, 0, 0, 7, 3, 0},
	    { 0, 0, 0, 1, 0, 6, 0, 7, 0},
	    { 0, 0, 4, 0, 8, 0, 9, 0, 0},
	    { 0, 6, 0, 7, 0, 9, 5, 0, 0}
	};
	// "medium"
	//solve(test_K);

	int[][] test_L = {
	    { 0, 7, 9, 0, 0, 6, 8, 0, 0},
	    { 2, 0, 6, 3, 5, 1, 0, 0, 0},
	    { 1, 0, 0, 9, 7, 0, 5, 0, 0},
	    { 0, 6, 0, 0, 9, 0, 7, 5, 0},
	    { 0, 0, 0, 0, 1, 0, 0, 0, 0},
	    { 0, 9, 2, 0, 4, 0, 0, 8, 0},
	    { 0, 0, 3, 0, 8, 2, 0, 0, 6},
	    { 0, 0, 0, 1, 6, 4, 3, 0, 5},
	    { 0, 0, 7, 5, 0, 0, 2, 4, 0}
	};
	// "easy"
	//solve(test_L);

	int[][] test_M = {
	    { 9, 0, 8, 0, 0, 0, 6, 3, 0},
	    { 0, 0, 0, 0, 0, 0, 8, 0, 7},
	    { 5, 0, 0, 8, 0, 0, 0, 0, 4},
	    { 0, 0, 1, 0, 4, 8, 0, 0, 0},
	    { 2, 0, 0, 6, 0, 5, 0, 0, 8},
	    { 0, 0, 0, 7, 2, 0, 4, 0, 0},
	    { 1, 0, 0, 0, 0, 6, 0, 0, 2},
	    { 8, 0, 5, 0, 0, 0, 0, 0, 0},
	    { 0, 7, 4, 0, 0, 0, 9, 0, 1}
	};
	// "hard"
	//solve(test_M);

	int[][] test_N = {
	    { 0, 9, 7, 6, 0, 0, 0, 0, 0},
	    { 2, 8, 0, 0, 0, 0, 0, 5, 4},
	    { 0, 0, 0, 0, 0, 0, 0, 2, 0},
	    { 0, 0, 0, 0, 8, 4, 0, 6, 0},
	    { 0, 6, 0, 0, 7, 0, 0, 3, 0},
	    { 0, 2, 0, 9, 1, 0, 0, 0, 0},
	    { 0, 3, 0, 0, 0, 0, 0, 0, 0},
	    { 1, 7, 0, 0, 0, 0, 0, 8, 5},
	    { 0, 0, 0, 0, 0, 9, 6, 7, 0}
	};
	// "evil"
	//solve(test_N);

	int[][] test_O = {
	    { 0, 0, 0, 0, 6, 0, 3, 0, 0},
	    { 8, 0, 0, 0, 5, 0, 2, 0, 0},
	    { 0, 0, 6, 0, 0, 1, 0, 8, 4},
	    { 2, 0, 0, 0, 0, 8, 6, 0, 9},
	    { 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    { 6, 0, 4, 3, 0, 0, 0, 0, 8},
	    { 4, 6, 0, 7, 0, 0, 9, 0, 0},
	    { 0, 0, 1, 0, 4, 0, 0, 0, 5},
	    { 0, 0, 7, 0, 3, 0, 0, 0, 0}
	};
	//solve(test_O);

	// ENTER YOUR SUDOKU HERE
	int[][] testCray = {
	    { 0, 9, 0, 0, 1, 0, 0, 2, 4},
	    { 6, 0, 0, 4, 5, 0, 0, 0, 0},
	    { 0, 0, 5, 0, 0, 0, 0, 6, 3},
	    { 0, 0, 0, 1, 3, 0, 6, 0, 0},
	    { 5, 0, 9, 0, 0, 0, 2, 0, 1},
	    { 0, 0, 3, 0, 2, 8, 0, 0, 5},
	    { 7, 5, 0, 0, 0, 0, 9, 0, 2},
	    { 0, 0, 6, 0, 4, 9, 0, 0, 7},
	    { 9, 3, 0, 0, 8, 0, 0, 5, 0}
	};
	solve(testCray);
	
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