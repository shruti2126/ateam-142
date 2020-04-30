package application;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.scene.chart.PieChart;

/**
 * @author Samuel Bahr 
 */
public class Report {

	/**
	 * @return the monthly report and can be called
	 */
	public ArrayList<ArrayList<Milk>> getMonthReport() {
		return monthReport;
	}

	/**
	 * @return the annual report and can be called
	 */
	public ArrayList<ArrayList<Milk>> getAnnual() {
		return annual;
	}

	/**
	 * @return the date-range report and can be called
	 */

	public ArrayList<ArrayList<Milk>> getRangeReport() {
		return rangeReport;
	}

	/**
	 * @return the farm-specific report and can be called
	 */

	public ArrayList<ArrayList<Milk>> getReportList() {
		return reportList;
	}

	private String title;//Type of Report

	private ArrayList<ArrayList<Milk>> reportList = new ArrayList<ArrayList<Milk>>(); //FarmReportData
	private ArrayList<ArrayList<Milk>> monthReport = new ArrayList<ArrayList<Milk>>();//MonthReportData
	private ArrayList<ArrayList<Milk>> annual = new ArrayList<ArrayList<Milk>>();//AnnualReportData
	private ArrayList<ArrayList<Milk>> rangeReport = new ArrayList<ArrayList<Milk>>();//RangeReportData

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

	/**
	 * The createPieChart method is used to create a graphical representation of
	 * data for each report.
	 * 
	 * @return - PieChart which represents the data graphically.
	 */
	public PieChart createPieChart() {
		PieChart pieChart = new PieChart(); // New instance of Pie Chart
		if (getTitle().equals("Annul Report")) { // Creates PieChart for annual report

			int[] weights = getWeightsAnnual(); // fetches weights
			String[] farmId = getNameAnnual(); // fetches aliases

			for (int i = 0; i < weights.length; i++) { // iterates and creates pie chart
				pieChart.getData().add(createData(farmId[i], weights[i]));
			}
			return pieChart; // returns newly created Pie Chart

		} else if (getTitle().equals("FarmId Report")) { // Creates PieChart for FarmID report
			int[] weights = getWeightsFarm(); // fetches the weights
			String[] months = new String[] { "January", "Feburary", "March", "April", "May", "June", "July", "August",
					"September", "October", "November", "December" };

			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(months[i], weights[i])); //// iterates and creates pie chart
			}

