let map;
let marker;

document.addEventListener('DOMContentLoaded', async function () {
    const container = document.getElementById("map");
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
    };

    map = new kakao.maps.Map(container, options);

    const markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

    marker = new kakao.maps.Marker({
        position: markerPosition,
    });
    marker.setMap(map);

    marker.addListener("click", function () {
        const copiedAddress =
            document.getElementById("meetingAddress").textContent;
        window.open(
            `https://map.kakao.com/link/search/${copiedAddress}`,
            "_blank"
        );
    });

    document
        .querySelector(".edit-button")
        .addEventListener("click", function () {
            const keyword = prompt("장소나 주소를 입력하세요.");
            if (keyword) {
                searchPlaces(keyword);
            }
        });

    await updateAddressFromLatLng(33.450701, 126.570667);
});

async function updateAddressFromLatLng(lat, lng) {
    return new Promise((resolve, reject) => {
        const geocoder = new kakao.maps.services.Geocoder();
        const coord = new kakao.maps.LatLng(lat, lng);

        geocoder.coord2Address(
            coord.getLng(),
            coord.getLat(),
            function (result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    const address = result[0].address.address_name;
                    document.getElementById("meetingAddress").textContent = address;
                    resolve(address);
                } else {
                    reject(status);
                }
            }
        );
    });
}

function searchPlaces(keyword) {
    const places = new kakao.maps.services.Places();
    places.keywordSearch(keyword, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            const message = result
                .map(
                    (item, index) =>
                        `${index + 1}. ${item.place_name} (${item.address_name})`
                )
                .join("\n");
            const selection = parseInt(
                prompt(`${message}\n원하는 번호를 선택하세요.`)
            );

            if (selection > 0 && selection <= result.length) {
                document.getElementById("meetingAddress").textContent =
                    result[selection - 1].address_name;

                const newPosition = new kakao.maps.LatLng(result[selection - 1].y, result[selection - 1].x);
                marker.setPosition(newPosition);
                map.setCenter(newPosition);
            } else {
                alert("잘못된 번호입니다.");
            }
        } else {
            alert("검색 결과가 없습니다.");
        }
    });
}


function copyAddressToClipboard() {
    const addressToCopy =
        document.getElementById("meetingAddress").textContent;

    const tempInput = document.createElement("textarea");
    tempInput.value = addressToCopy;
    document.body.appendChild(tempInput);
    tempInput.select();
    document.execCommand("copy");
    document.body.removeChild(tempInput);
    alert("복사완료");
}