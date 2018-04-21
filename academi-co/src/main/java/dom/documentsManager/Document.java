package dom.documentsManager;

public interface Document {

	public long getId();

	public String getName();

	public byte[] getData();
	
	public void download(String targetPath);
}
