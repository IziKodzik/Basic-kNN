package mainPack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {

		if(args.length != 3)
			System.exit(-1);

		int k = - 1;
		try{
			k = Integer.parseInt(args[0]);
		}catch (NumberFormatException e){
			System.out.println("First argument is invalid.");
			System.exit(-1);
		}
		BufferedReader trainFile = null;
		try {
			 trainFile = new BufferedReader(new FileReader(args[1]));
		}catch (IOException e){
			System.out.println("Problem with argument: " + args[1]);
			System.exit(-2);
		}
		BufferedReader testFile = null;
		try{
			testFile = new BufferedReader(new FileReader(args[2]));
		}catch (IOException e){
			System.out.println("Problem with argument: " + args[2]);
			System.exit(-3);
		}

		System.out.println("Program arguments are valid.");

		List<Flower> oldFlowers = new ArrayList<>();
		for(String line = trainFile.readLine(); line != null; line = trainFile.readLine())
			oldFlowers.add(parseTestLine(line));

		List<Flower> newFlowers = new ArrayList<>();
		for(String line = testFile.readLine(); line != null; line = testFile.readLine()) {
			newFlowers.add(parseTestLine(line));
			System.out.println();
		}

		int guessed = 0,
				misGuessed = 0;

		for(Flower f : newFlowers) {
			System.out.println(f.name);
			System.out.println(f.recognize(k, oldFlowers) + " recognized");
			if(f.name.equals(f.recognize(k,oldFlowers)))
				++guessed;
			else
				++misGuessed;
		}

		System.out.println("Guessed: " +guessed+ " of: " + (guessed+misGuessed));

		Scanner scanner = new Scanner(System.in);


		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Waiting for input flower properties/exit.");
		System.out.println("Format of flower properties:");
		System.out.println("prop0,prop1,prop2,...,propn");
		System.out.println("No more properties than in train-set file.");

		for(String line = scanner.nextLine(); !line.toLowerCase().equals("exit"); line = scanner.nextLine()){

			try {
				Flower flower = parseHandLine(line);
				flower.name = flower.recognize(k,oldFlowers);
				System.out.println("I guess the flower is " + flower.name);

			} catch (NumberFormatException e){
				System.out.println("Wrong properties format.");
			}
		}


	}

	public static Flower parseHandLine(String line)
	throws NumberFormatException {

		List<Double> properties = new ArrayList<>();
		String[] splitResult = line.split(",");
		for(int op = 0 ; op < splitResult.length; ++op){
				properties.add(Double.valueOf(splitResult[op]));
		}
		return new Flower(properties,null);

	}
	public static Flower parseTestLine(String line){

		List<Double> properties = new ArrayList<>();
		String[] splitResult = line.split(",");
		for(int op = 0 ; op < splitResult.length-1;++op) {
			try {
				properties.add(Double.valueOf(splitResult[op]));
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		return new Flower(properties,splitResult[splitResult.length-1]);

	}

}
