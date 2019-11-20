import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;


class DataSetGenerator
{
    public static void main(String args[])throws IOException,FileNotFoundException {


        int i = 1;
        while( i < 11)
        {
            File file = new File("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/DataFiles/random"+ i + ".txt");
        
            // creates the file
            file.createNewFile();
        
            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file); 
        
            Random gen = new Random();
            writer.write(i*28+"\n");
            for(int j=0;j<i*28;j++)
            {
               int val = gen.nextInt(100000) & Integer.MAX_VALUE;
               writer.write(val+"\n"); 
            }

            writer.flush();
            writer.close();
            
            i++;
        
        }
        
     }
}