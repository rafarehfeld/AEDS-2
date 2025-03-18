#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* combinador(char str1[], char str2[]) {
    int tamanho = strlen(str1) + strlen(str2);
    char* c = (char*)malloc(tamanho + 1);

    int menor = strlen(str1) > strlen(str2) ? strlen(str2):strlen(str1);
    int j = 0;
    int i;

    for(i = 0; i < menor; i++) {
        c[j] = str1[i];
        j++;
        c[j] = str2[i];
        j++;
    }

    while (i < strlen(str1)) {
        c[j++] = str1[i++];
    }
    while (i < strlen(str2)) {
        c[j++] = str2[i++];
    }

    return c;
}

int main()
{
    char str1[100];
    char str2[100];

    scanf("%s %s", str1, str2);

    char* resultado = combinador(str1, str2);

    printf("%s", resultado);

    return 0;
}
