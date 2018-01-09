/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import android.util.Log;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            //add word to list
            wordList.add(word);
            //add word to set
            wordSet.add(word);
            //add word to map
            String sortedWord = sortLetters(word);
            if(lettersToWord.containsKey(sortedWord)) {
                //add word to list at key
                ArrayList<String> list = lettersToWord.get(sortedWord);
                list.add(word);
            } else {
                //make new list with word and add to map
                ArrayList<String> newList = new ArrayList<>();
                newList.add(word);
                lettersToWord.put(sortedWord, newList);
            }
        }
    }

    private String sortLetters(String word) {
        //convert to char array and sort
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        //convert back to String and return
        String sorted = new String(chars);
        return sorted;
    }

    public boolean isGoodWord(String word, String base) {

        return (wordSet.contains(word) && !word.contains(base));
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortedTarget = sortLetters(targetWord);
        if(lettersToWord.get(sortedTarget) != null) {
            result = lettersToWord.get(sortedTarget);
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String newWord = word + alphabet;
            result.addAll(getAnagrams(newWord));
        }
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
