package ui;

import java.util.Scanner;

import model.*;

public class Main {
    
    private Scanner reader;

    private Controler manager; 

    public static void main(String[] args) {
        
        Main main = new Main();

        menu();

    }

    public static void menu(){

        System.out.println("Welcome to our program");

    }

    public Main(){

        reader = new Scanner(System.in);

        manager = new Controler();

    }

    public Scanner getReader() {
        return reader;
    }

    public void setReader(Scanner reader) {
        this.reader = reader;
    }

    public Controler getManager() {
        return manager;
    }

    public void setManager(Controler manager) {
        this.manager = manager;
    }

    

}
