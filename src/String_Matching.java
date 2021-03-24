
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
//============================ 
// NAME : Ahmed Fahad ALmutairi
// ID : 1740898
//============================

public class String_Matching {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner input = new Scanner(System.in);
        // load the text from input file   by call LoadText() method  and convert to lower case 
        String StringText = LoadText().toLowerCase();
        System.out.println("the length of our text is: " + StringText.length() + "\n---------------------------------------------");
        // ask user to enter number of patterns
        System.out.print("Enter number of patterns: ");
        int numberOfPatterns = input.nextInt();
        System.out.println("_______________________________________________");
        // ask the to enter length of each Pattern  and generate Pattern  by call  generatePattern()
        String Pattern[] = generatePattern(numberOfPatterns, StringText);

        // startTime variable for calculate the time brute force
        long startTime = System.nanoTime();
        
        for (int i = 0; i < numberOfPatterns; i++) {
            BruteForceAlgorithm(Pattern[i], StringText);
        }
        // endTime variable for calculate the time brute force
        long endTime = System.nanoTime();
        
        // TotalTime_for_Brute_Force variable for calculate the time brute force
        long TotalTime_for_Brute_Force=(endTime - startTime) / numberOfPatterns;
        
        System.out.println("Brute Force Algorithm Time taken in nano seconds: "
                + TotalTime_for_Brute_Force);

        Map<String, Integer> ST[] = new Map[numberOfPatterns];
        for (int i = 0; i < numberOfPatterns; i++) {
            ST[i] = CreateShiftTable(StringText, Pattern[i]);
        }
        // startTime variable for calculate the time Horspool
        startTime = System.nanoTime();

        for (int i = 0; i < numberOfPatterns; i++) {
            Horspool(Pattern[i], StringText, ST[i]);
        }
        // endTime variable for calculate the time Horspool
        endTime = System.nanoTime();
        
        // TotalTime_for_Horspool variable for calculate the time brute force
        long TotalTime_for_Horspool=(endTime - startTime) / numberOfPatterns;
        
        System.out.println("Horspool Algorithm Time taken in nano seconds: "
                + TotalTime_for_Horspool);

        if (TotalTime_for_Horspool<TotalTime_for_Brute_Force) {
            System.out.println("\n Horspool Algorithm is better ");
        }
        else System.out.println("\n Brute Force Algorithm is better ");
    }

    public static String LoadText() throws FileNotFoundException {
        // this  method will read and write the text from input file 
        Scanner sc = new Scanner(new File("text.txt"));
        String s = "";
        while (sc.hasNext()) {
            s = s + " " + sc.nextLine();
        }
        return s;
    }

    public static String[] generatePattern(int numberOfPatterns, String StringText) throws IOException {
        // ask the to enter length of each Pattern and generate Pattern and save it in to array Patterns  and retrun it
        String Patterns[] = new String[numberOfPatterns];
        Scanner input = new Scanner(System.in);
        FileWriter fout = new FileWriter("pattern.txt");
        for (int i = 0; i < Patterns.length; i++) {
            System.out.print("Enter the length of pattern " + (i + 1) + " : ");
            int number = input.nextInt();
            int RandomNumber = (int) (Math.random() * (StringText.length() - number));

            Patterns[i] = StringText.substring(RandomNumber, RandomNumber + number);
            fout.append(Patterns[i]);
        }
        fout.close();
        return Patterns;
    }

    public static int BruteForceAlgorithm(String Pattern, String Text) {
        // this Brute_Force  Algorithm
        int j = 0;
        int time = 0;
        for (int i = 0; i < (Text.length() - Pattern.length()); i++) {
            j = 0;
            while (j < Pattern.length() && Text.charAt(i + j) == (Pattern.charAt(j))) {

                time = time + 1;
                j++;
                if (j == Pattern.length()) {
                    return time;
                }
            }
        }
        return time;
    }

    public static int Horspool(String Pattern, String Text, Map<String, Integer> ST) {
        // this Horspool  Algorithm
        int n = Text.length();
        int m = Pattern.length();
        int k = 0;
        char T[] = Text.toCharArray();
        char P[] = Pattern.toCharArray();
        int i = m - 1;

        int c = 0;
        while (i <= (n - 1)) {
            k = 0;
            while (k <= (m - 1) && P[m - 1 - k] == T[i - k]) {
                k = k + 1;
                c = c + 1;
            }
            if (k == m) {
                return c;
            } else {
                i = i + ST.get(Text.substring(i, i + 1));
            }
        }
        return c;
    }

    public static Map<String, Integer> CreateShiftTable(String s, String p) {
        // this method  will generate  ShiftTable
        String stable = makeLetterSet(s).toString();
        char ctable[] = stable.toCharArray();

        int table[] = new int[stable.length()];
        int m = p.length();
        Map<String, Integer> shift = new HashMap<String, Integer>();
        for (int i = 0; i < table.length; i++) {
            shift.put(stable.substring(i, i + 1), m);
        }
        for (int j = 0; j <= m - 2; j++) {
            shift.put(p.substring(j, j + 1), m - 1 - j);
        }
        return shift;

    }

    public static Set<String> makeLetterSet(String str) {
         // this method  will create  Letter Hash Set
        HashSet letters = new HashSet();
        for (int i = 0; i < str.length(); i++) {
            letters.add(str.charAt(i));
        }
        return letters;

    }

}
