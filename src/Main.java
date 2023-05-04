import java.util.Scanner;

public class Main {
    public static void encode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string: ");
        String word = scanner.nextLine();
        String bin;
        StringBuilder codeBinary = new StringBuilder();
        int refresh = 0;
        int contZero = 0;
        int contOne  = 0;

        for (int i = 0; i < word.length(); i++) {
            char letter = (word.charAt(i));
            if (Integer.toBinaryString(letter).length() < 7) {
                int difference = 7 - Integer.toBinaryString(letter).length();
                bin = "0".repeat(difference) + Integer.toBinaryString(letter);
            } else {
                bin = Integer.toBinaryString(letter);
            }
            codeBinary.append(bin);
        }

        System.out.println("Encoded string:");
        for (int i = 0; i < codeBinary.length();) {
            if (codeBinary.charAt(i) == '0') {
                System.out.print("00 ");
                for (int j = 0; codeBinary.charAt(i+j) == '0'; j++) {
                    refresh += 1;
                    contZero += 1;
                    if (i + j >= codeBinary.length() - 1){
                        break;
                    }
                }
                String p = "0".repeat(contZero);
                System.out.print(p + " ");
                contZero = 0;
                i = refresh;
            } else {
                System.out.print("0 ");
                for (int k = 0; codeBinary.charAt(i+k) == '1'; k++) {
                    refresh += 1;
                    contOne += 1;
                    if (i + k >= codeBinary.length() - 1) {
                        break;
                    }
                }
                String p = "0".repeat(contOne);
                System.out.print(p + " ");
                contOne = 0;
                i = refresh;
            }
        }
        System.out.println();
    }

    public static void decode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input encoded string: ");
        String input = scanner.nextLine();
        String[] encodedMessage = input.split(" ");
        StringBuilder binary = new StringBuilder();
        String cod = "";
        int cont = 0;

        int checkCh = checkOtherCharacters(encodedMessage);
        int checkBlock = checkFirstBlock(encodedMessage);
        int checkLengthBlo = checkLengthBlock(encodedMessage);

        if (checkCh == 1 || checkBlock == 1 || checkLengthBlo == 1) {
            System.out.print("Encoded string is not valid.");
            System.out.println();
        } else {
            for (int i = 0; i < encodedMessage.length;) {
                int len = encodedMessage[i+1].length();
                if (encodedMessage[i].equals("0")) {
                    binary.append("1".repeat(len));
                } else {
                    binary.append("0".repeat(len));
                }
                i += 2;
            }

            int checkLengthBin = checkLengthBinary(binary);

            if (checkLengthBin == 1) {
                System.out.print("Encoded string is not valid.");
                System.out.println();
            } else {
                String[] binaryMessage = new String[binary.length() / 7];
                int j = 0;

                for (int i = 0; i < binary.length(); i++) {
                    cod += binary.charAt(i);
                    cont += 1;
                    if (cont > 6) {
                        binaryMessage[j] = cod;
                        j += 1;
                        cont = 0;
                        cod = "";
                    }
                }
                System.out.print("Decoded string:\n");
                for (int i = 0; i < binaryMessage.length; i++) {
                    int asc = Integer.parseInt(binaryMessage[i], 2);
                    char msg = (char) asc;
                    System.out.print(msg);
                }
                System.out.println();
            }
        }
    }

    public static int checkOtherCharacters(String[] array) {
        int validation = 0;
        for (int i = 0; i < array.length; i++) {
            try {
                validation += Integer.parseInt(array[i]);
            } catch (Exception e) {
                return 1;
            }
        }
        if (validation != 0) {
            validation = 1;
        }
        return validation;
    }

    public static int checkFirstBlock(String[] array) {
        int validation = 0;
        for (int i = 0; i < array.length; i += 2) {
            if (!"0".equals(array[i]) && !"00".equals(array[i])) {
                validation += 1;
                break;
            }
        }
        return validation;
    }

    public static int checkLengthBlock(String[] array) {
        int validation = 0;
        if (array.length % 2 != 0) {
            validation += 1;
        }
        return validation;
    }

    public static int checkLengthBinary(StringBuilder str) {
        int validation = 0;
        if (str.length() % 7 != 0) {
            validation += 1;
        }
        return validation;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String option = "";

        while (!"exit".equals(option)) {
            System.out.println("Please input operation (encode/decode/exit):");
            option = scanner.nextLine();
            if ("encode".equals(option)) {
                encode();
            } else if ("decode".equals(option)) {
                decode();
            } else if ("exit".equals(option)) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.printf("There is no '%s' operation\n", option);
            }
            System.out.println();
        }
    }
}