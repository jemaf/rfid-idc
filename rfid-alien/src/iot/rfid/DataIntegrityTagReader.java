/**
 * 
 */
package iot.rfid;

import iot.rfid.config.Configuration;
import iot.rfid.reader.RFIDReaderManager;

import com.alien.enterpriseRFID.tags.Tag;

/**
 * @author lucasm
 *
 */
public class DataIntegrityTagReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RFIDReaderManager readerManager = new RFIDReaderManager();
		readerManager.createReaderByIp(Configuration.DCC_READER_IP);
		readerManager.openConnection();
		
		//readerManager.writeUserData("lucas oliveira meirelles");
		
		Tag[] tags = readerManager.getReadTags();
		int i = 1;
		for (Tag tag : tags) {
			Tag newTag = ((Tag)tag.clone());
			newTag.setG2Data(0, String.format("lycas %s", i++));
			tag.updateTag(newTag);
		}

		Tag[] tags2 = readerManager.getReadTags();
		for (Tag tag : tags2) {
			System.out.println(String.format("Tag %s", tag.getTagID()));
			System.out.println(String.format("CRC %s", tag.getCRC()));
			for (String data : tag.getG2Data()) {
				System.out.println(String.format("Data %s", data));
			}
			System.out.println();
		}
		
		readerManager.closeConnection();

	}

}
