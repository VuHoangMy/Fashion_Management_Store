package app.DTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Fines implements Serializable {

    private double aFineOfGoLate;
    private double aFineOfLeaveEarly;
    private double aFineOfDayOff;
    private File file;

    public Fines(double aFineOfGoLate, double aFineOfLeaveEarly, double aFineOfDayOff) {
        this.aFineOfGoLate = aFineOfGoLate;
        this.aFineOfLeaveEarly = aFineOfLeaveEarly;
        this.aFineOfDayOff = aFineOfDayOff;
    }

    public Fines() {
    }

    public double getaFineOfGoLate() {
        return aFineOfGoLate;
    }

    public void setaFineOfGoLate(double aFineOfGoLate) {
        this.aFineOfGoLate = aFineOfGoLate;
    }

    public double getaFineOfLeaveEarly() {
        return aFineOfLeaveEarly;
    }

    public void setaFineOfLeaveEarly(double aFineOfLeaveEarly) {
        this.aFineOfLeaveEarly = aFineOfLeaveEarly;
    }

    public double getaFineOfDayOff() {
        return aFineOfDayOff;
    }

    public void setaFineOfDayOff(double aFineOfDayOff) {
        this.aFineOfDayOff = aFineOfDayOff;
    }

    public void initFile() {
        this.file = new File("src/app/Data/fines.txt");
    }

    public void writeAcountIntoFile() {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);

            oos.writeObject(this);

            oos.flush();
            fout.close();
            oos.close();
            System.out.println("Save Account Success");
        } catch (Exception e) {
//            System.out.println("WriteFile Fines " + e);
//e.printStackTrace();
        }
    }

    public void readAccountFromFile() {

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Fines fines = (Fines)ois.readObject();
            this.setaFineOfDayOff(fines.getaFineOfDayOff());
            this.setaFineOfGoLate(fines.getaFineOfGoLate());
            this.setaFineOfLeaveEarly(fines.getaFineOfLeaveEarly());
            fis.close();
            ois.close();
        } catch (Exception e) {
//            System.out.println("Read File Account" + e);
        }
    }

}
