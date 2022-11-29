package Lab2;

public class Person
{
	Person(String first, String last, String birth)
	{
		firstName = first;
		lastName = last;
		birthDate = birth;
	}
	
	String firstName;
	String lastName;
	String birthDate;
	
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getBirthdate()
	{
		return birthDate;
	}
}


abstract class Employee extends Person
{
	Employee(String first, String last, String birth, int id, String job, String comp) {
		super(first, last, birth);
		this.employeeID = id;
		this.jobTitle = job;
		this.company = comp;
	}

	int employeeID;
	String jobTitle;
	String company;
	
	public abstract double getAnnualSalary();
	
	public int getEmployeeID()
	{
		return employeeID;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}
	
	public String getCompany()
	{
		return company;
	}
}


class HourlyEmployee extends Employee
{
	HourlyEmployee(String first, String last, String Birth, int employeeID, String jobTitle, String Company, double rate, double hours)
	{
		super(first, last, Birth, employeeID, jobTitle, Company);
		this.hourlyRate = rate;
		this.numberHoursPerWeek = hours;
	}
	public double getAnnualSalary()
	{
		return hourlyRate*numberHoursPerWeek*52;
	}
	
	double hourlyRate;
	double numberHoursPerWeek;

}

class SalariedEmployee extends Employee
{
	SalariedEmployee(String first, String last, String Birth, int employeeID, String jobTitle, String Company, double annualSalary)
	{
		super(first, last, Birth, employeeID, jobTitle, Company);
		this.annualSalary = annualSalary;
	}
	
	public double getAnnualSalary()
	{
		return annualSalary;
	}
	
	double annualSalary;
}

class CommissionEmployee extends SalariedEmployee
{
	CommissionEmployee(String first, String last, String Birth, int employeeID, String jobTitle, String Company, 
			double annualSalary, double sales, double percent)
	{
		super(first, last, Birth, employeeID, jobTitle, Company, annualSalary);
		this.salesTotal = sales;
		this.comissionPercentage = percent;
	}
	
	public double getAnnualSalary()
	{
		return annualSalary + (salesTotal*comissionPercentage);
	}
	
	double salesTotal;
	double comissionPercentage;
}




