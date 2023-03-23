
package app.CommonFuncion;

import app.DTO.Staff;

public class CurrentUserLogin {

    public static CurrentUserLogin instance;
    public Staff currentUser;
    
    public static CurrentUserLogin getCurrentStaff() {
        if (instance == null) {
            instance = new CurrentUserLogin();
        }
        return instance;
    }
    
    public void setCurrentUserLogin(Staff cStaff){
        this.currentUser = cStaff;
    }
    
    public Staff getCurrentuserLogin(){
        return this.currentUser;
    }


}
