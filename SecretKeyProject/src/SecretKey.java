//Eric Goodwin E01

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class SecretKey {
	
	public static void main(String[] args) {
		
		Person sender = new Person();  
		Person receiver = new Person();
		Message secretMessage = new Message();
		
		
		System.out.println("Generating Secret Key");
		secretMessage.generateSecretKey(); //generates the secret key
		//System.out.println(secretMessage.getSecretKey());  											TESTING ONLY
		
		sender.setSecretKey(secretMessage.getSecretKey()); //sets the secret key for the sender
		sender.setMessageToSend(); //sender creates the messages
		
		sender.setEncryptedMessage(secretMessage.encryptText(sender.getMessagetoSend(),sender.getSecretKey())); //encrypts message.  uses data fields from sender object
		
					
		
		System.out.println("Message sent: ");
		System.out.println(sender.getEncryptedMessage());
		
		
		receiver.setSecretKey(sender.getSecretKey()); //sets the secret key for the receiver from the sender.  used to simulation for the receiver to validate
		receiver.setEncryptedMessage(sender.getEncryptedMessage()); //sets encrypted message for the receiver from the sender 
	
		System.out.println();
	
		
		System.out.println();
		System.out.println("Validating secret key");
		secretMessage.setreceivedKey(receiver.getSecretKey()); //sends receivers secret key to secret message class 
	
		if (secretMessage.verifySecretKey(secretMessage.getreceivedKey())){; //verifies receiver has correct secret  key
		System.out.println("Secret Key Valid"); //in this simulation, will always be true
		System.out.println();
		}else{
			System.out.println("Secret Key Invalid.");
		}
			
		System.out.println("Decrypting Messsge");
		
		secretMessage.decryptCode( receiver.getEncryptedMessage(), receiver.getSecretKey());  //decrypts the secret message and displays normal text
		

		
		System.out.println("----------------------");
		System.out.println("Secret key used in this simulation:");
		secretMessage.printsecretKey();
	
		
	}
}



class Person{
	
	private String MessagetoSend;
	private char[] secretKeytoSend = new char[9];
	private StringBuilder encryptedMessage;
	
	void setMessageToSend(){
		System.out.println("Enter message to send:");
		Scanner reader = new Scanner(System.in);
		MessagetoSend = reader.nextLine();
		}

	String getMessagetoSend(){
		return MessagetoSend;
		}
	
	void setEncryptedMessage(StringBuilder encryptedMessage){
		this.encryptedMessage = encryptedMessage;
	}
	
	StringBuilder getEncryptedMessage(){
		return encryptedMessage;
	}
	
	void setSecretKey(char [] secretKey){
		this.secretKeytoSend = secretKey;
	}
	
	char [] getSecretKey(){
		return secretKeytoSend;
	}

	
}

class Message{
	
	private char[] secretKey = new char[9]; 	 //format  x1x2-y1x3x4-zy2
	private String message;
	private StringBuilder encryptedText;
	private char [] receivedKey = new char[9];
	private String saveEndingPunctuation = " ";
	
	//Methods
		
