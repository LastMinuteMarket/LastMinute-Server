package com.lastminute.lastminuteserver.utils;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public final class SpatialUtil {

    public static Point convertPoint(double x, double y) {
        try {
            return (Point) new WKTReader().read(String.format("POINT(%s %s)", x, y));
        } catch (ParseException e) {
            throw RequestException.of(RequestExceptionCode.INVALID_PLACEMENT_LOCATION);
        }
    }
}
