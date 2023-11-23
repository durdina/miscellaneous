package org.personal.durdina.blavastreets;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.print("Usage: java org.example.Main <inputfile>\n");
            System.out.print("\tinputfile\t file that contains strings delimited by new line\n");
        }
        StringCollection strDistance = new StringCollection();
        try (FileReader fileReader = new FileReader(args[0])) {
            strDistance.loadStrings(fileReader);
        }

        var stringsWithinDistance = strDistance.findStringsWithinDistance(1);
        for (SimilarityPair<String, String> stringSimilarityPair : stringsWithinDistance) {
            System.out.printf("%s - %s\n", stringSimilarityPair.m1(), stringSimilarityPair.m2());
        }
        System.out.printf("In total %d similarities", stringsWithinDistance.size());
    }
}