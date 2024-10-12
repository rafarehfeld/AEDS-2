import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Pokemon03 {
    
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

    public Pokemon03() {
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

    public Pokemon03(int id, int geracao, String nome, String descricao, String tipo1, String tipo2,
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGeracao() {
        return this.geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo1() {
        return this.tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return this.tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public List<String> getHabilidades() {
        return this.habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades != null ? habilidades : new ArrayList<>();
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return this.altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getTaxaCaptura() {
        return this.taxaCaptura;
    }

    public void setTaxaCaptura(int taxaCaptura) {
        this.taxaCaptura = taxaCaptura;
    }

    public boolean isEhLegendario() {
        return this.ehLegendario;
    }

    public void setEhLegendario(boolean ehLegendario) {
        this.ehLegendario = ehLegendario;
    }

    public Date getDataCaptura() {
        return this.dataCaptura;
    }

    public void setDataCaptura(Date dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public Pokemon03 duplicar() {
        Pokemon03 copia = new Pokemon03();
        copia.setId(this.id);
        copia.setGeracao(this.geracao);
        copia.setNome(this.nome);
        copia.setDescricao(this.descricao);
        copia.setTipo1(this.tipo1);
        copia.setTipo2(this.tipo2);
        copia.setHabilidades(new ArrayList<>(this.habilidades));
        copia.setPeso(this.peso);
        copia.setAltura(this.altura);
        copia.setTaxaCaptura(this.taxaCaptura);
        copia.setEhLegendario(this.ehLegendario);
        copia.setDataCaptura(this.dataCaptura);
        return copia;
    }

    public String formatar() {
        return "[#" + id + " -> " + nome + ": " + descricao + " - ['" + tipo1
                + (tipo2 != null ? "', '" + tipo2 + "']" : "']") + " - ['" + String.join("', '", habilidades) + "']" + " - "
                + peso + "kg - " + altura + "m - " + taxaCaptura + "% - "
                + (ehLegendario ? "true" : "false") + " - " + geracao + " gen] - "
                + new SimpleDateFormat("dd/MM/yyyy").format(dataCaptura);
    }

    public Pokemon03 carregar(int id) {
        Pokemon03 novoPokemon = null;
        String linha = "";
        BufferedReader leitor = null;
        String caminho = "/tmp/pokemon.csv"; 
        boolean encontrado = false;

        try {
            leitor = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), StandardCharsets.UTF_8));
            leitor.readLine();

            while ((linha = leitor.readLine()) != null && !encontrado) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (!dados[0].isEmpty()) {
                    int idArquivo = Integer.parseInt(dados[0]);

                    if (idArquivo == id) {
                        encontrado = true;

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

                        novoPokemon = new Pokemon03(idArquivo, Integer.parseInt(dados[1]), dados[2], dados[3], tipo1, tipo2,
                                habilidades,
                                peso, altura, taxaCaptura, ehLegendario, dataCaptura);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (leitor != null)
                    leitor.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return novoPokemon;
    }

    public static boolean pesquisarPorNome(List<Pokemon03> lista, String nomeBuscado) {
        long inicio = System.nanoTime();
        int comparacoes = 0;
        for (Pokemon03 pokemon : lista) {
            comparacoes++;
            if (pokemon.getNome().equalsIgnoreCase(nomeBuscado)) {
                long fim = System.nanoTime();
                System.out.println("SIM");
                // Gravar no arquivo de log: matrícula, tempo, comparações
                gravarLog(fim - inicio, comparacoes);
                return true;
            }
        }
        System.out.println("NAO");
        return false;
    }

    private static void gravarLog(long tempo, int comparacoes) {
        // Substitua "sua_matricula" pelo seu número de matrícula
        String matricula = "sua_matricula";
        try (FileWriter writer = new FileWriter("matricula_sequencial.txt", true)) {
            writer.write(matricula + "\t" + tempo + "\t" + comparacoes + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
    
        // Lista de Pokémons
        List<Pokemon03> listaPokemons = new ArrayList<>();
        
        // Inserir Pokémons na lista
        while (true) {
            String input = scanner.nextLine();
            
            // Verificar se o input é "FIM"
            if (input.equals("FIM")) {
                break;
            }
    
            try {
                int id = Integer.parseInt(input);
                Pokemon03 novoPokemon = new Pokemon03().carregar(id);
                if (novoPokemon != null) {
                    listaPokemons.add(novoPokemon);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número ou 'FIM'.");
            }
        }
    
        // Ler os nomes a serem pesquisados e realizar as pesquisas
        String nome;
        while (!(nome = scanner.nextLine()).equals("FIM")) {
            pesquisarPorNome(listaPokemons, nome);
        }
    
        scanner.close();
    }
    
}

