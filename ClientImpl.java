package MobilePurchaseSystem.client;

import java.util.Scanner;

import MobilePurchaseSystem.entity.Mobiles;
import MobilePurchaseSystem.service.ServiceImpl;

public class ClientImpl {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ServiceImpl s = new ServiceImpl(); // declaring object of ServiceImp class from service layer
		System.out.println("Welcome to Mobile Purchase System");
		while (true) {
			System.out.println("\n1.Customer Purchase" + "\n2.Show All Mobiles" + "\n3.Delete Mobile Details"
					+ "\n4.Search Mobiles Based On Price Range" + "\n5.To Add New Mobile Into The Store" + "\n6.Exit");
			System.out.print("Enter your choice:");
			int ch = sc.nextInt();
			// switch case to select the function to be done
			switch (ch) {
			case 1:
				System.out.println("Enter the mobile id of the mobile that you desired to purchase");
				int id = sc.nextInt();
				s.insertCustomerAndPurchaseDetails();
				s.updateMobileQuantity(id);
				break;
			case 2:
				System.out.println("Showing all mobiles ");
				for (Mobiles m : s.getAllMobilesDetails()) {
					System.out.println(m);
				}
				break;
			case 3:
				s.deleteMobile();
				System.out.println("Record deleted.");
				break;
			case 4:
				s.searchMobile();
				break;
			case 5:
				s.addMobileDetails();
				break;
			case 6:
				System.out.println("Thank You for using Mobile Purchase System!!!");
				System.exit(0);
			default:
				System.out.println("Please enter valid choice..");
			}
		}
	}
}