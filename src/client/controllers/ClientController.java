package client.controllers;

import java.awt.Desktop.Action;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import util.DocumentControllerInterface;
import util.DocumentInterface;

public class ClientController {
	
	private DocumentControllerInterface serverController;
	private static ClientController instance;
	private String userName;
	private Registry registry;
	private DocumentInterface openedDocument; 
	
	private boolean loadDocuments(String docName) {
		if(docName != null) {
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
	
	private ClientController() {
		try {
			registry = LocateRegistry.getRegistry(8000);
	        this.serverController = (DocumentControllerInterface)registry.lookup("MainServer");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ClientController getInstance() {
		if(instance == null) {
			instance = new ClientController();
		}
		return instance;
	}
	

    
}
