package smellytrivial;

import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    static ArrayList jugadores = new ArrayList();
    int[] posiciones = new int[7];
    int[] monederos = new int[7];
    boolean[] enCasillaCastigo = new boolean[7];


    LinkedList preguntasCultura = new LinkedList();
    LinkedList preguntasCiencias = new LinkedList();
    LinkedList preguntasDeportes = new LinkedList();
    LinkedList preguntasMusica = new LinkedList();

    int jugadorActual = 0;
    boolean estaSaliendoDeLaCarcel;

    public  Game(){
        for (int i = 0; i < 50; i++) {
            preguntasCultura.addLast("Pregunta de Cultura " + i);
            preguntasCiencias.addLast(("Pregunta de Ciencias " + i));
            preguntasDeportes.addLast(("Pregunta de Deportes " + i));
            preguntasMusica.addLast(crearPreguntaMusica(i));
        }
    }

    public String crearPreguntaMusica(int index){
        return "Pregunta de Música " + index;
    }


    public static boolean esJugable() {
        return (cuantosJugadores() >= 2 && cuantosJugadores() ==6);
    }

    public boolean agregar(String playerName) {


        jugadores.add(playerName);
        posiciones[cuantosJugadores()] = 0;
        monederos[cuantosJugadores()] = 0;
        enCasillaCastigo[cuantosJugadores()] = false;

        System.out.println(playerName + " se ha unido a la partida");
        System.out.println("Es el jugador número " + jugadores.size());
        return true;
    }

    public static int cuantosJugadores() {
        return jugadores.size(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   );
    }

    public void tirarDado(int puntosDado) {
        System.out.println(jugadores.get(jugadorActual) + " es el jugador actual");
        System.out.println("Ha sacado un " + puntosDado);

        if (enCasillaCastigo[jugadorActual]) {
            if (puntosDado % 2 != 0) { //debe ser impar
                estaSaliendoDeLaCarcel = true;
                enCasillaCastigo[jugadorActual] = false;

                System.out.println(jugadores.get(jugadorActual) + " sale de la casilla de castigo");
                posiciones[jugadorActual] = posiciones[jugadorActual] + puntosDado;
                if (posiciones[jugadorActual] > 11) posiciones[jugadorActual] = posiciones[jugadorActual] - 12;

                System.out.println(nuevaPosicionJugador());
                System.out.println("La categoría es " + categoriaActual());
                hacerPregunta();
            } else {
                System.out.println(jugadores.get(jugadorActual) + " no sale de la casilla de castigo");
                estaSaliendoDeLaCarcel = false;
            }

        } else {

            posiciones[jugadorActual] = posiciones[jugadorActual] + puntosDado;
            if (posiciones[jugadorActual] > 11) posiciones[jugadorActual] = posiciones[jugadorActual] - 12;

           System.out.println(nuevaPosicionJugador());
            System.out.println("La categoría es " + categoriaActual());
            hacerPregunta();
        }

    }

    private String categoriaActual() {
        if (posiciones[jugadorActual] == 0) return "Cultura popular";
        if (posiciones[jugadorActual] == 4) return "Cultura popular";
        if (posiciones[jugadorActual] == 8) return "Cultura popular";
        if (posiciones[jugadorActual] == 1) return "Ciencias";
        if (posiciones[jugadorActual] == 5) return "Ciencias";
        if (posiciones[jugadorActual] == 9) return "Ciencias";
        if (posiciones[jugadorActual] == 2) return "Deportes";
        if (posiciones[jugadorActual] == 6) return "Deportes";
        if (posiciones[jugadorActual] == 10) return "Deportes";
        return "Música";
    }

    public boolean fueRespuestaCorrecta() {
        if (enCasillaCastigo[jugadorActual]){
            if (estaSaliendoDeLaCarcel) {
                return fue_respuestacorrecta();
            } else {
                pasarSiguienteJugador();
                return true;
            }



        } else {
            pasarSiguienteJugador();
            return true;
        }
    }

    private boolean fue_respuestacorrecta() {
        System.out.println("Respuesta correcta!!!!");
        monederos[jugadorActual]++;
        System.out.println(jugadores.get(jugadorActual)
                + " ahora tiene "
                + monederos[jugadorActual]
                + " monedas doradas.");

        boolean ganador = jugadorHaGanado();
        pasarSiguienteJugador();

        return ganador;
    }

    public int pasarSiguienteJugador() {
        jugadorActual++;
        if (jugadorActual == jugadores.size()) jugadorActual = 0;
        return jugadorActual;
    }

    public boolean respuestaIncorrecta(){
        System.out.println("Respuesta incorrecta");
        System.out.println(jugadores.get(jugadorActual)+ " va a la casilla de castigo");
        enCasillaCastigo[jugadorActual] = true;

        pasarSiguienteJugador();
        return true;
    }


    public boolean jugadorHaGanado() {
        return (monederos[jugadorActual] == 6) ; //con 6 monedas gana.

    }


    public String nuevaPosicionJugador() {
        return "La nueva posición de "
                + jugadores.get(jugadorActual)
                + " es "
                + posiciones[jugadorActual];
    }

    public void meterJugadorCarcel(){
        enCasillaCastigo[0] = true;
    }

    public boolean comprobarJugadorCarcel(){

        return enCasillaCastigo[0];
    }

    public boolean probarPregunta(){
        for (int i = 0; i <70; i++){
            Object pregunta = preguntasCultura.removeFirst();
            if ( pregunta == null){
                return false;
            }
            System.out.println(pregunta);
            preguntasCultura.addLast(pregunta);
        }
        return true;
    }

    private void hacerPregunta(){
        if ( categoriaActual() == "Cultura Popular"){
            Object pregunta = preguntasCultura.removeFirst();
            System.out.println(pregunta);
            preguntasCultura.addLast(pregunta);
        }
        if ( categoriaActual() == "Ciencias"){
            Object pregunta = preguntasCiencias.removeFirst();
            System.out.println(pregunta);
            preguntasCiencias.addLast(pregunta);
        }
        if ( categoriaActual() == "Deportes"){
            Object pregunta = preguntasDeportes.removeFirst();
            System.out.println(pregunta);
            preguntasDeportes.addLast(pregunta);
        }
        if ( categoriaActual() == "Musica"){
            Object pregunta = preguntasMusica.removeFirst();
            System.out.println(pregunta);
            preguntasMusica.addLast(pregunta);
        }
    }
}