	StringBuilder encryptText(String message, char [] secretKey){
		
		if (message.matches(".*[.?!]")){		
		//	System.out.print(message.substring(message.length() - 1 )); 								TESTING ONLY
			saveEndingPunctuation = message.substring(message.length() - 1 );
		//	System.out.println("Punctuation Saved: " + saveEndingPunctuation);							TESTING ONLY
			}
			
		//remove punctuation
		message = message.replaceAll("\\p{Punct}+", "");
														//System.out.println(message);					TESTING ONLY
		
		//convert to lower case
		message = message.toLowerCase();
		String [] tokens = message.split(" ");
														/* 	BEGIN TESTING
														for (int i = 0; i <= tokens.length-1; i++){
														System.out.println(tokens[i]);
														}
		 												END TESTING
														 */
		
		//move first letter of each word to the end of the word
		for (int i = 0; i <= tokens.length - 1; i++){
		//System.out.println(tokens[i]);			TESTING ONLY
			
			try {  //added this because if user enters a special character at the start of the phrase, charAt() will through StringIndexOutOfBoundsException.  Program exits of this exception is thrown
			char firstLetter = tokens[i].charAt(0);
				
			if (tokens[i].matches("[^aeiou].*")){  //checks of if first character is NOT a vowel. if true, moves character to end of the word
				tokens[i] = tokens[i].substring(1);
				tokens[i] = tokens[i] + firstLetter + secretKey[0] + secretKey[1]; //adds x1x2 to end of word
			} else {
				tokens[i] = tokens[i] + secretKey[3] + secretKey[4] + secretKey[5]; //adds y1x3x4 to end of word
			}
			} catch (StringIndexOutOfBoundsException e){
				System.out.println("Invalid Entry. May not entry special characters are beginning of phrase.  Exiting ... ");
				System.exit(0);
			}
			
		//System.out.println(tokens[i]);  																TESTING ONLY
		}
		
		//build the string
		StringBuilder encryptedMessage = new StringBuilder();
		for (int i = 0; i <= tokens.length - 1; i++){
			encryptedMessage.append(tokens[i] + " ");

		}
		
		
		//inserts special character at multiples specified in secret key
		for (int i = 1; i <= encryptedMessage.length(); i++){ 
			if ( i % Character.getNumericValue(secretKey[7]) == 0){
				encryptedMessage.insert(i, secretKey[8]);
			}
		}
		
		String tempString = encryptedMessage.toString().trim(); //necessary to remove the last whitespace created by the final token in Stringbuilder
	//	System.out.println(tempString);  																TESTING ONLY
		
		encryptedMessage = encryptedMessage.replace(0, encryptedMessage.length(), tempString);
		encryptedMessage = encryptedMessage.append(saveEndingPunctuation);
		
	//	System.out.println(encryptedMessage); 															TESTING ONLY
		 
		return encryptedMessage;
	}
	
	void decryptCode(StringBuilder encryptedText, char [] secretKey){
				
		String decryptString = encryptedText.toString();  //convert StringBuilder to String 
		decryptString = decryptString.replaceAll("\\p{Punct}+", ""); //in order to remove the trailing punctuation that the encrypter adds back on
		String [] decryptTokens = decryptString.split(" "); //tokenize 
		
		
		if (decryptString.matches(".*[.?!]")){		
		//		System.out.print(decryptString.substring(decryptString.length() - 1 )); 				TESTING ONLY
				saveEndingPunctuation = decryptString.substring(decryptString.length() - 1 );
		//		System.out.println("Punctuation Saved: " + saveEndingPunctuation);						TESTING ONLY
				decryptString = decryptString.substring(0, decryptString.length());
			}
		
		
		
		//these two lines change the dash as it appears in the key format to ~ in order to split the key into tokens that I can use to match during decryption
		secretKey[2] = 126;
		secretKey[6] = 126;		
		
		String decryptKey = new String(secretKey);
		String [] keyTokens = decryptKey.split("~");
		
		
		
		
	/*	 for (int i = 0; i < decryptTokens.length; i++){ 												TESTING ONLY
			System.out.println(decryptTokens[i]);            											TESTING ONLY
		}																								TESTING ONLY
		 																								TESTING ONLY
		System.out.println(decryptKey);																	TESTING ONLY
		 																								TESTING ONLY
		 for (int i = 0; i < keyTokens.length; i++){													TESTING ONLY
			 System.out.println(keyTokens[i]);															TESTING ONLY
		 }																								TESTING ONLY
																										TESTING ONLY
	*/																								
		
		 //removes the special characters from the encrypted message
		for (int i = 0; i < decryptTokens.length; i++){
			decryptTokens[i] = decryptTokens[i].replace(String.valueOf(secretKey[8]), "");
			}
		
		for (int i = 0; i < decryptTokens.length; i++){
			//purpose of next two lines is to allow the second if statement to match x3x4 in the secretkey to any token that started with a vowel.  this is necessary because earlier in the decryption sequence, all punctuation is removed
			String tempToken = keyTokens[1];
			tempToken = tempToken.replaceAll("\\p{Punct}+", "");
				
			if (decryptTokens[i].contains(keyTokens[0])){
					decryptTokens[i] = decryptTokens[i].replace(keyTokens[0], "");
			//		System.out.println("After first keyToken removed: " + decryptTokens[i]); 			TESTING ONLY
					char lastLetter = decryptTokens[i].charAt(decryptTokens[i].length()-1);
			//		System.out.println("Last Letter is: " + lastLetter);								TESTING ONLY
			//		System.out.println("decryptTokens: " + decryptTokens[i]);							TESTING ONLY
					decryptTokens[i] = lastLetter + decryptTokens[i];	
					decryptTokens[i] = decryptTokens[i].replaceFirst(".$", "");
			//		System.out.println("After for loop: " + decryptTokens[i]);							TESTING ONLY
				}
				
				if (decryptTokens[i].contains(tempToken)){
					decryptTokens[i] = decryptTokens[i].replace(tempToken, "");
			//		System.out.println("After for loop: " + decryptTokens[i]);							TESTING ONLY
				}
		}
		
		
			
	//	System.out.println(decryptTokens[0]);														TESTING ONLY
		
			StringBuilder decryptedText = new StringBuilder();
			for (int i = 0; i < decryptTokens.length; i++){
				decryptedText.append(decryptTokens[i] + " ");
			}
			
			
			String tempString = decryptedText.toString().trim();	  //necessary to remove the last white space in the final token added to the StringBuilder
			decryptedText = decryptedText.replace(0, decryptedText.length(), tempString);
			decryptedText = decryptedText.append(saveEndingPunctuation);
		
			
			System.out.println(decryptedText);
			
			//change the ~ back to -
			secretKey[2] = 45;
			secretKey[6] = 45;	
			
		}
	
