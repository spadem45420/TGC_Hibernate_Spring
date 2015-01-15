<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
<head> 
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
  <title>Google Maps Multiple Markers</title> 
  <script src="http://maps.google.com/maps/api/js?sensor=false" 
          type="text/javascript"></script>
</head> 
<body>
  <div id="map" style="width: 1000px; height: 1000px;"></div>
  <script type="text/javascript">
    var locations = [
      ['卡卡城-三重店', 25.0594851, 121.49462370000003,'http://www.yahoo.com.tw'],
      ['卡卡城-東門店', 25.0334813, 121.52697379999995,7],
      ['艾客米東門店', 25.0338199, 121.53235700000005,6],
      ['瘋桌遊-益智遊戲專賣店(汐止店)', 25.0649195, 121.66398930000003,5],
      ['瘋桌遊-益智遊戲專賣店(松山店)', 25.0574056, 121.56452549999995, 4],
      ['卡牌屋-台北店', 25.046166, 121.51374599999997, 3],
      ['卡牌屋-新竹店', 24.7976445, 120.97865980000006, 2],
      ['艾客米忠孝復興店', 25.0418645, 121.54357619999996, 1]
    ];

    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 13,
      center: new google.maps.LatLng(25.0248429,121.54205589999992),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;

    for (i = 0; i < locations.length; i++) {  
      marker = new google.maps.Marker({
        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        map: map
      });

      google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
        return function() {
          infowindow.setContent(locations[i][0]);
          infowindow.open(map, marker);
        }
      })(marker, i));
      
      google.maps.event.addListener(marker, 'click', (function(marker, i) {
          return function() {
        	  window.location.href = locations[i][3];
          }
        })(marker, i));
      
      
//       google.maps.event.addListener(marker, 'click', function () {
//       	  window.location.href = "http://www.yahoo.com.tw";
//    	  });
    }
  </script>
</body>
</html>