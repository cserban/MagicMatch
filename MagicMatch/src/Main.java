import java.awt.Point;
import java.util.ArrayList;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map newMap = new Map(10, 5);
		ArrayList<Point> distroy = new ArrayList<>();
		//distroy.add(new Point(0,1));
		//distroy.add(new Point(0,3));
		//distroy.add(new Point(0,2));
		//newMap.distroy(distroy);
		System.out.println(newMap);
		View newView = new View(newMap);
	}

}
