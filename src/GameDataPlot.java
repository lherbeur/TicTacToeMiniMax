import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class GameDataPlot extends ApplicationFrame {

	public GameDataPlot( String applicationTitle , String chartTitle, int [] won, int [] played) {
	      
		super(applicationTitle);
	      
		JFreeChart lineChart = ChartFactory.createLineChart(
	         chartTitle,
	         "Games Played","Games Won",
	         createDataset(won, played),
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	   }

	   private DefaultCategoryDataset createDataset( int [] won, int [] played) {
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      
	      for (int i=0; i<won.length; i++)
	      {
	    	  dataset.addValue(won[i] , "won" , String.valueOf(played[i]));
	    	  dataset.addValue(played[i]-won[i] , "lost" , String.valueOf(played[i]));
	      }
	      
	      return dataset;
	   }
}
