package com;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HackerRankTest {
    private static final String SPACE = " ";
    private static final char SPACE_CHAR = ' ';
    private static final String NON_ALPHA_CHAR_IDENTIFY_PATTERN = "(?![\\p{Alpha}])";
    private static final String EMPTY_STR = "";

    public static void main(String[] args) {
        System.out.println(HackerRankTest.wguStringModifier("Automotive"));
        System.out.println(HackerRankTest.wguStringModifier("1defabc1 cb!a %%%def1123"));
        System.out.println(HackerRankTest.wguStringModifier("a hot character"));
        System.out.println(HackerRankTest.wguStringModifier("at"));
        System.out.println(HackerRankTest.wguStringModifier("x"));
        System.out.println(HackerRankTest.wguStringModifier("tipu-is-smart"));
        System.out.println(HackerRankTest.wguStringModifier("1234567890"));
        System.out.println(HackerRankTest.wguStringModifier("a98779079745092740"));
        System.out.println(HackerRankTest.wguStringModifier("98779079745092740a"));

    }
    /**
     * This method is to modify the string as per the needs.
     * @param strToModify - the string to be modified.
     * @return String - modified String.
     */
    public static String wguStringModifier(String strToModify) {
        String result = populateResultString(strToModify);
        // This is to handle default condition with mix set of chars.
        if(null == result || EMPTY_STR.equals(result)) {
            result = populateMixData(strToModify);
        }
        return result;
    }
    /**
     * @param strToModify
     * @return
     */
    private static String populateResultString(String strToModify) {
        String result = null;
        int strLength = strToModify.length();
        int strTrimLength = strToModify.trim().length();
        if(strLength <= 2 || strTrimLength <=2 ) {
            // If the string length lesser than equal to 2, return the data as is.
            result = strToModify;
        } else if(isNonAlpha(strToModify)){
            // If the string is non alphabets, return the data as is.
            result = strToModify;
        } else if(isAlpha(strToModify)){
            // If the string contains only alphabets and length greater than 2
            result = populatePatternString(strToModify, strLength, false);
        } else if(isOnlyFirstCharAlpha(strToModify) || isOnlyLastCharAlpha(strToModify)){
            // If the string contains alphabet only at first or last position, return data as is
            result = strToModify;
        } else if(isOnlyFirstCharNonAlpha(strToModify) || isOnlyLastCharNonAlpha(strToModify)){
            // If the string contains non-alphabet only at first or last position
            result = populatePatternString(strToModify, strLength, true);
        }
        return result;
    }
    
    private static String populatePatternString(String data, int strLen, boolean isSpecialChar){
        String result = null;
        if(isSpecialChar) {
            // This is to handle where special characters will be prepended
            result = populateSpecialCharPrependedResult(data, strLen); 
        } else {
            // This is to handle usecases where spaces will be prepended
            result = populateSpacePrependedString(data, strLen); 
        }
        return result;
    }
    /**
     * This method is to populate the String which has initial character as space with rest as alphabets.
     * @param data - data string to be modified
     * @param strLen - length of the data string
     * @return String - modified string with prepended space preserved as original.
     */
    private static String populateSpacePrependedString(String data, int strLen) {
        String tempStr = data.trim().substring(1, strLen-1);
        long distinctCharLength = tempStr.chars().distinct().count();
      
        int spaceCount = data.length() - data.replaceAll(SPACE, EMPTY_STR).length();
        char firstChar = data.trim().charAt(0) ;
        char lastChar = data.charAt(strLen-1);
        String prependStr = spaces(spaceCount);
        return prependStr + firstChar + String.valueOf(distinctCharLength) + lastChar;
    }
    /**
     * This method is to populate the String which has initial character as special character with rest as alphabets.
     * @param data - data string to be modified
     * @param strLen - length of the data string
     * @return String - modified string with prepended special character preserved as original.
     */
    private static String populateSpecialCharPrependedResult(String data, int strLen) {
        String tempStr = data.substring(2, strLen-1);
        long distinctCharLength = tempStr.chars().distinct().count();
      
        char firstChar = data.charAt(1) ;
        char lastChar = data.charAt(strLen-1);
        char prependStr = data.charAt(0);
        return String.valueOf(prependStr) + String.valueOf(firstChar) + String.valueOf(distinctCharLength) + String.valueOf(lastChar);
    }
    
    /**
     * This is to count the spaces.
     * @param spaces - no of spaces.
     * @return String created with number of spaces.
     */
    private static String spaces( int spaces ) {
        return CharBuffer.allocate( spaces ).toString().replace( '\0', SPACE_CHAR );
     }

    /**
     * This method is to populate the mixed data.
     * @param data - data string to be modified
     * @return String - modified string.
     */
    private static String populateMixData(String data) {
        String result = null;
        List<String> splitData = Arrays.asList(data.split(NON_ALPHA_CHAR_IDENTIFY_PATTERN));
        List<String> modifiedData = new ArrayList<>();
        for(int i =0; i<splitData.size(); i++){
            String splitDataStr = splitData.get(i);
            result = populateResultString(splitDataStr);
            // This is to handle default condition.
            if(null == result || EMPTY_STR.equals(result)) {
                result = splitDataStr;
            }
            
            if(null != result && !EMPTY_STR.equals(result)){
                modifiedData.add(result);
            }
        }
        if(null != modifiedData && modifiedData.size() > 0) {
            result = modifiedData.stream().collect(Collectors.joining());
        }
        return result;
    }
    /**
    * This method is to identify the all characters are alphabets.
    */
    private static boolean isAlpha(String data){
        return data.matches("[a-zA-z]+");
    }
   /**
    * This method is to identify the all characters are non alphabets except first.
    */
    private static boolean isOnlyFirstCharAlpha(String data){
        return isNonAlpha(data.substring(1, data.length()-1));
    }

   /**
    * This method is to identify the all characters are non alphabets except last.
    */
    private static boolean isOnlyLastCharAlpha(String data){
        return isNonAlpha(data.substring(0, data.length()-2));
    }
    
    /**
     * This method is to identify the all characters are alphabets except first.
     */
     private static boolean isOnlyFirstCharNonAlpha(String data){
         return isAlpha(data.substring(1, data.length()-1));
     }

    /**
     * This method is to identify the all characters are alphabets except last.
     */
     private static boolean isOnlyLastCharNonAlpha(String data){
         return isAlpha(data.substring(0, data.length()-2));
     }

   /**
    * This method is to identify the all characters are non alphabets.
    */
    private static boolean isNonAlpha(String data){
        return data.chars().noneMatch(Character::isAlphabetic);
    }
}
