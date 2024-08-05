package com.transaction.domain.util;

import java.util.Map;

public class AssociateWordsWithMccUtil {
    private AssociateWordsWithMccUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Aqui é possivel usar integração com IA para criar uma base de conhecimento
     * para substituir o HashMap
     * @param name
     * @return
     */
    public static String getMcc(String name){
        var association = Map.of(
                "UBER EATS","5812",
                "UBER TRIP","----",
                "PAG* TRIP","----",
                "PICPAY*","----");
        for (String mcc : association.keySet()) {
            if(name.startsWith(mcc)){
                return association.get(mcc);
            }
        }
        return "";
    }
}
