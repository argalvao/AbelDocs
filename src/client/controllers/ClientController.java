package client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DocumentControllerInterface;
import util.DocumentInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientController {

    private static ClientController instance;
    private DocumentControllerInterface serverController;
    private String userName;
    private Registry registry;
    private DocumentInterface openedDocument;

    private ClientController() {
        try {
            registry = LocateRegistry.getRegistry(8000);
            this.serverController = (DocumentControllerInterface) registry.lookup("MainServer");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private boolean loadDocuments(String docName) {
        if (docName != null) {
            try {
                this.serverController.openDocument(docName, this.userName);
                this.openedDocument = (DocumentInterface) this.registry.lookup(docName);
                return true;
            } catch (RemoteException | NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    ObservableList<String> getUserDocuments() {
        try {
            if (this.userName != null) {
                HashSet<String> documents = this.serverController.getUserDocuments(this.userName);
                if (documents != null) {
                    List<String> list = new ArrayList<>(documents);
                    System.out.println("Documents: " + documents);
                    return FXCollections.observableList(list);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String createDocument() {
        try {
            String documentName = MessageDigest.getInstance("MD5").digest(LocalDateTime.now().toString().getBytes()).toString().replace("[", "") + ".abelDocs";
            System.out.println(documentName);
            this.serverController.createDocument(documentName, this.userName);
            return documentName;
        } catch (RemoteException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    String openDocument(String documentName) {
        try {
            this.serverController.openDocument(documentName, this.userName);
            this.openedDocument = (DocumentInterface) registry.lookup(documentName);
            return this.openedDocument.read();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void registerUser(String name) {
        try {
            this.serverController.registerNewUser(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void write(String text) {
        try {
            this.openedDocument.write(text);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
