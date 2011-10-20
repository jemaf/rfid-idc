import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderException;
import com.alien.enterpriseRFID.tags.Tag;

public class RFIDReader {

	private static final String ip = "150.164.7.73";
	private static final int port = 23;
	private Tag[] tagList;

	/**
	 * faz a leitura das tags
	 * @throws AlienReaderException
	 */
	public void readTags() throws AlienReaderException {

		// instancia o leitor
		AlienClass1Reader reader = new AlienClass1Reader();
		reader.setNetworkConnection(ip, port);

		reader.open();
		tagList = reader.getTagList();

		if (tagList == null)
			System.err.println("No tags found");

		reader.close();
		printTags();
	}

	/**
	 * Imprime as tags
	 */
	private void printTags() {
		if (tagList == null) {
			System.out.println("No Tags Found");
		} else {
			System.out.println("Tag(s) found:");
			for (int i = 0; i < tagList.length; i++) {
				Tag tag = tagList[i];
				System.out.println("ID:" + tag.getTagID() + ", Discovered:" + tag.getDiscoverTime()
						+ ", Last Seen:" + tag.getRenewTime() + ", Antenna:" + tag.getAntenna()
						+ ", Reads:" + tag.getRenewCount());
			}
		}
	}

	public static void main(String args[]) {
		RFIDReader reader = new RFIDReader();
		try {
			reader.readTags();
		} catch (AlienReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
