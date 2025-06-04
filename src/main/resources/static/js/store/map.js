var address = document.getElementById('storeAddress').innerText.trim();
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
};
var map = new kakao.maps.Map(container, options);
var geocoder = new kakao.maps.services.Geocoder();
var destinationCoords = null;

geocoder.addressSearch(address, function (result, status) {
    if (status === kakao.maps.services.Status.OK) {
        destinationCoords = new kakao.maps.LatLng(result[0].y, result[0].x);
        map.setCenter(destinationCoords);
        new kakao.maps.Marker({
            map: map,
            position: destinationCoords,
            title: "매장 위치"
        });
    }
});

document.getElementById('directionsButton').addEventListener('click', function () {
    if (!destinationCoords) {
        alert("도착지를 다시 설정해주세요.");
        return;
    }
    var kakaoMapURL = `https://map.kakao.com/link/to/도착지,${destinationCoords.getLat()},${destinationCoords.getLng()}`;
    window.open(kakaoMapURL, '_blank');
});