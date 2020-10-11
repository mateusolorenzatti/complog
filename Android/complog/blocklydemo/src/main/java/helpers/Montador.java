package helpers;

public class Montador {

    public static String montarCodigo(String codigo) {
        String novo = "";
        String[] comandos = codigo.split("\n");

        for (String comando : comandos) {
            comando.trim();

            /* -- OPERAÇÔES -- */

            //Comando "vire();"
            if (comando.contains("vire(45,direita);")){
                novo += "2;";
            }
            if(comando.contains("vire(45,esquerda);")) {
                novo += "3;";
            }
            if(comando.contains("vire(90,direita);")) {
                novo += "4;";
            }
            if(comando.contains("vire(90,esquerda);")){
                novo += "5;";
            }
            if(comando.contains("vire(180,direita);")){
                novo += "6;";
            }
            if (comando.contains("vire(180,esquerda);")) {
                novo += "7;";
            }

            //Comando "avance();"
            if (comando.contains("avance(")) {
                int p1 = comando.indexOf("(");
                int p2 = comando.indexOf(")");
                novo += "1," + comando.substring(p1, p2).charAt(1) + ";";
            }

            //comando "diga();"
            if (comando.contains(("diga("))) {
                int p1 = comando.indexOf("(");
                int p2 = comando.indexOf(")");
                novo += "D," + comando.substring(p1 + 1, p2) + ";";
            }

            /* -- ESTRUTURAS -- */

            //Comando "if();"
            if (comando.contains("seEncontrar(")) {
                novo += "I{;";
            }

            //Comando "while();"
            if (comando.contains("repitaAteEncontrar(")) {
                novo += "W{;";
            }

            //Fim dos Instruturais
            if (comando.contains("}")) {
                novo += "};";
            }

        }

        novo += "f;";

        return novo;
    }
}
