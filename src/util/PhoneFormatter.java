package util;

public class PhoneFormatter {
    private static final String[] countryCodes = {
            "+1", "+7", "8",
            "+20", "+27",
            "+30", "+31", "+32", "+33", "+34", "+36", "+39",
            "+40", "+41", "+43", "+44", "+45", "+46", "+47", "+48", "+49",
            "+50", "+51", "+52", "+53", "+54", "+55", "+56", "+57", "+58",
            "+60", "+61", "+62", "+63", "+64", "+65", "+66",
            "+81", "+82", "+84", "+86",
            "+90", "+91", "+92", "+93", "+94", "+95", "+98",
    };

    public static String humanReadable(String source) {
        String cleanNumber = cutSymbols(source);
        if (cleanNumber.length()>0 && !cleanNumber.startsWith("+")) {
            cleanNumber = "+"+cleanNumber;
        }
        String countryCode = null;
        for (String code: countryCodes) {
            if (cleanNumber.startsWith(code)) {
                countryCode = code;
            }
        }
        if (countryCode==null) {
            countryCode = cleanNumber.substring(0,4);
        }
        String afterCountry = cleanNumber.substring(countryCode.length());

        String result = null;
        switch (afterCountry.length()) {
            case 10: //+70123456789 => +7 012 345-67-89
                result = countryCode
                        +" "
                        +afterCountry.substring(0,3)
                        +" "
                        +afterCountry.substring(3,6)
                        +"-"
                        +afterCountry.substring(6,8)
                        +"-"
                        +afterCountry.substring(8);
                break;
            case 9: //+20 123-456-789
                result = countryCode
                        +" "
                        +afterCountry.substring(0,3)
                        +"-"
                        +afterCountry.substring(3,6)
                        +"-"
                        +afterCountry.substring(6);
            case 7: //+999 662-44-44
                result = countryCode
                        +" "
                        +afterCountry.substring(0,3)
                        +"-"
                        +afterCountry.substring(3,5)
                        +"-"
                        +afterCountry.substring(5);
                break;
            default:
                result = countryCode+"-"+afterCountry;
                break;
        }
        return result;
    }

    //TODO: remove spaces, minuses and other non digit and non + symbols
    public static String cutSymbols(String source) {
        return source.trim();
    }
}
