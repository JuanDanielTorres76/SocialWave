package ui;

import java.util.Scanner;

import model.*;

public class Main {
    
    private Scanner reader;

    private Controler manager; 

    public static void main(String[] args) {

        startApp();

    }

    public Main(){

        reader = new Scanner(System.in);

        manager = new Controler();

    }

    public static void startApp(){

        System.out.println("Bienvenido a SocialWave.");
        
        Main main = new Main();

        main = new Main();

        main.startMenu();

    }

    public void startMenu() {
    
        int option = 0;
    
        while (option != 5) {

            System.out.println("\nEscoge una opción.\n");
        
            System.out.println(" 1 - Imprimir usuarios de SocialWave.");
        
            System.out.println(" 2 - Encontrar amigos en común entre dos usuarios.");
        
            System.out.println(" 3 - Analizar la influencia de un usuario de SocialWave.");
        
            System.out.println(" 4 - Recomendación de amigos para un usuario de SocialWave.");
        
            System.out.println(" 5 - Salir.");

            option = reader.nextInt();
            
            reader.nextLine();
            
            switch (option) {
                
                case 1:
                    
                    printUsersWithFriends();
                    
                    break;
    
                case 2:

                    System.out.print("Ingrese el nombre del primer usuario: ");

                    String userA = reader.next();
                    
                    System.out.print("Ingrese el nombre del segundo usuario: ");

                    String userB = reader.next();
                
                    findCommonFriends(userA, userB);
                
                    break;
    
                case 3:

                    System.out.print("Ingrese el nombre del usuario: ");

                    String user = reader.next();
                    
                    analyzeSocialInfluence(user);
                    
                    break;
    
                case 4:

                    // Prim - Recomendacion de amigos
                    
                    break;

                case 5:

                    System.out.println("\nHasta pronto!");

                    break;
    
                default:

                    System.out.println("\nEscoge una opción válida.");
                    
                    break;
            
            }
        
        }
    
    } 

    public void findCommonFriends(String userA, String userB){

        manager.printCommonFriends(userA, userB);

    }

    public void analyzeSocialInfluence(String user){

        System.out.println(manager.analyzeSocialInfluence(user));

    }

    public void printUsersWithFriends(){

        manager.printUsersWithFriends();

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