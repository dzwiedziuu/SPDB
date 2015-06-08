package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PointGenerator
{

	public void generate(String oFile, int n, int xmax, int ymax) throws IOException
	{
		Random random = new Random();
		BufferedWriter bw = new BufferedWriter(new FileWriter(oFile));
		for (int i = 0; i < n; i++)
			bw.write("" + i + " " + random.nextInt(xmax) + " " + random.nextInt(ymax) + " " + "0\n");
		bw.flush();
		bw.close();
	}
}
