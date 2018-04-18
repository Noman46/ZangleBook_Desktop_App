package ZangalBook;

public class Item {

	private String name;
	private byte[] imagess;
	
	public Item(String name,byte[] IMAGE) {
		
		this.name=name;
		this.imagess=IMAGE;
	}
	

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	

	public byte[] getImage() {
		// TODO Auto-generated method stub
		return imagess;
	}
}
