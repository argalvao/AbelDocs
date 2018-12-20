package server.models;

import java.io.File;
import java.rmi.RemoteException;

import util.DocumentoInterface;

public class Document implements DocumentoInterface{
	
	private File file;
	
	public Document(String path) {
		
	}

	@Override
	public boolean write() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
