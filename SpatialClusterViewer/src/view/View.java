package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.ModelObject;
import view.drawer.ModelObjectDrawer;
import view.drawer.ModelObjectDrawerFactory;

public class View extends JFrame
{
	private static final long serialVersionUID = 5880489977738002655L;
	private List<ModelObjectDrawer> list = new LinkedList<ModelObjectDrawer>();
	private Map<Integer, Color> clusterMap = new LinkedHashMap<Integer, Color>();
	private Random random = new Random();

	public View()
	{
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clusterMap.put(null, Color.LIGHT_GRAY);
	}

	public void showList(List<? extends ModelObject> list)
	{
		for (ModelObject mo : list)
			this.list.add(ModelObjectDrawerFactory.create(mo));
		JPanel jpanel = new MyJPanel();
		add(jpanel);
		setVisible(true);
	}

	public class MyJPanel extends JPanel implements MouseListener
	{
		private static final long serialVersionUID = -751876062558106970L;

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
			for (ModelObjectDrawer mp : list)
			{
				g.setColor(findColor(mp.getModelObject().getClusterId()));
				mp.drawObject(g);
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
			ModelObject mp = findClosest(arg0.getX(), arg0.getY());
			System.out.println(arg0.getX() + "x" + arg0.getY() + ", probably: " + (mp == null ? "unknown" : mp.toString()));
		}

		private ModelObject findClosest(int x, int y)
		{
			return list.get(0).findClosest(list, x, y);
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
