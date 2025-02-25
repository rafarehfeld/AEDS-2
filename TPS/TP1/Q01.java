import java.util.Scanner;

public class Q01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra;

        while(true){
            palavra = sc.nextLine();

            if(palavra.equals("FIM")){
                break;
            }

            boolean ehPalindromo = ehPalindromo(palavra);

            if(ehPalindromo) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }

        sc.close();
    }

    public static boolean ehPalindromo(String palavra) {
        int tamanho = palavra.length();

        for(int i = 0; i < tamanho; i++) {
            if(palavra.charAt(i) != palavra.charAt(tamanho - i - 1)) {
                return false;
            }
        }

        return true;
    }
}
