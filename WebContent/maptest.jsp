<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	var geocoder;
	var map;
	var myLatlng = new google.maps.LatLng(24.380592, 121.228281);
	function initialize() {
	    geocoder = new google.maps.Geocoder();
	    var mapOptions = {
	        zoom: 9,
	        center: myLatlng,
	        
	    };
	    map = new google.maps.Map(document.getElementById('map-canvas'),
	        mapOptions);
	    //依序marker繪製
	    $.each(mapjson.map, function (index, value) {
	    	console.log(value.workName+""+index)
	        codeAddress(value, index)
	    });
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>