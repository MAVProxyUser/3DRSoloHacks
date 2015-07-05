import sys
print "\n"
print(sys.version)
print "\n"

import math
# Vincenty's Direct formulae
def vinc_pt(phi1, lembda1, alpha12, s ) :
   """
   Returns the lat and long of projected point and reverse azimuth
   given a reference point and a distance and azimuth to project.
   lats, longs and azimuths are passed in decimal degrees
   Returns ( phi2,  lambda2,  alpha21 ) as a tuple

   f = flattening of the ellipsoid: 1/298.277223563
   a = length of the semi-major axis (radius at equator: 6378137.0)
   phi1 = latitude of the starting point
   lembda1 = longitude of the starting point
   alpha12 = azimuth (bearing) at the starting point
   s = length to project to next point
   """

   f = 1/298.277223563
   a = 6378137.0

   piD4 = math.atan( 1.0 )
   two_pi = piD4 * 8.0
   phi1    = phi1    * piD4 / 45.0
   lembda1 = lembda1 * piD4 / 45.0
   alpha12 = alpha12 * piD4 / 45.0
   if ( alpha12 < 0.0 ) :
      alpha12 = alpha12 + two_pi
   if ( alpha12 > two_pi ) :
      alpha12 = alpha12 - two_pi

   # length of the semi-minor axis (radius at the poles)
   b = a * (1.0 - f)
   TanU1 = (1-f) * math.tan(phi1)
   U1 = math.atan( TanU1 )
   sigma1 = math.atan2( TanU1, math.cos(alpha12) )
   Sinalpha = math.cos(U1) * math.sin(alpha12)
   cosalpha_sq = 1.0 - Sinalpha * Sinalpha

   u2 = cosalpha_sq * (a * a - b * b ) / (b * b)
   A = 1.0 + (u2 / 16384) * (4096 + u2 * (-768 + u2 * \
      (320 - 175 * u2) ) )
   B = (u2 / 1024) * (256 + u2 * (-128 + u2 * (74 - 47 * u2) ) )

   # Starting with the approx
   sigma = (s / (b * A))
   last_sigma = 2.0 * sigma + 2.0   # something impossible

   # Iterate the following 3 eqs unitl no sig change in sigma
   # two_sigma_m , delta_sigma
   while ( abs( (last_sigma - sigma) / sigma) > 1.0e-9 ) :

      two_sigma_m = 2 * sigma1 + sigma
      delta_sigma = B * math.sin(sigma) * ( math.cos(two_sigma_m) \
            + (B/4) * (math.cos(sigma) * \
            (-1 + 2 * math.pow( math.cos(two_sigma_m), 2 ) -  \
            (B/6) * math.cos(two_sigma_m) * \
            (-3 + 4 * math.pow(math.sin(sigma), 2 )) *  \
            (-3 + 4 * math.pow( math.cos (two_sigma_m), 2 ))))) \

      last_sigma = sigma
      sigma = (s / (b * A)) + delta_sigma

   phi2 = math.atan2 ( (math.sin(U1) * math.cos(sigma) + math.cos(U1) * math.sin(sigma) * math.cos(alpha12) ), \
      ((1-f) * math.sqrt( math.pow(Sinalpha, 2) +
      pow(math.sin(U1) * math.sin(sigma) - math.cos(U1) * math.cos(sigma) * math.cos(alpha12), 2))))

   lembda = math.atan2( (math.sin(sigma) * math.sin(alpha12 )), (math.cos(U1) * math.cos(sigma) -
      math.sin(U1) *  math.sin(sigma) * math.cos(alpha12)))

   C = (f/16) * cosalpha_sq * (4 + f * (4 - 3 * cosalpha_sq ))
   omega = lembda - (1-C) * f * Sinalpha *  \
      (sigma + C * math.sin(sigma) * (math.cos(two_sigma_m) +
      C * math.cos(sigma) * (-1 + 2 * math.pow(math.cos(two_sigma_m),2) )))

   lembda2 = lembda1 + omega
   alpha21 = math.atan2 ( Sinalpha, (-math.sin(U1) * math.sin(sigma) +
      math.cos(U1) * math.cos(sigma) * math.cos(alpha12)))

   alpha21 = alpha21 + two_pi / 2.0
   if ( alpha21 < 0.0 ) :
      alpha21 = alpha21 + two_pi
   if ( alpha21 > two_pi ) :
      alpha21 = alpha21 - two_pi

   phi2       = phi2       * 45.0 / piD4
   lembda2    = lembda2    * 45.0 / piD4
   alpha21    = alpha21    * 45.0 / piD4
   return phi2,  lembda2,  alpha21

