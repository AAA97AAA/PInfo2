package dom.documentsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonView;

import services.utility.View;

/**
 * Document representation implementation
 * with: unique id, name, content (as bytes)
 * 
 * @author kaikoveritch
 *
 */
@Entity
@Table(name = "DOCUMENTS")
public class ConcreteDocument implements Document, Serializable {
	
	// Error logger for file reading problems
	static private Logger logger = LogManager.getLogger(ConcreteDocument.class);

	// Serial version (auto-generated)
	private static final long serialVersionUID = 7828017987854024078L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.DocumentMinimal.class)
	private long id;
	
	@NotNull
	@Column(name = "NAME")
	@JsonView(View.DocumentBase.class)
	private String name;
	
	@Lob
	@NotNull
	@Column(name = "DATA")
	@JsonView(View.DocumentBase.class)
	private byte[] data;

	
	/***** Constructors *****/
	
	ConcreteDocument() {}

	ConcreteDocument(String name, byte[] data) {
		this.name = name;
		this.data = data;
	}
	
	
	/***** Manipulation *****/
	
	/**
	 * Method for fetching a pre-constructed object's data
	 * from a file.
	 * @param targetPath: Path of the file to read.
	 */
	@Override
	public void download(String targetPath) {
		
		// Get the file as stream
		File file = getFile(targetPath);
		FileInputStream stream = getStream(file);
		
		// Read file into buffer
		if (stream == null) {
			return;
		}
		byte[] buffer = getBytes(stream, (int) file.length());
		
		// Update the instance's content
		setName(file.getName());
		setData(buffer);
	}
	
	/**
	 * The following methods are the steps for the 'download' method
	 * and allow for better factoring and testing
	 * (hooray for error control !)
	 */
	
	private File getFile(String targetPath) {
		return new File(targetPath);
	}
	
	private FileInputStream getStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error("Could not open file.", e);
			return null;
		}
	}
	
	private byte[] getBytes(FileInputStream stream, int size) {
		byte[] buffer = new byte[size];
		try {
			stream.read(buffer);
		} catch (IOException e) {
			logger.error("Could not read stream.", e);
			return null;
		}
		try {
			stream.close();
		} catch (IOException e) {
			logger.error("Could not close stream.", e);
		}
		return buffer;
	}

	/***** Getters/Setters *****/

	@Override
	public long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	void setData(byte[] data) {
		this.data = data;
	}
	
	
	/***** Utility *****/

	@Override
	protected ConcreteDocument clone() {
		return new ConcreteDocument(name, data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ConcreteDocument)) {
			return false;
		}
		ConcreteDocument other = (ConcreteDocument) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", name=" + name + "]";
	}
}
