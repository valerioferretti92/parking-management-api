package testutils;

import com.valerioferretti.parking.model.Car;
import com.valerioferretti.parking.model.Fees;
import com.valerioferretti.parking.model.Parking;
import com.valerioferretti.parking.model.enums.CarType;
import com.valerioferretti.parking.model.enums.ParkingType;
import com.valerioferretti.parking.model.enums.PricingType;
import com.valerioferretti.parking.model.enums.RoleType;
import com.valerioferretti.parking.utils.Utils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static void setAuthenticationContextAdmin() {
        String email = "admin@admin.com";
        String password = "admin";
        Set<RoleType> roles = new HashSet<RoleType>() {{ add(RoleType.ADMIN); }};
        setAuthenticationContext(email, password, roles);
    }

    public static void setAuthenticationContextUser() {
        String email = "user@user.com";
        String password = "user";
        Set<RoleType> roles = new HashSet<RoleType>() {{ add(RoleType.USER); }};
        setAuthenticationContext(email, password, roles);
    }

    public static void setAuthenticationContext(String email, String password, Set<RoleType> roles) {
        Authentication authentication;
        Collection<? extends GrantedAuthority> authorities;

        authorities = Utils.getAuthoritiesFromRoles(roles);
        authentication = new UsernamePasswordAuthenticationToken(email, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static Car getTestCar(String carId){
        Car car;

        car = new Car();
        car.setCarId(carId);
        car.setCarType(CarType.GASOLINE);
        return car;
    }

    public static Parking getTestParking(String parkingId){
        Parking parking = new Parking();
        parking.setParkingId(parkingId);
        parking.setParkingType(ParkingType.GASOLINE_CAR_PARK);
        parking.setPricingType(PricingType.HOURLY_PRICING);
        parking.setCapacity(5);
        parking.setFees(new Fees());
        parking.getFees().setHourlyFee(5.0);
        parking.getFees().setFixedFee(10.0);
        return parking;
    }

}
