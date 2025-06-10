function toggleCalendar() {
    let calendar = document.getElementById("calendar");
    calendar.style.display = calendar.style.display === "none" ? "table" : "none";
    if (calendar.innerHTML === "") generateCalendar();
}

let selectedDate = "";

function generateCalendar() {
    let calendar = document.getElementById("calendar");
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth();

    let firstDay = new Date(year, month, 1).getDay();
    let lastDate = new Date(year, month + 1, 0).getDate();
    let days = ["일", "월", "화", "수", "목", "금", "토"];
    let html = "<tr>" + days.map(day => `<th>${day}</th>`).join("") + "</tr>";

    let date = 1;
    for (let i = 0; i < 6; i++) {
        let row = "<tr>";
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay || date > lastDate) {
                row += "<td></td>";
            } else {
                row += `<td onclick="selectDate(${year}, ${month + 1}, ${date})">${date}</td>`;
                date++;
            }
        }
        html += row + "</tr>";
        if (date > lastDate) break;
    }
    calendar.innerHTML = html;
}

function selectDate(year, month, day) {
    selectedDate = `${year}-${month.toString().padStart(2, "0")}-${day.toString().padStart(2, "0")}`;
    document.getElementById("timePickerContainer").style.display = "block";
    updateReservationDate();
}

function updateReservationDate() {
    let time = document.getElementById("timePicker").value;
    if (selectedDate && time) {
        document.getElementById("reservationDate").value = `${selectedDate} ${time}`;
    }
}

document.getElementById('reserve-button').addEventListener('click', function () {
    const userId = document.querySelector("#new-like-userid").value;
    const storeId = document.querySelector("#new-like-store-id").value;
    const date = document.getElementById("reservationDate").value;

    fetch("/reservation/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, storeId, reservationDate: date })
    }).then(res => {
        alert(res.ok ? "예약이 완료되었습니다!" : "예약 실패. 다시 시도해주세요.");
    });
});

generateCalendar();