	void generateSecretKey(){
			
		
		secretKey[0] = (char) getrandomAlpha();
		secretKey[1] = (char) getrandomAlpha();
		secretKey[2] = 45;
		secretKey[3] = (char) getrandomSpecial(); 
		secretKey[4] = (char) getrandomAlpha();
		secretKey[5] = (char) getrandomAlpha();
		secretKey[6] = 45;
		secretKey[7] = (char) getrandomNumber();
		secretKey[8] = (char) getrandomSpecial();
	
		}
	
	void setreceivedKey(char [] secretKey){
		this.receivedKey = secretKey;
	}
	
	char [] getreceivedKey(){
		return receivedKey;
	}
	
	boolean verifySecretKey(char [] secretKey){
			return Arrays.equals(getreceivedKey(), getSecretKey());
	
	}
	
	void setMessage(String message ){
		this.message = message; 
	
	}
	
	int getrandomAlpha(){
		
		boolean proceed = true;
		int x;
		
		do{
		Random ran = new Random();
		x = ran.nextInt(58) + 65;
		// System.out.println(x); testing
		
		//this code rejects any random numbers that generate 91 - 96 to exclude the corresponding ASCII characters
		//as a result, obtain a random number that will correspond to ASCII characters A-Z or a-z
		if (x <= 90 || x >= 97){
			proceed = false;
		}
				
		} while (proceed);
		
		
		return x;
	}
	
	int getrandomNumber(){
		Random ran = new Random();
		int y = ran.nextInt(4) + 50;
		//System.out.println(y); 		
		
		return y;
	}
	
	int getrandomSpecial(){
		boolean proceed = true;
		int z;
		
		do{
		Random ran = new Random();
		z = ran.nextInt(32) + 33;	
	//	System.out.println(z); TESTING ONLY	
			if ( z <= 47 || z >= 58){
				proceed = false;
			}
		
		} while (proceed);
		
		
		return z;
		
	}
	
	char [] getSecretKey(){
		
		return secretKey;
		
	}
	
	String getMessage(){

		return message;
	}

	void setEncryptedText(){
		encryptedText = encryptText(getMessage(),getSecretKey());
		}
	
	StringBuilder getEncryptedText(){
		return encryptedText;
	}
	
	void printsecretKey(){
		System.out.print("Secret Key: ");
		for (int i = 0; i < secretKey.length; i++){
		System.out.print(secretKey[i]);
		}
	}
	
}
