package items;

public class Trophy extends Item{

	private static String ITEM_NAME = "key";
	private static int ITEM_SIZE = 16;
	
	public Trophy(int worldx, int worldy) {
		super(ITEM_NAME, worldx, worldy, ITEM_SIZE);
		loadImage();
	}



}
