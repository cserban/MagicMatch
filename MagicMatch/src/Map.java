import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class Map {
	public Integer[][] map;
	int latura;
	public int numberOfTypes;
	int dir; //0 = N ; 1 = V; 2 = S; 3 = E
	public int selectedType = -1;
	ArrayList<Point> selected;
	ArrayList<Point> snake;
	
	public Map (int latura, int numberOfTypes)
	{
		this.latura = latura;
		this.numberOfTypes = numberOfTypes;
		map = new Integer[this.latura][this.latura];
		selected = new ArrayList<>();
		snake = new ArrayList<>();
		generateMap();
	}
	
	void generateMap()
	{
		Random randomGenerator = new Random();
		for (int i = 0; i < this.latura; i++)
			for (int j = 0; j < this.latura; j++)
			{
				map[i][j] = randomGenerator.nextInt(this.numberOfTypes);
			}
	}
	
	public void explode()
	{
		if (selected.size() > 2)
		{
			for (Point p : selected)
			{
				map[p.x][p.y] = -1;
			}
			removeSelection();
		}
	}
	
	public boolean selectItem(Point select)
	{
		if (selectedType == -1)
		{
			selectedType = map[select.x][select.y];
		}
		if (selectedType == map[select.x][select.y])
		{
			if (!selected.contains(select))
			{
				if (selected.size() > 0)
				{
					if(areNeighbour(select,selected.get(selected.size()-1)))
							{
						selected.add(select);
							}
				}
				else
				{
					selected.add(select);
				}
				if (selected.size() == 2)
				{
					setDirection(selected.get(0),selected.get(1));
				}
			}
			return true;
		}
		removeSelection();
		return false;
	}
	
	void setDirection(Point a, Point b)
	{
		if (a.x == b.x)
		{
			if (a.y < b.y)
				dir = 1;
			else
				dir = 3;
		}
		else
		{
			if (a.x < b.x)
				dir = 0;
			else
				dir = 2;
		}
	}
	
	
	public void removeSelection()
	{
		selected.clear();
		selectedType = -1;
	}
	
	public void animate()
	{
		if (selected.size() > 0)
		{
		snake.add(selected.get(0));
		selected.remove(0);
		for (int i = snake.size()-1; i > 0 ; i--)
		{
			Point a = snake.get(i);
			Point b = snake.get(i-1);
			map[a.x][a.y] = map[b.x][b.y]; 
		}
		Random randomGenerator = new Random();
		map[snake.get(0).x][snake.get(0).y] = randomGenerator.nextInt(this.numberOfTypes);
		if (selected.size() == 0)
		{
			snake.clear();
		}
		
		}
		
	}
	
	public void initDistroy()
	{
		selectedType = -1;
		switch (dir)
		{
		case 0: 
			for (int i = 0; i< selected.get(0).x ; i++)
			{
				snake.add(new Point(i,selected.get(0).y));
			}
			break;
		case 1: 
			for (int i = 0; i< selected.get(0).y ; i++)
			{
				snake.add(new Point(selected.get(0).x,i));
			}
			break;
		case 2: 
			for (int i = latura-1; i> selected.get(0).x ; i--)
			{
				snake.add(new Point(i,selected.get(0).y));
			}
			break;
		case 3: 
			for (int i = latura-1; i> selected.get(0).y; i--)
			{
				snake.add(new Point(selected.get(0).x,i));
			}
			break;
		}
	}
	
	boolean areNeighbour(Point a,Point b)
	{
		if (a.x == b.x)
		{
			if (a.y == (b.y - 1))
			{
				return true;
			}
			if (a.y == (b.y + 1))
			{
				return true;
			}
		}
		else if (a.y == b.y)
		{
			if (a.x == (b.x - 1))
			{
				return true;
			}
			if (a.x == (b.x + 1))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		
		String tmp = new String();
		for (int i = 0; i < this.latura; i++)
		{
			for (int j = 0; j < this.latura; j++)
			{
				tmp += map[i][j] + " ";
			}
			tmp += "\n";
		}
		return tmp;
	}
}
