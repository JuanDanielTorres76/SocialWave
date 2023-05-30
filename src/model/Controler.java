package model;

import java.util.*;

public class Controler {
    
    private IGraph<String, User> graph;

    public Controler() {
        graph = new GraphList<>(false);
    
        String[] names = {"Paula", "Maria", "David", "Juan", "Sofia", "Lucas", "Mateo", "Isabella", "Valentina", "Camila", "Benjamin", "Daniel", "Emily", "Olivia", "Mia", "Sebastian", "Santiago", "Victoria", "Gabriel", "Samuel", "Isabelle", "Natalie", "Angelina", "Carolina", "Juliana", "Andres", "Nicolas", "Carlos", "Fernando", "Diego", "Alicia", "Cecilia", "Diana", "Elena", "Fabiana", "Giselle", "Hannah", "Irene", "Jacqueline", "Katherine", "Liliana", "Mariana", "Nora", "Oriana", "Penelope"};
    
        for (int i = 0; i < names.length; i++) {
            
            User user = new User(names[i]);
            
            graph.addVertex(user.getName(), user);
        
        }
    
        addFriendship("Paula", "Maria"); addFriendship("Paula", "David"); addFriendship("Maria", "Juan"); addFriendship("David", "Juan");
        addFriendship("Paula", "Lucas"); addFriendship("Fabiana", "Juan"); addFriendship("Maria", "Sofia"); addFriendship("Maria", "Mateo"); 
        addFriendship("Maria", "Isabella"); addFriendship("Benjamin", "Juan"); addFriendship("David", "Sofia"); addFriendship("David", "Lucas");
        addFriendship("David", "Mateo"); addFriendship("Juan", "Sofia"); addFriendship("Juan", "Lucas"); addFriendship("Juan", "Mateo"); 
        addFriendship("Juan", "Isabella"); addFriendship("Isabella", "Camila"); addFriendship("Isabella", "Benjamin"); addFriendship("Isabella", "Daniel");
        addFriendship("Sofia", "Lucas"); addFriendship("Sofia", "Mateo"); addFriendship("Sofia", "Isabella"); addFriendship("Sofia", "Valentina");
        addFriendship("Lucas", "Mateo"); addFriendship("Lucas", "Isabella"); addFriendship("Lucas", "Valentina"); addFriendship("Lucas", "Camila"); 
        addFriendship("Mateo", "Isabella"); addFriendship("Mateo", "Valentina"); addFriendship("Mateo", "Camila"); addFriendship("Mateo", "Benjamin"); 
        addFriendship("Isabella", "Valentina"); addFriendship("Valentina", "Camila"); addFriendship("Valentina", "Benjamin"); addFriendship("Valentina", "Daniel");
        addFriendship("Valentina", "Olivia"); addFriendship("Camila", "Benjamin"); addFriendship("Camila", "Daniel"); addFriendship("Camila", "Olivia");
        addFriendship("Diego", "Alicia"); addFriendship("Alicia", "Cecilia"); addFriendship("Cecilia", "Diana"); addFriendship("Diana", "Elena");
        addFriendship("Elena", "Fabiana"); addFriendship("Fabiana", "Giselle"); addFriendship("Giselle", "Hannah"); addFriendship("Hannah", "Irene");
        addFriendship("Irene", "Jacqueline"); addFriendship("Jacqueline", "Katherine"); addFriendship("Katherine", "Liliana"); addFriendship("Liliana", "Mariana");
        addFriendship("Oriana", "Penelope"); addFriendship("Camila", "Mia"); addFriendship("Benjamin", "Daniel"); addFriendship("Olivia", "Sebastian");
        addFriendship("Mia", "Santiago"); addFriendship("Mia", "Gabriel"); addFriendship("Santiago", "Victoria"); addFriendship("Santiago", "Samuel"); addFriendship("Santiago", "Isabelle");
        addFriendship("Paula", "Penelope"); addFriendship("Paula", "Nora"); addFriendship("Maria", "Liliana"); addFriendship("David", "Carlos"); addFriendship("David", "Fernando");
        addFriendship("Sofia", "Irene"); addFriendship("Sofia", "Penelope"); addFriendship("Lucas", "Liliana"); addFriendship("Lucas", "Giselle"); addFriendship("Mateo", "Mariana");
        addFriendship("Valentina", "Diego"); addFriendship("Valentina", "Hannah"); addFriendship("Valentina", "Jacqueline"); addFriendship("Daniel", "Carolina"); addFriendship("Daniel", "Penelope");
        addFriendship("Olivia", "Elena"); addFriendship("Natalie", "Diana"); addFriendship("Emily", "Isabelle"); addFriendship("Sebastian", "Penelope"); addFriendship("Angelina", "Nora");
        
    }

    
    private void addFriendship(String userA, String userB) {
        
        User objUserA = graph.searchVertex(userA).getElement();
        
        User objUserB = graph.searchVertex(userB).getElement();

        objUserA.addFriend(objUserB);
        
        objUserB.addFriend(objUserA);

        
        graph.addEdge(userA, userB, 1); 
    
    }
    
