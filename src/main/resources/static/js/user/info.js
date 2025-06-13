function openModal() {
    document.getElementById("review-edit-Modal").style.display = "block";
}

document.getElementById("closeModal").onclick = function () {
    document.getElementById("review-edit-Modal").style.display = "none";
}

document.querySelectorAll(".button[data-bs-toggle='modal']").forEach(btn => {
    btn.addEventListener("click", event => {
        const review = event.target;

        document.querySelector("#edit-review-id").value = review.getAttribute("data-bs-id");
        document.querySelector("#edit-review-store").value = review.getAttribute("data-bs-store");
        document.querySelector("#edit-review-userid").value = review.getAttribute("data-bs-userid");
        document.querySelector("#edit-review-body").value = review.getAttribute("data-bs-body");

        openModal();
    });
});

document.querySelector("#review-update-btn").addEventListener("click", () => {
    const review = {
        id: document.querySelector("#edit-review-id").value,
        store: document.querySelector("#edit-review-store").value,
        userid: document.querySelector("#edit-review-userid").value,
        body: document.querySelector("#edit-review-body").value
    };

    fetch(`/api/reviews/${review.id}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(review)
    }).then(response => {
        alert(response.ok ? "댓글이 수정됐습니다." : "댓글 수정에 실패하였습니다.");
        window.location.reload();
    });
});

document.querySelectorAll(".review-delete-btn").forEach(btn => {
    btn.addEventListener("click", event => {
        const reviewId = event.target.getAttribute("data-review-id");

        fetch(`/api/reviews/${reviewId}`, {
            method: "DELETE"
        }).then(response => {
            if (!response.ok) {
                alert("댓글 삭제 실패");
                return;
            }

            alert(`${reviewId}번 댓글을 삭제했습니다`);
            window.location.reload();
        }).catch(error => {
            console.error("댓글 삭제 중 오류 발생:", error);
            alert("댓글 삭제 중 오류가 발생했습니다.");
        });
    });
});

function changePage(page) {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('page', page);
    window.location.search = urlParams.toString();
}

const links = document.querySelectorAll('.userinfo_category_link');

links.forEach(link => {
    link.addEventListener('click', () => {
        links.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
    });
});