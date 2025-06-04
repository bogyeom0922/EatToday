document.querySelector('.review_link').addEventListener('click', function () {
    document.querySelector('.rest_detail').scrollIntoView({ behavior: 'smooth', block: 'start' });
});

document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector(".review-form");
  const textarea = form.querySelector("textarea");

  form.addEventListener("submit", function (e) {
    if (!textarea.value.trim()) {
      e.preventDefault();
      alert("리뷰를 입력해주세요.");
    }
  });

  const deleteForms = document.querySelectorAll(".review-item form");
  deleteForms.forEach(form => {
    form.addEventListener("submit", function (e) {
      if (!confirm("리뷰를 삭제하시겠습니까?")) {
        e.preventDefault();
      }
    });
  });
});