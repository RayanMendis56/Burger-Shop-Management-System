import java.util.*;
class Burger{
	private String orderId;
	private String customerId;
	private String customerName;
	private int orderQty;
	private int orderStatus;
	
	public static final int UNIT_PRICE=500;
	
	public static int PREPARING=0;
	public static int DELIVERED=1;
	public static int CANCELED=2;
	
	Burger(String orderId,String customerId,String customerName,int orderQty){
		this.orderId=orderId;
		this.customerId=customerId;
		this.customerName=customerName;
		this.orderQty=orderQty;
		this.orderStatus=PREPARING;
		}
	
	public void setQuantity(int newqty){
		this.orderQty=newqty;
		}	
	public void setStatus(int newst){
		this.orderStatus=newst;
		}	
		
		
	public String getOrderId(){
		return orderId;
		}
	public String getCustomerId(){
		return customerId;
		}
	public String getCustomerName(){
		return customerName;
		}
	public int getOrderQty(){
		return orderQty;
		}
	public double getTotal(){
		double total=orderQty*UNIT_PRICE;
		return total;
		}
	public String getOrderStatus(){
		if(orderStatus==0){
			return "PREPARING";
			}
		if(orderStatus==1){
			return "DELIVERED";
			}
		else{
			return "CANCELED";
			}
		}
		
}

public class Main{
	public static Burger[] burgerArray=new Burger[]{
		new Burger("B0001","0712345678","Nimal",2),
		new Burger("B0002","0789123456","Namal",3),
		new Burger("B0003","0777777777","Kasun",1),
		new Burger("B0004","0751234567","Bimal",3),
		new Burger("B0005","0700123456","Supun",5),
		new Burger("B0006","0919123456","Kushan",6),
		new Burger("B0007","0777723456","Pasindu",4)
		};
	
	public static void main (String args[]){
		Scanner input=new Scanner(System.in);
		do{
		System.out.println("+-----------------------------------------------------------+");
		System.out.printf("|%40s%20s\n","iHungary Burger","|");
		System.out.println("+-----------------------------------------------------------+");
		
		System.out.println("[1] Place Order 		[2] Search Best Customer");
		System.out.println("[3] Search Order 		[4] Search Customer");
		System.out.println("[5] View Order			[6] Update Order Details");
		System.out.println("[7] Exit 	");
		System.out.println();
		System.out.print("Enter an option to continue > ");
		int op=input.nextInt();
		while(op>7 || op<0){
			System.out.print("Invalid Input...Enter number between(1-7) : ");
			op=input.nextInt();
			}
		
		switch(op){
			case 1:
				addNewOrder();
				break;
			case 2:
				SearchBestCustomer();
				break;
			case 3:
				searchOrderDetails();
				break;
			case 4:
				SearchCustomer();
				break;
			case 5:
				ViewOrder();
				break;
			case 6:
				UpdateOrderDetails();
				break;
			case 7:
				return;
				
				} 
			}while(true);
			
		
		}
	
	public static void clearConsole(){
			try{
					final String os=System.getProperty("os.name");
					if(os.contains("Windows")){
					new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
						}else{
							System.out.print("\033[H\033[2J");
							System.out.flush();
						}
					}catch(final Exception e){
					e.printStackTrace();
					//Handle any exceptions.
				}
		}
	
	//1.Place Order
		public static void addNewOrder(){
			Scanner input=new Scanner(System.in);
			System.out.println("+-----------------------------------------------------------+");
			System.out.printf("|%40s%20s\n","Place Order","|");
			System.out.println("+-----------------------------------------------------------+\n");
			L1:do{
				String orderId=generateOrderId();
				System.out.println("Order ID - "+orderId);
				System.out.println("==============================\n");
				
				System.out.print("Customer ID : ");
				String custId=input.next();
				if(!isValidCustID(custId)){
				 L2:do{
					System.out.println("Invalid Customer Id...");
					System.out.print("Do you want to go to home page(Y/N)?");
					char op=input.next().charAt(0);
					if(op=='Y'|op=='y'){
						break L1;
						}
					else if(op=='N'|op=='n'){
						continue L1;
						}
					else{
						System.out.println("Invalid Input...Try again...");
						continue L2;
							}
						}while(true);
					}
				int index=indexOf(custId);
				String custName;
				if(index!=-1){
					custName=burgerArray[index].getCustomerName();
					System.out.println("Customer Name : "+custName);
					}
				else{
					System.out.print("Customer Name : ");
					custName=input.next();
					}
				System.out.print("Enter Burger Quantity : ");
				int orderQty=input.nextInt();
				
				do{
					System.out.print("Are confirming the order(Y/N)?");
					char con=input.next().charAt(0);
					if(con=='Y'|con=='y'){
						break;
						}
					else if(con=='N'|con=='n'){
						continue;
						}
					else{
						System.out.println("Invalid Input...");
						}
						
					}while(true);
					//enter values to object attributes
					Burger newbr=new Burger(orderId,custId,custName,orderQty);
					extendsArray();
					burgerArray[burgerArray.length-1]=newbr;
			}while(true);
		}
		
