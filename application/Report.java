package application;

import java.awt.TextField;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sun.javafx.collections.MappingChange.Map;

import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Report {


  public ArrayList<ArrayList<Milk>> getMonthReport() {
    return monthReport;
  }

  public ArrayList<ArrayList<Milk>> getAnnual() {
    return annual;
  }

  public ArrayList<ArrayList<Milk>> getRangeReport() {
    return rangeReport;
  }
  
  public ArrayList<ArrayList<Milk>> getReportList() {
    return reportList;
  }


  private PieChart pieChart;
	private String title;
	private ArrayList<ArrayList<Milk>> reportList = new ArrayList<ArrayList<Milk>>();
	private ArrayList<ArrayList<Milk>> monthReport = new ArrayList<ArrayList<Milk>>();
	private int[] monthWeights;
	private ArrayList<ArrayList<Milk>> annual = new ArrayList<ArrayList<Milk>>();

	private ArrayList<ArrayList<Milk>> rangeReport = new ArrayList<ArrayList<Milk>>();

	public Report(String title) {
		super();
		this.title = title;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("Report for ");
		sb.append(this.title);
		return sb.toString();
	}

	public PieChart createPieChart() {
		PieChart pieChart = new PieChart();
		if (getTitle().equals("Annul Report")) {

			int[] weights = getWeightsAnnual();
			String[] farmId = getNameAnnual();

			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(farmId[i], weights[i]));
			}
			return pieChart;

		} else if (getTitle().equals("FarmId Report")) {
			int[] weights = getWeightsFarm();
			String[] months = new String[] { "January", "Feburary", "March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December" };

			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(months[i], weights[i]));
			}

			return pieChart;
		} else if (getTitle().equals("Month Report")) {
			int[] weights = getWeightsMonth();
			String[] names = getFarmNamesMonth();
			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(names[i], weights[i]));
			}
			return pieChart;
		} else if (getTitle().equals("Dates Report")) {

			int[] weights = getRangedWeights();
			String[] farmId = getRangedNames();
			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(farmId[i], weights[i]));

			}
			return pieChart;
		}
		return null;
	}

	private PieChart.Data createData(String alias, int weight) {
		return new PieChart.Data(alias, weight);
	}
	


	private int[] getWeightsFarm() {

		int[] weights = new int[12];
		for (int i = 0; i < reportList.size(); i++) {
			for (int j = 0; j < reportList.get(i).size(); j++) {
				weights[i] = reportList.get(i).get(j).getWeight();
			}
		}
		return weights;

	}

	private int[] getWeightsMonth() {
		int[] weights = new int[this.monthReport.size()];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < monthReport.get(i).size(); j++) {
				weights[i] += monthReport.get(i).get(j).getWeight();
			}
		}
		return weights;
	}

	private int[] getWeightsAnnual() {
		int weights[] = new int[annual.size()];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < annual.get(i).size(); j++) {
				weights[i] += annual.get(i).get(j).getWeight();
			}
		}
		return weights;
	}

	private String[] getNameAnnual() {
		String[] famrName = new String[annual.size()];
		for (int i = 0; i < annual.size(); i++) {
			famrName[i] = annual.get(i).get(0).getFarmID();

		}
		return famrName;
	}

	private String[] getFarmNamesMonth() {
		String[] names = new String[monthReport.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = monthReport.get(i).get(0).getFarmID();
		}
		return names;

	}

	private String[] getRangedNames() {
		String[] names = new String[rangeReport.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = rangeReport.get(i).get(0).getFarmID();
		}
		return names;
	}

	private int[] getRangedWeights() {
		int[] weights = new int[rangeReport.size()];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < rangeReport.get(i).size(); j++) {
				weights[i] += rangeReport.get(i).get(j).getWeight();
			}
		}
		return weights;
	}

	@SuppressWarnings("deprecation")
	public void monthReport(List<Milk> storage, int month) {
		List<Milk> milk = new ArrayList<Milk>();

		for (Milk m : storage) {
			if (m.getDate().getMonth() == month -1) {
				milk.add(m);
			}
		}
		ArrayList<ArrayList<Milk>> idMilk = new ArrayList<ArrayList<Milk>>();
		for (Milk milk2 : milk) {
			if (!contains(milk2, idMilk)) {
				ArrayList<Milk> newFarm = new ArrayList<Milk>();
				newFarm.add(milk2);
				idMilk.add(newFarm);
			}
		}
		this.monthReport = idMilk;

	}

	private boolean contains(Milk milk, ArrayList<ArrayList<Milk>> milkOrigin) {
		for (int i = 0; i < milkOrigin.size(); i++) {
			if (milk.getFarmID().contentEquals(milkOrigin.get(i).get(0).getFarmID())) {
				milkOrigin.get(i).add(milk);
				return true;
			}
		}

		return false;
	}

	public void annualReport(List<Milk> storage) {
		for (Milk milk : storage) {
			if (!contains(milk, annual)) {
				ArrayList<Milk> newFarm = new ArrayList<Milk>();
				newFarm.add(milk);
				annual.add(newFarm);
			}
		}

	}

	public void dateRangeReport(LocalDate begin, LocalDate end, List<Milk> storage) {
//		ZoneId defaultZoneId = ZoneId.systemDefault();
//		Date start = Date.from(begin.atStartOfDay(defaultZoneId).toInstant());
//		Date last= Date.from(begin.atStartOfDay(defaultZoneId).toInstant());
		
		ArrayList<Milk> range = new ArrayList<Milk>();
		for (Milk milk : storage) {
			LocalDate date = milk.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if(date.compareTo(begin) >= 0 && date.compareTo(end) <= 0) {
				range.add(milk);
			}
		}
		for (Milk milk2 : range) {
			if (!contains(milk2, rangeReport)) {
				ArrayList<Milk> newFarm = new ArrayList<Milk>();
				newFarm.add(milk2);
				rangeReport.add(newFarm);
			}
		}

	}

	@SuppressWarnings("deprecation")
	public void farmReport(Farm farm) {

		ArrayList<Milk> jan = new ArrayList<Milk>();
		ArrayList<Milk> feb = new ArrayList<Milk>();
		ArrayList<Milk> mar = new ArrayList<Milk>();
		ArrayList<Milk> apr = new ArrayList<Milk>();
		ArrayList<Milk> may = new ArrayList<Milk>();
		ArrayList<Milk> jun = new ArrayList<Milk>();
		ArrayList<Milk> jul = new ArrayList<Milk>();
		ArrayList<Milk> aug = new ArrayList<Milk>();
		ArrayList<Milk> sep = new ArrayList<Milk>();
		ArrayList<Milk> oct = new ArrayList<Milk>();
		ArrayList<Milk> nov = new ArrayList<Milk>();
		ArrayList<Milk> dec = new ArrayList<Milk>();
		
		Set<Milk> set = farm.getFarmProduct();
		for (Milk milk : set) {
			switch (milk.getDate().getMonth()) {
			case 0:
				jan.add(milk);
				break;
			case 1:
				feb.add(milk);
				break;
			case 2:
				mar.add(milk);
				break;
			case 3:
				apr.add(milk);
				break;
			case 4:
				may.add(milk);
				break;
			case 5:
				jun.add(milk);
				break;
			case 6:
				jul.add(milk);
				break;
			case 7:
				aug.add(milk);
				break;
			case 8:
				sep.add(milk);
				break;
			case 9:
				oct.add(milk);
				break;
			case 10:
				nov.add(milk);
				break;
			case 11:
				dec.add(milk);
				break;
			default:
				break;
			}
		}
		reportList.add(jan);
		reportList.add(feb);
		reportList.add(mar);
		reportList.add(apr);
		reportList.add(may);
		reportList.add(jun);
		reportList.add(jul);
		reportList.add(aug);
		reportList.add(sep);
		reportList.add(oct);
		reportList.add(nov);
		reportList.add(dec);

	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}



//  public static void main(String[] args) {
//    Report report = new Report ("123");
//    Milk m1 = new Milk (new Date(), "1", 25);
//    Milk m2 = new Milk (new Date(), "2", 45);
//    report.addMilk(m1);
//    report.addMilk(m2);
//    System.out.println(report.toString());
//  }

}
