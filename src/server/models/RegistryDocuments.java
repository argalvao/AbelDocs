package server.models;

import util.DocumentInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

public class RegistryDocuments {
    private static RegistryDocuments instance;
    private Registry registry;
    private int currentPort;
    private HashSet<Integer> usedPorts;

    private RegistryDocuments() {
        this.currentPort = 8001;
        this.usedPorts = new HashSet<>();
        this.usedPorts.add(8000);
    }

    public static RegistryDocuments getInstance() {
        if (instance == null) {
            instance = new RegistryDocuments();
        }
        return instance;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    private int getValidPort() {
        while (this.currentPort < 30000) {
            if (!this.usedPorts.contains(this.currentPort)) {
                this.usedPorts.add(this.currentPort);
                this.currentPort += 1;
                if (this.currentPort > 300000) {
                    this.currentPort = 8000;
                }
                return this.currentPort;
            }
            this.currentPort += 1;
        }
        return 8080;
    }

    public void bindDocument(Document document) throws RemoteException, AlreadyBoundException {
        DocumentInterface stub = (DocumentInterface) UnicastRemoteObject.exportObject(document, this.getValidPort());
        this.registry.bind(document.path, stub);
    }

    public void unbindDocument(String documentName) throws RemoteException, NotBoundException {
        this.registry.unbind(documentName);
    }
}
