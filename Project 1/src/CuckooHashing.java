
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Implementing cuckoo hashing
 * capacity of 15 for each hash table.
 *
 * Implemented using a 2-D array with capacity specified.
 * 
 * 
 * Hash fn1 : (key / prime1) % prime2
 * Hash fn2 : 3*(key / prime2) % prime1
 * 
 * prime1 = 11
 * prime2 = 13
 * 
 * Will print if it errors and we need to change hashing method for any input.
 * 
 */
class CuckooHashing
{

    /* Random list of prime numbers 
    661    673    677    683    691    701    709    719    727    733 
    739    743    751    757    761    769    773    787    797    809 
    811    821    823    827    829    839    853    857    859    863 
    877    881    883    887    907    911    919    929    937    941 
    947    953    967    971    977    983    991    997   1009   1013 
   1019   1021   1031   1033   1039   1049   1051   1061   1063   1069 
   1087   1091   1093   1097   1103   1109   1117   1123   1129   1151 
   1153   1163   1171   1181   1187   1193   1201   1213   1217   1223 
   1229   1231   1237   1249   1259   1277   1279   1283   1289   1291 
   1297   1301   1303   1307   1319   1321   1327   1361   1367   1373 
   1381   1399   1409   1423   1427   1429   1433   1439   1447   1451 
   1453   1459   1471   1481   1483   1487   1489   1493   1499   1511 
   1523   1531   1543   1549   1553   1559   1567   1571   1579   1583 
    */
    private static int constA = -1;
    private static int constB = -1;

    private static int primeA = 379;
    private static int primeB = 443;

    // Global variable for maintaining data set
    private static int k = 1;

    private static boolean flag = true;

    private static int inserts = 0;

    // Number of Hash tables being used = 2
    private static final int versions = 2;

    // Maximum number of elements in either table
    private static final int capacity = 200;

    private static int[][] hashtable = new int[2][capacity];

    // Init method for the hash table
    public static void initHash()
    {
        Random gen = new Random();
        
        constA = gen.nextInt(100);
        constB = gen.nextInt(100);

        if(constA < 0)
            constA = constA * -1;
        if(constB < 0)
            constB = constB * -1;

        for(int i=0;i<versions;i++)
        {
            for(int j=0;j<capacity;j++)
            {
                hashtable[i][j] = -1;
            }
        }
    }


    private static int nextPrime(int input){
        int counter;
        input++;
        int l = (int) Math.sqrt(input);
        while(true){
          counter = 0;
          for(int i = 2; i <= l; i ++){
            if(input % i == 0)  counter++;
          }
          if(counter == 0)
            return input;
          else{
            input++;
            continue;
          }
        }
      }

    public static void printHash()
    {
        System.out.println();
        for(int i=0;i<versions;i++)
        {
            for(int j=0;j<capacity;j++)
            {
                if(hashtable[i][j] != -1)
                    System.out.print(hashtable[i][j] + " ");
                else
                    System.out.print("-  ");
            }
            System.out.println();
        }
    }

    // Method which will return hash key
    private static int hash(int table,int value)
    {
        switch(table)
        {
            case 0 : return  ((constA + value) % primeA) % capacity;
            case 1 : return  ((constB + constA * value) % primeB) % capacity;
        }
        return -1;
    }

    private static void rehash()
    {
        primeA = nextPrime(primeA);
        primeB = nextPrime(primeB);
    }

    // The method which loops through and repositions the keys in the table.
    // If a cycle is found, it breaks and asks to change hash method.
    /**
     * 
     * @param key -> Hash Key for the table
     * @param value -> Value to be inserted in table
     * @param table -> 0 or 1
     * @param cycleCount -> number of cycles in the current loop
     * @param maxCount -> max cycles permitted
     * 
     * We return that a cycle is present if the cycle count exceeds 100
     */
    private static void putInHash(int key, int value, int table,int cycleCount,int maxCount)
    {
        inserts++;
        if(cycleCount > 10)
        {
            flag = false; 
        }

        if(flag)
        {
           // The position in the table is unoccupied so it is fine to directly place in the spot
            if(hashtable[table][key] == -1)
                hashtable[table][key] = value;
            else
            {
                int resValue = hashtable[table][key];
                int newTable = table == 0 ? 1 : 0;
                hashtable[table][key] = value;
                putInHash(hash(newTable,resValue),resValue,newTable,cycleCount + 1,maxCount);
            }
        }
        else
        {
           // System.out.println("Rehashing the values");
            rehash();
           // System.out.println("New primes are: "+primeA + "   " + primeB);
            runner();
            return;
        }
        



    }


    public static void cuckoo(int[] input)
    {   
        for(int i=0;i<input.length;i++)
        {
            putInHash(hash(0,input[i]),input[i],0,0,capacity);
        }
        k++;
        // printHash();
    }

    public static void runner()
    {      
        if(k > 7)
            return;

            File file = new File("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/DataFiles/random"+k+".txt");
            try 
            {
                inserts = 0;
                BufferedReader br = new BufferedReader(new FileReader(file));
                int maxSize = Integer.parseInt(br.readLine());

                int[] input = new int[maxSize];
                int i=0;
                // read line by line
                String line;
                while ((line = br.readLine()) != null && i < maxSize) {
                    input[i] = Integer.parseInt(line);
                    i++;
                }
                br.close();
                flag = true;
                initHash();
                cuckoo(input);

                double alpha = 1.00 * maxSize / (2 * capacity) ;

                System.out.println("Load Factor : " + alpha);
                System.out.println("Keys accessed : " + inserts);

                System.out.println();
            
                BufferedWriter writer = new BufferedWriter(new FileWriter
                        ("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/outputFiles/output_new_final.txt", true)); 
                writer.newLine();
                writer.write(alpha+","+inserts);
                writer.flush();
                writer.close();


            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        
    }

    public static void main(String[] args)
    {
        k = 1;
        while(k < 7)
        {
           // runner();
            File file = new File("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/DataFiles/random"+k+".txt");
            
            try 
            {
                BufferedReader br = new BufferedReader(new FileReader(file));
                int maxSize = Integer.parseInt(br.readLine());

                int[] input = new int[maxSize];
                int i=0;
                // read line by line
                String line;
                while ((line = br.readLine()) != null && i < maxSize) {
                    input[i] = Integer.parseInt(line);
                    i++;
                }
                br.close();
                flag = false;
                initHash();
                cuckoo(input);

                double alpha = 1.00 * maxSize / (2 * capacity) ;

                System.out.println("Load Factor : " + alpha);
                System.out.println("Keys accessed : " + inserts);
            
                BufferedWriter writer = new BufferedWriter(new FileWriter
                        ("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/outputFiles/output_new_final.txt", true)); 
                writer.newLine();
                writer.write(alpha+","+inserts);
                writer.flush();
                writer.close();


            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
            k++;
        }
        
    }

}