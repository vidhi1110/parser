package com.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    /*
     * String will be store with this map.
     */
    private static Map<String, String> memoryMap = new LinkedHashMap<>();

    /*
     * Commnads which are supported with this parser
     */
    private static List<String> commands = new ArrayList<>(Arrays.asList("set", "exit", "append", "print",
            "printwordcount", "printwords", "printlength", "list", "reverse"));

    /*
     * Regex pattern to split commands
     */
    private static Pattern regex = Pattern.compile("(\"[^\"]+\")|\\S+");

    /**
     * Main method to run parser.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("\"----------------------------------------\"");
        System.out.println("\"  159.341 Assignment 1 Semester 1 2023  \"");
        System.out.println("\"  Submitted by: Vidhi Dave, 22012391  \"");
        System.out.println("\"----------------------------------------\"");

        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        List<String> sequences = null;

        do {

            String nextLine = sc.nextLine().trim();
            sb.append(nextLine);
            if (nextLine.endsWith(";")) {
                nextLine = sb.toString();
                sb.setLength(0);
                sequences = split(nextLine.substring(0, nextLine.length() - 1));

                if (commands.contains(sequences.get(0))) {

                    if (!"exit".equals(sequences.get(0))) {
                        processCommand(sequences);
                    } else {
                        break;
                    }

                } else {
                    System.err.println("Please use proper command. Commands are " + commands);
                }

            } else {
                sb.append("\n");
            }

        } while (true);

        sc.close();

    }

    /**
     * Process particular command.
     *
     * @param sequences
     */
    private static void processCommand(List<String> sequences) {
        switch (sequences.get(0)) {

            case "set":
                processSetCommand(sequences);
                break;
            case "append":
                processAppendCommand(sequences);
                break;
            case "print":
                processPrintCommand(sequences);
                break;
            case "printwordcount":
                processPrintWordCountCommand(sequences);
                break;
            case "printwords":
                processPrintWordsCommand(sequences);
                break;
            case "printlength":
                processPrintLengthCommand(sequences);
                break;
            case "list":
                processListCommand(sequences);
                break;
            case "reverse":
                processReverseCommand(sequences);
                break;
        }
    }

    /**
     * Method to split string with the use of regex.
     *
     * @param string
     * @return
     */
    private static List<String> split(String string) {

        List<String> sequence = new ArrayList<>();
        Matcher matcher = regex.matcher(string);

        while (matcher.find()) {
            sequence.add(matcher.group(0));
        }
        return sequence;
    }


    /**
     * Process list command which will display all stored strings
     *
     * @param sequences
     */
    private static void processListCommand(List<String> sequences) {

        System.out.println("Identifier list (" + memoryMap.size() + "):");
        for (Entry<String, String> entry : memoryMap.entrySet()) {
            System.out.println(entry.getKey() + ": \"" + entry.getValue() + "\"");
        }
    }

    /**
     * Process print length command which will calculate length of provided string.
     *
     * @param sequences
     */
    private static void processPrintLengthCommand(List<String> sequences) {
        String result = evaluteExpression(1, sequences);
        if (result != null) {
            System.out.println("Length is:" + result.length());
        }
    }

    /**
     * Process print words command which will print words of provided string.
     *
     * @param sequences
     */
    private static void processPrintWordsCommand(List<String> sequences) {
        String result = evaluteExpression(1, sequences);
        if (result != null) {
            System.out.println("Words are: ");
            String[] words = result.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");

            for (int i = 0; i < words.length; i++) {
                System.out.println(words[i]);
            }
        }

    }

    /**
     * Process print words count command which will calculate words of provided string.
     *
     * @param sequences
     */
    private static void processPrintWordCountCommand(List<String> sequences) {
        String result = evaluteExpression(1, sequences);
        if (result != null) {
            System.out.println("Wordcount is: " + result.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+").length);
        }

    }

    /**
     * Process print command which will print provided string.
     *
     * @param sequences
     */
    private static void processPrintCommand(List<String> sequences) {
        String result = evaluteExpression(1, sequences);
        if (result != null) {
            System.out.println(result);
        }
    }

    /**
     * Process reverse command which will reverse provided string.
     *
     * @param sequences
     */
    private static void processReverseCommand(List<String> sequences) {
        String result = memoryMap.get(sequences.get(1));

        if (result != null) {
            String[] words = result.split(" ");
            StringBuilder sb = new StringBuilder();

            for (int i = words.length - 1; i >= 0; i--) {
                sb.append(words[i]);
                sb.append(" ");
            }
            memoryMap.put(sequences.get(1), sb.toString().trim());
        } else {
            System.err.println("Provided String is not found.");
        }
    }


    /**
     * Process append command which will append provided strings.
     *
     * @param sequences
     */
    private static void processAppendCommand(List<String> sequences) {
        String targetString = memoryMap.get(sequences.get(1));
        if (targetString != null) {
            String toAppend = evaluteExpression(2, sequences);
            memoryMap.put(sequences.get(1), targetString + toAppend);
        } else {
            System.err.println("Provided String is not found.");
        }
    }

    /**
     * Process set command which will store provided string in memory.
     *
     * @param sequences
     */
    private static void processSetCommand(List<String> sequences) {
        String result = evaluteExpression(2, sequences);

        if (result != null) {
            memoryMap.put(sequences.get(1), result);
        }

    }

    /**
     * Evalute expression to find result string.
     *
     * @param startIndex
     * @param sequences
     * @return
     */
    private static String evaluteExpression(int startIndex, List<String> sequences) {
        StringBuilder result = new StringBuilder();

        for (int i = startIndex; i < sequences.size(); i++) {
            String s = sequences.get(i);

            if ("SPACE".equals(s)) {
                result.append(" ");
            } else if ("NEWLINE".equals(s)) {
                result.append("\n");
            } else if ("TAB".equals(s)) {
                result.append("\t");
            } else if ("+".equals(s)) {
                i++;
                s = sequences.get(i);
                if (s.contains("\"")) {
                    result.append(s.substring(1, s.length() - 1));
                } else if ("SPACE".equals(s)) {
                    result.append(" ");
                } else if ("NEWLINE".equals(s)) {
                    result.append("\n");
                } else if ("TAB".equals(s)) {
                    result.append("\t");
                } else {
                    String toAppend = memoryMap.get(s.trim());

                    if (toAppend == null) {
                        System.err.println("String not found in memory. Before use please set that. Key : " + s);
                        return null;
                    } else {
                        result.append(toAppend);
                    }

                }
            } else if (s.contains("\"")) {
                result.append(s.substring(1, s.length() - 1));
            } else {
                String toAppend = memoryMap.get(s.trim());

                if (toAppend == null) {
                    System.err.println("String not found in memory. Before use please set that. Key : " + s);
                    return null;
                } else {
                    result.append(toAppend);
                }

            }
        }

        return result.toString();
    }

}
