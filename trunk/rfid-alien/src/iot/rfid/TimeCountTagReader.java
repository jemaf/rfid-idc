package iot.rfid;

import iot.rfid.config.Configuration;
import iot.rfid.reader.RFIDReaderManager;

import java.util.HashMap;
import java.util.Map;

import com.alien.enterpriseRFID.tags.Tag;

public class TimeCountTagReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++)
			execute();
	}

	private static void execute() {
		// if (args.length != 1) {
		// System.out
		// .println("Informe a quantidade de segundos para leitura.");
		// return;
		// }

		Integer timeCount = 2;
		if (timeCount == null || timeCount < 1) {
			System.out.println("Tempo de leitura deve ser maior que zero");
			return;
		}

		RFIDReaderManager readerManager = new RFIDReaderManager();

		readerManager.createReaderByIp(Configuration.DCC_READER_IP);
		readerManager.openConnection();

		long startTime = System.currentTimeMillis();

		Map<String, Integer> tagReadMap = new HashMap<String, Integer>();
		int count = 0;
		while (System.currentTimeMillis() - startTime < timeCount * 1000) {
			Tag[] tags = readerManager.getReadTags();
			if (tags != null) {
				for (Tag tag : tags) {
					Integer tagCount = tagReadMap.get(tag.getTagID());
					if (tagCount == null) {
						tagReadMap.put(tag.getTagID(), 1);
					} else {
						tagReadMap.put(tag.getTagID(), ++tagCount);
					}
				}
			}
		}

		System.out.println(String.format("Quantidade de tags lidas: %s",
				tagReadMap.keySet().size()));
		for (String key : tagReadMap.keySet()) {
			count += tagReadMap.get(key);
		}
		System.out.println(String.format("Total de leituras : %s", count));

		readerManager.closeConnection();
	}

}