		//checking the validity od customer ID
		public static boolean isValidCustID(String custId){
			if(custId.length()!=10){
				return false;
				}
			if(custId.charAt(0)!='0'){
				return false;
				}
			for(int i=0;i<custId.length();i++){
				if(custId.charAt(i)<48 || custId.charAt(i)>57){
					return false;
					}
				}
			return true;
			}
		
		//generating the order ID
		public static String generateOrderId(){
			if(burgerArray.length==0){
				return "B0001";
				}
			Burger lastBurger=burgerArray[burgerArray.length-1];
			String lastOrderId=lastBurger.getOrderId();
			int orderIdNumber=Integer.parseInt(lastOrderId.substring(1));
			String newOrderId=String.format("B%04d",orderIdNumber+1);
			return newOrderId;
			}
			
		//add a method to find the index of the customer if customer exists
		public static int indexOf(String customerId){
			for(int i=0;i<burgerArray.length;i++){
				if(burgerArray[i].getCustomerId().equals(customerId)){
					return i;
					}
				}
				return -1;
			}
		//extending burger Object Array by one element
		public static void extendsArray(){
			Burger[] tempburger=new Burger[burgerArray.length+1];
			for (int i = 0; i < burgerArray.length; i++)
			{
				tempburger[i]=burgerArray[i];
			}
			burgerArray=tempburger;
			}
		
		
		
	//2.Search Best Customer
		public static void SearchBestCustomer(){
			Scanner input=new Scanner(System.in);
				System.out.println("+-----------------------------------------------------------+");
				System.out.printf("|%40s%20s\n","Best Customer List","|");
				System.out.println("+-----------------------------------------------------------+");
				System.out.println();
				
			//----Removing Duplicates-------//here?
				
				//copy the values of the object Array
				Burger[] tempBurger=new Burger[burgerArray.length];
				
				for (int i = 0; i < burgerArray.length; i++)
				{
					tempBurger[i]=burgerArray[i];
				}
				
				//String array to store customerId and names without duplicates
				String[] customerIdArray=new String[0];
				String[] customerNameArray=new String[0];
				
				for (int i = 0; i < burgerArray.length; i++)
				{
					String cid=burgerArray[i].getCustomerId();
					
					boolean isDuplicate=false;
					//check that customerId already exists
					for(int j=0;j<customerIdArray.length;j++){
						if(cid.equals(customerIdArray[j])){
							isDuplicate=true;
							break;
							}
						}
					if(!isDuplicate){
						String[] tempcid=new String[customerIdArray.length+1];
						String[] tempcn=new String[customerNameArray.length+1];
						for(int k=0;k<customerIdArray.length;k++){
							tempcid[k]=customerIdArray[k];
							tempcn[k]=customerNameArray[k];
							}
							tempcid[tempcid.length-1]=cid;
							tempcn[tempcn.length-1]=burgerArray[i].getCustomerName();
							customerIdArray=tempcid;
							customerNameArray=tempcn;
						}
				}
				
				//----Get total values per customer--------------
				double[] customerTotalArray=new double[0];
				for(int i=0;i<customerIdArray.length;i++){
					String cid=customerIdArray[i];
					double total=0;
					for(int j=0;j<burgerArray.length;j++){
						if(cid.equals(burgerArray[j].getCustomerId())){
							total+=burgerArray[j].getTotal();
							}
						}
					double[] tempCustomerTotalArray=new double[customerTotalArray.length+1];
					for(int k=0;k<customerTotalArray.length;k++){
						tempCustomerTotalArray[k]=customerTotalArray[k];
						}
					tempCustomerTotalArray[tempCustomerTotalArray.length-1]=total;
					customerTotalArray=tempCustomerTotalArray;
					}
				
				
				//------------Sorting Arrays----------------------
				for(int i=0;i<customerIdArray.length-1;i++){
					for(int j=0;j<customerIdArray.length-1-i;j++){
						if(customerTotalArray[j]>customerTotalArray[j+1]){
							double temp1=customerTotalArray[j+1];
							customerTotalArray[j+1]=customerTotalArray[j];
							customerTotalArray[j]=temp1;
							
							String temp2=customerIdArray[j+1];
							customerIdArray[j+1]=customerIdArray[j];
							customerIdArray[j]=temp2;
							
							String temp3=customerNameArray[j+1];
							customerNameArray[j+1]=customerNameArray[j];
							customerNameArray[j]=temp3;
							}
						}
					}
				
				//-------Print the Best Customer List-------------
				System.out.println("-------------------------------------------------------------");
				System.out.println("\tCustomer ID\t\tName\t\tValue");
				System.out.println("-------------------------------------------------------------");
				for (int i = customerIdArray.length-1; i >=0; i--)
				{
					System.out.println("\t"+customerIdArray[i]+"\t\t"+customerNameArray[i]+"\t\t"+customerTotalArray[i]);
					System.out.println("------------------------------------------------------------");
				}
				do{
					System.out.print("Do you want to go to home page(Y/N)? ");
					char op=input.next().charAt(0);
					
					if(op=='Y'||op=='y'){
						break;
						}
					else if(op=='N'||op=='n'){
						continue;
						}
					else{
						System.out.println("Wrong Input....Try Again...");
						continue;
						}
					}while(true);
				
				
				 
		}
		
