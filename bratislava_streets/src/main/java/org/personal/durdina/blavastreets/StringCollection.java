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
            lengthBucket.strings().add(new LengthBucket.StringPair(normalizeString(str), str));
        }
    }

    public List<SimilarityPair<String, String>> findStringsWithinDistance(int distance) {
        var result = new ArrayList<SimilarityPair<String, String>>();
        // start with the shortest bucket (it does not really matter though)
        for (int i = 0; i < lengthBuckets.size(); i++) {
            // take all strings from the bucket
            for (LengthBucket.StringPair testedStrA : lengthBuckets.get(i).strings()) {
                var csc = new FuzzyStringCompare(testedStrA.normalized(), distance);
                // compare each string with strings of similar lengths within given distance
                for (int j = i + 1; j < lengthBuckets.size() && j < i + distance + 1; j++) {
                    for (LengthBucket.StringPair testedStrB : lengthBuckets.get(j).strings()) {
                        if (csc.isEqual(testedStrB.normalized())) {
                            result.add(new SimilarityPair<>(testedStrA.original(), testedStrB.original()));
                        }
                    }
                }
            }

        }
        return result;
    }

    private static String normalizeString(String input) {
        return Normalizer.normalize(input.trim(), Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }


    public record SimilarityPair<T1, T2>(T1 s1, T2 s2) {
    }

}
