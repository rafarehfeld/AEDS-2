#include <stdio.h>
#include <string.h>
#include <ctype.h>

int ContMaiusculas(char str[]) {
    int cont = 0;

    for(int i= 0; str[i] != '\0'; i++) {
        if(isupper(str[i])) {
            cont++;
        }
    }

    return cont;
}

int main() {
    char str[100];

    while(1) {
        fgets(str, sizeof(str), stdin);
        
        str[strcspn(str, "\n")] = '\0';

        if(strcmp(str, "FIM") == 0) {
            break;
        }

        int resultado = ContMaiusculas(str);

        printf("%d\n", resultado);
    }

    return 0;
}
