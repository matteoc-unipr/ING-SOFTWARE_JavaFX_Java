package com.matteo;

import java.util.Scanner;

public class Calculator {

	public static void printInterface() {
		System.out.println("+--------------------------+");
		System.out.println("|        CALCULATOR        |");
		System.out.println("|                          |");
		System.out.println("| Valid numbers type:      |");
		System.out.println("|                          |");
		System.out.println("| -> int                   |");
		System.out.println("| -> double                |");
		System.out.println("|                          |");
		System.out.println("| Valid operations:        |");
		System.out.println("|                          |");
		System.out.println("| ->  +                    |");
		System.out.println("| ->  -                    |");
		System.out.println("| ->  *                    |");
		System.out.println("| ->  /                    |");
		System.out.println("| ->  %                    |");
		System.out.println("|                          |");
		System.out.println("| Other actions:           |");
		System.out.println("|                          |");
		System.out.println("| -> q: Quit               |");
		System.out.println("| -> r: Reset              |");
		System.out.println("|                          |");
		System.out.println("+--------------------------+");
		System.out.println("");
	}
	
	
	
	public static boolean in_array(String e, String[] arr) {
		// True if an element is in array
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(e)) {
				return true;
			}
		}
		return false;
	}
	public static double operation(String op, double n1, double out) {
		//Switch-case syntax for operations
		switch(op) {
		case "+":
			out += n1;
			break;
		case "-":
			out -= n1;
			break;
		case "*":
			out *= n1;
			break;
		case "/":
			out /= n1;
			break;
		case "%":
			out %= n1;
			break;
			
		}
		
		return out;
	}
	
	//Operation validity check
	public static boolean operationCheck(String op) {
		String[] valid_input_op = {"+","-","*","/","%","q","r"};
		boolean op_val = false;
		if (in_array(op, valid_input_op) == true) {
			op_val = true;	
		}
		return op_val;
	}
	
	
	//Input validity check
	public static boolean inputCheck(String s) {
		boolean valid = true;
		int i = 0;
		while(i<s.length()) {
			if(!((s.charAt(i) >= '0' && s.charAt(i) <= '9') | s.charAt(i) == '.'| s.charAt(i) == ',')) {
				valid = false;
			}
			i++;
		}
		return valid;
	}

	
	
	
	
	
	
	//main
	public static void main(String[] args) {
		
		double n1 = 0, out = 0;
		String s = "";
		String op = "";	// +  -  *  /  %
		boolean first = true;
		printInterface();
		
		while (true) {
			// Opening input stream
			Scanner input = new Scanner(System.in);
			op = "";
			System.out.print("Select operation:");
			op = input.nextLine();
			
			
			
			
			while(operationCheck(op) == false) {
				System.out.println("Invalid operation...Try again.");
				System.out.print("Select operation:");
				op = input.nextLine();
			}
			
			
			
			
			//Program ending condition
			if(op.equalsIgnoreCase("q")) {
				System.out.println("Program terminated.");
				System.exit(0);
			}
			
			
			
			//Reset condition
			if(op.equalsIgnoreCase("r")) {
				System.out.println("Reset.");
				out = 0;
				first = true;
				System.out.print("Select operation:");
				op = input.nextLine();	
			}	
			
			
			
			
			//If - else statement to distinguish if the user needs to insert one or two operands
			if (first) {
				System.out.print("First number:");
				s = input.nextLine();
				while(inputCheck(s) == false) {
					System.out.print("Invalid format -> please insert a number:");
					s = input.nextLine();
				}
				out = Double.parseDouble(s);
				
				System.out.print("Second number:");
				s = input.nextLine();
				while(inputCheck(s) == false) {
					System.out.print("Invalid format -> please insert a number:");
					s = input.nextLine();
				}
				n1 = Double.parseDouble(s);
				
				first = false;
			} 
			
			
			else {
				System.out.print("Insert number:");
				s = input.nextLine();
				while(inputCheck(s) == false) {
					System.out.print("Invalid format -> please insert a number:");
					s = input.nextLine();
				}
				n1 = Double.parseDouble(s);
			}
			
			
			out = operation(op, n1, out);
			
			//Print output
			System.out.print("Result: ");
			System.out.println(out);
			
		
		}
	
	}

}

