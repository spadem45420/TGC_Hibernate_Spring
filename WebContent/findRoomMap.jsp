<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="controller.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>map</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/favicon-20150109015243617.ico" />
<link rel="bookmark"
	href="${pageContext.request.contextPath}/images/favicon-20150109015243617.ico" />
<link rel="stylesheet"
	href="//yui.yahooapis.com/3.18.1/build/cssreset/cssreset-min.css">
<link rel="stylesheet" type="text/css" href="../css/slicebox.css" />
<style type="text/css">
#index {
	background-color: #88AA00;
	height: 55px;
	width: 1161px;
	margin: 35px 0px auto;
}

#oindex {
	width: 1200px;
	margin: 0 auto;
}
#index li {
	float: left;
	margin-left: 30px;
	margin-top: 10px;
}


#table5 a {
	width: 100%;
	background-color: white;
}

#search-box, #tags {
	font-size: 15px;
	width: 150px;
	background: #FFFFFF url('../images/search.gif') no-repeat 8px 9px;
	padding: 5px 3px 5px 30px;
}

.inputt {
	font-size: 15px;
	width: 150px;
	padding: 5px 3px 5px 30px;
}

#mapfind:hover {
	text-decoration: underline;
	color: #FFFFFF;
}
#map-canvas {
width: 600px;
height: 600px;
margin: 20px;
padding: 0px
      }
body,input { font-size: 9pt; }

</style>
<script type='text/javascript'src='http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.js'></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
<script>
var img;
$(function () {
	//利用canvas產生一個內含文字的圖檔
	function createMarkerIcon(text, opt) {
	//定義預設參數
	var defaultOptions = {
	fontStyle: "normal", //normal, bold, italic
	fontName: "Microsoft JhengHei",
	fontSize: 14, //以Pixel為單位
	bgColor: "#0066FF",
	fgColor: "white",
	padding: 4,
	arrowHeight: 6 //下方尖角高度
	};
	options = $.extend(defaultOptions, opt);
	//建立Canvas，開始幹活兒
	var canvas = document.createElement("canvas"),
	context = canvas.getContext("2d");
	//評估文字尺寸
	var font = options.fontStyle + " " + options.fontSize + "px " +
	options.fontName;
	context.font = font;
	var metrics = context.measureText(text);
	//文字大小加上padding作為外部尺寸
	var w = metrics.width + options.padding * 2;
	//高度以Font的大小為準
	var h = options.fontSize + options.padding * 3 +
	options.arrowHeight;
	canvas.width = w;
	canvas.height = h;
	//邊框及背景
	context.beginPath();
	context.rect(0, 0, w, h - options.arrowHeight);
	context.fillStyle = options.bgColor;
	context.fill();
	//畫出下方尖角
	context.beginPath();
	var x = w / 2, y = h, arwSz = options.arrowHeight;
	context.moveTo(x, y);
	context.lineTo(x - arwSz, y - arwSz);
	context.lineTo(x + arwSz, y - arwSz);
	context.lineTo(x, y);
	context.fill();
	//印出文字
	context.textAlign = "center";
	context.fillStyle = options.fgColor;
	context.font = font;
	context.fillText(text,
	w / 2,
	(h - options.arrowHeight) / 2 + options.padding);
	//傳回DataURI字串
	return canvas.toDataURL();
	}
    var mapjson=${mapjson}
    var listmapjson=${listmapjson}
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
    function codeAddress(address, i) {
        //取得車站的經緯度，並繪制地圖
				var myLatlngs = new google.maps.LatLng(address.wrorkLongitude, address.wrorkLatitude);
                var marker = new google.maps.Marker({
                    map: map,
                    position: myLatlngs,
                    icon: createMarkerIcon(address.workName),
                    /* title: address,
                        icon: image  */
                   
                });
                attachSecretMessage(marker, i, address);//增加marker


    }
    var infoWindows = [];
    function attachSecretMessage(marker, num ,value) {
    	
    	var vv = '<div id="content" style="width:300px;height:300px">'+
        '<h4 id="firstHeading"><b style="color:#0055BC; font-size:16px">'+value.workName+'</b></h4>'+
        '<div id="bodyContent">'+
//         '<p>'+value.companyName+'</p>'+
//         '<p style="color:#C29551">'+value.wrorkDetailed+'</p>'+
        '<img src="${pageContext.servletContext.contextPath}/controller/GetImages?id='+value.StoreId+'&type=STORES" width="300px">'+
        '<p style="color:#707070" id="storeSSS">桌遊店名:'+value.StroeName+'</p>'+
        '<p style="color:#707070">開團人數:'+value.Upper+'</p>'+
        '<p style="color:#707070">開團時間:'+value.EndTime+'</p>'+
        '</div>'+
        '</div>';
        
        var infowindow = new google.maps.InfoWindow({
        	content:vv,
        	
//             content: markerContent[num];
        });
        infoWindows.push(infowindow); 
    	
    	
        google.maps.event.addListener(marker, 'click', function() {
        	 
      		  for (var i=0;i<infoWindows.length;i++) {   
      		     infoWindows[i].close();   
      		  }   
      		 
        	infowindow.open(marker.get('map'), marker);
        });
        
        google.maps.event.addListener(marker, 'dblclick', function() {
       	 
//         	  window.location.href = "http://www.google.com";
// 			  $('#bodyContent').append(123);
			  alert($('#storeSSS').text());
      	});
        
        
	
        
//         google.maps.event.addListener(marker, 'click', function() {
//         	infowindow.close();
     
        	
//         });
//         google.maps.event.addListener(marker, 'click', function () {
//             window.location.href = "http://www.yahoo.com.tw";
//         });
    }

    google.maps.event.addDomListener(window, 'load', initialize);
})
</script>
</head>
<body>
	<div id="map-canvas"></div>
	<img src="res/bg.jpg" style="display:none" id="bg">
</body>
</html>