			//Creating the searchOf method
			public static int searchOf(String custId){
				for (int i = 0; i < burgerArray.length; i++)
				{
					if(custId.equals(burgerArray[i].getCustomerId())){
						return i;
							}
						}
					return -1;
				}
				
		//3.SearchOrderDetails
		public static void searchOrderDetails(){
			Scanner input=new Scanner(System.in);
		L3:do{
				System.out.println("+-----------------------------------------------------------+");
				System.out.printf("|%40s%20s\n","Search Order Details","|");
				System.out.println("+-----------------------------------------------------------+");
				System.out.println();
				System.out.print("Enter the Order ID : ");
				String orderID=input.next();
				
				boolean validOrderId=IsValidOrderId(orderID);
				
				if(validOrderId){
					//prints order details here
					for (int i = 0; i < burgerArray.length; i++){
						String checkid=burgerArray[i].getOrderId();
						if(checkid.equals(orderID)){
							System.out.println("--------------------------------------------------------------------------------");
							System.out.println("Order Id\tCustomer ID\tName\tQuantity\tOrder Value\tOrder Status");
							System.out.println("--------------------------------------------------------------------------------");
							System.out.println(burgerArray[i].getOrderId()+"\t"+burgerArray[i].getCustomerId()+"\t"+burgerArray[i].getCustomerName()+"\t"+burgerArray[i].getOrderQty()+"\t"+burgerArray[i].getTotal()+"\t"+burgerArray[i].getOrderStatus());
							}
					}
					
					}
				else{
					L4:do{
						System.out.print("Invalid OrderId....Do you want try again(Y/N) ? ");
						char op=input.next().charAt(0);
						
						if(op=='Y'||op=='y'){
							break L4;
							}
						else if(op=='N'||op=='n'){
							break L3;
							}
						else{
							System.out.println("Wrong Input...Try again...");
							continue;
							}
						}while(true);
					
					}
				
				}while(true);
			}
		
		//Method to check the validity of the orderID
		public static boolean IsValidOrderId(String orderId) {
				return orderId != null && orderId.matches("B\\d{4}");
		}
		
