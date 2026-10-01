CREATE DATABASE db_cinema_booking;

CREATE TABLE ticket_bookings(
                                booking_id SERIAL PRIMARY KEY,
                                movie_title VARCHAR(50) NOT NULL ,
                                customer_name VARCHAR(100) NOT NULL ,
                                show_time TIMESTAMP NOT NULL ,
                                booking_date TIMESTAMP NOT NULL ,
                                seat_quantity INT NOT NULL ,
                                status VARCHAR(30) NOT NULL CHECK ( status IN ('Booked' , 'Cancelled' , 'CheckedIn'))
);
CREATE OR REPLACE FUNCTION get_all_ticket_bookings()
    RETURNS SETOF ticket_bookings
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings;
END;
$$;

SELECT *
FROM get_all_ticket_bookings();

CREATE OR REPLACE FUNCTION get_ticket_bookings_by_booking_id(
    f_booking_id INT
)
    RETURNS SETOF ticket_bookings
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings WHERE booking_id = f_booking_id;
END;
$$;

SELECT *
FROM get_ticket_bookings_by_booking_id(1);

CREATE OR REPLACE PROCEDURE proc_insert_ticket_bookings(
    IN p_movie_title VARCHAR(50),
    IN p_customer_name VARCHAR(100),
    IN p_show_time TIMESTAMP,
    IN p_booking_date TIMESTAMP,
    IN p_seat_quantity INT,
    IN p_status VARCHAR(30)
)
    LANGUAGE plpgsql
AS
$$
BEGIN
    INSERT INTO ticket_bookings (movie_title, customer_name, show_time, booking_date, seat_quantity, status)
    VALUES (p_movie_title, p_customer_name, p_show_time, p_booking_date, p_seat_quantity, p_status);
END;
$$;

CREATE OR REPLACE FUNCTION get_ticket_bookings_by_customer_name(
    f_customer_name VARCHAR(100)
)
    RETURNS SETOF ticket_bookings
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings WHERE customer_name = f_customer_name;
END;
$$;

SELECT * FROM get_ticket_bookings_by_customer_name('alibaba');

CREATE OR REPLACE PROCEDURE proc_update_ticket_booking_by_booking_id(
    IN p_booking_id INT,
    IN p_movie_title VARCHAR(50),
    IN p_customer_name VARCHAR(100),
    IN p_show_time TIMESTAMP,
    IN p_booking_date TIMESTAMP,
    IN p_seat_quantity INT,
    IN p_status VARCHAR(30)
)
    LANGUAGE plpgsql
AS
$$
BEGIN
    UPDATE ticket_bookings SET
                               movie_title = p_movie_title,
                               customer_name = p_customer_name,
                               show_time = p_show_time,
                               booking_date = p_booking_date,
                               seat_quantity = p_seat_quantity,
                               status = p_status
    WHERE booking_id = p_booking_id;
END;
$$;

CREATE OR REPLACE PROCEDURE proc_delete_ticket_bookings_by_bookings_id(
    IN p_booking_id INT

)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM ticket_bookings WHERE booking_id = p_booking_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_search_ticket_bookings_by_title_movie(
    p_movie_title VARCHAR(50)
)
    RETURNS SETOF ticket_bookings AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings WHERE movie_title ILIKE '%' || p_movie_title || '%';
END;
$$ LANGUAGE plpgsql;


