package org.personal.durdina.blavastreets;

import org.junit.Test;
import org.personal.durdina.blavastreets.fuzzy.FuzzyStringCompare;

import java.text.Collator;
import java.text.Normalizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FuzzyStringCompareTest {

    @Test
    public void equalEmpty() {
        var csc = new FuzzyStringCompare("", 0);
        assertTrue(csc.isEqual(""));
    }

    public void equalEmptyAfter1Drop() {
        var csc = new FuzzyStringCompare("a", 1);
        assertFalse(csc.isEqual("ab"));
        assertTrue(csc.isEqual("a"));
        assertTrue(csc.isEqual(""));
    }

    public void equalEmptyAfter1DropReversed() {
        var csc = new FuzzyStringCompare("", 1);
        assertFalse(csc.isEqual("ab"));
        assertTrue(csc.isEqual("a"));
        assertTrue(csc.isEqual(""));
    }

    @Test
    public void equalAfter1Drop() {
        var csc = new FuzzyStringCompare("Alstrova", 1);
        assertFalse(csc.isEqual("Alstromova"));
        assertTrue(csc.isEqual("Alstrova"));
        assertTrue(csc.isEqual("Alstrova"));
        assertTrue(csc.isEqual("Astrova"));
        assertFalse(csc.isEqual("Altova"));
    }

    @Test
    public void equalAfter1DropReversed() {
        var csc = new FuzzyStringCompare("Astrova", 1);
        assertFalse(csc.isEqual("Astromova"));
        assertTrue(csc.isEqual("Alstrova"));
        assertTrue(csc.isEqual("Astrova"));
        assertFalse(csc.isEqual("Altova"));
    }

    @Test
    public void equalAfter2Drops() {
        var csc = new FuzzyStringCompare("Alstrova", 2);
        assertFalse(csc.isEqual("Alstromova"));
//        assertTrue(csc.isEqual("Alstromova")); // TODO: Uncomment after fixing a bug
        assertTrue(csc.isEqual("Alstrova"));
        assertTrue(csc.isEqual("Astrova"));
        assertTrue(csc.isEqual("Altova"));
        assertFalse(csc.isEqual("Atova"));
    }

    @Test
    public void equalAfter2DropsReversed() {
        var csc = new FuzzyStringCompare("Altova", 2);
        assertFalse(csc.isEqual("Astromova"));
        assertTrue(csc.isEqual("Alstrova"));
        assertTrue(csc.isEqual("Astrova"));
        assertTrue(csc.isEqual("Altova"));
    }

    @Test
    public void equalForEdgeCases() {
        var csc = new FuzzyStringCompare("Jantárová", 1);
        assertFalse(csc.isEqual("Jantárová cesta"));
    }

}