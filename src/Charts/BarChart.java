
package Charts;

import Extras.Fields;
import java.awt.Color;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;


public class BarChart  implements Fields {

    private ChartPanel chartpanel;
    private JDBCCategoryDataset dataset;
    private CategoryPlot plot;
    private BarRenderer renderer;
    
    public BarChart(String query , String []title)  {
        try {
            dataset = new JDBCCategoryDataset(database,driver,user,password);
            dataset.executeQuery(query);
            JFreeChart chart = ChartFactory.createBarChart3D(
                    title[0], title[1], title[2], dataset,
                    PlotOrientation.VERTICAL, true, true, false);

//            // -------------------- Coloring -------------------- //
           // chart.setBackgroundPaint(Color.white);
            plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setRangeGridlinePaint(Color.white);
            renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.DARK_GRAY);
            renderer.setSeriesPaint(1, Color.cyan);
            //renderer.setDrawBarOutline(false);
            renderer.setItemMargin(0.0);
//            // ------------------------ End ------------------- //

            chartpanel = new ChartPanel(chart, false);
            chartpanel.addChartMouseListener(new Handlers());

            
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
    }

    public ChartPanel getChartPanel() {
        return chartpanel;
    }
}
