
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class LinearProbing
{

    private static int capacity = 400;

    private static int constA = -1;
    private static int constB = -1;

    private static long access = 0L;

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
    private static int prime  = 379;



    private static int[] hash;


    private static void initHash()
    {

        Random gen = new Random();
        
        constA = gen.nextInt(100);
        constB = gen.nextInt(100);

        if(constA < 0)
            constA = constA * -1;
        if(constB < 0)
            constB = constB * -1;

        hash = new int[capacity];
        for(int i=0;i<capacity;i++)
        {
            hash[i] = -1;
        }
    }

    private static int getHashCode(int value)
    {
        return ((constA * constB + value) % prime ) % capacity;
    }


    public static void printHash()
    {
        System.out.println();
        for(int i=0;i<capacity;i++)
        {
            System.out.print(hash[i] + " ");
        }
    }


    private static void putInHash(int key,int value,int count)
    {

        if(count == capacity)
        { 
            System.out.println("Hash table is full");
            return;
        }
         
        if(hash[key] != -1)
            {
                while(count < capacity && hash[key] != -1)
                {
                    count++;
                    access++;
                    key = (key + 1) % capacity;
                }

                if(count == capacity)
                { 
                    System.out.println("Hash table is full");
                    return;
                }
            }
        
            hash[key] = value;
            access++;
                
    }

    public static void linearProbe(int[] input) throws Exception
    {
        initHash();

        for(int i=0;i<input.length;i++)
        {
            putInHash(getHashCode(input[i]), input[i], 0);
        }

        //printHash();
    }


    public static void main(String args[])
    {
        int k = 1;
        while(k < 11)
        {
            File file = new File("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/DataFiles/random"+k+".txt");
            k++;
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
                linearProbe(input);

                double alpha = 1.00 * maxSize / (capacity) ;

                System.out.println("Load Factor : " + alpha);
                System.out.println("Keys accessed : " + access);
            
                BufferedWriter writer = new BufferedWriter(new FileWriter
                        ("/Users/thomasparayil/Desktop/Data Structures Project/Project 1/outputFiles/output_probe_new.txt", true)); 
                writer.newLine();
                writer.write(alpha+","+access);
                writer.flush();
                writer.close();


            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch(IOException e){
                System.out.println(e.getMessage());
            }catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }


}