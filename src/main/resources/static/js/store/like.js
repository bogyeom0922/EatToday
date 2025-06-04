let state = 0;
const url = "/api/" + document.querySelector("#new-like-userid").value + "/" + document.querySelector("#new-like-store-id").value + "/likes";

fetch(url)
    .then(response => {
        if (!response.ok) throw new Error('Server response was not ok');
        return response.json();
    })
    .then(data => {
        document.getElementById('bookmark_button').innerText = data == null ? "즐겨찾기" : "즐겨찾기 해제";
        state = data == null ? 0 : 1;
    }).catch(console.error);

document.getElementById('bookmark_button').addEventListener("click", () => {
    if (state === 0) {
        state = 1;
        document.getElementById('bookmark_button').innerText = "즐겨찾기 해제";
        const like = {
            user_id: document.querySelector("#new-like-userid").value,
            store_id: document.querySelector("#new-like-store-id").value,
            like_state: 1
        };
        fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(like)
        }).then(() => window.location.reload())
          .catch(() => alert("좋아요 생성 실패"));
    } else {
        document.getElementById('bookmark_button').innerText = "즐겨찾기";
        fetch(url, { method: "DELETE" })
            .then(response => {
                if (!response.ok) throw new Error("삭제 실패");
                alert("즐겨찾기를 해제했습니다");
                window.location.reload(true);
            }).catch(() => alert("좋아요 삭제 실패"));
    }
});