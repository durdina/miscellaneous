package org.personal.durdina.blavastreets;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.print("Usage: java org.example.Main <inputfile>\n");
            System.out.print("\tinputfile\t file that contains strings delimited by new line\n");
        }
        StringCollection strCollection = new StringCollection();
        try (FileReader fileReader = new FileReader(args[0])) {
            strCollection.loadStrings(fileReader);
        }

        var stringsWithinDistance = strCollection.findStringsWithinDistance(1);
        for (var similarityPair : stringsWithinDistance) {
            System.out.printf("%s - %s\n", similarityPair.s1(), similarityPair.s2());
        }
        System.out.printf("In total %d similarities", stringsWithinDistance.size());
    }
}