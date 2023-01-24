import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Project1{

	public static void main(String[] args) throws IOException {
		
		FactoryImpl factoryImpl = new FactoryImpl();
		
		File inFile = new File(args[0]);
		Scanner scan = new Scanner(inFile);
		
		File output = new File(args[1]);
		FileWriter fWriter = new FileWriter(output);
		
		factoryImpl.generate();
		while (scan.hasNextLine()) {
		  String line = scan.nextLine();
		  
		
		  String[] values = line.split(" ");
		  if (values[0].equals("AF")) {
			  int product_id = Integer.parseInt(values[1]);
			  int product_value = Integer.parseInt(values[2]);
		   	  Product newproduct = new Product(product_id, product_value);
		   	  
		   	  factoryImpl.addFirst(newproduct);
		   	  
		  }
		  if (values[0].equals("AL")) {
			  int product_id = Integer.parseInt(values[1]);
			  int product_value = Integer.parseInt(values[2]);
		   	  Product newproduct = new Product(product_id, product_value);
		   	  
		   	  factoryImpl.addLast(newproduct);

		  }
		  if (values[0].equals("A")) {
			  int index = Integer.parseInt(values[1]);
			  int product_id = Integer.parseInt(values[2]);
			  int product_value = Integer.parseInt(values[3]);
		   	  Product newproduct = new Product(product_id, product_value);
		   	  try {
		   		  factoryImpl.add(index, newproduct);
		   	  } catch(IndexOutOfBoundsException e) {
		   		  fWriter.write("Index out of bounds."+"\n");
		   	  }
		  }
		  if (values[0].equals("RF")) {
			  try {
				  fWriter.write(factoryImpl.removeFirst().toString()+"\n");
			  }catch(NoSuchElementException e) {
				  fWriter.write("Factory is empty."+"\n");
			  }
			  
			  
		  }
		  if (values[0].equals("RL")) {
			  try {
				  fWriter.write(factoryImpl.removeLast().toString()+"\n");
			  }catch(NoSuchElementException e) {
				  fWriter.write("Factory is empty."+"\n");
			  }
		  }
		  if (values[0].equals("RI")) {
			  int index = Integer.parseInt(values[1]);

			  try {
				  fWriter.write(factoryImpl.removeIndex(index).toString()+"\n");
			  }catch(IndexOutOfBoundsException e) {
				  fWriter.write("Index out of bounds."+"\n");
			  }
		  }
		  if (values[0].equals("RP")) {
			  int product_value = Integer.parseInt(values[1]);
			  try {
				  fWriter.write(factoryImpl.removeProduct(product_value).toString()+"\n");
			  } catch(NoSuchElementException e) {
				  fWriter.write("Product not found."+"\n");
			  }
		  }
		  if (values[0].equals("F")) {
			  int product_id = Integer.parseInt(values[1]);
			  try {
				  fWriter.write(factoryImpl.find(product_id).toString()+"\n");
			  } catch(NoSuchElementException e) {
				  fWriter.write("Product not found."+"\n");
			  }
		  }
		  if (values[0].equals("G")) {
			  int index = Integer.parseInt(values[1]);
			  try {
				  fWriter.write(factoryImpl.get(index).toString()+"\n");
			  } catch(IndexOutOfBoundsException e) {
				  fWriter.write("Index out of bounds."+"\n");
			  }
		  }
		  if (values[0].equals("U")) {
			  int product_id = Integer.parseInt(values[1]);
			  int product_value = Integer.parseInt(values[2]);
			  try {
				  fWriter.write((factoryImpl.update(product_id, product_value)).toString()+"\n");
			  } catch(NoSuchElementException e){
				  fWriter.write("Product not found."+"\n");
			  }
		  }
		  if (values[0].equals("FD")) {
			  fWriter.write(factoryImpl.filterDuplicates()+"\n");
		  }
		  if (values[0].equals("R")) {
			  factoryImpl.reverse();
              fWriter.write(factoryImpl.printAll()+"\n");

		  }
		  if (values[0].equals("P")) {
			  
			  fWriter.write(factoryImpl.printAll()+"\n");
		  }
		 
		  		  
		}
		scan.close();
        fWriter.close();
	}
}