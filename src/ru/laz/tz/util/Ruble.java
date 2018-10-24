package ru.laz.tz.util;


import java.util.IllegalFormatException;

public class Ruble {

    public enum Currency{
        RUBLE, CENT;
    }

    private long cent;

    public Ruble(long value, Ruble.Currency cur) {
        this.cent = cur.equals(Currency.CENT) ? value : value*100;
    }


    public long getCent() {
        return cent;
    }

    public void setCent(long cent) {
        this.cent = cent;
    }


    public long getRoubles() {
        return Math.round(cent/100.0f);
    }

    public int getLeftCents() {
        return (int)(cent%100);
    }
    
    
    public static Ruble toRuble(String inputStr) {
    	
    	String[] components = inputStr.split("\\.");
    	if (components.length != 2) throw new IllegalArgumentException("Ruble must be in 100.00 format");
    	
    	int rub,cent;
    	
    	try {
	    	rub = Integer.parseInt(components[0]);
	    	cent = Integer.parseInt(components[1]);
    	}
    	catch (NumberFormatException nfe) {
    		 throw new IllegalArgumentException("Ruble must be in 100.00 format");
    	}
	
    	return new Ruble(rub*100 + cent, Currency.CENT);
    }
    
    public Ruble addRuble(Ruble inputRub) {
    	
    	this.cent+=inputRub.getCent();
    	
    	return new Ruble(this.cent, Ruble.Currency.CENT);
    }
    

    
    
    @Override
    public String toString() {
        return getRoubles() + "." + getLeftCents();
    }

}
