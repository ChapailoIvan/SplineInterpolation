import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

public class Main  {
    public static void main(String[] args) {
        Spline.calculateValues();
        Spline.calculateSplineCoeff();
        Spline.buildSpline();
        Spline.getError();

        DrawFrame frame = new DrawFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}

class DrawFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 720;
    public static final int DEFAULT_HEIGHT = 720;

    public DrawFrame()
    {
        setTitle("Лабораторная работа №2");
        add(new DrawPanel());
    }
}

class DrawPanel extends JPanel
{
    final static double shX = 760;
    final static double shY = -340;
    final static double step = 0.0001;
    final static double scale = 200;

    public void paintTemplate (Graphics2D Graph) {
        Graph.draw(new Line2D.Double(shX, 100, shX, 700));
        Graph.draw(new Line2D.Double(100, DrawFrame.DEFAULT_HEIGHT + shY, 1400, DrawFrame.DEFAULT_HEIGHT + shY));

        String string1 = "Исходная функция";
        String string2 = "Интерполяционный cплайн";
        String errorString = "Погрешность интерполирования: ";

        Graph.drawString(errorString + Double.toString(Spline.error), 75, 750);

        Graph.drawString(string1, 75, 59);
        Graph.drawString(string2, 75, 84);

        Graph.setColor(Color.red);
        Graph.fill(new Rectangle(50, 50, 10, 10));

        Graph.setColor(Color.blue);
        Graph.fill(new Rectangle(50, 75, 10, 10));
    }

    public void paintFunction (Graphics2D Graph) {
        double x = Spline.L;
        Graph.setColor(Color.red);

        double y = DrawFrame.DEFAULT_HEIGHT + shY;

        while (x <= Spline.R) {
            Graph.draw(new Line2D.Double(scale * x + shX,         y - scale * Spline.getFuncValue(x) ,
                                         scale * x + step + shX,  y - scale * Spline.getFuncValue(x + step)));

            x += step;
        }
    }

    public void paintSpline(Graphics2D Graph) {
        double x = Spline.L;
        double y = DrawFrame.DEFAULT_HEIGHT + shY;

        Graph.setColor(Color.blue);
        double x1 = Spline.getSplineValue(1.5);
        while (x <= Spline.R - step) {
            Graph.draw(new Line2D.Double(scale * x + shX,       y - scale * Spline.getSplineValue(x),
                                         scale * x + step + shX,y - scale * Spline.getSplineValue(x + step)));
            x += step;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D Graph = (Graphics2D)g;

        paintTemplate(Graph);
        paintFunction(Graph);
        paintSpline(Graph);
    }
}