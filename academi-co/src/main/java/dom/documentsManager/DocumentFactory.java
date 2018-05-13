package dom.documentsManager;

/**
 * Document instantiator class.
 * 
 * @author kaikoveritch
 *
 */
public class DocumentFactory {

	static public Document createDocument(String name, byte[] data) {
		return new ConcreteDocument(name, data);
	}
	
	static public Document createDocument(Document document) {
		return ((ConcreteDocument) document).clone();
	}
	
	/**
	 * Creates a new document and loads its data from the given path.
	 * 
	 * @param path
	 * @return
	 */
	static public Document loadDocument(String path) {
		
		// Create empty Document instance
		Document doc = new ConcreteDocument();
		
		// Fill document data by reading file
		doc.download(path);
		
		// Return result
		return doc;
	}
	
	/**
	 * Replaces the oldDocument data with the newDocument data.
	 * 
	 * @param oldDocument
	 * @param newDocument
	 */
	static public void replaceDocument(Document oldDocument, Document newDocument) {
		ConcreteDocument target = ((ConcreteDocument) oldDocument);
		target.setName(newDocument.getName());
		target.setData(newDocument.getData());
	}
}
