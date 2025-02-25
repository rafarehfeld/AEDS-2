import java.util.Scanner;

public class Q03{
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();

        while(texto.charAt(0) != 'F' && texto.charAt(1) != 'I' && texto.charAt(2) != 'M'){
            String resultado = cifrar(texto);

            System.out.println(resultado);

            texto = sc.nextLine();
        }

        sc.close();
    }

    public static String cifrar(String texto) {
        StringBuilder textoCifrado = new StringBuilder();
                
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);

            if (Character.isUpperCase(caractere)) {
                caractere = (char) (((caractere - 'A' + 3) % 26) + 'A');
            }
            else if (Character.isLowerCase(caractere)) {
                caractere = (char) (((caractere - 'a' + 3) % 26) + 'a');
            }
                    
            textoCifrado.append(caractere);
        }
                
        return textoCifrado.toString();
    }
}
