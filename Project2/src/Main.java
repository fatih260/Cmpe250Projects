import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File inFile = new File(args[0]);
        Scanner scan = new Scanner(inFile);

        File bst_output = new File(args[1]+"_bst.txt");
		FileWriter fWriter_bst = new FileWriter(bst_output);

        File avl_output = new File(args[1]+"_avl.txt");
		FileWriter fWriter = new FileWriter(avl_output);

        withBST<String> bst = new withBST<>();
        withAVL<String> avl = new withAVL<>(fWriter);
        

        String firstline = scan.nextLine();
        String[] firstvalues = firstline.split(" ");
        String root_ip = firstvalues[0];
        bst.BinarySearchTree(root_ip);
        avl.avlTree(root_ip);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] values = line.split(" ");
            if (values.length==0){
                break;
            }
            if(values[0].equals("ADDNODE")){
                String ip_add = values[1];
                fWriter_bst.write(bst.insert(ip_add));
                avl.insert(ip_add);
                
                    
            }else if(values[0].equals("DELETE")){
                String ip_add = values[1];
                fWriter_bst.write(bst.remove(ip_add));
                
                avl.remove(ip_add);
                
            }else if(values[0].equals("SEND")){
                String ip_sender = values[1];
                String ip_receiver = values[2];
                fWriter_bst.write(bst.send(ip_sender, ip_receiver));
                fWriter.write(avl.send(ip_sender, ip_receiver));

            }else {
                continue;
            }
        }
        scan.close();
        fWriter.close();
        fWriter_bst.close();
    }
}
