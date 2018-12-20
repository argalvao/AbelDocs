package server.controllers;

import server.models.Document;
import server.models.RegistryDocuments;
import util.DocumentControllerInterface;
import util.exceptions.IncorrectArgumentsException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Controller implements DocumentControllerInterface {

    private static Controller instance;
    private HashMap<String, HashSet<String>> userDocuments;
    private HashMap<String, HashSet<String>> documentUsers;
    private HashMap<String, Document> openedDocuments;

    private Controller() throws IOException {
        this.userDocuments = new HashMap<>();
        this.documentUsers = new HashMap<>();
        this.openedDocuments = new HashMap<>();
        readInputFile("docs.csv");
    }

    public static Controller getInstance() {
        if (instance == null) {
            try {
                instance = new Controller();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private void readInputFile(String path) throws IOException, IncorrectArgumentsException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        for (String line : lines) {
            String[] elements = line.split(";");
            if (elements.length != 2) {
                throw new IncorrectArgumentsException();
            }
            if (!this.userDocuments.containsKey(elements[0])) {
                this.userDocuments.put(elements[0], new HashSet<>());
            }
            this.userDocuments.get(elements[0]).add(elements[1].replace(" ", ""));
            if (!this.documentUsers.containsKey(elements[1])) {
                this.documentUsers.put(elements[1], new HashSet<>());
            }
            this.documentUsers.get(elements[1]).add(elements[0]);
        }
    }

    public synchronized String openDocument(String documentName, String user) throws RemoteException {
        if (this.openedDocuments.containsKey(documentName) && this.documentUsers.get(documentName).contains(user)) {
            return documentName;
        }
        if (this.documentUsers.containsKey(documentName) && this.documentUsers.get(documentName).contains(user)) {
            try {
                this.openedDocuments.put(documentName, new Document(documentName));
                try {
                    RegistryDocuments.getInstance().bindDocument(this.openedDocuments.get(documentName));
                    return documentName;
                } catch (AlreadyBoundException e) {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public synchronized void closeDocument(String documentName) throws RemoteException {
        this.openedDocuments.remove(documentName);
        try {
            RegistryDocuments.getInstance().unbindDocument(documentName);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean createDocument(String documentName, String owner) throws RemoteException {
        if (!this.userDocuments.containsKey(owner)) {
            this.userDocuments.put(owner, new HashSet<>());
        }
        if (!this.documentUsers.containsKey(documentName)) {
            this.documentUsers.put(documentName, new HashSet<>());
            this.documentUsers.get(documentName).add(owner);
            this.userDocuments.get(owner).add(documentName);
            try {
                Document document = new Document(documentName);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public HashSet<String> getUserDocuments(String user) throws RemoteException {
        return this.userDocuments.get(user);
    }

    public HashSet<String> getDocumentUsers(String document) throws RemoteException {
        return documentUsers.get(document);
    }

    public void registerNewUser(String username) throws RemoteException {
        if (!this.userDocuments.containsKey(username)) {
            this.userDocuments.put(username, new HashSet<>());
        }
    }
}
