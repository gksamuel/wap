/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author gachanja
 */
@Named("work")
@SessionScoped
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;
    private int category = 1;
    private int page = 0;
    private String message;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Work() {
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
