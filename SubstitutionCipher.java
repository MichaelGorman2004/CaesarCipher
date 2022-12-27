
import java.io.*;
import java.util.*;

public class SubstitutionCipher {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // uppercase string
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz"; //lowercase string

    public static String shift(String input, int shift) {
        shift = shift%26; //turns shift into smallest equivalent version
        //below makes all negative shifts into equivalent positive value by adding the negative value to 26
        if(shift < 0) {
           shift = 26 + shift;
           }
           
           StringBuilder shiftString = new StringBuilder(); //stringbuilder initialization
           int index = -1; //initializes index at an unrealist place
           char letter; //initializes letter char variab;e
           for(int i = 0; i < input.length(); i ++) { //runs through string index by index
              
              if(Character.isLetter(input.charAt(i))) { //if the char at is a letter
                 
                 if(Character.isUpperCase(input.charAt(i))) { //if letter is uppercase
                    
                    index = UPPERCASE.indexOf(input.charAt(i)); //index is the index of char at i of uppercase
                    
                    if( (25 - index) >= shift ) { //if the difference between last index and the index of char is great than shift
                       
                       letter = UPPERCASE.charAt(index + shift); //just add the shift
                       shiftString.append(letter); //append to stringbuilder
                       
                       }else if( (25 - index) < shift) { //if difference less than shift
                          letter = UPPERCASE.charAt(index - (26 - shift)); // subtract difference between 26 and shift from the index 
                         shiftString.append(letter); //append
                          }
                       
                    }else{
                       
                      index = LOWERCASE.indexOf(input.charAt(i)); //if not uppercase
                      if( (25 - index) >= shift ) { //if the difference between last index and the index of char is great than shift
                       
                       letter = LOWERCASE.charAt(index + shift); //index + shift
                       shiftString.append(letter); //append
                       
                       }else if( (25 - index) < shift) { //if less than shift
                          letter = LOWERCASE.charAt(index - (26 - shift)); //subtract difference of 26 and shift from index
                         shiftString.append(letter); //append
                          }
                      
                       }
                    
                 }else { //if not letter
                    
                    shiftString.append(input.charAt(i)); //append char
                    
                    }
           }
        
        return shiftString.toString(); //return stringbuilder as a string
    }

    public static String promptForString(Scanner input, String promptMsg) {
       
        System.out.print(promptMsg);//prints prompt message
        String userString = input.nextLine();//takes user input
        
        while(userString.isEmpty()) {//while user input is empty, print statement and re-ask for it until not empty
           
           System.out.println("ERROR! Empty Input Not Allowed!");
           System.out.print(promptMsg);
           userString = input.nextLine();
           
           }
           
      return userString; //return user string
      
    }

    public static boolean transformFile(String inFile, String outFile, int shift) {
        boolean ran = false; //sets boolean to false
        try {
           File fileString = new File(inFile);//declares File
           Scanner input = new Scanner(fileString); //declares scanner
           String fileLine;//initializes string to temporarily store in within while loop
           File writeFile = new File(outFile);//initializes filewriter
           PrintWriter fileWrite = new PrintWriter(writeFile); //initializes printwriter
           while(input.hasNextLine()) { //while more input to take
              fileLine = input.nextLine();//store a string
              fileWrite.println(shift(fileLine, shift));//write to file of shifted version
              }
           input.close();//close scanner
           fileWrite.close();
           ran = true;//changes boolean to true if ran
           }catch(Exception e) {
              System.out.println("ERROR! File inFile not found or cannot write to outFile");//error statement
              }
        
        return ran; //return boolean
        
    }

    public static char getChoice(Scanner input) {
        String stringChoice = input.nextLine();//get input from scanner
        
        char choice = stringChoice.charAt(0);// change input to a char
        choice = Character.toUpperCase(choice);//change char to uppercase
        boolean wrongLetter = true;//sets condition for while loop
        while(wrongLetter) {
           if(choice == 'E' || choice == 'D' || choice == 'Q') {//if choice is correct
              wrongLetter = false;//end loop
              }else {//print error statement, retake user input and run loop again
           System.out.println("ERROR! Enter a valid value!");
           stringChoice = input.nextLine();
           choice = stringChoice.charAt(0);
              }
           }
        
         
        return choice;//return user choice in uppercase 
    }

    public static void displayMenu() {//automatic display method to give users their choices
        System.out.println("[E]ncode a file");
        System.out.println("[D]ecode a file");
        System.out.println("[Q]uit");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);//declare scanner
        
        String inputFile;//initialize input file string
        String outFile;//initialize out file string
        int shift;//initialize shift 
        boolean keepRunning = true;//sets while loop condition
        while(keepRunning) {
           displayMenu();//start by displaying menu
           System.out.print("Enter your choice: "); //request user choice
           char choice = getChoice(in);//get user choice and store to choice char
           if(choice == 'E') {//if encode file
              inputFile = promptForString(in, "Enter an input file: ");//stores input file
              outFile = promptForString(in, "Enter an output file: "); //stores output file
              shift = Integer.parseInt(promptForString(in, "Enter a shift amount: "));//store shift amount
              transformFile(inputFile, outFile, shift);//shift the in file and store it in outfile
              System.out.print("Finished writing to file.");//print finish 
              System.out.println();
              System.out.println();//two lines for formatting
              }else if (choice == 'D') {//if user wants to decode a file
              inputFile = promptForString(in, "Enter an input file: "); //store input file
              outFile = promptForString(in, "Enter an output file: "); //store output file
              shift = Integer.parseInt(promptForString(in, "Enter a shift amount: "));//store shift amount
              transformFile(inputFile, outFile, shift * -1);//shift the in file and store it in outfile
              System.out.print("Finished writing to file.");//print finish 
              System.out.println();
              System.out.println();//two lines for formatting
                 }else if(choice == 'Q') {//if user quits
                    System.out.println();//new line formatting
                    keepRunning = false;//end loop
                    System.out.println("Goodbye!");//print goodbye
                    }
           }

        in.close();//close scanner
    }

}