    // BFS

    public List<User> findCommonFriends(String userA, String userB) {
        
        List<User> commonFriends = new ArrayList<>();
        
        graph.BFS(userA);
        
        List<Vertex<String, User>> neighborsA = graph.searchVertex(userA).getGraphList();
        
        graph.BFS(userB);
        
        List<Vertex<String, User>> neighborsB = graph.searchVertex(userB).getGraphList();
        
        for (Vertex<String, User> neighborA : neighborsA) {
            
            for (Vertex<String, User> neighborB : neighborsB) {
                
                if (neighborA.getKey().equals(neighborB.getKey())) {
                    
                    commonFriends.add(neighborA.getElement());
                
                }
            
            }
        
        }
        
        return commonFriends;
    
    }

    public void printUsersWithFriends() {
        for (Vertex<String, User> vertex : graph.getVertices()) {
            
            User user = vertex.getElement();
            
            System.out.print("Usuario: " + user.getName() + " - Amigos: ");
            
            for (User friend : user.getFriends()) {
                
                System.out.print(friend.getName() + ". ");
            
            }
            
            System.out.println();
        
        }
    
    }
    
    public void printCommonFriends(String userA, String userB) {
        List<User> commonFriends = findCommonFriends(userA, userB);
    
        System.out.print("\nAmigos en comun entre " + userA + " y " + userB + ":");
    
        if (commonFriends.isEmpty()) {
            
            System.out.println("\nNo tienen amigos en comun.");
        
        } else {
            
            for (User friend : commonFriends) {
                
                System.out.print(" " + friend.getName() + " -");
            
            }
        
        }
    
    }

    // Dijkstra

    public String analyzeSocialInfluence(String user) {

        double closeness = calculateCloseness(user);
        
        double betweenness = calculateBetweenness(user);
        
        double influence = closeness + betweenness;

        String msj = "";

        if(influence >= 300){

            msj = "\n" + "La influencia social de " + user + " es de " + influence + "\n" +
            user + " cuenta con una gran influencia en la red social. (influencia mayor a la media)" + "\n" +
            "Datos especificos:" + "\n" +
            "Calculo de cercania con los usuarios: " + closeness + "\n" +
            "Calculo de intermedicacion con los usuarios: " + betweenness + "\n";

        
        } else{
            
            msj = "La influencia social de " + user + " es de " + influence + "\n" +
            user + " no cuenta con una gran influencia en la red social. (influencia menor a la media)" + "\n" +
            "Datos especificos:" + "\n" +
            "Calculo de cercania con los usuarios: " + closeness + "\n" +
            "Calculo de intermedicacion con los usuarios: " + betweenness + "\n";
        
        }
        
        return msj;
    
    }    

    private double calculateCloseness(String user) {
        
        double sum = 0;
        
        for (Vertex<String, User> vertex : graph.getVertices()) {
            
            String otherUser = vertex.getKey();
            
            if (!user.equals(otherUser)) {
                
                Path<String> path = graph.dijkstra(user, otherUser);

                // Si hay un camino valido entre los usuarios.
                
                if (path.getDistance() != Double.MAX_VALUE) {
                    
                    sum += path.getDistance();
                
                }
            
            }
        
        }
        
        return sum;
    
    }
    

    private double calculateBetweenness(String user) {
        
        double betweenness = 0;
        
        for (Vertex<String, User> source : graph.getVertices()) {
            
            for (Vertex<String, User> destination : graph.getVertices()) {
                
                if (!source.equals(destination)) {
                    
                    Path<String> path = graph.dijkstra(source.getKey(), destination.getKey());
                    
                    if (path.getPath().contains(user)) {
                        
                        betweenness++;
                    
                    }
                
                }
            
            }
        
        }
        
        return betweenness;
    
    }
    
}