import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class SecretKey {
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Person sender = new Person();  
		Person receiver = new Person();
		Message secretMessage = new Message();
		
		
		
		
		System.out.println("Generating Secret Key");
		secretMessage.generateSecretKey(); //generates the secret key
		//System.out.println(secretMessage.getSecretKey());  TESTING ONLY
				
		sender.setSecretKey(secretMessage.getSecretKey()); //sets the secret key for the sender
		sender.setMessageToSend(); //sender creates the messages
		
		
		sender.setEncryptedMessage(secretMessage.encryptText(sender.getMessagetoSend(),sender.getSecretKey())); //encrypts message.  uses data field from sender object
		
		

		
		receiver.setSecretKey(sender.getSecretKey()); //sets the secret key for the receiver from the sender.  used to simulation for the receiver to validate
		receiver.setEncryptedMessage(sender.getEncryptedMessage()); //sets encrypted message for the receiver from the sender 
		
		
		System.out.println("Encrypted message received:");
		System.out.println(receiver.getEncryptedMessage());
		System.out.println();
		System.out.println("Enter secret key:");
		System.out.println(receiver.getSecretKey());
		
		System.out.println();
		System.out.println("Valdidating secret key");
		secretMessage.setreceivedKey(receiver.getSecretKey()); //sends receivers secret key to secret message class 
		if (secretMessage.verifySecretKey(secretMessage.getreceivedKey())){; //verifies receiver has correct secret  key
		System.out.println("Secret Key Valid"); //in this simulation, will always be true
		}else{
			System.out.println("Secret Key Invalid.");
		}
			
		System.out.println("Decrypting Messsge");
		// DECRYPTION METHOD HRRE
		//System.out.println(secretMessage.readText(secretMessge.getencryptedText);)
		
		
		

		
		
		
	
	//	secretMessage.setMessage(sender.getMessagetoSend());

	//	secretMessage.setEncryptedText();
	//	sender.setEncryptedMessage(secretMessage.getEncryptedText());
		
		
	//	System.out.print(secretMessage.getEncryptedText());
		
		
		
	//	secretMessage.getSecretKey();
		//secretMessage.getrandomSpecial();
		
		

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
	
	private char[] secretKey = new char[9]; 			 //format  x1x2-y1x3x4-zy2
	private String message;
	private StringBuilder encryptedText;
	private char [] receivedKey = new char[9];
	
	//Methods
	
	void readText(String encryptedText){
		
	}
	
	StringBuilder encryptText(String message, char [] secretKey){
		//remove punctuation
		message = message.replaceAll("\\p{Punct}+", "");
														//System.out.println(message); TESTING ONLY
		
		//convert to lower case
		message = message.toLowerCase();
														//System.out.print		String [] tokens = message.split(" ");
														/* 	BEGIN TESTING
														for (int i = 0; i <= tokens.length-1; i++){
														System.out.println(tokens[i]);
														}
		 												END TESTING
														 */
		
		//move first letter of each word to the end of the word
		for (int i = 0; i <= tokens.length - 1; i++){
		//System.out.println(tokens[i]);
			char firstLetter = tokens[i].charAt(0);
			if (tokens[i].matches("[^aeiou].*")){  //checks of if first character is NOT a vowel. if true, moves character to end of the word
				tokens[i] = tokens[i].substring(1);
				tokens[i] = tokens[i] + firstLetter + secretKey[0] + secretKey[1]; //adds x1x2 to end of word
			} else {
				tokens[i] = tokens[i] + secretKey[3] + secretKey[4] + secretKey[5]; //adds y1x3x4 to end of word
			}
		//System.out.println(tokens[i]);  //TESTING ONLY
		}
		
		//build the string
		
		StringBuilder encryptedMessage = new StringBuilder();
		for (int i = 0; i <= tokens.length - 1; i++){
			encryptedMessage.append(tokens[i] + " ");
		}
		
		
		
			//int offset = Character.getNumericValue(secretKey[7]);
			//System.out.println(offset);
		//	int length = encryptedMessage.length();
			for (int i = 1; i <= encryptedMessage.length(); i++){ 
				if ( i % Character.getNumericValue(secretKey[7]) == 0){
					encryptedMessage.insert(i, secretKey[8]);
				}
			}
	//	System.out.println(encryptedMessage); TESTING
		
		return encryptedMessage;
	}
	
	StringBuilder decryptCode(StringBuilder encryptedText, char [] secretKey){
		
		
		
		
	return null;
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
//		return null;
		
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
	
}
