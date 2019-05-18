import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DES {
    private static String blok = "";
    private static String lewy = "";
    private static String prawy = "";
    private static List<String> bloki = new ArrayList<String>();
    private static File file1;
    //key
    private static String KLUCZ = "";
    private static String L_KLUCZ = "";
    private static String R_KLUCZ = "";
    private static String Obecny_KLUCZ = "";
    static List<String> klucze = new ArrayList<String>();

    //64
    static int[] pierwsza_permutacja = new int[]{58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
    //64
    static int[] odwrotna_permutacja = new int[]{40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};

    //56
    static int[] permutacja_klucza = new int[]{57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};

    //48
    static int[] kompresja = new int[]{14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};

    //48
    static int[] e_bit_selector_table = new int[]{32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};

    static int[] permutacja_P = new int[]{16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};

    //16
    static int[] left_shift = new int[]{1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    static int[][] S1 = new int[][]{
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };

    static int[][] S2 = new int[][]{
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };

    static int[][] S3 = new int[][]{
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}

    };
    static int[][] S4 = new int[][]{
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };

    static int[][] S5 = new int[][]{
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };
    static int[][] S6 = new int[][]{
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };
    static int[][] S7 = new int[][]{
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };
    static int[][] S8 = new int[][]{
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };

    static int[][][] S = new int[][][]{S1, S2, S3, S4, S5, S6, S7, S8};

    private static String permutuj(String tab, int[] permutacja) {
        String result = "";
        for (int i = 0; i < permutacja.length; i++) {
            result += tab.charAt(permutacja[i] - 1);
        }
        return result;
    }

    private static void paginate() {
        String b = bloki.get(bloki.size() - 1);
        if (b.length() < 64) {
            b += "1";
            for (int i = b.length(); i < 64; i++) {
                b += "0";
            }
            bloki.remove(bloki.size() - 1);
            bloki.add(b);
        } else {
            String new_block = "1000000000000000000000000000000000000000000000000000000000000000";
            bloki.add(new_block);
        }
    }

    private static void depaginate() {
        String b = bloki.get(bloki.size() - 1);
        int i;
        for (i = 64; b.charAt(i - 1) != '1'; i--) {
            b = b.substring(0, i);
        }
        b = b.substring(0, i - 1);
        bloki.remove(bloki.size() - 1);
        if (!b.equals("")) {
            bloki.add(b);
        }
    }

    private static String f(int index) {
        String new_right = permutuj(prawy, e_bit_selector_table);
        String napis = "";
        for (int i = 0; i < new_right.length(); i++) {
            int val = new_right.charAt(i) + klucze.get(index).charAt(i);
            val %= 2;
            char znak = (char) (val + 48);
            napis += znak;
        }

        List<String> bloki_S = new ArrayList<String>();
        for (int i = 1; i <= napis.length() / 6; i++) {//napis ma 48 znakow wiec na 6 zeby bylo 8 blokow
            String bl = napis.substring((i - 1) * 6, i * 6);
            bloki_S.add(bl);
        }
        new_right = "";
        for (int i = 0; i < bloki_S.size(); i++) {//6-bitowe bloki wchodza wychodza 4-bitowe
            char[] chars = bloki_S.get(i).toCharArray();
            int i_index = (chars[5] - 48) + (chars[0] - 48) * 2;
            int j_index = (chars[4] - 48) + (chars[3] - 48) * 2 + (chars[2] - 48) * 4 + (chars[1] - 48) * 8;
            int val = S[i][i_index][j_index];
            String s_val = String.format("%4s", Integer.toBinaryString(val)).replace(" ", "0");
            new_right += s_val;
        }
        return new_right;
    }

    private static void shift(int index) {
        StringBuilder sb_left = new StringBuilder(L_KLUCZ);
        StringBuilder sb_right = new StringBuilder(R_KLUCZ);
        for (int i = 0; i < left_shift[index]; i++) {
            char znak = L_KLUCZ.charAt(0);
            sb_left.deleteCharAt(0);
            sb_left.append(znak);
            L_KLUCZ = sb_left.toString();
            znak = R_KLUCZ.charAt(0);
            sb_right.deleteCharAt(0);
            sb_right.append(znak);
            R_KLUCZ = sb_right.toString();
        }
    }

    private static void podzial() {
        L_KLUCZ = KLUCZ.substring(0, KLUCZ.length() / 2);
        R_KLUCZ = KLUCZ.substring(KLUCZ.length() / 2);
    }

    private static void generuj(String klucz) {
        KLUCZ = klucz;
        KLUCZ = permutuj(KLUCZ, permutacja_klucza);
        podzial();
        for (int i = 0; i < left_shift.length; i++) {
            shift(left_shift[i]);
            Obecny_KLUCZ = L_KLUCZ + R_KLUCZ;
            klucze.add(permutuj(Obecny_KLUCZ, kompresja));
        }
    }



    protected static void szyfruj(String klucz, String input) {
//         String key = "1020000000000000000000000000000000000000000000000000000000000008";
        //      123000000000000008
        String data = input;
        bloki.clear();
        bloki.add(data);
        paginate();
        String output = "";
        String key = hexToBin(klucz);
        for (String bl : bloki) {

            data = permutuj(bl, pierwsza_permutacja);

            generuj(key);

            lewy = data.substring(0, 32);
            prawy = data.substring(32);

            for (int i = 0; i < 16; i++) {
                String new_right = permutuj(f(i), permutacja_P);
                String tmp = lewy;

                String c_right = "";
                for (int j = 0; j < lewy.length(); j++) {
                    c_right += (char) ((lewy.charAt(j) + new_right.charAt(j)) % 2 + 48);
                }
                lewy = prawy;
                prawy = c_right;
            }

            blok = prawy + lewy;

            output += permutuj(blok, odwrotna_permutacja);
        }
        System.out.println("Wynik: " + output);

    }


    protected static void deszyfruj(String klucz, String input) {
        String data = input;
        bloki.clear();
        for (int i = 0; i < data.length() / 64; i++) {
            bloki.add(data.substring(i * 64, (i + 1) * 64));
        }
        ArrayList<String> output_blocks = new ArrayList<>();

        String output = "";
        String key = hexToBin(klucz);
        for (String bl : bloki) {

            data = permutuj(bl, pierwsza_permutacja);

            generuj(key);

            lewy = data.substring(0, 32);
            prawy = data.substring(32);

            for (int i = 0; i < 16; i++) {
                String new_right = permutuj(f(15 - i), permutacja_P);
                String tmp = lewy;

                String c_right = "";
                for (int j = 0; j < lewy.length(); j++) {
                    c_right += (char) ((lewy.charAt(j) + new_right.charAt(j)) % 2 + 48);
                }
                lewy = prawy;
                prawy = c_right;
            }

            blok = prawy + lewy;

            output_blocks.add(permutuj(blok, odwrotna_permutacja));
        }
        bloki = output_blocks;
        depaginate();
        String result = "";
        for (String output_block : output_blocks) {
            result += output_block;
        }
        System.out.println("Wynik: " + result);
    }
    private static String hexToBin(String hex){
        String bin = "";
        String binFragment = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for(int i = 0; i < hex.length(); i++){
            iHex = Integer.parseInt(""+hex.charAt(i),16);
            binFragment = Integer.toBinaryString(iHex);

            while(binFragment.length() < 4){
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }
        return bin;
    }

    protected static void szyfrujPlik(String klucz, File file) {


        int tmpI;
        file1=file;
        String tmpS;
        String result = "";
        bloki.clear();
        String data;
        String key = hexToBin(klucz);
        String output = "";
        ArrayList<String> output_blocks = new ArrayList<>();
        String tmp123;
        try {

            FileInputStream fis = new FileInputStream(file);
            for (int i = 1; (tmpI = fis.read()) != -1; i++) {
                tmpS = String.format("%8s", Integer.toBinaryString(tmpI & 0xFF)).replace(' ', '0');
                result += String.format("%8s", tmpS);
                if (i % 8 == 0) {
                    bloki.add(result);
                    result = "";

                }

            }
            if(result!="")
                bloki.add(result);
            paginate();
            for (String bl : bloki) {

                data = permutuj(bl, pierwsza_permutacja);

                generuj(key);

                lewy = data.substring(0, 32);
                prawy = data.substring(32);

                for (int i = 0; i < 16; i++) {
                    String new_right = permutuj(f(i), permutacja_P);
                    String tmp = lewy;

                    String c_right = "";
                    for (int j = 0; j < lewy.length(); j++) {
                        c_right += (char) ((lewy.charAt(j) + new_right.charAt(j)) % 2 + 48);
                    }
                    lewy = prawy;
                    prawy = c_right;
                }

                blok = prawy + lewy;

                output_blocks.add(permutuj(blok, odwrotna_permutacja));
            }
            File outputFile = new File("zakodowany_" + file.getName());
            FileOutputStream fos = new FileOutputStream(outputFile);
            for (String s : output_blocks) {
                for (int i = 0; i < 64; i += 8) {
                    tmp123 = s.substring(i, i + 8);
                    fos.write(Integer.parseInt(tmp123, 2));
                }
            }

            outputFile.createNewFile();
            System.out.println("ZASZYFROWANO");
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }


    protected static void deszyfrujPlik(String klucz, File file) {

        int tmpI;
        String tmpS;
        String result = "";
        bloki.clear();
        String data;
        String key = hexToBin(klucz);
        String output = "";
        ArrayList<String> output_blocks = new ArrayList<>();
        String tmp123;
        try {

            FileInputStream fis = new FileInputStream(file);
            for (int i = 1; (tmpI = fis.read()) != -1; i++) {
                tmpS = String.format("%8s", Integer.toBinaryString(tmpI & 0xFF)).replace(' ', '0');
                result += String.format("%8s", tmpS);
                if (i % 8 == 0 && i != 0) {
                    bloki.add(result);
                    result = "";
                }
            }

            for (String bl : bloki) {

                data = permutuj(bl, pierwsza_permutacja);

                generuj(key);

                lewy = data.substring(0, 32);
                prawy = data.substring(32);

                for (int i = 0; i < 16; i++) {
                    String new_right = permutuj(f(15-i), permutacja_P);
                    String tmp = lewy;

                    String c_right = "";
                    for (int j = 0; j < lewy.length(); j++) {
                        c_right += (char) ((lewy.charAt(j) + new_right.charAt(j)) % 2 + 48);
                    }
                    lewy = prawy;
                    prawy = c_right;
                }

                blok = prawy + lewy;

                output_blocks.add(permutuj(blok, odwrotna_permutacja));
            }
            bloki = output_blocks;
            depaginate();
            File outputFile = new File("odkodowany_" + file.getName());
            FileOutputStream fos = new FileOutputStream(outputFile);
            for (String s : output_blocks) {
                for (int i = 0; i < 64; i += 8) {
                    tmp123 = s.substring(i, i + 8);
                    fos.write(Integer.parseInt(tmp123, 2));
                }
            }

            outputFile.createNewFile();
            System.out.println("ODSZYFROWANO!!");
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("DES");
        System.out.println("Wybierz opcję:...");
        System.out.println("1. Szyfrowanie");
        System.out.println("2. Deszyfracja");
        System.out.println("3. Szyfrowanie pliku");
        System.out.println("4. Deszyfracja pliku");
        int numer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Wybierz opcje:");
        try{
            numer = Integer.parseInt(br.readLine());
        }catch(NumberFormatException | IOException nfe){
            System.err.println("Zly Format!");
        }
        switch (numer) {
            case 1: {
                System.out.println("wybrano szyfracje");
                System.out.flush();
                Scanner kluczSC = new Scanner(System.in);
                System.out.println("Podaj klucz ");
                String klucz = kluczSC.nextLine();

                Scanner inputSC = new Scanner(System.in);
                System.out.println("Podaj input ");
                String input = inputSC.nextLine();
                szyfruj(klucz,input);
            }
            case 2: {
                System.out.println("wybrano deszyfracje");
                System.out.flush();
                Scanner kluczSC = new Scanner(System.in);
                System.out.println("Podaj klucz ");
                String klucz = kluczSC.nextLine();

                Scanner inputSC = new Scanner(System.in);
                System.out.println("Podaj input ");
                String input = inputSC.nextLine();
                deszyfruj(klucz,input);


            }
            case 3: {
                System.out.println("wybrano szyfracje pliku");
                System.out.flush();
                Scanner kluczSC = new Scanner(System.in);
                System.out.println("Podaj klucz ");
                String klucz = kluczSC.nextLine();
                Scanner plik1 = new Scanner(System.in);
                System.out.println("Podaj nazwe pliku: ");
                String plik = plik1.nextLine();
                File originalFil=new File("D:\\pobrane\\DES\\"+plik);
                szyfrujPlik(klucz,originalFil);
            }
            case 4: {
                System.out.println("wybrano deszyfracje pliku");
                System.out.flush();
                Scanner kluczSC = new Scanner(System.in);
                System.out.println("Podaj klucz ");
                String klucz = kluczSC.nextLine();
                Scanner plik1 = new Scanner(System.in);
                System.out.println("Podaj nazwe pliku: ");
                String plik = plik1.nextLine();
                File originalFil=new File("D:\\pobrane\\DES\\"+plik);
                deszyfrujPlik(klucz,originalFil);
            }
        }
}}


