package com.example.trabalhoddm;

import androidx.annotation.NonNull;

public class MinhaThread extends Thread{
    private String nome;

    public MinhaThread(String nome) {

        this.nome = nome;
    }

    public void run(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
