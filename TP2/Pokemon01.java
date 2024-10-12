import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Pokemon01 {
    
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

    public Pokemon01() {
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

    public Pokemon01(int id, int geracao, String nome, String descricao, String tipo1, String tipo2,
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

    public Pokemon01 duplicar() {
        Pokemon01 copia = new Pokemon01();
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

    public Pokemon01 carregar(int id) {
        Pokemon01 novoPokemon = null;
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

                        novoPokemon = new Pokemon01(idArquivo, Integer.parseInt(dados[1]), dados[2], dados[3], tipo1, tipo2,
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

    public static void main(String args[]) {
        String linha = "";
        int idBuscado = 0;
        Pokemon01 leitor = new Pokemon01();
        Pokemon01 resultado = null;
        linha = MyIO.readLine();
        while (!linha.equals("FIM")) {
            idBuscado = Integer.parseInt(linha);
            resultado = leitor.carregar(idBuscado);
            if (resultado != null) {
                System.out.println(resultado.formatar());
            } else {
                System.out.println("Pokémon com ID " + idBuscado);
            }
            linha = MyIO.readLine();
        }

    }

}
