let state = 0;
const url = "/api/" + document.querySelector("#new-like-userid").value + "/" + document.querySelector("#new-like-store-id").value + "/likes";
fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Server response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            if (data == null) {
                document.getElementById('bookmark_button').innerText = "즐겨찾기";
                state = 0;
            } else {
                document.getElementById('bookmark_button').innerText = "즐겨찾기 해제";
                state = 1;
            }
        }).catch(error => {
    console.error('There was a problem with the fetch operation:', error);
});

const likeCreate = document.getElementById('bookmark_button');
likeCreate.addEventListener("click", function () {
    if (state === 0) {
        state = 1;
        document.getElementById('bookmark_button').innerText = "즐겨찾기 해제";
        const like = {
            user_id: document.querySelector("#new-like-userid").value,
            store_id: document.querySelector("#new-like-store-id").value,
            like_state: 1,
        };
        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(like)
        }).then(response => response.json())
                .then(data => {
                    let like_id = data.id;
                    console.log("생성된 좋아요 id: " + like_id);
                    let like_state = data.state;
                    window.location.reload();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("좋아요 생성 실패");
                });
    } else {
        document.getElementById('bookmark_button').innerText = "즐겨찾기";
        fetch(url, {
            method: "DELETE"
        }).then(response => {
            if (!response.ok) {
                alert("좋아요 삭제 실패");
                console.log(document.querySelector("#new-like-userid").value);
                console.log(document.querySelector("#new-like-store-id").value);
                return;
            }
            document.getElementById('bookmark_button').innerText = "즐겨찾기";
            const msg = "매장 아이디:" + document.querySelector("#new-like-store-id").value + "번 즐겨찾기를 해제했습니다";
            alert(msg);
            window.location.reload(true);
        }).catch(error => {
            console.error('Error:', error);
            alert("좋아요 삭제 실패");

        });
    }
});