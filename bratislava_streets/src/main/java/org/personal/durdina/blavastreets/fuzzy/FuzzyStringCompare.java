package org.personal.durdina.blavastreets.fuzzy;

import java.text.Normalizer;

public class FuzzyStringCompare {

    private final String a;
    private final int possibleMismatches;

    public FuzzyStringCompare(String a, int possibleMismatches) {
        this.a = a;
        this.possibleMismatches = possibleMismatches;
    }

    public boolean isEqual(String b) {
        int aix = 0;
        int bix = 0;
        int possibleMismatches = this.possibleMismatches;
        while (bix < b.length() && aix < a.length()) {
            if (a.charAt(aix) == b.charAt(bix)) {
                aix++;
                bix++;
            } else if (aix + 1 < a.length() && bix + 1 < b.length() && a.charAt(aix + 1) == b.charAt(bix + 1) && possibleMismatches > 0) {
                aix++;
                bix++;
                possibleMismatches--;
            // This should be done recursively to test all possible drop combinations (1..possible from a, 1..possibleMismatches from b)
            } else if (aix + 1 < a.length() && a.charAt(aix + 1) == b.charAt(bix) && possibleMismatches > 0) {
                aix++;
                possibleMismatches--;
            } else if (bix + 1 < b.length() && a.charAt(aix) == b.charAt(bix + 1) && possibleMismatches > 0) {
                bix++;
                possibleMismatches--;
            } else {
                return false;
            }
        }
        return bix == b.length() && aix == a.length();
    }

}
