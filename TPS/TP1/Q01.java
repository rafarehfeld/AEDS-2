import java.util.Scanner;

public class Q01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            String palavra = sc.nextLine();

            Boolean ehPalindromo = ehPalindromo(palavra);

            if(ehPalindromo) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
    }

    public static boolean ehPalindromo(String palavra) {
        int tamanho = palavra.length();

        for(int i = 0; i < tamanho; i++) {
            if(palavra.charAt(i) == palavra.charAt(tamanho - i - 1)) {
                return true;
            }
        }

        return false;
    }
}
