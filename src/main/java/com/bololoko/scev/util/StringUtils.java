package com.bololoko.scev.util;

import java.text.Normalizer;

public class StringUtils {

	public static String padronizaString(String nome) {
		
		if(nome == null) {
			return "";
		}
		
		String padronizado = nome.trim();
		padronizado = padronizado.toUpperCase();
		padronizado = Normalizer.normalize(padronizado, Normalizer.Form.NFD)
								.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		padronizado = padronizado.replaceAll("[^\\p{Alnum}\\s]", "");
		padronizado = padronizado.replaceAll("\\s+", " ").trim();
		
		return padronizado;
	}
}
