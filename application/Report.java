package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sun.javafx.collections.MappingChange.Map;

public class Report {

	private String title;
	private TreeSet<Milk> content;
	private List<List<Milk>> reportList = new ArrayList<List<Milk>>();
	private ArrayList<ArrayList<Milk>> monthReport = new ArrayList<ArrayList<Milk>>();
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
		sb.append("\n");
		for (Milk m : content) {
			sb.append(m);
			sb.append("\n");
		}

		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public void farmReport(Farm farm) {

		List<Milk> jan = new ArrayList<Milk>();
		List<Milk> feb = new ArrayList<Milk>();
		List<Milk> mar = new ArrayList<Milk>();
		List<Milk> apr = new ArrayList<Milk>();
		List<Milk> may = new ArrayList<Milk>();
		List<Milk> jun = new ArrayList<Milk>();
		List<Milk> jul = new ArrayList<Milk>();
		List<Milk> aug = new ArrayList<Milk>();
		List<Milk> sep = new ArrayList<Milk>();
		List<Milk> oct = new ArrayList<Milk>();
		List<Milk> nov = new ArrayList<Milk>();
		List<Milk> dec = new ArrayList<Milk>();
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

	@SuppressWarnings("deprecation")
	public void monthReport(List<Milk> storage, int month) {
		List<Milk> milk = new ArrayList<Milk>();
		for (Milk m : storage) {
			if (m.getDate().getMonth() == month) {
				milk.add(m);
			}
		}
		ArrayList<ArrayList<Milk>> idMilk = new ArrayList<ArrayList<Milk>>();
		for (Milk milk2 : milk) {
           if(!contains(milk2, idMilk)) {
        	   ArrayList<Milk> newFarm = new ArrayList<Milk>();
        	   newFarm.add(milk2);
        	   idMilk.add(newFarm);
           }
		}
		this.monthReport = idMilk;

	}

	private boolean contains(Milk milk, ArrayList<ArrayList<Milk>> milkOrigin) {
		for(int i = 0; i < milkOrigin.size(); i++) {
			if(milk.getFarmID() == milkOrigin.get(i).get(0).getFarmID()) {
				for(int j = 0; j < milkOrigin.get(i).size(); j++) {
					if(milkOrigin.get(i).get(j) == null) {
						milkOrigin.get(i).add(j, milk);
						return true;
					}
				}
			}
		}
		return false;
	}

	public void annualReport(List<Milk> storage) {
		for (Milk milk : storage) {
			if(!contains(milk, annual)) {
	        	   ArrayList<Milk> newFarm = new ArrayList<Milk>();
	        	   newFarm.add(milk);
	        	   annual.add(newFarm);
	           }
		}
		
	}

	public void dateRangeReport(Date begin, Date end, List<Milk> storage) {
		ArrayList<Milk> range = new ArrayList<Milk>();
		for(Milk milk : storage) {
			if(milk.getDate().before(end) && milk.getDate().after(begin)) {
				range.add(milk);
			}
		}
		for (Milk milk2 : range) {
	           if(!contains(milk2, rangeReport)) {
	        	   ArrayList<Milk> newFarm = new ArrayList<Milk>();
	        	   newFarm.add(milk2);
	        	   rangeReport.add(newFarm);
	           }
			}
		
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

	/**
	 * @return the content
	 */
	

	/**
	 * @param content the content to set
	 */
	public void setContent(TreeSet<Milk> content) {
		this.content = content;
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
