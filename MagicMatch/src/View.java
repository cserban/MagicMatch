import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class View {
	JFrame jFrame;
	JTable jTable;
	MyTableModel model;
	static int latura;
	int run = 2;// 2-stop; 1 -step; 0 - run
	static Map map;
	static Color []colors;

	View(Map newMap) {
		map = newMap;
		colors = new Color[map.numberOfTypes];
		Random randomColor = new Random();
		
		for (int i=0; i< map.numberOfTypes; i++)
		{
			colors[i] = new Color(randomColor.nextInt(255),randomColor.nextInt(255),randomColor.nextInt(255));
		}
		
		latura = map.latura;
		jFrame = new JFrame("Lab1");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		model = new MyTableModel();
		jTable = new JTable(model);

		jTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		jTable.setRowHeight(500 / latura);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < latura; i++) {
			jTable.getColumnModel().getColumn(i)
					.setPreferredWidth(500 / this.latura);
		}
		JPanel panel = new JPanel();
		panel.add(jTable);

		JButton run = new JButton("RUN");
		run.setSize(500 / 3, 50);
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleRun();
			}
		});
		
		JButton stop = new JButton("STOP");
		stop.setSize(500 / 3, 50);
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleStop();
			}
		});
		

		
		JButton step = new JButton("STEP");
		step.setSize(500 / 3, 50);
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleStep();
			}
		});

		JPanel panel1 = new JPanel(new GridLayout(1,3));
		
		panel1.add(run, BorderLayout.EAST);
		panel1.add(stop, BorderLayout.CENTER);
		panel1.add(step, BorderLayout.WEST);

		jFrame.getContentPane().add(panel, BorderLayout.NORTH);
		jFrame.getContentPane().add(panel1, BorderLayout.SOUTH);
		
		jFrame.setSize(500, 600);
		jFrame.setResizable(false);
		jFrame.setVisible(true);
		
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = jTable.rowAtPoint(evt.getPoint());
		        int col = jTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		            System.out.println(row + " " + col);
		            map.selectItem(new Point(row,col));
		            jTable.repaint();
		        }
		    }
		});

	}

	protected void handleStep() {
		if (map.dir != -1)
			map.initDistroy();
		map.animate();
		jTable.repaint();
		
	}

	protected void handleStop() {
		map.initDistroy();
		jTable.repaint();
	}

	protected void handleRun() {
		run = 0;
		map.initDistroy();
		run();
	}

	public void run() {
		
		
		new Thread(new Runnable() {
		    @Override public void run() {
		    	
		    	while(map.snake.size() > 0){
		    	map.animate();
		    	
		    	
				jTable.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	
		    	}
		    }
		    }).start();
		

	}

	static class MyTableCellRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			MyTableModel model = (MyTableModel) table.getModel();
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			
			if (map.map[row][column] != -1)
			 {
				c.setBackground(colors[map.map[row][column]]);
			 }
			else
			{
				c.setBackground(Color.red);
			}
			
			
			if (map.selected.contains(new Point(row,column)))
			{
				c.setBackground(Color.darkGray);
			}
			return c;
		}
	}

	static class MyTableModel extends DefaultTableModel {

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN,
				Color.CYAN);

		@Override
		public int getRowCount() {
			return latura;
		}

		@Override
		public int getColumnCount() {
			return latura;
		}

		public void setRowColour(int row, Color c) {
			rowColours.set(row, c);
			fireTableRowsUpdated(row, row);
		}

		public Color getRowColour(int row) {
			return Color.CYAN;
		}
	}
}
