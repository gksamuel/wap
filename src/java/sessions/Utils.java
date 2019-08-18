/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gachanja
 */
@Named("utils")
@SessionScoped
public class Utils implements Serializable {

    private static final long serialVersionUID = 5342773285819721641L;
    private ArrayList<Map> monthList;
    private ArrayList<Map> yearList;
    private final String title = "MyJob.co.ke";
    private String message;
    private String jobCategory;
    private String symbol = "=";
    private Integer pageNo = 0;
    private Integer pageCount = 0;
    private String maxJob;
    private String minJob;
    @PersistenceContext(unitName = "wapPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    private final Integer pageSize = 10;

    public ArrayList<Map> getMonthList() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 0, 1);
        monthList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        for (int x = 1; x <= 12; x++) {
            Map<String, String> month = new HashMap();
            month.put("monthId", String.valueOf(x));
            month.put("month", sdf.format(cal.getTime()));
            monthList.add(month);
            cal.add(Calendar.MONTH, 1);
        }
        return monthList;
    }

    public void setMonthList(ArrayList<Map> monthList) {
        this.monthList = monthList;
    }

    public ArrayList<Map> getYearList() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        int currentYear = Integer.valueOf(sdf.format(new Date()));
        yearList = new ArrayList();
        for (int x = currentYear - 50; x <= currentYear + 5; x++) {
            Map<String, String> month = new HashMap();
            month.put("yearID", String.valueOf(x));
            yearList.add(month);
        }
        return yearList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setYearList(ArrayList<Map> yearList) {
        this.yearList = yearList;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void addPageNo() {
        pageNo++;
        setPageNo(pageNo);
    }

    public void subPageNo() {
        pageNo--;
        setPageNo(pageNo);
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getMaxJob() {
        return maxJob;
    }

    public void setMaxJob(String maxJob) {
        this.maxJob = maxJob;
    }

    public String getMinJob() {
        return minJob;
    }

    public void setMinJob(String minJob) {
        this.minJob = minJob;
    }

}
