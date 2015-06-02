package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.ModelPoint;

public class View extends JFrame
{
	private static final long serialVersionUID = 5880489977738002655L;
	private List<ModelPoint> list;
	private Map<Integer, Color> clusterMap = new LinkedHashMap<Integer, Color>();
	private Random random = new Random();

	public View()
	{
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showList(List<ModelPoint> list)
	{
		this.list = list;
		JPanel jpanel = new MyJPanel();
		add(jpanel);
		setVisible(true);
	}

	public class MyJPanel extends JPanel implements MouseListener
	{
		private static final long serialVersionUID = -751876062558106970L;
		private static final int DIAMETER = 5;
		private static final int REGION_DIAMETER = 80;

		public MyJPanel()
		{
			addMouseListener(this);
		}

		@Override
		public void paint(Graphics g)
		{
			g.setColor(Color.BLACK);
			for (int i = 0; i < 5; i++)
				g.drawLine(i * 100, 0, i * 100, 500);
			for (int i = 0; i < 5; i++)
				g.drawLine(0, i * 100, 500, i * 100);
			for (ModelPoint mp : list)
			{
				g.setColor(findColor(mp.getClusterId()));
				g.fillOval(mp.getX() - DIAMETER / 2, mp.getY() - DIAMETER / 2, DIAMETER, DIAMETER);
				if (mp.getClusterId() != null)
				{
					g.setColor(Color.BLACK);
					g.drawOval(mp.getX() - REGION_DIAMETER / 2, mp.getY() - REGION_DIAMETER / 2, REGION_DIAMETER, REGION_DIAMETER);
				}
			}
		}

		private Color findColor(Integer colorId)
		{
			Color c = clusterMap.get(colorId);
			if (c == null)
			{
				c = generateRandomColor();
				clusterMap.put(colorId, c);
			}
			return c;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			ModelPoint mp = findClosest(arg0.getX(), arg0.getY());
			System.out.println(arg0.getX() + "x" + arg0.getY() + ", probably: " + mp.getId() + "[" + mp.getX() + "x" + mp.getY() + ":" + mp.getClusterId()
					+ "]");
		}

		private ModelPoint findClosest(int x, int y)
		{
			double minDist = Double.MAX_VALUE;
			ModelPoint minMp = null;
			for (ModelPoint mp : list)
			{
				double curDist = (x - mp.getX()) * (x - mp.getX()) + (y - mp.getY()) * (y - mp.getY());
				if (curDist < minDist)
				{
					minMp = mp;
					minDist = curDist;
				}
			}
			return minMp;
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public Color generateRandomColor()
	{
		return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
	}
}
