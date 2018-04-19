package dom.documentsManager;

public class DocumentFactory {

	static public Document createDocument(String name, byte[] data) {
		return new ConcreteDocument(name, data);
	}
	
	static public Document createDocument(Document document) {
		return document.clone();
	}
	
	static public Document loadDocument(String path) {
		
		// Create empty Document instance
		Document doc = new ConcreteDocument();
		
		// Fill document data by reading file
		doc.download(path);
		
		// Return result
		return doc;
	}
}
