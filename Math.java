package bat_ctdl;

import java.util.Scanner;

public class Math {

    public static String decimalToBinary(double num) {
        int intPart = (int) num;
        String intBinary = Integer.toBinaryString(intPart);
        double fractionalPart = num - intPart;
        StringBuilder fracBinary = new StringBuilder();
        fracBinary.append(".");
        while (fractionalPart > 0) {
            // Giới hạn độ dài để tránh vòng lặp vô hạn
            if (fracBinary.length() > 32) {
                break;
            }
            double temp = fractionalPart * 2;
            if (temp >= 1) {
                fracBinary.append(1);
                fractionalPart = temp - 1;
            } else {
                fracBinary.append(0);
                fractionalPart = temp;
            }
        }

        return intBinary + fracBinary.toString();
    }
    public static String decimalToHex(double num) {
        int intPart = (int) num;
        String intHex = Integer.toHexString(intPart).toUpperCase();
        double fractionalPart = num - intPart;
        StringBuilder fracHex = new StringBuilder();
        fracHex.append(".");
        while (fractionalPart > 0) {
            if (fracHex.length() > 10) {
                break;
            }
            fractionalPart *= 16;
            int fractionalInt = (int) fractionalPart;
            fracHex.append(Integer.toHexString(fractionalInt).toUpperCase());
            fractionalPart -= fractionalInt;
        }
        return intHex + fracHex;
    }

    public static double binaryToDecimal(double binary) {
        String binaryString = String.valueOf(binary);
        String[] parts = binaryString.split("\\.");
        String integerPart = parts.length > 0 ? parts[0] : "";
        String fractionalPart = parts.length > 1 ? parts[1] : "";
        if (integerPart.isEmpty()) {
            throw new IllegalArgumentException("Phần nguyên của số nhị phân không thể rỗng");
        }
        int intDecimal = Integer.parseInt(integerPart, 2);
        double fracDecimal = 0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            if (fractionalPart.charAt(i) == '1') {
                fracDecimal += 1.0 / (1 << (i + 1));
            }
        }

        return intDecimal + fracDecimal;
    }

    public static double hexToDecimal(String hex) {
        String[] parts = hex.split("\\.");
        String integerPart = parts.length > 0 ? parts[0] : "";
        String fractionalPart = parts.length > 1 ? parts[1] : "";
        int intDecimal;
        try {
            intDecimal = Integer.parseInt(integerPart, 16);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Phần nguyên của số thập lục phân không hợp lệ.");
        }
        double fracDecimal = 0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            char hexDigit = fractionalPart.charAt(i);
            int decimalValue = Character.digit(hexDigit, 16);
            if (decimalValue < 0) {
                throw new IllegalArgumentException("Ký tự không hợp lệ trong phần thập phân: " + hexDigit);
            }
            fracDecimal += decimalValue / powerOf16(i + 1);
        }

        return intDecimal + fracDecimal;
    }
    public static double powerOf16(int n) {
        double result = 1;
        for (int i = 0; i < n; i++) {
            result *= 16;
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("Enter 1:Đổi số thực dương ở hệ thập phân sang hệ nhị phân");
            System.out.println("Enter 2:Đổi số thực dương ở hệ thập phân sang hệ thập lục");
            System.out.println("Enter 3:Đổi số nhị phân sang số thập phân");
            System.out.println("Enter 4:Đổi số thập lục sang số thập phân");
            System.out.println("Enter 5: thoát");
            int type = sc.nextInt();
            switch (type){
                case 1 :{
                    System.out.println("Nhập vào số cần đồi: ");
                    int n = sc.nextInt();
                    System.out.println("Số được chuyển sang nhị phân là: "+ decimalToBinary(n));
                    break;
                }
                case 2:{
                    System.out.println("Nhập vào số cần đồi: ");
                    double n = sc.nextDouble();
                    System.out.println("Số được chuyển sang thập lục là: "+ decimalToHex(n));
                    break;
                }
                case 3:{
                    System.out.println("Nhập vào số cần đồi: ");
                    double n = sc.nextDouble();
                    System.out.println("Số được chuyển sang thập phân là: "+ binaryToDecimal(n));
                    break;
                }
                case 4:{
                    sc.nextLine();
                    System.out.println("Nhập số thập lục phân (ví dụ: 1A3F.4C): ");
                    String hexNumber = sc.nextLine().trim();
                    System.out.println("Số thập phân: " + hexToDecimal(hexNumber));
                break;
                }
                case 5:{
                    return;
                }
                default:
                    break;
            }
        }
    }
}
