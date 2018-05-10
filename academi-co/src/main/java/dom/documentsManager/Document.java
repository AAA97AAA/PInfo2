package dom.documentsManager;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Document representation definition.
 * 
 * @author kaikoveritch
 *
 */
@JsonDeserialize(as = ConcreteDocument.class)
public interface Document {

	public long getId();

	public String getName();

	public byte[] getData();
	
	public void download(String targetPath);
}
