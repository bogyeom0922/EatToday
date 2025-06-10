document.addEventListener("DOMContentLoaded", function () {
  const reviewCreateBtn = document.querySelector("#review-create-btn");
  reviewCreateBtn.addEventListener("click", function () {
    const review = {
      userid: document.querySelector("#new-review-userid").value,
      body: document.querySelector("#new-review-body").value,
      storeid: document.querySelector("#new-review-store-id").value,
    };
    console.log(review);
    const url = "/api/stores/" + review.storeid + "/reviews";
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(review)
    }).then(response => {
      window.location.reload();
    });
  });
});