print "\n"
print vinc_pt(40.12076, -83.07773, 10, 200)

# inputs
radius = 15.0 # m - the following code is an approximation that stays reasonably accurate for distances < 100km
centerLat = 40.12076
# latitude of circle center, decimal degrees
centerLon = -83.07773
# Longitude of circle center, decimal degrees

# parameters
N = 10 # number of discrete sample points to be generated along the circle

# generate points
circlePoints = []
#point['lat']=centerLat
#point['lon']=centerLon
#circlePoints.append(point)

for k in xrange(N):
    # compute
    angle = math.pi*2*k/N
    dx = radius*math.cos(angle)
    dy = radius*math.sin(angle)
    point = {}
    point['lat']=centerLat + (180/math.pi)*(dy/6378137)
    point['lon']=centerLon + (180/math.pi)*(dx/6378137)/math.cos(centerLat*math.pi/180)
    # add to list
    circlePoints.append(point)

print "\n"
#print circlePoints

for point in circlePoints:
    print point

def bearing(pointA, pointB):

    # Calculates the bearing between two points.
    #
    # :Parameters:
    #   - `pointA: The tuple representing the latitude/longitude for the
    #     first point. Latitude and longitude must be in decimal degrees
    #   - `pointB: The tuple representing the latitude/longitude for the
    #     second point. Latitude and longitude must be in decimal degrees
    #
    # :Returns:
    #   The bearing in degrees
    #
    # :Returns Type:
    #   float

    # if (type(pointA) != tuple) or (type(pointB) != tuple):
    #     raise TypeError("Only tuples are supported as arguments")

    lat1 = math.radians(pointA[0])
    lat2 = math.radians(pointB[0])

    diffLong = math.radians(pointB[1] - pointA[1])

    x = math.sin(diffLong) * math.cos(lat2)
    y = math.cos(lat1) * math.sin(lat2) - (math.sin(lat1)
            * math.cos(lat2) * math.cos(diffLong))

    initial_bearing = math.atan2(x, y)

    # Now we have the initial bearing but math.atan2 return values
    # from -180 to + 180 which is not what we want for a compass bearing
    # The solution is to normalize the initial bearing as shown below
    initial_bearing = math.degrees(initial_bearing)
    compass_bearing = (initial_bearing + 360) % 360

    return compass_bearing


# Point #1 - 40.12076, -83.07773
# https://www.google.com/maps/dir/40.12083920247139,++-83.07758744139487/40.12076,-83.0775538/40.120888152290696,+-83.07767554745823/40.120888152290696,++-83.07778445254178/40.12083920247139,+-83.07787255860514/40.12076,++-83.07790621212672/40.12068079752861,++-83.07787255860514/40.1206318477093,+-83.07778445254178/40.1206318477093,+-83.07767554745823/40.12076,+-83.07773/@40.1205266,-83.080423,17z/data=!3m1!4b1!4m53!4m52!1m5!1m1!1s0x0:0x0!2m2!1d-83.0775874!2d40.1208392!1m0!1m5!1m1!1s0x0:0x0!2m2!1d-83.0776755!2d40.1208882!1m5!1m1!1s0x0:0x0!2m2!1d-83.0777845!2d40.1208882!1m5!1m1!1s0x0:0x0!2m2!1d-83.0778726!2d40.1208392!1m5!1m1!1s0x0:0x0!2m2!1d-83.0779062!2d40.12076!1m5!1m1!1s0x0:0x0!2m2!1d-83.0778726!2d40.1206808!1m5!1m1!1s0x0:0x0!2m2!1d-83.0777845!2d40.1206318!1m3!2m2!1d-83.0776755!2d40.1206318!1m3!2m2!1d-83.07773!2d40.12076!3e2
#
#Point #2 - 40.120662, -83.078250

print "Bearing "  
b = bearing((40.12076, -83.07773), (40.120662, -83.078250))
print b 

from math import radians, cos, sin, asin, sqrt

def haversine(lon1, lat1, lon2, lat2):
    """
    Calculate the great circle distance between two points 
    on the earth (specified in decimal degrees)
    """
    # convert decimal degrees to radians 
    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])

    # haversine formula 
    dlon = lon2 - lon1 
    dlat = lat2 - lat1 
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a)) 
    r = 6371 # Radius of earth in kilometers. Use 3956 for miles
    return c * r


# Old center to new center
h = haversine(40.12076, -83.07773, 40.120662, -83.078250)
print h 

for point in circlePoints:
    print vinc_pt(point['lat'], point['lon'], h, b)

# print vinc_pt(40.12076, -83.07773, distance, bearing)
