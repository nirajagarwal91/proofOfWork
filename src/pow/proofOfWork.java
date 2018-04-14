package pow_m12511318;
// Java Libraries 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class proofOfWork {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int difficulty;
		String message = getFileData("input.txt");
		System.out.println("Message:="+ message);
		
		// Input Difficulty to store the target in file target.txt
		System.out.println("Enter Difficulty (Should be Less than 256) :: ");
		Scanner inputDifficulty = new Scanner(System.in);
		difficulty = inputDifficulty.nextInt();
		targetGenerationFunction(difficulty);
		inputDifficulty.close();
		
		//Get information for the target
		String target = getFileData("target.txt");
		System.out.println("Target ::" + target + "\nLength :: "+ target.length());
		
		//Generate Random Number to test
		String randomNumber = randomSolution();
				
		//Convert the generated HashString Generated from Hexa Decimal to Binary.
		String hexString = generateHashSHA256(message+randomNumber);
		String binaryConvertedString = convertHexToBinary(hexString);
		
		//To generate Solution such that Hash(messsage || solution) <= target
		double startTime, endTime;
		startTime = System.currentTimeMillis();
		while(true) {
			if(binaryConvertedString.compareTo(target)<=0) {
				generateSolution(randomNumber);
				System.out.println("Hash :: "+ hexString + "\nLength :: "+ hexString.length());
				System.out.println("As binary: "+binaryConvertedString + "\nLength = "+ binaryConvertedString.length());
				System.out.println(randomNumber);
				break;
			}
			else {
				randomNumber = randomSolution();
				hexString = generateHashSHA256(message+randomNumber);
				binaryConvertedString = convertHexToBinary(hexString);
			}
		}
		endTime = System.currentTimeMillis();
		
		System.out.println("For difficulty of "+ difficulty + "time taken is = " + (endTime - startTime) + " ms");
		
		// Verification
		
		System.out.println("================================= VERIFICATION OF THE SOLUTION ======================================");
		System.out.println();
		boolean verify = verificationFunction(getFileData("input.txt"),getFileData("solution.txt"),target);
		if(verify) {
			System.out.println("Returned 1. Solution is Correct !!");
		}
		else {
			System.out.println("Returned 0. Solution is Incorrect !!");
		}
	}
	
	// Function to get the data from each file in the Data subfolder.
		public static String getFileData(String filename) throws FileNotFoundException, UnsupportedEncodingException
		{
			File fileFetch = new File("C:\\Users\\Niraj\\eclipse-workspace\\pow_m12511318\\Data\\"+filename);
			Scanner scanFileFetch = new Scanner(fileFetch);
			String textInFile = null;
			while(scanFileFetch.hasNextLine()) 
			{
				textInFile = scanFileFetch.nextLine(); //Reading text data from file
			}
			scanFileFetch.close();
			return textInFile;
		}
		
		// Method to print the message Encrypted or Decrypted to the called text file
		public static void printInFileData(String fileName, String message) throws FileNotFoundException 
		{
			PrintWriter fileStore = new PrintWriter("C:\\Users\\Niraj\\eclipse-workspace\\pow_m12511318\\data\\"+ fileName);
	    	fileStore.print(message);
	    	fileStore.close(); 
		}
		
		// Function to generate targets based on the input difficulty
		public static void targetGenerationFunction(int difficulty) throws FileNotFoundException {
			if(difficulty <= 256) {
				String temp="";
				for(int i=0; i<256; i++) 
				{
					if(i<difficulty) {
						temp = temp + Integer.toBinaryString(0); //appending the integer selected randomly to a string
					}
					else {
						temp = temp + Integer.toBinaryString(1);
					}
				}
				printInFileData("target.txt", temp);
			}
			else {
				System.out.println("Difficulty cannot be more than 256. Hashing used is SHA256");
			}
		}
		
		// Function that generates hash vaue given the input message
		private static String hashStringGenetare(String message, String algorithm) throws Exception {
			try {
				MessageDigest digest = MessageDigest.getInstance(algorithm);
	            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
	            return convertBytesToHex(hashedBytes);
	        	}
			catch (Exception ex) {
	            throw new Exception("Could not generate hash from String", ex);
	        }
	    }
		
		// Function to Convert Binary to Hexadecimal
		public static String convertBytesToHex(byte[] bytemessage)
		{
			return DatatypeConverter.printHexBinary(bytemessage);
		}
		
		// Function that generates hash string value using SHA256
		public static String generateHashSHA256(String message) throws Exception {
	        return hashStringGenetare(message, "SHA-256");
	    }
		
		// Function to generate a random solution to generate a hash value
		public static String randomSolution() {
			Random rand = new Random();
			int  randomNumber = rand.nextInt((int) Math.pow(2, 32));
			return Integer.toString(randomNumber);
		}
		
		// Function that generates the solution and stores it in file solution.txt
		public static void generateSolution(String number) throws FileNotFoundException {
			printInFileData("solution.txt", number);
		}
		
		// Function to convert Hexadecimal to Binary digits and pads 0's in the beginning if the length is less than 256
		public static String convertHexToBinary(String s) {
			String bin = new BigInteger(s, 16).toString(2);
		    Integer length = bin.length();
		    if (length < 256) {
		        for (int i = 0; i < 256 - length; i++) {
		            bin = "0" + bin;
		        }
		    }
		    return bin;
		}
		
		// Verification function that verifies the generated solution
		public static boolean verificationFunction(String input, String solution, String target) throws Exception {
			String hexString = generateHashSHA256(input+solution);
			String binaryConvertedString = convertHexToBinary(hexString);
			if(binaryConvertedString.compareTo(target)<=0) {
				return true;
			}
			else {
				return false;
			}
		}
}
