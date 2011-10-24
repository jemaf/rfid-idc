/**
 * 
 */
package iot.rfid;

import java.util.HashMap;
import java.util.Map;

import com.alien.enterpriseRFID.tags.Tag;

import iot.rfid.config.Configuration;
import iot.rfid.exception.RFIDReaderException;
import iot.rfid.reader.RFIDReaderManager;

/**
 * Classe para leitura de múltiplas tags.<br>
 * Faz a leitura de um número pre-determinado de tags.
 * @author lucasm
 *
 */
public class MultipleTagReader {

	/**
	 * Realiza a leitura do número de tags.
	 * @param readerManager Instancia da classe manager de leitura
	 * @param numberOfTags Numero de tags a serem lidas
	 */
	public static void readTags(RFIDReaderManager readerManager, int numberOfTags) {
		if (readerManager == null) {
			throw new RFIDReaderException("Classe de leitura não pode ser null");
		}
		
		Map<String, String> readTagsMap = new HashMap<String, String>();
		
		long initialTime = System.currentTimeMillis();
		while (readTagsMap.keySet().size() < numberOfTags) {
			Tag[] tags = readerManager.getReadTags();
			for (Tag tag : tags) {
				String tagId = readTagsMap.get(tag.getTagID());
				if (tagId == null) {
					readTagsMap.put(tag.getTagID(), tag.getTagID());
				}
			}
		}
		long finalTime = System.currentTimeMillis();
		
		
		System.out.println(String.format("Tempo total para leitura de %s tags : %s seg.", numberOfTags, (double)(finalTime-initialTime)/1000));
		System.out.println("Tags lidas:");
		for (String tagId : readTagsMap.keySet()) {
			System.out.println(tagId);
		}
	}
	
	/**
	 * Main.
	 * @param args Argumentos de entrada
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Numero de tags a serem lidas nao informado.");
			return;
		}
		
		Integer tagNumber = Integer.parseInt(args[0]);
		
		RFIDReaderManager readerManager = new RFIDReaderManager();
		readerManager.createReaderByIp(Configuration.DCC_READER_IP);
		boolean connectionOpened = readerManager.openConnection();

		if (connectionOpened) {
			MultipleTagReader.readTags(readerManager, tagNumber);
		}
		
		readerManager.closeConnection();
	}

}
