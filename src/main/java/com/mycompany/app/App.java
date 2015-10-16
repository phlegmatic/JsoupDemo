package com.mycompany.app;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.mycompany.pojo.Address;
import com.mycompany.pojo.FrequncyData;
import com.mycompany.pojo.SearchCriteria;
import com.mycompany.pojo.TinDetail;
import com.mycompany.pojo.TinDetailExplosion;

//Task: Verify the status and validity 
//of Sales Tax Number (TIN) and obtain allied 
//information if valid

/* This clss is starting point 
 * Purpose :displaying menu and output to the user*/
public class App {
	static App core = new App();
	static Validator validator = new Validator();
	static Datagetters datagetters = new Datagetters();
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws IOException {

		int choice = 0;
		System.out.println("Command Options: ");
		System.out.println("1.Search by Tin");
		System.out.println("2.Search by Pan");
		System.out.println("3.Search by Entity Name");
		System.out.println("4. exit");
		System.out.println("5: Display Menu");
		SearchCriteria searchcriteria = new SearchCriteria();
		Scanner scan = new Scanner(System.in);
		String input = "";
		while (choice != 4) {
			input = "";
			choice = scan.nextInt();
			searchcriteria = new SearchCriteria();

			switch (choice) {
				case 1 :
					System.out.println("Enter Tin number");
					input = scan.next();
					if (!validator.validateTin(input)) {
						System.out
								.println("Invalid TIN format! Enter choice again");
					} else {
						searchcriteria.setTinNumber(input);
						searchByCriteria(searchcriteria, "tin");
					}
					break;
				case 2 :
					System.out.println("Enter pan number");
					input = scan.next();
					if (!validator.validatePan(input)) {
						System.out
								.println("Invalid PAN format ! Enter choice again!");
					} else {
						searchcriteria.setPanNumber(input);
						searchByCriteria(searchcriteria, "pan");
					}
					break;
				case 3 :
					scan.nextLine();
					System.out.println("Enter dealer name");
					input = scan.nextLine();
					if (input.length() != 0) {
						searchcriteria.setDealerName(input);
						searchByCriteria(searchcriteria, "dealer");
					} else {
						System.out
								.println("Blank dealer name is invalid ! Enter choice again");
					}
					break;
				case 5 :
					System.out.println("Command Options: ");
					System.out.println("1.Search by Tin");
					System.out.println("2.Search by Pan");
					System.out.println("3.Search by Entity Name");
					System.out.println("4. exit");
					System.out.println("5: Display Menu");
					break;
				default :
					System.out.println("Bye!");
					break;
			} // end of switch

		}

	}

	private static void searchByCriteria(SearchCriteria searchcriteria,
			String input) throws IOException {
		// System.out.println("dealer                      :"
		// + searchcriteria.getDealerName());
		// System.out.println("Tin                      :"
		// + searchcriteria.getTinNumber());
		// System.out.println("Pan                      :"
		// + searchcriteria.getPanNumber());
		List<TinDetail> tinDetails = datagetters.getTinDetails(searchcriteria);

		if (tinDetails.isEmpty()) {
			if (input.equals("tin")) {
				System.out.println("Warning! Invalid TIN");
			} else if (input.equals("pan")) {
				System.out
						.println("Warning! No Entities R1egistered with this PAN");
			} else {
				System.out.println("Warning! Entity Not Registered under VAT");
			}
		} else {
			tinDetails = datagetters.getTinExplosionData(tinDetails,
					searchcriteria);
			for (TinDetail tinDetail : tinDetails) {
				if (tinDetail.getStatus().contains(ScrapingConstants.Cancelled)
						&& tinDetails.size() == 1) {
					if (input.equals("tin")) {
						System.out.println("Warning! Cancelled TIN");
					} else {
						System.out.println("Warning! Cancelled Registration");
					}
				} else {

					System.out.println("TIN Valid Since "
							+ tinDetail.getTinDetailExplosion()
									.getEffectiveOrCancelledDate());

				}

				core.printOutPut(tinDetail, true);
			}
		}

	}

	// private void printOutPut(List<TinDetail> tinDetails, boolean freqFlag) {
	// for (TinDetail tinDetail : tinDetails) {
	// System.out.print(tinDetail.getTinNumber());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(tinDetail.getDealerName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(tinDetail.getStatus());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// TinDetailExplosion tinDetailExplosion = tinDetail
	// .getTinDetailExplosion();
	// if (tinDetailExplosion != null) {
	// System.out.print(tinDetailExplosion
	// .getEffectiveOrCancelledDate());
	// System.out.print(ScrapingConstants.columnDelimeter);
	//
	// Address address = tinDetailExplosion
	// .getTinDetailExplosionAddress();
	// System.out.print(address.getAddressLineOne());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getStreetName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getAddressLineTwo());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getAddressLineThree());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getTalukaName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getDistrictName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getCityName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getStateName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(address.getPinCode());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(tinDetailExplosion.getOldRCNo());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(tinDetailExplosion.getLocationName());
	// System.out.print(tinDetailExplosion.getActName());
	// System.out.print(ScrapingConstants.columnDelimeter);
	// System.out.print(ScrapingConstants.RowDelimeter);
	// if (freqFlag && tinDetailExplosion.getFreqeuncyData() != null) {
	// List<FrequncyData> freqdataList = tinDetailExplosion
	// .getFreqeuncyData();
	// if (!freqdataList.isEmpty()) {
	// System.out.println();
	// for (FrequncyData freq : freqdataList) {
	// System.out.println(freq.getFinYear().trim()
	// + " ======== " + freq.getFrequencyName());
	// }
	// }
	// }
	// }
	//
	// }
	// }

	private void printOutPut(TinDetail tinDetail, boolean freqFlag) {
		// for (TinDetail tinDetail : tinDetails) {
		System.out.print(tinDetail.getTinNumber());
		System.out.print(ScrapingConstants.columnDelimeter);
		System.out.print(tinDetail.getDealerName());
		System.out.print(ScrapingConstants.columnDelimeter);
		System.out.print(tinDetail.getStatus());
		System.out.print(ScrapingConstants.columnDelimeter);
		TinDetailExplosion tinDetailExplosion = tinDetail
				.getTinDetailExplosion();
		if (tinDetailExplosion != null) {
			System.out.print(tinDetailExplosion.getEffectiveOrCancelledDate());
			System.out.print(ScrapingConstants.columnDelimeter);

			Address address = tinDetailExplosion.getTinDetailExplosionAddress();
			System.out.print(address.getAddressLineOne());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getStreetName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getAddressLineTwo());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getAddressLineThree());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getTalukaName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getDistrictName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getCityName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getStateName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(address.getPinCode());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetailExplosion.getOldRCNo());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetailExplosion.getLocationName());
			System.out.print(tinDetailExplosion.getActName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(ScrapingConstants.RowDelimeter);
			if (freqFlag && tinDetailExplosion.getFreqeuncyData() != null) {
				List<FrequncyData> freqdataList = tinDetailExplosion
						.getFreqeuncyData();
				if (!freqdataList.isEmpty()) {
					System.out.println();
					for (FrequncyData freq : freqdataList) {
						System.out.println(freq.getFinYear().trim()
								+ " ======== " + freq.getFrequencyName());
					}
				}
			}
		}

		// }
	}
}
