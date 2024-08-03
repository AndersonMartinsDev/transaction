package com.transaction.domain.util;

import java.util.Arrays;
import java.util.List;

public class AssociateWordsWithMccUtil {
    private AssociateWordsWithMccUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static String getMcc(String name){
        var words = new String[]{"","",""};
        var index = Arrays.binarySearch(words,name);
        return index != -1 ? words[index] : null;
    }
}
