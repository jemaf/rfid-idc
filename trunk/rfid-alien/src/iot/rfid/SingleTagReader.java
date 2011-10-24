/**
 * 
 */
package iot.rfid;

import iot.rfid.config.Configuration;
import iot.rfid.reader.RFIDReaderManager;

/**
 * Classe para leitura de uma única tag.<br>
 * A execução desta classe fica condicionada a leitura de pelo menos uma tag pelo leitor.
 * @author lucasm
 *
 */
public class SingleTagReader {

	/**
	 * Main
	 * @param args Parametros de execucao
	 */
	public static void main(String[] args) {
		RFIDReaderManager readerManager = new RFIDReaderManager();
		readerManager.createReaderByIp(Configuration.DCC_READER_IP);
		boolean connectionOpened = readerManager.openConnection();

		if (connectionOpened) {
			MultipleTagReader.readTags(readerManager, 1);
		}

		readerManager.closeConnection();
	}

}
