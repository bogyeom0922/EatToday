document.getElementById("login-form").addEventListener("submit", async function(event) {
      event.preventDefault(); // 기본 제출 동작 방지

      const uid = document.getElementById("uid").value;
      const password = document.getElementById("upassword").value;

      const response = await fetch("/login", {
          method: "POST",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify({uid, upassword: password}),
          credentials: 'include'
      });

      if (response.ok) {
          alert("로그인 성공!");
          const categoryResponse = await fetch("/category", {
              method: "GET",
              headers: {},
              credentials: `include`
          });

          if (categoryResponse.ok) {
              alert("EatToday에 오신 것을 환영합니다!");
              window.location.href = "/category"
          } else {
              const errorMessage = await categoryResponse.text();
              alert(`카테고리 접근 실패: ${errorMessage}`);
          }
      }
  });

  function getAuthHeaders() {
      const token = localStorage.getItem("token");
      return token ? { "Authorization": token } : {};
  }

  const signUpButton = document.getElementById('signUp');
  const signInButton = document.getElementById('signIn');
  const container = document.getElementById('container');

  signUpButton.addEventListener('click', () => {
      container.classList.add("right-panel-active");
  });

  signInButton.addEventListener('click', () => {
      container.classList.remove("right-panel-active");
  });