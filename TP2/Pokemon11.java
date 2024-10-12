import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Pokemon11 {

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
    public Pokemon11() {
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
    public Pokemon11(int id, int geracao, String nome, String descricao, String tipo1, String tipo2,
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

    // Getters e Setters
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getTaxaCaptura() {
        return this.taxaCaptura;
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
    public Pokemon11 carregar(String[] dados) throws Exception {
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
        return new Pokemon11(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), dados[2], dados[3], tipo1, tipo2,
                habilidades, peso, altura, taxaCaptura, ehLegendario, dataCaptura);
    }

    // Método Counting Sort para ordenar Pokémons
    public static List<Pokemon11> countingSort(List<Pokemon11> pokemons) {
        // Encontrar a maior taxa de captura
        int maxTaxaCaptura = pokemons.stream().mapToInt(Pokemon11::getTaxaCaptura).max().orElse(0);
        int[] count = new int[maxTaxaCaptura + 1];
        List<Pokemon11> sortedPokemons = new ArrayList<>(pokemons.size());

        // Contar as ocorrências de cada taxa de captura
        for (Pokemon11 pokemon : pokemons) {
            count[pokemon.getTaxaCaptura()]++;
        }

        // Calcular as posições finais
        for (int i = 1; i <= maxTaxaCaptura; i++) {
            count[i] += count[i - 1];
        }

        // Colocar os Pokémons na ordem correta
        Pokemon11[] output = new Pokemon11[pokemons.size()];

        for (int i = pokemons.size() - 1; i >= 0; i--) {
            Pokemon11 pokemon = pokemons.get(i);
            int taxa = pokemon.getTaxaCaptura();
            output[count[taxa] - 1] = pokemon; // Adicionar Pokémon na posição correta
            count[taxa]--; // Decrementar contagem
        }

        // Adicionar à lista de saída apenas os Pokémons
        sortedPokemons.addAll(Arrays.asList(output));

        // Ordenar os Pokémons com taxa de captura igual pelo nome
        Collections.sort(sortedPokemons, Comparator.comparing(Pokemon11::getTaxaCaptura).thenComparing(Pokemon11::getNome));

        return sortedPokemons;
    }

    public static void main(String[] args) {
        List<Pokemon11> pokemons = new ArrayList<>();

        // Carregar os dados do arquivo CSV
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/pokemon.csv"), StandardCharsets.UTF_8))) {
            leitor.readLine(); // Ignora cabeçalho

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // Carregar Pokémon do CSV
                Pokemon11 pokemon = new Pokemon11().carregar(dados);
                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        List<Pokemon11> pokemonsSelecionados = new ArrayList<>();
        String input;

        // Loop para permitir múltiplos IDs até que "FIM" seja digitado
        do {
            input = scanner.nextLine().trim();  // Lê a linha de entrada

            if (!input.equals("FIM")) {
                try {
                    int idPokemon = Integer.parseInt(input);  // Converte a entrada para número

                    // Procurar pelo Pokémon com o ID fornecido
                    Pokemon11 pokemonEncontrado = null;
                    for (Pokemon11 pokemon : pokemons) {
                        if (pokemon.getId() == idPokemon) {
                            pokemonEncontrado = pokemon;
                            break;
                        }
                    }

                    // Adicionar Pokémon encontrado à lista de seleção
                    if (pokemonEncontrado != null) {
                        pokemonsSelecionados.add(pokemonEncontrado);
                    } else {
                        System.out.println("Pokémon com ID " + idPokemon + " não encontrado.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Por favor, insira um número válido para o ID do Pokémon.");
                }
            }

        } while (!input.equals("FIM"));

        // Ordenar Pokémons selecionados pelo método Counting Sort
        List<Pokemon11> pokemonsOrdenados = countingSort(pokemonsSelecionados);

        // Exibir os Pokémons ordenados
        for (Pokemon11 pokemon : pokemonsOrdenados) {
            System.out.println(pokemon.formatar());
        }
        scanner.close();
    }
}
