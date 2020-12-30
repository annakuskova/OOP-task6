import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Six {
    public static void main(String[]args) throws NoSuchAlgorithmException {
        System.out.println("1. " + bell(3));
        System.out.println("2. " + translateWord("flag"));
        System.out.println("   " + translateSentence("I like to eat honey waffles."));
        System.out.println("3. " + validColor("rgba(0,0,0,0.123456789)"));
        System.out.println("4. " + stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        System.out.println("5. " + getHashTags("How the Avocado Became the Fruit of the Global Trade"));
        System.out.println("6. " + ulam(9));
        System.out.println("7. " + longestNonrepeatingSubstring("abcabcbb"));
        System.out.println("8. " + convertToRoman(12));
        System.out.println("9. " + formula("6 * 4 = 24"));
        System.out.println("10. " + palindromedescendant(13001120));
    }

    //1. Число Белла - это количество способов, которыми массив из n элементов может
    //быть разбит на непустые подмножества
    //принимает число n и возвращает соответствующее число Белла
    //bell(2) ➞ 2
    //// sampleArr = [1, 2]
    //// possiblePartitions = [[[1, 2]], [[1], [2]]]
    public static int bell(int n) {
        int[][] bell = new int[n+1][n+1];
        bell[0][0] = 1;

        for (int i=1; i<=n; i++) {
            bell[i][0] = bell[i-1][i-1];

            for (int j=1; j<=i; j++) {
                bell[i][j] = bell[i-1][j-1] + bell[i][j-1];
            }
        }

        return bell[n][0];
    }

    //2. Первая функция translateWord (word) получает слово на английском и возвращает это
    //слово, переведенное на латинский язык. Вторая функция translateSentence (предложение)
    //берет английское предложение и возвращает это предложение, переведенное на латинский язык.
    //have ➞ avehay, ate ➞ ateyay
    public static String translateWord(String word) {
        String result = word;

        if (String.valueOf(result.charAt(0)).toLowerCase().matches("[aeiouy]")) {
            result += "yay";
        } else {
            result = result.toLowerCase();
            String newWord = result.split("[aeiouy]")[0];
            result = result.replaceFirst(newWord,"") + newWord + "ay";
            result = String.valueOf(result.charAt(0)).toUpperCase() + result.substring(1);
        }

        return result;
    }


    public static String translateSentence(String str) {
        String[] tokens = str.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            if (String.valueOf(tokens[i].charAt(0)).toLowerCase().matches("[aeiouy]")) {
                if (String.valueOf(tokens[i].charAt(tokens[i].length() - 1)).matches("[!?.,:;]")) {
                    tokens[i] = tokens[i].substring(0, tokens[i].length() - 1) + "yay" + tokens[i].charAt(tokens[i].length() - 1);
                } else {
                    tokens[i] += "yay";
                }
            } else {
                if (String.valueOf(tokens[i].charAt(0)).matches("[QWRTPSDFGHJKLZXCVBNM]")) {
                    if (String.valueOf(tokens[i].charAt(tokens[i].length() - 1)).matches("[!?.,:;]")) {
                        char mark = tokens[i].charAt(tokens[i].length() - 1);
                        tokens[i] = tokens[i].substring(0, tokens[i].length() - 1);
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                        tokens[i] = String.valueOf(tokens[i].charAt(0)).toUpperCase() + tokens[i].substring(1) + mark;
                    } else {
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                        tokens[i] = String.valueOf(tokens[i].charAt(0)).toUpperCase() + tokens[i].substring(1);
                    }
                } else {
                    if (String.valueOf(tokens[i].charAt(tokens[i].length() - 1)).matches("[!?.,:;]")) {
                        char mark = tokens[i].charAt(tokens[i].length() - 1);
                        tokens[i] = tokens[i].substring(0, tokens[i].length() - 1);
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                        tokens[i] = tokens[i] + mark;
                    } else {
                        tokens[i] = tokens[i].toLowerCase();
                        String newWord = tokens[i].split("[aeiouy]")[0];
                        tokens[i] = tokens[i].replaceFirst(newWord,"") + newWord + "ay";
                    }
                }
            }
        }

        return String.join(" ", tokens);
    }

    //3. принимает строку
    //(например, " rgb(0, 0, 0)") и возвращает true, если ее формат правильный, в
    //противном случае возвращает false
    public static boolean validColor(String str) {
        if (!str.startsWith("rgb") && !str.startsWith("rgba")) {
            return false;
        }

        String[] n = str.split("\\(")[1].split(",");
        n[n.length - 1] = n[n.length - 1].substring(0, n[n.length - 1].length() - 1);

        if (str.startsWith("rgb") && !str.startsWith("rgba")) {
            if (str.contains(".")) {
                return false;
            }

            for (int i = 0; i < n.length; i ++) {
                if (n[i].trim().equals("")) {
                    return false;
                }

                int num = Integer.parseInt(n[i].trim());

                if (!(num >= 0 && num <= 255)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < n.length - 1; i ++) {
                if (n[i].trim().equals("")) {
                    return false;
                }

                int num = Integer.parseInt(n[i].trim());

                if (!(num >= 0 && num <= 255)) {
                    return false;
                }
            }

            if (n[3].trim().equals("")) return false;

            double num = Double.parseDouble(n[3].trim());

            return num >= 0 && num <= 1;
        }

        return true;
    }

    //4. принимает URL (строку), удаляет дублирующиеся
    //параметры запроса и параметры, указанные во втором аргументе (который будет
    //необязательным массивом)
    public static String stripUrlParams(String url, String ...paramsToStrip) {
        String str = "";

        if (!url.contains("?"))
            return url;
        else {
            str = url.substring(url.indexOf("?") + 1);
            url = url.substring(0, url.indexOf("?") + 1);
        }

        char[] params = str.toCharArray();
        StringBuilder print = new StringBuilder();

        for (char param : params) {
            if (Character.isLetter(param))
                if (!(print.toString().contains(String.valueOf(param)))) {
                    if (paramsToStrip.length > 0) {
                        for (String arg : paramsToStrip) {
                            if (!(arg.contains(String.valueOf(param))))
                                print.append(str, str.lastIndexOf(param), str.lastIndexOf(param) + 3).append("&");
                        }
                    }
                    else
                        print.append(str, str.lastIndexOf(param), str.lastIndexOf(param) + 3).append("&");
                }
        }

        return url + print.substring(0, print.length()-1);
    }

    //извлекает три самых длинных слова из заголовка
    //газеты и преобразует их в хэштеги.
    public static ArrayList<String> getHashTags(String str){
        String[] tokens = str.toLowerCase().split(" ");
        ArrayList<String> hashtags = new ArrayList<>();

        while (hashtags.size() < 3) {
            double maxLength = Double.NEGATIVE_INFINITY;
            String word = "";
            int idx = 0;

            try {
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].length() > maxLength) {
                        maxLength = tokens[i].length();
                        word = tokens[i];
                        idx = i;
                    }
                }

                if (String.valueOf(word.charAt(word.length() - 1)).matches("[!?.,;:]")) {
                    hashtags.add("#" + word.substring(0, word.length() - 1));
                } else {
                    hashtags.add("#" + word);
                }
                tokens[idx] = "";
            } catch (StringIndexOutOfBoundsException e) {
                return hashtags;
            }
        }

        return hashtags;
    }

    // принимает число n и возвращает n-е число в последовательности Улама.
    public static int ulam (int n){
        int[] arr = new int[n];
        arr[0]=1;
        arr[1]=2;
        int len=2, next=3;

        while (next < Integer.MAX_VALUE && len < n){
            int count = 0;

            for (int i = 0; i < len; i++){
                for (int j = len-1; j > i; j--){
                    if (arr[i] + arr[j] == next && arr[i] != arr[j])
                        count++;
                    else if (count > 1)
                        break;
                }

                if (count > 1)
                    break;
            }
            if (count == 1) {
                arr[len] = next;
                len++;
            }
            next++;
        }
        return arr[n-1];
    }

    //возвращает самую длинную неповторяющуюся подстроку для строкового ввода
    public static String longestNonrepeatingSubstring(String str){
        String substr = "";
        char [] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (char c : chars) {
            if (!builder.toString().contains(String.valueOf(c)))
                builder.append(c);
            else {
                if (builder.length() > substr.length()) {
                    substr = builder.toString();
                }
                builder = new StringBuilder("" + c);
            }
        }

        str = builder.toString();

        if (str.length() > substr.length())
            substr = str;

        return substr;
    }

    //я принимает арабское число и преобразует его в римское число.
    public static String convertToRoman (int num){
        StringBuilder roman = new StringBuilder();

        if (num < 1 || num > 3999)
            return "Введите меньшее число. ";

        while (num >= 1000) {
            roman.append("M");
            num -= 1000;
        }

        while (num >= 900) {
            roman.append("CM");
            num -= 900;
        }

        while (num >= 500) {
            roman.append("D");
            num -= 500;
        }

        while (num >= 400) {
            roman.append("CD");
            num -= 400;
        }

        while (num >= 100) {
            roman.append("C");
            num -= 100;
        }

        while (num >= 90) {
            roman.append("XC");
            num -= 90;
        }

        while (num >= 50) {
            roman.append("L");
            num -= 50;
        }

        while (num >= 40) {
            roman.append("XL");
            num -= 40;
        }

        while (num >= 10) {
            roman.append("X");
            num -= 10;
        }

        while (num >= 9) {
            roman.append("IX");
            num -= 9;
        }

        while (num >= 5) {
            roman.append("V");
            num -= 5;
        }

        while (num >= 4) {
            roman.append("IV");
            num -= 4;
        }

        while (num >= 1) {
            roman.append("I");
            num -= 1;
        }

        return roman.toString();
    }

    //я принимает строку и возвращает true или false в
    //зависимости от того, является ли формула правильной или нет.
    public static boolean formula(String formula){
        String[] tokens = formula.split(" ");
        int ans = 0;
        int expectedResult = 0;

        if (!Character.isDigit(tokens[0].charAt(0))) return false;
        else ans = Integer.parseInt(tokens[0]);

        int i = 1;

        while (!tokens[i].equals("=")) {
            if (tokens[i].equals("+")){
                ans += Integer.parseInt(tokens[i + 1]);
            }
            if (tokens[i].equals("-")){
                ans -= Integer.parseInt(tokens[i + 1]);
            }
            if (tokens[i].equals("*")){
                ans *= Integer.parseInt(tokens[i + 1]);
            }
            if (tokens[i].equals("/")){
                ans /= Integer.parseInt(tokens[i + 1]);
            }

            i += 2;
        }

        i = formula.indexOf('=');
        String check = formula.substring(i + 1);

        if (check.contains("=")) return false;
        else expectedResult = Integer.parseInt(tokens[tokens.length - 1]);

        return ans == expectedResult;
    }

    //возвращает значение true, если само число является
    //палиндромом или любой из его потомков вплоть до 2 цифр (однозначное число - тривиально палиндром)
    public static boolean palindromedescendant(int num){
        boolean isDescendant = false;
        StringBuffer buffer1 =new StringBuffer(num);
        StringBuffer buffer2 =new StringBuffer(num);

        if (buffer1.length() % 2 != 0)
            return false;
        else {
            while (!isDescendant){
                if(buffer1 != buffer1.reverse()){
                    for(int i = 0; i < buffer1.length(); i += 2){
                        int a = Integer.parseInt(String.valueOf(buffer1.charAt(i)));
                        int b = Integer.parseInt(String.valueOf(buffer1.charAt(i + 1)));
                        buffer2.append(a + b);
                    }
                }
                else
                    isDescendant = true;
            }
        }

        return isDescendant;
    }
}
