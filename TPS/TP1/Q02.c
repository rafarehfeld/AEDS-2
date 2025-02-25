#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

bool ehPalindromo(char palavra[]) {
    int tamanho = strlen(palavra);

    for(int i = 0; i < tamanho; i++){
        if(palavra[i] != palavra[tamanho - i - 1]){
            return false;
        }
    }
    return true;
}

int main()
{
    char palavra[100];

    while(1){
        fgets(palavra, sizeof(palavra), stdin);

        palavra[strcspn(palavra, "\n")] = '\0';

        if(strcmp(palavra, "FIM") == 0){
            break;
        }

        bool resultado = ehPalindromo(palavra);

        if(resultado) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }
    return 0;
}
