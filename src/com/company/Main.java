package com.company;

import java.util.*;
import java.io.*;


public class Main {

    static char[][] config = {{'(', ')'}, {'[', ']'}, {'{', '}'}, {'<', '>'}};

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);                                    //сканер консоли, ввод

        //бесконечный цикл работы программы

        File file_input, file_config;                                                    //файл
        String input_string;                                                //строка которую надо найти

        do {                                                                //ввод имени файла
            try {
                System.out.print("Enter the file name: " + new java.io.File("").getAbsolutePath());
                String input = "\\" + in.next();                                       //считывает ввод из консоли
                file_input = new File(new java.io.File("").getAbsolutePath() + input);
            } catch (Throwable e) {
                System.out.println("Error name file long: " + e);
                file_input = new File("bad.txt");
            }
        } while (!Exists(file_input));

        do {                                                                //ввод имени файла
            try {
                System.out.print("Enter the file configuration name: " + new java.io.File("").getAbsolutePath());
                String input = "\\" + in.next();                                       //считывает ввод из консоли
                file_config = new File(new java.io.File("").getAbsolutePath() + input);
            } catch (Throwable e) {
                System.out.println("Error name file long: " + e);
                file_config = new File("bad.txt");
            }
        } while (!Exists(file_config));

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(
                                             new File("Out.txt"))));) {

            try {

                BufferedReader reader_config = new BufferedReader(
                        new FileReader(file_config));

                BufferedReader reader = new BufferedReader(
                        new FileReader(file_input));

                String b = "";

                b = reader_config.readLine();

                while (b != null) {

                    char[] elem = {b.charAt(2), b.charAt(4)};
                    config[OpenCloseWound(b.charAt(0), 0)] = elem;

                    b = reader_config.readLine();

                }

                int a = 0;

                String stek = "";
                int[][] XYPosStek = new int[1000][1000];
                int XPos = 1, YPos = 1;

                do {
                    a = reader.read();

                    char chr = (char) a;

                    if (OpenCloseWound(chr, 0) != -1) {
                        stek += chr;
                        int[] c = {YPos, XPos};
                        XYPosStek[stek.length() - 1] = c;
                    } else if (OpenCloseWound(chr, 1) != -1) {

                        if (stek.length() > 0 && OpenCloseWound(chr, 1) == OpenCloseWound(stek.charAt(stek.length() - 1), 0)) {
                            stek = stek.substring(0, stek.length() - 1);
                        } else
                            System.out.println("Bad in " + YPos + " : " + XPos);

                    }

                    XPos++;

                    if (chr == '\n') {
                        XPos = 1;
                        YPos++;
                    }


                } while (a != -1);

                for (int i = 0; i < stek.length(); i++)
                    System.out.println("Non closed -> " + stek.charAt(i) + " in " + XYPosStek[i][0] + " : " + XYPosStek[i][1]);

            } catch (FileNotFoundException e) {
                System.out.println("Error reader_non_file: " + e);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    static int OpenCloseWound(char chr, int i) {
        if (config[0][i] == chr) return 0;
        else if (config[1][i] == chr) return 1;
        else if (config[2][i] == chr) return 2;
        else if (config[3][i] == chr) return 3;
        else return -1;
    }

    static boolean Exists(File file) {
        if (file.exists()) return (true);
        else
            System.out.println("##Error: file not found. Please try again.##");
        return (false);
    }
}