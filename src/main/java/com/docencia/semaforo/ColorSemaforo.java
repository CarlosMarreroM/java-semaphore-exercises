package com.docencia.semaforo;

public class ColorSemaforo implements Runnable {

    public enum Color {
        ROJO, VERDE, AMBAR
    }

    private Color colorActual = Color.ROJO;
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {

            System.out.println("Color actual: " + colorActual);

            try {
                switch (colorActual) {
                    case ROJO -> Thread.sleep(3000);
                    case VERDE -> Thread.sleep(3000);
                    case AMBAR -> Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // Cambiar al siguiente color
            cambiarColor();
        }
        System.out.println("Semáforo detenido.");
    }

    private void cambiarColor() {
        switch (colorActual) {
            case ROJO -> colorActual = Color.VERDE;
            case VERDE -> colorActual = Color.AMBAR;
            case AMBAR -> colorActual = Color.ROJO;
        }
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {

        ColorSemaforo semaforo = new ColorSemaforo();
        Thread hilo = new Thread(semaforo);

        hilo.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // esperar 20 segundos

        semaforo.stop();

        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Programa finalizado.");
    }

}
