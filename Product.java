package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Product {
    private Integer booking_id;
    private String movie_title;
    private String customer_name;
    private Timestamp show_time;
    private Date booking_date;
    private Integer seat_quantity;
    private String status;

    public Product(String movie_title, String customer_name, Timestamp show_time, Date booking_date, Integer seat_quantity, String status) {
        this.movie_title = movie_title;
        this.customer_name = customer_name;
        this.show_time = show_time;
        this.booking_date = booking_date;
        this.seat_quantity = seat_quantity;
        this.status = status;
    }
    public Product(Integer booking_id, String movie_title, String customer_name, Timestamp show_time, Date booking_date, Integer seat_quantity, String status) {
        this.booking_id = booking_id;
        this.movie_title = movie_title;
        this.customer_name = customer_name;
        this.show_time = show_time;
        this.booking_date = booking_date;
        this.seat_quantity = seat_quantity;
        this.status = status;
    }
    public Product() {

    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Timestamp getShow_time() {
        return show_time;
    }

    public void setShow_time(Timestamp show_time) {
        this.show_time = show_time;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public Integer getSeat_quantity() {
        return seat_quantity;
    }

    public void setSeat_quantity(Integer seat_quantity) {
        this.seat_quantity = seat_quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        System.out.println("ID : " + booking_id + "|Title movie: " + movie_title +"|Customer name:" +customer_name + "|Show time" +show_time +"|Booking date" +booking_date+"|Seat quantity:"+seat_quantity+"|Status:"+status );
        return  status;
    }
}

