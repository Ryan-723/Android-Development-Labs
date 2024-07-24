package com.example.webymax.models;

public class HostingCost {
    // Variables to store customer information and costs
    private String customerName;
    private String province;
    private String webSpace;
    private String salesDate;
    double dbCost;
    double stagingCost;
    double planCost;

    // Constructor to initialize customer and cost details
    public HostingCost(String customerName, String province, String webSpace, String salesDate, double dbCost, double stagingCost, double planCost) {
        this.customerName = customerName;
        this.province = province;
        this.webSpace = webSpace;
        this.salesDate = salesDate;
        this.dbCost = dbCost;
        this.stagingCost = stagingCost;
        this.planCost = planCost;
    }

    // Method to calculate and get the hosting cost
    public String getHostingCost() {
        // Calculate additional costs
        double additionalCost = dbCost + stagingCost;
        double webSpaceCost = 0;
        double totalCost = 0;

        // Calculate web space cost based on the selected option
        switch (webSpace) {
            case "10GB":
                webSpaceCost = 0;
                break;
            case "20GB":
                webSpaceCost = 6.5;
                break;
            case "40GB":
                webSpaceCost = 8.5;
                break;
        }

        // Calculate the total hosting cost
        totalCost = planCost + additionalCost + webSpaceCost;

        // Return a simple message with the total cost and customer details
        return "For " + customerName + " from " + province + ", the Total Hosting Cost is $" + String.valueOf(totalCost) + " on Sales Date: " + salesDate;
    }
}
