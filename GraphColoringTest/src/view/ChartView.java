package view;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class ChartView extends JFrame
{
	public ChartView(int chartNumber)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 740);
		setLayout(new GridLayout(chartNumber, 1));
	}
}
