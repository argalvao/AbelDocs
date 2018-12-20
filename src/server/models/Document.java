package server.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import util.DocumentInterface;

public class Document implements DocumentInterface {

	private BufferedWriter bufferedWriter;
	public final String path;

	public Document(String path) throws IOException {
		this.path = path;
		File file = new File(path);
		if (!file.exists()) {
			//noinspection ResultOfMethodCallIgnored
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file);
		this.bufferedWriter = new BufferedWriter(this.bufferedWriter);
	}

	@Override
	public synchronized void write(String toWrite) throws RemoteException {
		try {
			this.bufferedWriter.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