		//4.SearchCustomerDetails
		public static void SearchCustomer(){
			Scanner input=new Scanner(System.in);
		L5:do{
				System.out.println("+-----------------------------------------------------------+");
				System.out.printf("|%40s%20s\n","Search Customer Details","|");
				System.out.println("+-----------------------------------------------------------+");
				System.out.println();
				System.out.print("Enter Customer ID : ");
				String customerID=input.next();
				
				boolean check=isValidCustID(customerID);
				if(check){
					//print customer details
					for(int i=0;i<burgerArray.length;i++){
						if(customerID.equals(burgerArray[i].getCustomerId())){
							System.out.println("Customer ID - "+burgerArray[i].getCustomerId());
							System.out.println("Customer Name - "+burgerArray[i].getCustomerName());
							System.out.println("\nCustomer Order Details");
							System.out.println("===========================");
							System.out.println("\n\n");
							System.out.println("----------------------------------------------------------------------");
							System.out.println("Order ID\t\tOrder Quantity\t\tTotal Value");
							System.out.println("----------------------------------------------------------------------");
							for(int j=0;j<burgerArray.length;j++){
								if(customerID.equals(burgerArray[j].getCustomerId())){
									System.out.println(burgerArray[j].getOrderId()+"\t\t"+burgerArray[j].getOrderQty()+"\t\t"+burgerArray[j].getTotal());
									System.out.println("----------------------------------------------------------------------------------------------------");
									}
								}
							
							}
						}
					}
				else{
				L6:do{
						System.out.println("Invalid Customer ID...Do you want to try again(Y/N) ? ");
						char op=input.next().charAt(0);
						if(op=='Y'||op=='y'){
							break L6;
							}
						else if(op=='N'||op=='n'){
							break L5;
							}
						else{
							System.out.println("Invalid Input...Try again...");
							continue L6;
							}
						}while(true);
					}
				
				}while(true);
				
		}
		
		
		//05.View Order
		public static void ViewOrder(){
			Scanner input=new Scanner(System.in);
			do{
				System.out.println("+-----------------------------------------------------------+");
				System.out.printf("|%40s%20s\n","View Order","|");
				System.out.println("+-----------------------------------------------------------+");
				System.out.println();
				System.out.println("[01].Preparing Orders");
				System.out.println("[02].Delivered Orders");
				System.out.println("[03].Canceled Orders");
				
				System.out.print("Enter an option > ");
				int option=input.nextInt();
				
				switch(option){
					case 1:
						clearConsole();
						preparingOrders();
						break;
					case 2:
						clearConsole();
						deliveredOrders();
						break;
					case 3:
						clearConsole();
						canceledOrders();
						break;
					
					}
				
				}while(true);
			
			
			}
		
