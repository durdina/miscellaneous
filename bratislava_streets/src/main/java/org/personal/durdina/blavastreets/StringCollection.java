package org.personal.durdina.blavastreets;

import org.personal.durdina.blavastreets.fuzzy.FuzzyStringCompare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class StringCollection {

    private static final int MAX_STRING_LENGTH = 40;

    List<LengthBucket> lengthBuckets;

    public StringCollection() {
        var buckets = new ArrayList<LengthBucket>(MAX_STRING_LENGTH);
        for (int i = 0; i < MAX_STRING_LENGTH; i++) {
            buckets.add(new LengthBucket(i));
        }
        this.lengthBuckets = buckets;
    }

    public void loadStrings(Reader reader) throws IOException {
        var br = new BufferedReader(reader);
        while (br.ready()) {
            String str = br.readLine();
            int strLen = str.length();
            LengthBucket lengthBucket = lengthBuckets.get(strLen);
            lengthBucket.strings().add(new StringPair(noAccentsLowercase(str), str));
        }
    }

    public void printStrings() {
        for (LengthBucket lb : lengthBuckets) {
            for (StringPair s : lb.strings()) {
                System.out.printf("%s\n", s.original());
            }
        }
    }

    public List<SimilarityPair<String, String>> findStringsWithinDistance(int distance) {
        var result = new ArrayList<SimilarityPair<String, String>>();
        // start with the longest bucket
        for (int i = 0; i < lengthBuckets.size(); i++) {
            // take all strings from the bucket
            for (StringPair testedStrA : lengthBuckets.get(i).strings()) {
                var csc = new FuzzyStringCompare(testedStrA.normalized(), distance);
                for (int j = i + 1; j < lengthBuckets.size(); j++) {
//                for (int j = i + 1; j < lengthBuckets.size() && j < i + distance + 1; j++) {
                    for (StringPair testedStrB : lengthBuckets.get(j).strings()) {
                        if (csc.isEqual(testedStrB.normalized())) {
                            result.add(new SimilarityPair<>(testedStrA.original(), testedStrB.original()));
                        }
                    }
                }
            }

        }
        return result;
    }

    private static String noAccentsLowercase(String input) {
        return Normalizer.normalize(input.trim(), Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

}
