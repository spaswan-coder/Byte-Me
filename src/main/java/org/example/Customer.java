package org.example;

public class Customer {
    private String name;
    private boolean isVIP;

    public Customer(String name) {
        this.name = name;
        this.isVIP = false;
    }

    public String getName() {
        return name;
    }

    public boolean isVIP() {
        return isVIP;
    }
    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    public void becomeVIP(double amountPaid) {
        if (amountPaid >= 50) {
            this.isVIP = true;
            System.out.println(name + " has become a VIP customer!");
        } else {
            System.out.println("Insufficient amount to become VIP. Minimum required is $50.");
        }
    }

    @Override
    public String toString() {
        return name + (isVIP ? " (VIP)" : " (Regular)");
    }
}
