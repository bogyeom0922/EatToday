document.addEventListener('DOMContentLoaded', function () {
    history.replaceState(null, null, location.href);

    window.onpopstate = function (event) {
        history.go(1);
    };
});

function logout() {
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