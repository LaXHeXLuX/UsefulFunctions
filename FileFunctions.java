package UsefulFunctions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileFunctions {
    public static String[] parseInput(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();

        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }

        return Converter.listToArrString(lines);
    }
    public static int[][] parseInputGroups(String filename) throws IOException {
        String[] lines = parseInput(filename);
        List<List<Integer>> groups = new ArrayList<>();
        List<Integer> currentGroup = new ArrayList<>();

        for (String line : lines) {
            if (Objects.equals(line, "")) {
                groups.add(currentGroup);
                currentGroup = new ArrayList<>();
            }
            else currentGroup.add(Integer.parseInt(line));
        }

        return Converter.listListToArrArrInt(groups);
    }
    public static String[][] parseInputGroupsString(String filename) throws IOException {
        String[] lines = parseInput(filename);
        List<List<String>> groups = new ArrayList<>();
        List<String> currentGroup = new ArrayList<>();

        for (String line : lines) {
            if (Objects.equals(line, "")) {
                groups.add(currentGroup);
                currentGroup = new ArrayList<>();
            }
            else currentGroup.add(line);
        }
        groups.add(currentGroup);

        return Converter.listListToArrArrString(groups);
    }
    public static int[] parseInputInt(String filename) throws IOException {
        String[] lines = parseInput(filename);
        return Converter.arrStringToArrInt(lines);
    }
    public static String removeStartEnd(String line, String start, String end) {
        line = line.split(start)[1];
        line = line.split(end)[0];
        return line;
    }
}
