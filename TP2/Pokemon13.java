import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Pokemon13 {

    private int id;
    private int geracao;
    private int taxaCaptura;
    private String nome;
    private String descricao;
    private String tipo1;
    private String tipo2;
    private List<String> habilidades;
    private double peso;
    private double altura;
    private boolean ehLegendario;
    private Date dataCaptura;

    // Construtor padrão
    public Pokemon13() {
        this.id = 0;
        this.geracao = 0;
        this.nome = "";
        this.descricao = "";
        this.tipo1 = "";
        this.tipo2 = "";
        this.habilidades = new ArrayList<>();
        this.peso = 0.0;
        this.altura = 0.0;
        this.taxaCaptura = 0;
        this.ehLegendario = false;
        this.dataCaptura = null;
    }

    // Construtor com parâmetros
    public Pokemon13(int id, int geracao, String nome, String descricao, String tipo1, String tipo2,
                     List<String> habilidades, double peso, double altura, int taxaCaptura, boolean ehLegendario, Date dataCaptura) {
        this.id = id;
        this.geracao = geracao;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.habilidades = habilidades;
        this.peso = peso;
        this.altura = altura;
        this.taxaCaptura = taxaCaptura;
        this.ehLegendario = ehLegendario;
        this.dataCaptura = dataCaptura;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTipos() {
        return this.tipo1 + (this.tipo2 != null ? "," + this.tipo2 : "");
    }

    // Método de formatação dos atributos
    public String formatar() {
        return "[#" + id + " -> " + nome + ": " + descricao + " - ['" + tipo1
                + (tipo2 != null ? "', '" + tipo2 + "']" : "']") + " - ['" + String.join("', '", habilidades) + "']" + " - "
                + peso + "kg - " + altura + "m - " + taxaCaptura + "% - "
                + (ehLegendario ? "true" : "false") + " - " + geracao + " gen] - "
                + new SimpleDateFormat("dd/MM/yyyy").format(dataCaptura);
    }

    // Método para carregar os dados do CSV
    public Pokemon13 carregar(String[] dados) throws Exception {
        String tipo1 = dados[4].trim();
        String tipo2 = dados[5].isEmpty() ? null : dados[5].trim();
        String habilidadesLimpa = dados[6].replace("[", "").replace("]", "").replace("\"", "")
                .replace("'", "").trim();
        List<String> habilidades = Arrays.asList(habilidadesLimpa.split(",\\s*"));
        double peso = !dados[7].isEmpty() ? Double.parseDouble(dados[7]) : 0.0;
        double altura = !dados[8].isEmpty() ? Double.parseDouble(dados[8]) : 0.0;
        int taxaCaptura = !dados[9].isEmpty() ? Integer.parseInt(dados[9]) : 0;
        boolean ehLegendario = dados[10].equals("1");
        Date dataCaptura = null;
        if (!dados[11].isEmpty()) {
            dataCaptura = new SimpleDateFormat("dd/MM/yyyy").parse(dados[11]);
        }
        return new Pokemon13(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), dados[2], dados[3], tipo1, tipo2,
                habilidades, peso, altura, taxaCaptura, ehLegendario, dataCaptura);
    }

    // Método de MergeSort para ordenar por tipos e nome
    public static void mergeSort(List<Pokemon13> lista, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(lista, inicio, meio);
            mergeSort(lista, meio + 1, fim);
            merge(lista, inicio, meio, fim);
        }
    }

    private static void merge(List<Pokemon13> lista, int inicio, int meio, int fim) {
        List<Pokemon13> esquerda = new ArrayList<>(lista.subList(inicio, meio + 1));
        List<Pokemon13> direita = new ArrayList<>(lista.subList(meio + 1, fim + 1));

        int i = 0, j = 0, k = inicio;
        while (i < esquerda.size() && j < direita.size()) {
            Pokemon13 pokeEsq = esquerda.get(i);
            Pokemon13 pokeDir = direita.get(j);
            int comparacao = pokeEsq.getTipos().compareTo(pokeDir.getTipos());

            // Comparar tipos, em caso de empate, comparar nome
            if (comparacao < 0 || (comparacao == 0 && pokeEsq.getNome().compareTo(pokeDir.getNome()) < 0)) {
                lista.set(k++, pokeEsq);
                i++;
            } else {
                lista.set(k++, pokeDir);
                j++;
            }
        }

        while (i < esquerda.size()) {
            lista.set(k++, esquerda.get(i++));
        }

        while (j < direita.size()) {
            lista.set(k++, direita.get(j++));
        }
    }

    public static void main(String[] args) {
        List<Pokemon13> pokemons = new ArrayList<>();

        // Carregar os dados do arquivo CSV
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/pokemon.csv"), StandardCharsets.UTF_8))) {
            leitor.readLine(); // Ignora cabeçalho

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // Carregar Pokémon do CSV
                Pokemon13 pokemon = new Pokemon13().carregar(dados);
                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        List<Pokemon13> pokemonsSelecionados = new ArrayList<>();
        String input;

        // Loop para permitir múltiplos IDs até que "FIM" seja digitado
        do {
            input = scanner.nextLine().trim();  // Lê a linha de entrada

            if (!input.equalsIgnoreCase("FIM")) {
                try {
                    int idPokemon = Integer.parseInt(input);  // Converte a entrada para número

                    // Procurar pelo Pokémon com o ID fornecido
                    Pokemon13 pokemonEncontrado = null;
                    for (Pokemon13 pokemon : pokemons) {
                        if (pokemon.getId() == idPokemon) {
                            pokemonEncontrado = pokemon;
                            break;
                        }
                    }

                    // Adicionar Pokémon à lista de selecionados
                    if (pokemonEncontrado != null) {
                        pokemonsSelecionados.add(pokemonEncontrado);
                    } else {
                        System.out.println("Pokémon com ID " + idPokemon + " não encontrado.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Por favor, insira um número válido para o ID do Pokémon.");
                }
            }

        } while (!input.equalsIgnoreCase("FIM"));

        // Ordenar os Pokémon selecionados pelo atributo 'types', e em caso de empate, pelo 'nome'
        mergeSort(pokemonsSelecionados, 0, pokemonsSelecionados.size() - 1);

        // Exibir Pokémon ordenados
        for (Pokemon13 pokemon : pokemonsSelecionados) {
            System.out.println(pokemon.formatar());
        }

        // Gerar arquivo de log
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("matrícula_mergesort.txt", StandardCharsets.UTF_8))) {
            writer.write("Pokémon ordenados por tipo e nome:\n");
            for (Pokemon13 pokemon : pokemonsSelecionados) {
                writer.write(pokemon.formatar() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}
