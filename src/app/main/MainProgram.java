package app.main;

import app.slideshow.Slideshow;

public class MainProgram {

    private static MainProgram instance, instanceTransactionF, instanceBillF, instanceStaffF;
    private Slideshow slideshow;

    public MainProgram() {
    }

    public static MainProgram getInstance() {
        if (instance == null) {
//           System.out.println(" Create Main Program Instance");
            instance = new MainProgram();
        }
//        System.out.println("Instance MainProgram EXISTED");
        return instance;
    }

    public static MainProgram getInstanceTransactionForm() {
        if (instanceTransactionF == null) {
            instanceTransactionF = new MainProgram();
        }
        return instanceTransactionF;
    }

    public static MainProgram getInstanceBillForm() {
        if (instanceBillF == null) {
            instanceBillF = new MainProgram();
        }
        return instanceBillF;
    }

    public static MainProgram getInstanceStaffForm() {
        if (instanceStaffF == null) {
            instanceStaffF = new MainProgram();
        }
        return instanceStaffF;
    }

    public static void setNullAllInstance() {
        instance = null;
        instanceTransactionF = null;
        instanceStaffF = null;
        instanceBillF = null;
    }

    public Slideshow getSlideshow() {
        return slideshow;
    }

    public void setSlideshow(Slideshow slideshow) {
        this.slideshow = slideshow;
    }
}