		//preparing orders
		public static void preparingOrders(){
			Scanner input=new Scanner(System.in);
			System.out.println("+-----------------------------------------------------------+");
			System.out.printf("|%40s%20s\n","Preparing Orders","|");
			System.out.println("+-----------------------------------------------------------+");
			System.out.println();
			System.out.println("Order ID\tCustomer ID\tCustomer Name\tQuantity");
			System.out.println("------------------------------------------------------------");
			for (int i = 0; i < burgerArray.length; i++)
			{
				if(burgerArray[i].getOrderStatus().equals("PREPARING")){
					System.out.println(burgerArray[i].getOrderId()+"\t"+burgerArray[i].getCustomerId()+"\t"+burgerArray[i].getCustomerName()+"\t"+burgerArray[i].getOrderQty());
					System.out.println("------------------------------------------------------------");
					}
			}
		L7:do{
				System.out.print("Do you want to go to Home Page(Y/N)?");
				char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					break L7;
					}
				else{
					continue L7;
					}
				}while(true);
			}
		
		//delivered orders
		public static void deliveredOrders(){
			Scanner input=new Scanner(System.in);
			System.out.println("+-----------------------------------------------------------+");
			System.out.printf("|%40s%20s\n","Delivered Orders","|");
			System.out.println("+-----------------------------------------------------------+");
			System.out.println();
			System.out.println("Order ID\tCustomer ID\tCustomer Name\tQuantity");
			System.out.println("------------------------------------------------------------");
			for (int i = 0; i < burgerArray.length; i++)
			{
				if(burgerArray[i].getOrderStatus().equals("DELIVERED")){
					System.out.println(burgerArray[i].getOrderId()+"\t"+burgerArray[i].getCustomerId()+"\t"+burgerArray[i].getCustomerName()+"\t"+burgerArray[i].getOrderQty());
					System.out.println("------------------------------------------------------------");
					}
			}
			L7:do{
				System.out.print("Do you want to go to Home Page(Y/N)?");
				char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					break L7;
					}
				else{
					continue L7;
					}
				}while(true);
			}
		
		//canceled orders
		public static void canceledOrders(){
			Scanner input=new Scanner(System.in);
			
			System.out.println("+-----------------------------------------------------------+");
			System.out.printf("|%40s%20s\n","Canceled Orders","|");
			System.out.println("+-----------------------------------------------------------+");
			System.out.println();
			System.out.println("Order ID\tCustomer ID\tCustomer Name\tQuantity");
			System.out.println("------------------------------------------------------------");
			for (int i = 0; i < burgerArray.length; i++)
			{
				if(burgerArray[i].getOrderStatus().equals("CANCELED")){
					System.out.println(burgerArray[i].getOrderId()+"\t"+burgerArray[i].getCustomerId()+"\t"+burgerArray[i].getCustomerName()+"\t"+burgerArray[i].getOrderQty());
					System.out.println("------------------------------------------------------------");
					}
			}
			L7:do{
				System.out.print("Do you want to go to Home Page(Y/N)?");
				char op=input.next().charAt(0);
				if(op=='Y'||op=='y'){
					break L7;
					}
				else{
					continue L7;
					}
				}while(true);
			}
		
		//06.update order details
		public static void UpdateOrderDetails(){
			Scanner input=new Scanner(System.in);
			do{
				System.out.println("+-----------------------------------------------------------+");
				System.out.printf("|%40s%20s\n","Update Order Details","|");
				System.out.println("+-----------------------------------------------------------+");
				System.out.println();
				System.out.print("Enter Order ID - ");
				String orderID=input.next();
				
				boolean validOrderID=IsValidOrderId(orderID);
				
				if(validOrderID){
					for (int i = 0; i < burgerArray.length; i++)
					{
						if(orderID.equals(burgerArray[i].getOrderId())){
							if(burgerArray[i].getOrderStatus().equals("DELIVERED")){
								System.out.println("This order is already Delivered...");
								}
							else if(burgerArray[i].getOrderStatus().equals("CANCELED")){
								System.out.println("This order is already Canceled...");
								}
							else{
								System.out.println("Order ID - "+burgerArray[i].getOrderId());
								System.out.println("Customer ID - "+burgerArray[i].getCustomerId());
								System.out.println("Name - "+burgerArray[i].getCustomerName());
								System.out.println("Quantity - "+burgerArray[i].getOrderQty());
								System.out.println("Order Value - "+burgerArray[i].getTotal());
								System.out.println("Order Status - Preparing");
								
								
								System.out.println("What do you want to update ? ");
								System.out.println("\t[1].Order Quantity");
								System.out.println("\t[2].Order Status");
								System.out.print("Enter your option > ");
								int option=input.nextInt();
								
								switch(option){
									case 1:
									clearConsole();
									System.out.println("Quantity Update");
									System.out.println("==================");
									System.out.println();
									System.out.println("Order ID - "+burgerArray[i].getOrderId());
									System.out.println("Customer ID - "+burgerArray[i].getCustomerId());
									System.out.println("Name - "+burgerArray[i].getCustomerName());	
									System.out.println();
									System.out.print("Enter new quantity to update value - ");
									int newqty=input.nextInt();
									
									burgerArray[i].setQuantity(newqty);//Assigning the new Value
									
									System.out.println("\tUpdate Order Quantity Successfully....");
										
									System.out.println("New Order Quantity - "+burgerArray[i].getOrderQty());
									System.out.println("New Order Value - "+burgerArray[i].getTotal());
									
									break;
									
									case 2:
									clearConsole();
									System.out.println("Status Update");
									System.out.println("================");
									System.out.println();
									System.out.println("Order ID - "+burgerArray[i].getOrderId());
									System.out.println("Customer ID - "+burgerArray[i].getCustomerId());
									System.out.println("Name - "+burgerArray[i].getCustomerName());	
									System.out.println();
									System.out.println("\t[1]Delivered");
									System.out.println("\t[2]Canceled");
									System.out.print("Enter the number(1/2) ofnew order status > ");
									int newst=input.nextInt();
										
									burgerArray[i].setStatus(newst);//Assigning the new value
									
									System.out.println("\tUpdate Order status succesfully...");
										
										switch(newst){
											case 1:
											System.out.println("New Order Status - Delivered");
											break;
											
											case 2:
											System.out.println("New Order Status - Canceled");
											break;
											}
									
									break;
									
									}
								}
							}
					}
					
					}
				else{
					System.out.println("Invalid Order ID...Try Again...");
					}
				}while(true);
		}
		
	
		
}
			

