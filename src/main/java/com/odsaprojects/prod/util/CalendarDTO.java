/**
 * 
 */
package com.odsaprojects.prod.util;

/**
 * @author Osvaldo D. Sandoval
 *
 */
public class CalendarDTO {

	public long id;
    public String title;
    public String start;
    public String end;
    public String color;
    
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }

}
