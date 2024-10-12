import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Pokemon07 {

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
    public Pokemon07() {
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
    public Pokemon07(int id, int geracao, String nome, String descricao, String tipo1, String tipo2,
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

    public Date getDataCaptura() {
        return this.dataCaptura;
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
    public Pokemon07 carregar(String[] dados) throws Exception {
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
        return new Pokemon07(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), dados[2], dados[3], tipo1, tipo2,
                habilidades, peso, altura, taxaCaptura, ehLegendario, dataCaptura);
    }

    // Método de inserção para ordenar por data de captura
    public static List<Pokemon07> ordenarPorDataCaptura(List<Pokemon07> pokemons) {
        List<Pokemon07> ordenados = new ArrayList<>();
        
        for (Pokemon07 pokemon : pokemons) {
            int i = 0;
            while (i < ordenados.size() && ordenados.get(i).getDataCaptura().compareTo(pokemon.getDataCaptura()) <= 0) {
                i++;
            }
            ordenados.add(i, pokemon);
        }
        return ordenados;
    }

    public static void main(String[] args) {
        List<Pokemon07> pokemons = new ArrayList<>();
        
        // Carregar os dados do arquivo CSV
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/pokemon.csv"), StandardCharsets.UTF_8))) {
            leitor.readLine(); // Ignora cabeçalho

            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // Carregar Pokémon do CSV
                Pokemon07 pokemon = new Pokemon07().carregar(dados);
                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        List<Pokemon07> pokemonsEncontrados = new ArrayList<>();
        String input;

        // Loop para permitir múltiplas entradas até que "FIM" seja digitado
        do {
            input = scanner.nextLine().trim();  // Lê a linha de entrada

            if (!input.equals("FIM")) {
                try {
                    int idPokemon = Integer.parseInt(input);  // Converte a entrada para número

                    // Procurar pelo Pokémon com o ID fornecido
                    Pokemon07 pokemonEncontrado = null;
                    for (Pokemon07 pokemon : pokemons) {
                        if (pokemon.getId() == idPokemon) {
                            pokemonEncontrado = pokemon;
                            break;
                        }
                    }

                    // Adicionar o Pokémon encontrado à lista de encontrados
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

        // Ordenar os Pokémon encontrados por data de captura
        List<Pokemon07> pokemonsOrdenados = ordenarPorDataCaptura(pokemonsEncontrados);

        // Exibir os Pokémon ordenados
        for (Pokemon07 pokemon : pokemonsOrdenados) {
            System.out.println(pokemon.formatar());
        }

        scanner.close();
    }
}
