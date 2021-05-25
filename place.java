package workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class place extends JPanel
{
    private int N;
    private double[][] Point;
    private double[][] a;
    private Point mousPt;
    private int nPoint;

    public place()
    {
        N = 10;
        Point = new double[N][2];
        nPoint = -1;
        a = new double[][]{{130,100},{100,500},{300,500},{500,420},{500,300},{210,130},{500,200},{450,250},{900,350},{950,200}};
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                Point[i][j] = a[i][j];
            }
        }

        setPreferredSize(new Dimension(1000,500));

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                mousPt = e.getPoint();
                for (int i = 0; i < N; i++)
                {
                    if (Math.pow(Point[i][0]-mousPt.x,2) + Math.pow(Point[i][1]-mousPt.y,2) <= 25)
                    {
                        nPoint = i;
                        i = N;
                    }
                    else nPoint = -1;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (nPoint != -1)
                {
                    Point[nPoint][0] = e.getX();
                    Point[nPoint][1] = e.getY();
                    repaint();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g)
    {
        int width = 1000;
        int hight = 500;
        super.paintComponent(g);

        int k = 0;
        double[][] p = new double[4][2];
        double[] b = new double[2];
        b[0] = Point[0][0];
        b[1] = Point[0][1];
        for (int i = 0; i < N - 2; i+=2)
        {
            p[0][0] = b[0];
            p[0][1] = b[1];
            p[1][0] = Point[i+1][0];
            p[1][1] = Point[i+1][1];
            p[2][0] = Point[i+2][0];
            p[2][1] = Point[i+2][1];
            if ((N - 4 - k * 2)  / 2 == 0)
            {
                p[3][0] = Point[i+3][0];
                p[3][1] = Point[i+3][1];
            }
            else if (N - 2 - k * 2 == 1)
            {
                p[3][0] = p[2][0];
                p[3][1] = p[2][1];
            }
            else
            {
                b[0] = (Point[i+2][0] + Point[i+3][0]) / 2;
                b[1] = (Point[i+2][1] + Point[i+3][1]) / 2;
                p[3][0] = b[0];
                p[3][1] = b[1];

                g.setColor(Color.red);
                g.fillOval((int)p[3][0]-5,(int)p[3][1]-5,10,10);
            }
            k+=1;

            double x0 = p[0][0];
            double y0 = p[0][1];
            for (double t = 0; t <= 1; t += 0.0005)
            {
                double x = Math.pow((1-t),3)*p[0][0] + 3*Math.pow((1-t),2)*t*p[1][0] + 3*(1-t)*Math.pow(t,2)*p[2][0] + Math.pow(t,3)*p[3][0];
                double y = Math.pow((1-t),3)*p[0][1] + 3*Math.pow((1-t),2)*t*p[1][1] + 3*(1-t)*Math.pow(t,2)*p[2][1] + Math.pow(t,3)*p[3][1];

                g.setColor(Color.blue);
                g.drawLine((int)x0,(int)y0,(int)x,(int)y);

                x0 = x;
                y0 = y;
            }
        }

        g.setColor(Color.green);
        for (int i = 0; i < N; i++) {
            g.fillOval((int) Point[i][0] - 5, (int) Point[i][1] - 5, 10, 10);
        }

        g.setColor(Color.MAGENTA);
        for (int i = 0; i < 9; i++)
        {
            g.drawLine((int)Point[i][0],(int)Point[i][1],(int)Point[i+1][0],(int)Point[i+1][1]);
        }
    }
}