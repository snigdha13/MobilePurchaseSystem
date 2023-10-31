package MobilePurchaseSystem.dao;

//importing required packages
import java.util.List;
import java.util.Scanner;

import MobilePurchaseSystem.entity.Mobiles;
import MobilePurchaseSystem.entity.PurchaseDetails;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class DatabaseConnection {
	Scanner sc=new Scanner(System.in);
	Mobiles mb = new Mobiles();
		//function to insert customer purchase details
	   public void insertCustomerAndPurchaseDetails() {
		
		// Create an EntityManagerFactory with the persistence unit name "pu".
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

		// Creating an EntityManager instance from the EntityManagerFactory.
		EntityManager em = emf.createEntityManager();

		// Begin a new transaction with the EntityManager.
		em.getTransaction().begin();
		try {
			// Specify the mobileId and customer details
			System.out.print("Enter customer name (max 20 characters, first character uppercase): ");
	        String customerName = sc.nextLine();

	        System.out.print("Enter mail ID: ");
	        String mailId = sc.nextLine();

	        System.out.print("Enter phone number (exactly 10 digits): ");
	        String phoneNo = sc.nextLine();

	        System.out.print("Enter mobile ID: ");
	        int mobileId = sc.nextInt();

	        if (!validateCustomerName(customerName) || !validateMailId(mailId) || !validatePhoneNumber(phoneNo)) {
	            System.out.println("Validation failed. Please check your inputs.");
	            return;
	        }
		    
		    // Fetch the Mobile entity
		    Mobiles mobile = em.find(Mobiles.class, mobileId);

		    if (mobile != null && mobile.getQuantity() > 0) {
		        // Quantity is valid, proceed to create and save the entities
		        PurchaseDetails customer = new PurchaseDetails();
		        customer.setCustomerName(customerName);
		        customer.setMailId(mailId);
		        customer.setPhoneNo(phoneNo);
		        customer.setMobile(mobile);
		        em.persist(customer);
		        updateMobileQuantity(mobileId); //calling function to update the mobile quantity when a purchase is done
		    } 
		    else {
		        // Quantity is not valid, display an error message
		        System.out.println("Mobile quantity is insufficient.");
		    }

		    // Commit the transaction.
		    em.getTransaction().commit();
		} 
		catch (Exception e) {
		    if (em.getTransaction().isActive()) {
		        em.getTransaction().rollback();
		    }
		    e.printStackTrace();
		} 
		finally {
		    // Close the EntityManager and the EntityManagerFactory when you're done.
		    em.close();
		    emf.close();
		}
	}
	public static boolean validateCustomerName(String name) {
        Pattern pattern = Pattern.compile("^[A-Z][a-zA-Z]{0,19}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validateMailId(String mailId) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(mailId);
        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
	
	//function to update the mobile quantity when a purchase is done
	public void updateMobileQuantity(int mobileId2) {
		
		// Create an EntityManagerFactory with the persistence unit name "pu".
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

		// Creating an EntityManager instance from the EntityManagerFactory.
		EntityManager em = emf.createEntityManager();

		// Begin a new transaction with the EntityManager.
		em.getTransaction().begin();

		try {
		    // Specify the mobileId of the purchased mobile
		    int mobileId = mobileId2; 

		    // Fetch the Mobile entity
		    Mobiles mobile = em.find(Mobiles.class, mobileId);

		    if (mobile != null && mobile.getQuantity() > 0) {
		        // Mobile is available, update the quantity
		        int currentQuantity = mobile.getQuantity();
		        mobile.setQuantity(currentQuantity - 1);
		        em.merge(mobile);
		    } 
		    else {
		        // Mobile is not available, display an error message
		        System.out.println("Mobile is out of stock.");
		    }

		    // Commit the transaction.
		    em.getTransaction().commit();
		} 
		catch (Exception e) {
		    if (em.getTransaction().isActive()) {
		        em.getTransaction().rollback();
		    }
		    e.printStackTrace();
		} 
		finally {
		    // Close the EntityManager and the EntityManagerFactory when you're done.
		    em.close();
		    emf.close();
		}
	}
	
	//function to view all the mobiles records in the table
	public List<Mobiles> getAllMobilesDetails(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

		// Creating an EntityManager instance from the EntityManagerFactory.
		EntityManager em = emf.createEntityManager();
		jakarta.persistence.Query q= em.createQuery("select x from Mobiles x");
		List<Mobiles> records= (List<Mobiles>) q.getResultList();
		return records;
		
	}
	
	//method to delete mobile record from the table based on mobile id
	public void deleteMobile() {
		// Create an EntityManagerFactory with the persistence unit name "pu".
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

		// Creating an EntityManager instance from the EntityManagerFactory.
		EntityManager em = emf.createEntityManager();

		// Begin a new transaction with the EntityManager.
		em.getTransaction().begin();

		try {
		    // Specify the mobileId of the mobile to delete
			System.out.println("Enter the mobile id that is to be deleted.");
		    int mobileIdToDelete = sc.nextInt();

		    // Find the Mobile entity by ID
		    Mobiles mobileToDelete = em.find(Mobiles.class, mobileIdToDelete);

		    if (mobileToDelete != null) {
		        // Mobile found, delete it
		        em.remove(mobileToDelete);
		        System.out.println("Mobile with ID " + mobileIdToDelete + " has been deleted.");
		    } 
		    else {
		        System.out.println("Mobile with ID " + mobileIdToDelete + " not found.");
		    }

		    // Commit the transaction.
		    em.getTransaction().commit();
		} 
		catch (Exception e) {
		    if (em.getTransaction().isActive()) {
		        em.getTransaction().rollback();
		    }
		    e.printStackTrace();
		} 
		finally {
		    // Close the EntityManager and the EntityManagerFactory when you're done.
		    em.close();
		    emf.close();
		}
	}
	
	//method to search mobiles based on price range
	public  void searchMobile() {
		
		// Create an EntityManagerFactory with the persistence unit name "pu".
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

		// Creating an EntityManager instance from the EntityManagerFactory.
		EntityManager em = emf.createEntityManager();

		// Begin a new transaction with the EntityManager.
		em.getTransaction().begin();

		try {
			// Enter the desired minimum price
			System.out.println("Enter the minimum price range.");
		    double minPrice = sc.nextDouble(); 
		    // Enter the desired maximum price
		    System.out.println("Enter the maximum price range.");
		    double maxPrice = sc.nextDouble(); 

		    TypedQuery<Mobiles> query = em.createQuery("SELECT m FROM Mobiles m WHERE m.price BETWEEN :minPrice AND :maxPrice", Mobiles.class);
		    query.setParameter("minPrice", minPrice);
		    query.setParameter("maxPrice", maxPrice);

		    List<Mobiles> mobilesInRange = query.getResultList();

		    if (mobilesInRange.isEmpty()) {
		        System.out.println("No mobiles found in the specified price range.");
		    } 
		    else {
		        System.out.println("Mobiles in the price range from " + minPrice + " to " + maxPrice + ":");
		        for (Mobiles mobile : mobilesInRange) {
		            System.out.println("Mobile ID: " + mobile.getMobileId() + ", Name: " + mobile.getName() + ", Price: " + mobile.getPrice());
		        }
		    }

		    // Commit the transaction.
		    em.getTransaction().commit();
		} 
		catch (Exception e) {
		    if (em.getTransaction().isActive()) {
		        em.getTransaction().rollback();
		    }
		    e.printStackTrace();
		} 
		finally {
		    // Close the EntityManager and the EntityManagerFactory when you're done.
		    em.close();
		    emf.close();
		}
	}
	
	//method to add new mobile details into the table
	public void addMobileDetails() {
		
		// Creating an EntityManagerFactory instance using the "pu" persistence unit.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

		// Creating an EntityManager instance from the EntityManagerFactory.
		EntityManager em = emf.createEntityManager();

		// Begin a new transaction with the EntityManager.
		em.getTransaction().begin();
		
		System.out.println("Enter any number to add more records and '0' to end.");
		int ch;
		
		//looping to insert more records at a time
		do {
			System.out.println("Enter any number to add more records and '0' to end.");
			ch=sc.nextInt();
			if(ch!=0) {
			System.out.print("Enter mobile id:");
			mb.setMobileId(sc.nextInt());
			System.out.print("Enter mobile name:");
			mb.setName(sc.next());
			System.out.print("Enter price of the mobile:");
			mb.setPrice(sc.nextDouble());
			System.out.print("Enter the quantity of mobiles:");
			mb.setQuantity(sc.nextInt());
			}	
		}while(ch!=0);
		
		// Persist (insert) the Mobiles object into the database.
		em.persist(mb);

		// Commit the transaction, which will save the changes to the database.
		em.getTransaction().commit();

		// Close the EntityManager.
		em.close();

		// Close the EntityManagerFactory.
		emf.close();
	}
}
