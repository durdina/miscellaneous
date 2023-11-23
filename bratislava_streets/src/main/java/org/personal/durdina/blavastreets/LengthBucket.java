package org.personal.durdina.blavastreets;

import java.util.ArrayList;
import java.util.List;

public record LengthBucket(int length, List<StringPair> strings) {

    public LengthBucket(int length) {
        this(length, new ArrayList<>());
    }

    // TODO: Rework to full fledged class with high level method for retrieval
    // lengthBuckets.get(j).normalizedStrings().get(js)
    // -> List<Pair> getNormalizedStrings(j, js);

    public record StringPair(String normalized, String original) {
    }
}