			return pieChart; // returns newly created Pie Chart
		} else if (getTitle().equals("Month Report")) { // Creates PieChart for Month Report
			int[] weights = getWeightsMonth(); // fetches weights
			String[] names = getFarmNamesMonth();// fetches aliases
			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(names[i], weights[i])); //// iterates and creates pie chart
			}
			return pieChart; // returns newly created Pie Chart
		} else if (getTitle().equals("Dates Report")) {

			int[] weights = getRangedWeights(); // fetches weights
			String[] farmId = getRangedNames(); // fetches aliases
			for (int i = 0; i < weights.length; i++) {
				pieChart.getData().add(createData(farmId[i], weights[i])); //// iterates and creates pie chart

			}
			return pieChart;// returns newly created Pie Chart
		}
		return null;
	}

	/**
	 * The createData method is a helper method used to build the pieces of the pie
	 * chart. An alias and a weight is passed in to associate the piece with a name
	 * and an assignment of value
	 * 
	 * @param alias  - Name for the PieChart.Data segment
	 * @param weight - Value associated with the Piechart.Data segment
	 * @return - A new Piechart.Data segment
	 */
	private PieChart.Data createData(String alias, int weight) {
		return new PieChart.Data(alias, weight);
	}

	/**
	 * The getWeightsFarm method iterates through the Farm Report list and sums the
	 * milk from each index(Farm) into a new array for which will be displayed
	 * throughout the report.
	 * 
	 * @return Representing the milk weights for every specific farms used during
	 *         the Farm report.
	 */
	private int[] getWeightsFarm() {

		int[] weights = new int[reportList.size()]; // New Array Created
		for (int i = 0; i < weights.length; i++) {// Iteration
			for (int j = 0; j < reportList.get(i).size(); j++) {
				weights[i] += reportList.get(i).get(j).getWeight(); // Added towards the sum
			}
		}
		return weights;

	}

	/**
	 * The getWeightsMonth method iterates through the Month Report list and sums
	 * the milk from each index(Farm) into a new array for which will be displayed
	 * throughout the report.
	 * 
	 * @return- Representing the milk weights for every specific farms used during
	 *          the Farm report.
	 * 
	 */
	private int[] getWeightsMonth() {
		int[] weights = new int[this.monthReport.size()]; // Created New Array
		for (int i = 0; i < weights.length; i++) { // Iteration
			for (int j = 0; j < monthReport.get(i).size(); j++) {
				weights[i] += monthReport.get(i).get(j).getWeight(); // Adds toward the sum
			}
		}
		return weights;
	}

	/**
	 * The getWeightsAnnual method iterates through the Annual list and sums the
	 * milk from each index(Farm) into a new array for which will be displayed
	 * throughout the report.
	 * 
	 * @return - Representing the milk weights for every specific farms used during
	 *         the Annual report.
	 */
	private int[] getWeightsAnnual() {
		int weights[] = new int[annual.size()]; // New array
		for (int i = 0; i < weights.length; i++) { // Iteration
			for (int j = 0; j < annual.get(i).size(); j++) {
				weights[i] += annual.get(i).get(j).getWeight(); // Adds toward the sum
			}
		}
		return weights;
	}

	/**
	 * The getNameAnnual method iterates through the Annual List and fetches the
	 * Farm's ID through each index and adds the string to a new array which will be
	 * utilized throughout the report.
	 * 
	 * @return - An array representing all farm names throughout the annual report
	 */
	private String[] getNameAnnual() {
		String[] famrName = new String[annual.size()];
		for (int i = 0; i < annual.size(); i++) {
			famrName[i] = annual.get(i).get(0).getFarmID();

		}
		return famrName;
	}

	/**
	 * The getFarmNamesMonth method iterates through the Month List and fetches the
	 * Farm's ID through each index and adds the string to a new array which will be
	 * utilized throughout the report.
	 * 
	 * @return - An array representing all farm names throughout the month report
	 */
	private String[] getFarmNamesMonth() {
		String[] names = new String[monthReport.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = monthReport.get(i).get(0).getFarmID();
		}
		return names;

	}

	/**
	 * The getRangedNames method iterates through the RangeReport List and fetches
	 * the Farm's ID through each index and adds the string to a new array which
	 * will be utilized throughout the report.
	 * 
	 * @return - An array representing all farm names throughout the RangeDate
	 *         report
	 */
	private String[] getRangedNames() {
		String[] names = new String[rangeReport.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = rangeReport.get(i).get(0).getFarmID();
		}
		return names;
	}

	/**
	 * The getRangedWeights method iterates through the RangeReport list and sums
	 * the milk from each index(Farm) into a new array for which will be displayed
	 * throughout the report.
	 * 
	 * @return - Representing the milk weights for every specific farms throughout
	 *         the Range Date report.
	 */
	private int[] getRangedWeights() {
		int[] weights = new int[rangeReport.size()]; // New array created
		for (int i = 0; i < weights.length; i++) { // Iteration
			for (int j = 0; j < rangeReport.get(i).size(); j++) {
				weights[i] += rangeReport.get(i).get(j).getWeight(); // Adds weight into array
			}
		}
		return weights;
	}

	/**
	 * The month report fetches all of the milk data, and filters each milk object
	 * into a list if it's month attribute matches the month parameter being passed
	 * in. After, the milk is sorted into it's corresponding farm.
	 * 
	 * @param storage - Entire Milk Data
	 * @param month   - The specific month being analyzed.
	 */
	@SuppressWarnings("deprecation")
	public void monthReport(List<Milk> storage, int month) {
		List<Milk> milk = new ArrayList<Milk>();

		for (Milk m : storage) {
			if (m.getDate().getMonth() == month - 1) { // Filter Process
				milk.add(m); // Adds to list if criteria is met
			}
		}
		ArrayList<ArrayList<Milk>> idMilk = new ArrayList<ArrayList<Milk>>();
		for (Milk milk2 : milk) { // Sorting Process
			if (!contains(milk2, idMilk)) { // Helper Private Method
				ArrayList<Milk> newFarm = new ArrayList<Milk>();
				newFarm.add(milk2);
				idMilk.add(newFarm);
			}
		}
		this.monthReport = idMilk; // Sets the month report

	}

	/**
	 * The Contain helper method determines whether a farm object already exists
	 * among the data structure being passed in. If the farm object does exist, the
	 * milk object will be added towards that list
	 * 
	 * @param milk       - Milk object that is being compared
	 * @param milkOrigin - DataStructure for which the milk is being compared
	 *                   against
	 * @return -- boolean depicting whether the milk's farm exist amongst the
	 *         DataStructure passed in
	 */
	private boolean contains(Milk milk, ArrayList<ArrayList<Milk>> milkOrigin) {
		for (int i = 0; i < milkOrigin.size(); i++) {
			if (milk.getFarmID().contentEquals(milkOrigin.get(i).get(0).getFarmID())) { // Compares
				milkOrigin.get(i).add(milk); // Adds milk to current list
				return true;
			}
		}

		return false;
	}

	/**
	 * The annual report fetches all of the milk data, and sorts each object into
	 * it's corresponding farm. If the farm doens't exist, a new farm list object
	 * will be created where the milk will be added towards.
	 * 
	 * @param storage - Entire Milk Data
	 */
	public void annualReport(List<Milk> storage) {
		for (Milk milk : storage) { // Iterates through each milk object and determines whether it's farm has been
									// added into the annualReportList.

			if (!contains(milk, annual)) { // Helper private method
				ArrayList<Milk> newFarm = new ArrayList<Milk>(); // New list created if designated farm list is
																	// Nonexistent
				newFarm.add(milk);
				annual.add(newFarm);
			}
		}

	}

	/**
	 * The DateRangeReport method is essentially a filter report for which only
	 * allows Milk that lie in between the date threshold to be analyzed. After, all
	 * of the milk has been filtered, then the milk will be sorted based upon it's
	 * farm.
	 * 
	 * @param begin   - The starting date filter ... Dates must appear on or after
	 *                this date
	 * @param end     - The ending date filter ... Dates must appear on or before
	 *                this date
	 * @param storage - Entire Milk Data
	 */
	public void dateRangeReport(LocalDate begin, LocalDate end, List<Milk> storage) {

		ArrayList<Milk> range = new ArrayList<Milk>(); // List of Filtered Milk Objects
		for (Milk milk : storage) { // Filter Process
			LocalDate date = milk.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (date.compareTo(begin) >= 0 && date.compareTo(end) <= 0) { // Comparing the Begin/End filter dates with
																			// each milk object
				range.add(milk); // Added to the range list if criteria is met
			}
		}
		for (Milk milk2 : range) { // Sorting Process
			if (!contains(milk2, rangeReport)) { // Calls the contain method(sorting helper method), allowing to test
													// whether the farm exists within the range list. If not,
													// a new list will be created where the farm will be added and the
													// milk be placed into
				ArrayList<Milk> newFarm = new ArrayList<Milk>(); // New list if farm doesn't currently exist in the
																	// range list
				newFarm.add(milk2);
				rangeReport.add(newFarm);
			}
		}

	}

	/**
	 * The farm report method intakes a Farm Object, fetches the milk supply of the
	 * farm, and sorts the milk into months. After the sort, data from each month
	 * will be combined into one list
	 * 
	 * @param Farm object which will be under analysis for the report
	 */
	@SuppressWarnings("deprecation")
	public void farmReport(Farm farm) {

		ArrayList<Milk> jan = new ArrayList<Milk>(); // List of months Jan -> December
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

		Set<Milk> set = farm.getFarmProduct(); // Fetches the farm's milk supply
		for (Milk milk : set) { // Sorting Process based upon the month of each milk object
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
		reportList.add(jan); // Adds the list of each month to the report list which is than exported
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
	 * Grabs the title of the report
	 * 
	 * @return the title - Specifies the type of report
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the report type
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
