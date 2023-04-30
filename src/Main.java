import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<String> linhasComputadas = new ArrayList<>();

        File f = new File("teste.ed");
        try(Scanner fscan = new Scanner(f)){
            while(fscan.hasNext()){
                String linha = processaString(fscan.nextLine());
                if(linha!=null) {
                    System.out.println(linha);
                    linhasComputadas.add(String.valueOf(Integer.parseInt(linha, 16)));
                }


            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String strFinal = "";

        for(String linha : linhasComputadas){
            System.out.println(linha);
            strFinal = strFinal.concat(linha+"\n");
        }
        System.out.println(strFinal);
        Files.write(Path.of("teste"), strFinal.getBytes());
    }


    public static String processaString(String input){
        String out = null;
        ArrayList<String> regsValidos = new ArrayList<>();
        regsValidos.add("a");
        regsValidos.add("b");
        regsValidos.add("c");

        if(input.contains("is the brother") && !input.split(" ")[0].equals("what")){
            String reg = input.split(" ")[0];
            if(reg.toLowerCase().equals("a")){
                out = "1000";
            }
            else if(reg.toLowerCase().equals("b")){
                out = "1001";
            }
            else if(reg.toLowerCase().equals("c")){
                out = "1002";
            }
            else{
                throw new RuntimeException("Registrador invalido, precisa ser A B ou C\n Nome do registrador informado: "+reg);
            }
        }


        else if(input.split(" ")[1].equals("vale")){
            int nmr  = 0;
            try {
                nmr = Integer.parseInt(input.split(" ")[2]);
            } catch (NumberFormatException e) {
                if(input.split(" ")[2].equals("tudo"))
                    nmr = 1;
                else if(input.split(" ")[2].equals("nada"))
                    ;
                else
                    throw new RuntimeException(e);
            }

            if(nmr >= 0x10 && nmr<= 0xFF){

                //Integer.toHexString(numero*precisa ser int);
                out="1"+Integer.toHexString(nmr);
            } else if (nmr<0x10 && nmr >= 0) {
                out="10"+Integer.toHexString(nmr);
            }


            if ( regsValidos.contains(input.split(" ")[0])) {
                out+=regsValidos.indexOf(input.split(" ")[0]);
            }else
                throw new RuntimeException("Registrador invalido, precisa ser A B ou C\n Nome do registrador informado: "+input.split(" ")[0]);
        }
        else if(input.split(" ")[1].equals("respondeu")){
            if(regsValidos.contains(input.split(" ")[0]))
                out = "600"+regsValidos.indexOf(input.split(" ")[0]);
            else
                throw new RuntimeException("Registrador invalido, precisa ser A B ou C\n Nome do registrador informado: "+input.split(" ")[0]);
        }
        else if(input.contains("é igual ao que")){
            //A e B é igual ao que C
            if(regsValidos.contains(input.split(" ")[0]) && regsValidos.contains(input.split(" ")[2]) && regsValidos.contains(input.split(" ")[7])){
                out="3"+regsValidos.indexOf(input.split(" ")[0])+regsValidos.indexOf(input.split(" ")[2])+regsValidos.indexOf(input.split(" ")[7]);
            }
            else
                throw new RuntimeException("Registrador invalido, precisa ser A B ou C");
        }
        else if(input.contains("qual é a diferença")){
            //A e B é igual ao que C
            if(regsValidos.contains(input.split(" ")[0]) && regsValidos.contains(input.split(" ")[2]) && regsValidos.contains(input.split(" ")[4])){
                out="4"+regsValidos.indexOf(input.split(" ")[2])+regsValidos.indexOf(input.split(" ")[4])+regsValidos.indexOf(input.split(" ")[0]);
            }
            else
                throw new RuntimeException("Registrador invalido, precisa ser A B ou C");
        }

        return out;
    }



}