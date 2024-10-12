import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Pokemon05 {

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
    public Pokemon05() {
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
    public Pokemon05(int id, int geracao, String nome, String descricao, String tipo1, String tipo2,
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

    // Método de formatação dos atributos
    public String formatar() {
        return "[#" + id + " -> " + nome + ": " + descricao + " - ['" + tipo1
                + (tipo2 != null ? "', '" + tipo2 + "']" : "']") + " - ['" + String.join("', '", habilidades) + "']" + " - "
                + peso + "kg - " + altura + "m - " + taxaCaptura + "% - "
                + (ehLegendario ? "true" : "false") + " - " + geracao + " gen] - "
                + new SimpleDateFormat("dd/MM/yyyy").format(dataCaptura);
    }

    // Método para carregar os dados do CSV
    public Pokemon05 carregar(String[] dados) throws Exception {
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
        return new Pokemon05(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), dados[2], dados[3], tipo1, tipo2,
                habilidades, peso, altura, taxaCaptura, ehLegendario, dataCaptura);
    }

    // Método para ordenar a lista de Pokémon por nome usando seleção
    public static void ordenarPorNome(List<Pokemon05> pokemons) {
        int n = pokemons.size();
        for (int i = 0; i < n - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < n; j++) {
                if (pokemons.get(j).getNome().compareToIgnoreCase(pokemons.get(indiceMenor).getNome()) < 0) {
                    indiceMenor = j;
                }
            }
            // Troca os Pokémon
            Collections.swap(pokemons, i, indiceMenor);
        }
    }

    public static void main(String[] args) {
        List<Pokemon05> pokemons = new ArrayList<>();
        
        // Carregar os dados do arquivo CSV
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/pokemon.csv"), StandardCharsets.UTF_8))) {
            leitor.readLine(); // Ignora cabeçalho

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // Carregar Pokémon do CSV
                Pokemon05 pokemon = new Pokemon05().carregar(dados);
                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        List<Pokemon05> pokemonsEncontrados = new ArrayList<>();
        String input;
        
        // Loop para permitir múltiplos IDs até que "FIM" seja digitado
        do {
            input = scanner.nextLine().trim();  // Lê a linha de entrada

            if (!input.equals("FIM")) {
                try {
                    int idPokemon = Integer.parseInt(input);  // Converte a entrada para número

                    // Procurar pelo Pokémon com o ID fornecido
                    Pokemon05 pokemonEncontrado = null;
                    for (Pokemon05 pokemon : pokemons) {
                        if (pokemon.getId() == idPokemon) {
                            pokemonEncontrado = pokemon;
                            break;
                        }
                    }

                    // Adiciona o Pokémon encontrado à lista
                    if (pokemonEncontrado != null) {
                        pokemonsEncontrados.add(pokemonEncontrado);
                    } else {
                        System.out.println("Pokémon com ID " + idPokemon + " não encontrado.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Por favor, insira um número válido para o ID do Pokémon.");
                }
            }

        } while (!input.equals("FIM"));

        // Ordenar Pokémon encontrados por nome
        ordenarPorNome(pokemonsEncontrados);

        // Exibir as características dos Pokémon encontrados
        if (pokemonsEncontrados.isEmpty()) {
            System.out.println("Nenhum Pokémon encontrado.");
        } else {
            for (Pokemon05 pokemon : pokemonsEncontrados) {
                System.out.println(pokemon.formatar());
            }
        }
        
        scanner.close(); // Fechar o scanner para evitar vazamento de recursos
    }
}
