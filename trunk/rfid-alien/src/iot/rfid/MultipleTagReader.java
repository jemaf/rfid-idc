/**
 * 
 */
package iot.rfid;

import java.util.HashMap;
import java.util.Map;

import com.alien.enterpriseRFID.tags.Tag;

import iot.rfid.config.Configuration;
import iot.rfid.reader.RFIDReaderManager;

/**
 * @author lucasm
 *
 */
public class MultipleTagReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Numero de tags a serem lidas nao informado.");
			return;
		}
		
		Integer tagNumber = Integer.parseInt(args[0]);
		Map<String, String> readTagsMap = new HashMap<String, String>();
		
		RFIDReaderManager readerManager = new RFIDReaderManager();
		readerManager.createReaderByIp(Configuration.DCC_READER_IP);
		readerManager.openConnection();

		long initialTime = System.currentTimeMillis();
		while (readTagsMap.keySet().size() < tagNumber) {
			Tag[] tags = readerManager.getReadTags();
			for (Tag tag : tags) {
				String tagId = readTagsMap.get(tag.getTagID());
				if (tagId == null) {
					readTagsMap.put(tag.getTagID(), tag.getTagID());
				}
			}
		}
		long finalTime = System.currentTimeMillis();
		
		readerManager.closeConnection();
		
		System.out.println(String.format("Tempo total para leitura de %s tags : %s seg.", tagNumber, (double)(finalTime-initialTime)/1000));
		System.out.println("Tags lidas:");
		for (String tagId : readTagsMap.keySet()) {
			System.out.println(tagId);
		}
	}

}
