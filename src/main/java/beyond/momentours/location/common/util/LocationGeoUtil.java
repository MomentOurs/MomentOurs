package beyond.momentours.location.common.util;

import ch.hsr.geohash.GeoHash;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

public class LocationGeoUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static Point createPoint(BigDecimal latitude, BigDecimal longitude) {
        return geometryFactory.createPoint(new Coordinate(longitude.doubleValue(), latitude.doubleValue()));
    }

    public static String generateGeoHash(BigDecimal latitude, BigDecimal longitude, int precision) {
        return GeoHash.withCharacterPrecision(latitude.doubleValue(), longitude.doubleValue(), precision).toBase32();
    }
}
