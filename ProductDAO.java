package persistence;

import entity.Product;
import untils.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
   public List<Product>findAll(){
        List<Product>listTicketBooking=new ArrayList<>();
            try(
                    Connection con = ConnectionDB.openConnection();
                    CallableStatement ctst =con.prepareCall("SELECT * FROM get_all_ticket_bookings()");
                    ResultSet rs = ctst.executeQuery();
                    ) {
                while (rs.next()) {
                    Product ticketMovie =  new Product(
                        rs.getInt("booking_id"),
                        rs.getString("movie_title"),
                        rs.getString("customer_name"),
                        rs.getTimestamp("show_time"),
                            rs.getDate("booking_date"),
                            rs.getInt("seat_quantity"),
                            rs.getString("status")
                    );
                    listTicketBooking.add(ticketMovie);
                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            return listTicketBooking;
   }
    // Tìm kiếm theo tên khách hàng (Gọi Function SQL đã sửa)
    public List<Product> searchByCustomerName(String keyword) {
        List<Product> list = new ArrayList<>();
        try (
                Connection con = ConnectionDB.openConnection();
                CallableStatement ctst = con.prepareCall("SELECT * FROM get_search_ticket_bookings_by_customer_name(?)")
        ) {
            ctst.setString(1, keyword);
            try (ResultSet rs = ctst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("booking_id"),
                            rs.getString("movie_title"),
                            rs.getString("customer_name"),
                            rs.getTimestamp("show_time"),
                            rs.getDate("booking_date"),
                            rs.getInt("seat_quantity"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Tìm kiếm theo tên phim (Gọi Function SQL tương ứng)
    public List<Product> searchByMovieTitle(String keyword) {
        List<Product> list = new ArrayList<>();
        try (
                Connection con = ConnectionDB.openConnection();
                CallableStatement ctst = con.prepareCall("SELECT * FROM get_search_ticket_bookings_by_title_movie(?)")
        ) {
            ctst.setString(1, keyword);
            try (ResultSet rs = ctst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("booking_id"),
                            rs.getString("movie_title"),
                            rs.getString("customer_name"),
                            rs.getTimestamp("show_time"),
                            rs.getDate("booking_date"),
                            rs.getInt("seat_quantity"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Product findById(int booking_id) {
        try(
                Connection con = ConnectionDB.openConnection();
                CallableStatement ctst = con.prepareCall("SELECT * FROM get_product_by_id(?)");
        ){
            ctst.setInt(1, booking_id);
            try(ResultSet rs = ctst.executeQuery();){
                if(rs.next()) {
                    return new Product(
                            rs.getInt("booking_id"),
                            rs.getString("movie_title"),
                            rs.getString("customer_name"),
                            rs.getTimestamp("show_time"),
                            rs.getDate("booking_date"),
                            rs.getInt("seat_quantity"),
                            rs.getString("status")
                    );
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

public void addTicketBookings(Product ticketBooking){
try (
        Connection con = ConnectionDB.openConnection();
        CallableStatement ctst = con.prepareCall("CALL proc_insert_ticket_bookings(?,?,?,?,?,?)");
        ){
    ctst.setString(1,ticketBooking.getMovie_title());
    ctst.setString(2,ticketBooking.getCustomer_name());
    ctst.setTimestamp(3,ticketBooking.getShow_time());
    ctst.setDate(4,ticketBooking.getBooking_date());
    ctst.setInt(5,ticketBooking.getSeat_quantity());
    ctst.setString(6,ticketBooking.getStatus());
    ctst.executeUpdate();
}catch (SQLException e){
    throw new RuntimeException(e);
   }
    }

    public void updateTicketBooking(int booking_id,Product ticketBooking){
        Product ticketBookings =findById(booking_id);
    if (ticketBooking!=null){
    throw new RuntimeException("Không tìm thấy vé có ID = " + booking_id);
        }
        try (
                Connection con = ConnectionDB.openConnection();
                CallableStatement ctst = con.prepareCall("Call proc_update_ticket_booking_by_booking_id(?,?,?,?,?,?)")
                ){
                ctst.setString(1,ticketBookings.getMovie_title());
                ctst.setString(2,ticketBookings.getCustomer_name());
                ctst.setTimestamp(3,ticketBookings.getShow_time());
                ctst.setDate(4,ticketBookings.getBooking_date());
                ctst.setInt(5,ticketBookings.getSeat_quantity());
                ctst.setString(6,ticketBookings.getStatus());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteTicketBooking(int booking_id){
        Product ticketBooking =findById(booking_id);
        if (ticketBooking == null){
            throw new RuntimeException("Không tìm thấy vé có ID = " + booking_id);
        }
        try (
                Connection con = ConnectionDB.openConnection();
                CallableStatement ctst = con.prepareCall("CALL proc_delete_ticket_bookings_by_bookings_id(?)")
                ){
                ctst.setInt(1, booking_id);
                ctst.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
