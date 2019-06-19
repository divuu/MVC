/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technomedia.digipark.database.utils;

/**
 *
 * @author DELL
 */
public class NumberToWords {
    
    String string;
    String st1[] = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE",};
    String st2[] = {"HUNDRED", "THOUSAND", "LAC", "CRORE"};
    String st3[] = {"TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINTEEN",};
    String st4[] = {"TWENTY", "THIRTY", "FOURTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINTY"};
    
    public String convertingValues(String values){
        
        String str2 = "";
        NumberToWords numberToWords = new NumberToWords();
        
        //Splitting number into two parts, Integer and Decimal parts
        String beforeDecimal = values.split("\\.")[0];
        String afterDecimal = values.split("\\.")[1];
        
//        System.out.println(beforeDecimal);
//        System.out.println(afterDecimal);

        // For Rupees
        long rupees = Long.parseLong(beforeDecimal, 10);

        String str1 = numberToWords.convertNumberToWords(rupees);
        str1 += " Rupees";

        // For Paise

        int paise = Integer.parseInt(afterDecimal);
        if (paise != 0) {
            str2 = " and " + numberToWords.convertNumberToWords(Long.valueOf(String.valueOf(paise)));
            str2 += " Paise";
        }
        String convertedWords = str1 + str2 + " Only";
        System.out.println(convertedWords);
        
        return convertedWords;
        
    }

    public String convertNumberToWords(long number) {

        int n = 1;
        long word;
        string = "";
        while (number != 0) {
            switch (n) {
                case 1:
                    word = number % 100;
                    pass(word);
                    if (number > 100 && number % 100 != 0) {
                        show("AND ");
                    }
                    number /= 100;
                    break;
                case 2:
                    word = number % 10;
                    if (word != 0) {
                        show(" ");
                        show(st2[0]);
                        show(" ");
                        pass(word);
                    }
                    number /= 10;
                    break;
                case 3:
                    word = number % 100;
                    if (word != 0) {
                        show(" ");
                        show(st2[1]);
                        show(" ");
                        pass(word);
                    }
                    number /= 100;
                    break;
                case 4:
                    word = number % 100;
                    if (word != 0) {
                        show(" ");
                        show(st2[2]);
                        show(" ");
                        pass(word);
                    }
                    number /= 100;
                    break;
                case 5:
                    word = number % 100;
                    if (word != 0) {
                        show(" ");
                        show(st2[3]);
                        show(" ");
                        pass(word);
                    }
                    number /= 100;
                    break;
                case 6:
                    word = number % 10;
                    if (word != 0) {
                        show(" ");
                        show(st2[0]);
                        show(" ");
                        pass(word);
                    }
                    number /= 10;
                    break;
                case 7:
                    word = number % 100;
                    if (word != 0) {
                        show(" ");
                        show(st2[1]);
                        show(" ");
                        pass(word);
                    }
                    number /= 100;
                    break;
                case 8:
                    word = number % 100;
                    if (word != 0) {
                        show(" ");
                        show(st2[2]);
                        show(" ");
                        pass(word);
                    }
                    number /= 100;
                    break;
                case 9:
                    word = number % 100;
                    if (word != 0) {
                        show(" ");
                        show(st2[3]);
                        show(" ");
                        pass(word);
                    }
                    number /= 100;
                    break;

            }
            n++;
        }
        
        return string;
    }

    public void pass(long number) {
        int word, q;
        if (number < 10) {
            show(st1[ (int) number]);
        }
        if (number > 9 && number < 20) {
            show(st3[ (int) number - 10]);
        }
        if (number > 19) {
            word = (int) (number % 10);
            if (word == 0) {
                q = (int) number / 10;
                show(st4[q - 2]);
            } else {
                q = (int) number / 10;
                show(st1[word]);
                show(" ");
                show(st4[q - 2]);
            }
        }
    }

    public void show(String s) {
        String st;
        st = string;
        string = s;
        string += st;
    }
}
