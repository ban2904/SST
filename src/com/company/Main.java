package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 */


public class Main {

    public static void main(String[] args) throws IOException{
        ArrayList<String> listOfSeparators = new ArrayList<String>();
        listOfSeparators.add(" ");
        listOfSeparators.add(",");
        listOfSeparators.add(".");
        listOfSeparators.add("!");
        listOfSeparators.add("?");
        listOfSeparators.add("(");
        listOfSeparators.add(")");
        listOfSeparators.add("[");
        listOfSeparators.add("]");
        listOfSeparators.add("-");
        listOfSeparators.add(";");
        listOfSeparators.add(":");
        listOfSeparators.add("\n");
        listOfSeparators.add("\r");
        listOfSeparators.add("\t");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter URL: ");
        String url = sc.nextLine();
        Document doc = Jsoup.connect(url)
            .userAgent("Chrome/81.0.4044.138")
            .get();
        String text = doc.body().text();
        String separatorsString = String.join("|\\", listOfSeparators);
        Map<String,Word> countMap = new HashMap<String,Word>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;
        while ((line= reader.readLine()) != null) {
            String[] words = line.split(separatorsString);
            for (String word : words) {
                if ("".equals(word)) {
                    continue;
                }
                Word wordObj = countMap.get(word);
                if (wordObj == null) {
                    wordObj = new Word();
                    wordObj.word = word;
                    wordObj.count = 0;
                    countMap.put(word, wordObj);
                }
                wordObj.count++;
            }
        }
        reader.close();

        SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
        for (Word word : sortedWords) {
            System.out.println(word.word + "\t" + word.count);
        }

     }
}


