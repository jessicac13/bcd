import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Cafeteira 
{

    public static void escreve(ArrayList<ArrayList<String>> linhas, String filename)
    {
        try 
        {
            FileWriter arquivo = new FileWriter(filename, true);
            for (ArrayList<String> elem : linhas)
            {
                arquivo.append(String.join(",", elem));
                arquivo.append("\n");
            }
            arquivo.flush();
            arquivo.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<String>> le(String filename)
     {
        ArrayList<ArrayList<String>> linhas = new ArrayList<>();
        try 
        {
            File entrada = new File(filename);
            Scanner linha = new Scanner(entrada);

            while (linha.hasNext()) 
            {
                String[] registro = linha.nextLine().split(",");
                ArrayList<String> list = new ArrayList<>(Arrays.asList(registro));
                linhas.add(list);
            }
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        return linhas;
    }
   
    public static void infoUsuario(int id) 
    {
        ArrayList<ArrayList<String>> linhas = le("usuarios.csv");
        boolean encontrou = false;

        for (ArrayList<String> elem : linhas)
        {
            if (Integer.parseInt(elem.get(0)) == id) 
            {
                System.out.println(String.join(",", elem));
                encontrou = true;                
            }
        }
        if (!encontrou) 
        {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }
    }

    public static void historicoCafe(int id) 
    {
        ArrayList<ArrayList<String>> linhas = le("compras.csv");
        boolean encontrou = false;

        for (ArrayList<String> elem : linhas) 
        {
            if (Integer.parseInt(elem.get(0)) == id) 
            {
                System.out.println(String.join(",", elem));
                encontrou = true;                
            }
        }
        if (!encontrou)
        {
            System.out.println("Compras feitas pelo usuário com ID " + id + " não foram encontradas.");
        }
    }

    public static void infoCafeteira() 
    {
        ArrayList<ArrayList<String>> linhas = le("cafeteira.csv");
        System.out.println(linhas);
    }

    public static void adicionarUsuario(String nome, String email, int id_inserido) 
    {
        if (email.indexOf("@") != -1) 
        {
            ArrayList<ArrayList<String>> linhas = new ArrayList<>();
            ArrayList<String> linha = new ArrayList<>();

            linha.add(Integer.toString(id_inserido));
            linha.add(nome);
            linha.add(email);

            linhas.add(linha);

            escreve(linhas, "usuarios.csv");

            System.out.println("\nUsuário de id " + id_inserido + " adicionado");
        } 
        else
        {
            System.out.println("\nE-mail inválido!");
        }
    }

    public static void removerUsuario(int id)
    {
        ArrayList<ArrayList<String>> linhas = le("usuarios.csv");
        boolean encontrou = false;

        for (ArrayList<String> elem : linhas) 
        {
            if (Integer.parseInt(elem.get(0)) == id) 
            {
                linhas.remove(elem);
                System.out.println("\nUsuário de id " + id + " removido");
                encontrou = true;

                try {
                    FileWriter arquivo = new FileWriter("usuarios.csv", false);
                    for (ArrayList<String> palavra : linhas) {
                        arquivo.append(String.join(",", palavra));
                        arquivo.append("\n");
                    }
                    arquivo.flush();
                    arquivo.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (!encontrou) 
        {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }
    }

    private static int contadorServir = 0;

    public static void servirCafe(int id, int tipo) 
    {
        ArrayList<ArrayList<String>> linhas = le("usuarios.csv");
        boolean encontrou = false;

        for (ArrayList<String> elem : linhas) 
        {
            if (Integer.parseInt(elem.get(0)) == id) 
            {
                ArrayList<ArrayList<String>> linhas0 = le("cafeteira.csv");
                encontrou = true;

                if(linhas0.get(0).get(0).equals("Vazia"))
                {
                    System.out.println("\nNão é possível servir, verifique as informações da cafeteira, caso esteja vazia, reabasteça!");
                }
                else if (linhas0.get(0).get(0).equals("Metade")) 
                {
                    ArrayList<ArrayList<String>> linhas1 = new ArrayList<>();
                    ArrayList<String> palavra = new ArrayList<>();
                    
                    palavra.add(Integer.toString(id));
                    palavra.add(Integer.toString(tipo));

                    LocalDateTime dataHoraAtual = LocalDateTime.now();
                    DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    String dataHoraFormatada = dataHoraAtual.format(formatacao);

                    palavra.add(dataHoraFormatada);

                    linhas1.add(palavra);

                    escreve(linhas1, "compras.csv");

                    System.out.println("\nId " + id + " servindo café tipo " + tipo);
                    
                    if (tipo == 2)
                    {
                        contadorServir = contadorServir + 2;
                    }
                    else
                    {
                        contadorServir++;   
                    }                                  

                    if (contadorServir >= 5)
                    {
                        try 
                        {
                            FileWriter arquivo = new FileWriter("cafeteira.csv", false);
                            arquivo.append("Vazia");
                            arquivo.flush();
                            arquivo.close();
                        } 
                        catch (IOException e) 
                        {
                            e.printStackTrace();
                        }                        
                    }              
                }
                else 
                {
                    ArrayList<ArrayList<String>> linhas1 = new ArrayList<>();
                    ArrayList<String> palavra = new ArrayList<>();
                    
                    palavra.add(Integer.toString(id));
                    palavra.add(Integer.toString(tipo));

                    LocalDateTime dataHoraAtual = LocalDateTime.now();
                    DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    String dataHoraFormatada = dataHoraAtual.format(formatacao);

                    palavra.add(dataHoraFormatada);

                    linhas1.add(palavra);

                    escreve(linhas1, "compras.csv");

                    System.out.println("\nId " + id + " servindo café tipo " + tipo);
                    
                    if (tipo == 2)
                    {
                        contadorServir = contadorServir + 2;
                    }
                    else
                    {
                        contadorServir++;
                    }              

                    if(contadorServir >= 5)
                    {
                        try 
                        {
                            FileWriter arquivo = new FileWriter("cafeteira.csv", false);
                            arquivo.append("Metade");
                            arquivo.flush();
                            arquivo.close();
                        } 
                        catch (IOException e) 
                        {
                            e.printStackTrace();
                        }   
                    contadorServir = 0;                         
                    }  
                }
            }
        }
        if (!encontrou)
        {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }    
    }
    
    public static void reabastecerCafeteira() 
    {
        ArrayList<ArrayList<String>> linhas = le("cafeteira.csv");

        if (linhas.get(0).get(0).equals("Cheia")) 
        {
            System.out.println("\nNão é possível reabaster, nível completo!");
        } 
        else if (linhas.get(0).get(0).equals("Vazia")) 
        {
            System.out.println("\nReabastecendo cafeteira ...");
            try
            {
                FileWriter arquivo = new FileWriter("cafeteira.csv", false);
                arquivo.append("Metade");
                arquivo.flush();
                arquivo.close();
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        } 
        else if (linhas.get(0).get(0).equals("Metade")) 
        {
            System.out.println("\nReabastecendo cafeteira ...");
            try 
            {
                FileWriter arquivo = new FileWriter("cafeteira.csv", false);
                arquivo.append("Cheia");
                arquivo.flush();
                arquivo.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) 
    {
        System.out.println("\n Cafeteira System");

        boolean continua = true;
        int opcao;
        int id = 0;
        int id_inserido = 0;
        int tipo;
        String nome = null;
        String email = null;
      
        Scanner in = new Scanner(System.in);

        while (continua) {
            System.out.println("\n===========================================");
            System.out.println("Digite 1: Para informações de usuário");
            System.out.println("Digite 2: Para histórico de cafés");
            System.out.println("Digite 3: Para informações da cafeteira");
            System.out.println("Digite 4: Para adicionar novo usuário");
            System.out.println("Digite 5: Para remover usuário");
            System.out.println("Digite 6: Para servir café");
            System.out.println("Digite 7: Para reabastecer cafeteira");
            System.out.println("Digite 8: Para sair");
            System.out.println("===========================================");
            System.out.print("Sua opção: ");

            opcao = in.nextInt();

            switch (opcao) 
            {
                case 1:
                    System.out.println("\n**************************************************");
                    System.out.print("Entre com o id do usuário: ");
                    id = in.nextInt();
                    infoUsuario(id);
                    System.out.println("**************************************************");
                    break;

                case 2:
                    System.out.println("\n**************************************************");
                    System.out.print("Entre com o id do usuário: ");
                    id = in.nextInt();
                    historicoCafe(id);
                    System.out.println("**************************************************");
                    break;

                case 3:
                    System.out.println("\n**************************************************");
                    infoCafeteira();
                    System.out.println("**************************************************");
                    break;

                case 4:
                    System.out.println("\n**************************************************");
                    System.out.print("Entre com o nome do usuário: ");
                    nome = in.next();
                    System.out.print("Entre com o e-mail do usuário: ");
                    email = in.next();
                    id_inserido++;
                    adicionarUsuario(nome, email, id_inserido);
                    System.out.println("**************************************************");
                    break;

                case 5:
                    System.out.println("\n**************************************************");
                    System.out.print("Entre com o id do usuário: ");
                    id = in.nextInt();
                    removerUsuario(id);
                    System.out.println("**************************************************");
                    break;

                case 6:
                    System.out.println("\n**************************************************");
                    System.out.print("Entre com o id do usuário: ");
                    id = in.nextInt();
                    System.out.print("Entre com o tipo de café (1 ou 2): ");
                    tipo = in.nextInt();
                    if (tipo != 1 && tipo!=2)
                    {
                        System.out.print("Escolha de tipo inválida!"); 
                    }
                    else
                    {
                        servirCafe(id, tipo);
                        System.out.println("**************************************************");
                    }
                    
                    break;

                case 7:
                    System.out.println("\n**************************************************");
                    reabastecerCafeteira();
                    System.out.println("**************************************************");
                    break;

                case 8:
                    continua = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        in.close();
    }
}
