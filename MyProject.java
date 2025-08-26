import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double minPricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double minPricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.minPricePerDay = minPricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return minPricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String custId;
    private String custName;

    public Customer(String custId, String custName) {
        this.custId = custId;
        this.custName = custName;
    }

    public String getCustomerId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalMenu {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalMenu() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent..");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental r : rentals) {
            if (r.getCar() == car) {
                rentalToRemove = r;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully..");
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n**** CAR RENTAL MENU ****");
            System.out.println("1. Rent a car ");
            System.out.println("2. Return a car ");
            System.out.println("3. Exit ");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");

                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable Cars: ");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = sc.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUST" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name  : " + newCustomer.getCustName());
                    System.out.println("Car : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental days : " + rentalDays);
                    System.out.printf("Total Price : $%.2f%n", totalPrice);

                    System.out.print("\nConfirm Rental (Yes/No): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Yes")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully...");
                    } else {
                        System.out.println("\nRental Canceled..");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent..");
                }

            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental r : rentals) {
                        if (r.getCar() == carToReturn) {
                            customer = r.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car Returned Successfully by " + customer.getCustName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing..");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented..");
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("\nInvalid choice. Please enter a valid option..");
            }
        }
        System.out.println("\nThank you for using the Car Rental Menu !!");
    }
}


public class MyProject {
    public static void main(String[] args) {
        CarRentalMenu rentalMenu = new CarRentalMenu();
        Car car1 = new Car("UP15", "Mahindra", "Scorpio", 180);
        Car car2 = new Car("UP16", "Honda", "City", 200);
        Car car3 = new Car("UP17", "Ford", "Figo", 130);

        rentalMenu.addCar(car1);
        rentalMenu.addCar(car2);
        rentalMenu.addCar(car3);

        rentalMenu.menu();
    }
}

