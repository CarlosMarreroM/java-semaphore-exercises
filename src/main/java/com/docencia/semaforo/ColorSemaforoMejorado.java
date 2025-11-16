package com.docencia.semaforo;

import java.util.concurrent.Semaphore;

public class ColorSemaforoMejorado implements Runnable {

    private final Semaphore turnRojo = new Semaphore(1);
    private final Semaphore turnVerde = new Semaphore(0);
    private final Semaphore turnAmbar = new Semaphore(0);

    private volatile boolean running = true;

    @Override
    public void run() {
        try {
            while (running) {

                // ROJO
                turnRojo.acquire();
                if (!running)
                    break;
                System.out.println("ROJO");
                Thread.sleep(3000);
                turnVerde.release();

                // VERDE
                turnVerde.acquire();
                if (!running)
                    break;
                System.out.println("VERDE");
                Thread.sleep(3000);
                turnAmbar.release();

                // AMBAR
                turnAmbar.acquire();
                if (!running)
                    break;
                System.out.println("AMBAR");
                Thread.sleep(1000);
                turnRojo.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // liberar todos para evitar bloqueos
        turnRojo.release();
        turnVerde.release();
        turnAmbar.release();

        System.out.println("Semáforo detenido (mejorado).");
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) throws InterruptedException {
        ColorSemaforoMejorado sem = new ColorSemaforoMejorado();
        Thread hilo = new Thread(sem);

        hilo.start();

        Thread.sleep(20000);
        sem.stop();

        hilo.join();
        System.out.println("Programa finalizado (mejorado).");
    }
}