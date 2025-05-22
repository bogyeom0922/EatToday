// 페이지 로드 시 호출
document.addEventListener('DOMContentLoaded', function () {
    // 현재 페이지의 상태를 설정
    history.replaceState(null, null, location.href);

    // 뒤로 가기 버튼 클릭 시 동작
    window.onpopstate = function (event) {
        // 페이지가 히스토리에서 이동하지 않도록 막음
        history.go(1);
    };
});

function logout() {
    // 세션 처리 후 리디렉션
    fetch('/logout', {method: 'POST'})
        .then(response => {
            if (response.ok) {
                location.replace('/user/login');
            } else {
                alert('로그아웃 중 오류가 발생했습니다.');
            }
        }
    );
}

//window.onload = function() {
//    const uid = "{{uid}}";
//    fetch(`/recommend/${uid}`)
//        .then(response => response.json())
//        .then(data => {
//            if(Array.isArray(data)) {
//                const storesContainer = document.getElementById("stores");
//
//                data.forEach((store, index) => {
//                    let storeClass = `random_store${index + 1}`;
//                    let storeDiv = document.createElement("div");
//                    storeDiv.classList.add(storeClass);
//                    storeDiv.onclick = function () {
//                        window.location.href = `/rest/${store.id}/${uid}`;
//                    };
//
//                    storeDiv.innerHTML = `
//            <img src="${store.img}" class="random_store_img">
//            <p class="random_store_name">${store.name}</p>
//            <p class="random_store_address">${store.address}</p>
//        `;
//
//                    storesContainer.appendChild(storeDiv);
//                });
//            }
//            else{
//                console.error("Data is not an array:", data);
//            }
//        })
//        .catch(error => console.error("Error fetching data: ", error));
//}
