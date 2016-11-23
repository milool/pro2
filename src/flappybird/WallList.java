package flappybird;

import java.util.ArrayList;

public class WallList extends ArrayList<Wall> {

	private int index;
	
	public WallList() {
		super();
		index = 0;
	}
	
	@Override
	public void clear() {
		super.clear();
		index = 0;
	}
	
	public Wall getNext() {
		if (index >= this.size()) {
			index = 0;
		}
		return this.get(index++);
	}
	
	public Wall getPrevious() {
		if (index <= 0) {
			return this.get(this.size()-1);
		}
		return this.get(index-1);
	}
	
}
