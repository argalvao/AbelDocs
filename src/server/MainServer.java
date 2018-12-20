package server;

import server.controllers.Controller;
import server.models.RegistryDocuments;
import util.DocumentControllerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    public static void main(String... args) {
        try {
            DocumentControllerInterface stub = (DocumentControllerInterface) UnicastRemoteObject.exportObject(Controller.getInstance(), 8000);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(8000);
            registry.bind("MainServer", stub);
            RegistryDocuments.getInstance().setRegistry(registry);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
