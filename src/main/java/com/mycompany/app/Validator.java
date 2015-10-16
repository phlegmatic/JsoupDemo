package com.mycompany.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* 
 * Purpose :Validate inputs which have fixed format*/
public class Validator {

	// boolean validate(SearchCriteria searchcriteria) {
	// boolean panvalidation = true;
	// boolean validatetinRes = true;
	// if (searchcriteria.getTinNumber() != null
	// || searchcriteria.getTinNumber() != "") {
	// String tinNum = searchcriteria.getTinNumber();
	// validatetinRes = validateTin(tinNum);
	//
	// }
	// if (searchcriteria.getPanNumber() != null
	// || searchcriteria.getPanNumber() != "") {
	// String panNum = searchcriteria.getPanNumber();
	// panvalidation = validatePan(panNum);
	//
	// }
	// if (!validatetinRes) {
	// System.out.println("Invalid TIN");
	// }
	// if (!panvalidation) {
	// System.out.println("Invalid Pan");
	// }
	// return (validatetinRes && panvalidation);
	//
	// }

	public boolean validatePan(String panNum) {

		Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		Matcher matcher = pattern.matcher(panNum);
		// Check if pattern matches
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public boolean validateTin(String tinNum) {
		// M V A T R C No Should be 11 Digits, Begin With 27 or 99,ends with
		// 'V'or'C'or'P'
		if (tinNum.length() != 12) {
			return false;
		} else if (!(tinNum.startsWith("27") || tinNum.startsWith("99"))) {
			return false;
		} else if (!((tinNum.endsWith("V") || tinNum.endsWith("C") || tinNum
				.endsWith("P")))) {
			return false;
		}
		return true;

	}

}
