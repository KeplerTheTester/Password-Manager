package com.example.passwordholder;

import java.util.ArrayList;

public class PasswordGenerator {
    int length;

    public PasswordGenerator(ArrayList<Requirement> requirements)
    {

    }

    public static String simplePassword()
    {

        //need to create a random length of the password between 8 to 15
        //most simple passwords will only have letters and numbers
        //generate random chances of random
        int size = (int)(Math.random() * ((15 - 8) + 1) + 8);
        System.out.println(size);
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder = new StringBuilder(size);
        for(int i=0; i<size; i++)
        {
            //call a random chance to be letter, and of that, chances to uppercase, then chances to
            // number by association, each password has its 0wn patter but they are all independent
            // of the algorithm
            int chanceToUpperCase = (int)(Math.random() * ((2 - 1) + 1) + 1);///Chance for the letter to be uppercase
            int chanceToBeLetter = (int)(Math.random() * ((2 - 1) + 1) + 1);//chance for the next thing to be a letter
            if(chanceToBeLetter == 1)
            {
                int location = (int)(Math.random() * ((15 - 0) + 1) + 0);
                char temp = alphabet[location];
                if(chanceToUpperCase == 1)
                {
                    stringBuilder.append(Character.toUpperCase(temp));

                }
                else
                {
                    stringBuilder.append(temp);
                }
            }
            else
            {
                int number = (int)(Math.random() * ((9 - 0) + 1) + 0);
                stringBuilder.append(number);
            }
        }
       // System.out.println(stringBuilder);
        stringBuilder.length();
        return new String(stringBuilder);
    }

    public static void main(String [] args)
    {
        System.out.println(simplePassword());
    }


}
