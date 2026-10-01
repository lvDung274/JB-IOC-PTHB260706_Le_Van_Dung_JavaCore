package presentation;

import entity.Product;
import persistence.ProductDAO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static ProductDAO ticketDao = new ProductDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
                    =======================================
                       CINEMA TICKET BOOKING MANAGEMENT
                    =======================================
                    1. Danh sách tất cả các phiếu đặt vé
                    2. Thêm mới phiếu đặt vé
                    3. Cập nhật thông tin phiếu đặt vé
                    4. Xóa phiếu đặt vé
                    5. Tìm kiếm phiếu đặt vé theo tên khách hàng
                    6. Tìm kiếm phiếu đặt vé theo tên phim
                    7. Thoát chương trình
                    =======================================
                    Nhập lựa chọn của bạn:
                    """);
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên từ 1 đến 7!");
                continue;
            }

            switch (choice) {
                case 1:
                    List<Product> listTicketBooking = ticketDao.findAll();
                    if (listTicketBooking.isEmpty()) {
                        System.err.println("Danh sách trống!");
                    } else {
                        listTicketBooking.forEach(System.out::println);
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Nhập tên phim:");
                        String titleMovie = sc.nextLine().trim();
                        if (titleMovie.isEmpty()) {
                            System.err.println("Tên phim không được để trống!");
                            break;
                        }

                        System.out.println("Nhập tên khách hàng:");
                        String customerName = sc.nextLine().trim();
                        if (customerName.isEmpty()) {
                            System.err.println("Tên khách hàng không được để trống!");
                            break;
                        }

                        System.out.println("Nhập suất chiếu (yyyy-MM-dd HH:mm:ss):");
                        Timestamp showTime = Timestamp.valueOf(sc.nextLine().trim());

                        System.out.println("Nhập ngày booking (yyyy-MM-dd):");
                        Date bookingDate = Date.valueOf(sc.nextLine().trim());

                        System.out.println("Nhập số lượng ghế :");
                        int seatQuantity = Integer.parseInt(sc.nextLine().trim());
                        if (seatQuantity <= 0) {
                            System.err.println("Số lượng ghế phải lớn hơn 0!");
                            break;
                        }

                        System.out.println("Nhập trạng thái (Booked / Cancelled / CheckedIn):");
                        String ticketStatus = sc.nextLine().trim();
                        if (!ticketStatus.equals("Booked") && !ticketStatus.equals("Cancelled") && !ticketStatus.equals("CheckedIn")) {
                            System.err.println("Trạng thái không hợp lệ!");
                            break;
                        }

                        Product newTicket = new Product();
                        newTicket.setMovie_title(titleMovie);
                        newTicket.setCustomer_name(customerName);
                        newTicket.setShow_time(showTime);
                        newTicket.setBooking_date(bookingDate);
                        newTicket.setSeat_quantity(seatQuantity);
                        newTicket.setStatus(ticketStatus);

                        ticketDao.addTicketBookings(newTicket);
                        System.out.println("Thêm phiếu đặt vé thành công!");
                    } catch (Exception e) {
                        System.err.println("Lỗi khi thêm phiếu đặt vé: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Nhập ID phiếu đặt vé cần cập nhật:");
                        int updateId = Integer.parseInt(sc.nextLine().trim());
                        Product existing = ticketDao.findById(updateId);
                        if (existing == null) {
                            System.err.println("Không tìm thấy vé có ID = " + updateId);
                            break;
                        }

                        System.out.println("Nhập tên phim mới:");
                        String movieTitle = sc.nextLine().trim();
                        System.out.println("Nhập tên khách hàng mới:");
                        String customerName = sc.nextLine().trim();
                        System.out.println("Nhập suất chiếu mới (yyyy-MM-dd HH:mm:ss):");
                        Timestamp showTime = Timestamp.valueOf(sc.nextLine().trim());
                        System.out.println("Nhập ngày đặt vé mới (yyyy-MM-dd):");
                        Date bookingDate = Date.valueOf(sc.nextLine().trim());
                        System.out.println("Nhập số lượng ghế mới:");
                        int seatQuantity = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Nhập trạng thái mới (Booked / Cancelled / CheckedIn):");
                        String status = sc.nextLine().trim();

                        Product updatedTicket = new Product();
                        updatedTicket.setMovie_title(movieTitle);
                        updatedTicket.setCustomer_name(customerName);
                        updatedTicket.setShow_time(showTime);
                        updatedTicket.setBooking_date(bookingDate);
                        updatedTicket.setSeat_quantity(seatQuantity);
                        updatedTicket.setStatus(status);

                        ticketDao.updateTicketBooking(updateId, updatedTicket);
                        System.out.println("Cập nhật phiếu đặt vé thành công!");
                    } catch (Exception e) {
                        System.err.println("Lỗi khi cập nhật phiếu đặt vé: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.println("Nhập ID phiếu đặt vé cần xóa:");
                        int deleteId = Integer.parseInt(sc.nextLine().trim());
                        Product existing = ticketDao.findById(deleteId);
                        if (existing == null) {
                            System.err.println("Không tìm thấy vé có ID = " + deleteId);
                            break;
                        }
                        System.out.println("Bạn có chắc chắn muốn xóa vé ID " + deleteId + "? (Nhập 'y' để xác nhận):");
                        String confirm = sc.nextLine().trim();
                        if (confirm.equalsIgnoreCase("y")) {
                            ticketDao.deleteTicketBooking(deleteId);
                            System.out.println("Xóa phiếu đặt vé thành công!");
                        } else {
                            System.out.println("Đã hủy thao tác xóa.");
                        }
                    } catch (Exception e) {
                        System.err.println("Lỗi khi xóa phiếu đặt vé: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Nhập tên khách hàng cần tìm kiếm:");
                    String searchCustomer = sc.nextLine().trim();
                    List<Product> customerResults = ticketDao.searchByCustomerName(searchCustomer);
                    if (customerResults.isEmpty()) {
                        System.err.println("Không tìm thấy phiếu đặt vé nào cho khách hàng: " + searchCustomer);
                    } else {
                        customerResults.forEach(System.out::println);
                    }
                    break;

                case 6:
                    System.out.println("Nhập tên phim cần tìm kiếm:");
                    String searchMovie = sc.nextLine().trim();
                    List<Product> movieResults = ticketDao.searchByMovieTitle(searchMovie);
                    if (movieResults.isEmpty()) {
                        System.err.println("Không tìm thấy phiếu đặt vé nào cho phim: " + searchMovie);
                    } else {
                        movieResults.forEach(System.out::println);
                    }
                    break;

                case 7:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 1 đến 7.");
            }
        } while (true);
    }
}