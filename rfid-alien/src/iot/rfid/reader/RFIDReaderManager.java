/**
 * 
 */
package iot.rfid.reader;

import iot.rfid.exception.RFIDReaderException;

import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderConnectionException;
import com.alien.enterpriseRFID.reader.AlienReaderException;
import com.alien.enterpriseRFID.reader.AlienReaderNotValidException;
import com.alien.enterpriseRFID.reader.AlienReaderTimeoutException;
import com.alien.enterpriseRFID.tags.Tag;

/**
 * Classe Manager para utiliza��o de fun��es de leitura
 * @author lucasm
 * 
 */
public class RFIDReaderManager {
	public static final int NETWORK_PORT_DEFAULT = 23;
	public static final String USERNAME_DEFAULT = "alien";
	public static final String PASSWORD_DEFAULT = "password";
	public static final String SERIAL_PORT_DEFAULT = "COM1";

	private AlienClass1Reader reader = null;

	public AlienClass1Reader createReaderBySerialPort() {
		return this.createReaderBySerialPort(SERIAL_PORT_DEFAULT);
	}

	public AlienClass1Reader createReaderBySerialPort(String port) {
		AlienClass1Reader reader = new AlienClass1Reader();
		reader.setSerialConnection(port);
//		reader.setTagType(AlienClass1Reader.CLASS1GEN2);
		
		this.reader = reader;
		return reader;
	}

	public AlienClass1Reader createReaderByIp(String ip) {
		return this.createReaderByIp(ip, RFIDReaderManager.NETWORK_PORT_DEFAULT,
				RFIDReaderManager.USERNAME_DEFAULT,
				RFIDReaderManager.PASSWORD_DEFAULT);
	}

	public AlienClass1Reader createReaderByIp(String ip, int port) {
		return this.createReaderByIp(ip, port,
				RFIDReaderManager.USERNAME_DEFAULT,
				RFIDReaderManager.PASSWORD_DEFAULT);
	}

	public AlienClass1Reader createReaderByIp(String ip, int port,
			String username) {
		return this.createReaderByIp(ip, port, username,
				RFIDReaderManager.PASSWORD_DEFAULT);
	}

	public AlienClass1Reader createReaderByIp(String ip, int port,
			String username, String password) {
		AlienClass1Reader reader = new AlienClass1Reader();
		reader.setNetworkConnection(ip, port);
		reader.setUsername(username);
		reader.setPassword(password);

		this.reader = reader;

		return reader;
	}

	public Tag[] getReadTags() {
		if (this.reader == null) {
			return null;
		}

		try {
			return this.reader.getTagList();
		} catch (AlienReaderException e) {
			System.out.println("Erro ao ler tags de leitor:");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public boolean openConnection() {
		if (this.reader != null) {
			try {
				this.reader.open();
				return true;
			} catch (AlienReaderNotValidException e) {
				System.out.println("Erro ao tentar abrir conexao com leitor.");
				e.printStackTrace();
			} catch (AlienReaderTimeoutException e) {
				System.out.println("Erro ao tentar abrir conexao com leitor.");
				e.printStackTrace();
			} catch (AlienReaderConnectionException e) {
				System.out.println("Erro ao tentar abrir conexao com leitor.");
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean closeConnection() {
		if (this.reader != null) {
			if (this.reader.isOpen()) {
				this.reader.close();
				return true;
			}
		}

		return false;
	}

	public boolean writeEPCData(String data) {
		if (this.reader != null) {
			try {
				this.reader.programEPC(data);
				return true;
			} catch (AlienReaderException e) {
				throw new RFIDReaderException("Erro ao gravar EPC nas tags.", e);
			}
		}
		
		return false;
	}
	
	public boolean writeUserData(String data) {
		if (this.reader != null) {
			try {
				this.reader.programUser(data);
				return true;
			} catch (AlienReaderException e) {
				throw new RFIDReaderException("Erro ao gravar User data nas tags.", e);
			}
		}
		
		return false;
	}
}
