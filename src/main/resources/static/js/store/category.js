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

console.log("스크립트 로드");
    window.addEventListener('DOMContentLoaded', function() {
        console.log("loaded");
        fetch("/api/recommend/allRecommend")
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                if(Array.isArray(data)) {
                    const storesContainer = document.querySelector('.random_store_container');

                    data.forEach((store, index) => {
                        const storeDiv = document.createElement("div");
                        storeDiv.classList.add(`random_store${index === 0 ? '' : index + 1}`);

                        storeDiv.innerHTML = `
                            <img src="${store.store_img}" class="random_store_img">
                            <p class="random_store_name">${store.store_name}</p>
                            <p class="random_store_address">${store.store_address}</p>
                        `;
                        storeDiv.onclick = function () {
                            window.location.href = `/rest/${store.id}/${store.uid}`;
                        };
                        storesContainer.appendChild(storeDiv);
                    });
                }
                else{
                    console.error("Data is not an array:", data);
                }
            })
            .catch(error => console.error("Error fetching data: ", error));
    });