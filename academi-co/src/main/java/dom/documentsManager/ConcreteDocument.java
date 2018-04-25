package dom.documentsManager;

import java.io.File;
import java.io.FileInputStream;
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

	// Serial version (auto-generated)
	private static final long serialVersionUID = 7828017987854024078L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	@Lob
	@NotNull
	@Column(name = "DATA")
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
		
		// Get the file and ready buffer
		File file = new File(targetPath);
		byte[] buffer = new byte[(int) file.length()];
		
		// Read file into buffer (hooray for error control !)
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			stream.read(buffer); // Here the file is read
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				stream.close();
			} catch (IOException | NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		// Update the instance's content
		setName(file.getName());
		setData(buffer);
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", name=" + name + "]";
	}
}
