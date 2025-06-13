function toggleCalendar() {
    let calendar = document.getElementById("calendar");
    calendar.style.display = (calendar.style.display === "none" || calendar.style.display === "") ? "table" : "none";
    if (calendar.innerHTML === "") {
        generateCalendar();
    }
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
            if (i === 0 && j < firstDay) {
                row += "<td></td>";
            } else if (date > lastDate) {
                row += "<td></td>";
            } else {
                row += `<td onclick="selectDate(${year}, ${month + 1}, ${date})">${date}</td>`;
                date++;
            }
        }
        row += "</tr>";
        html += row;
        if (date > lastDate) break;
    }
    calendar.innerHTML = html;
}

function selectDate(year, month, day) {
    let reservationDate = document.getElementById("reservationDate");
    let timePickerContainer = document.getElementById("timePickerContainer");
    let timePicker = document.getElementById("timePicker");

    selectedDate = `${year}-${month.toString().padStart(2, "0")}-${day.toString().padStart(2, "0")}`;

    timePicker.innerHTML = "";

    for (let hour = 0; hour < 24; hour++) {
        let time = `${hour.toString().padStart(2, "0")}:00`;
        let option = document.createElement("option");
        option.value = time;
        option.textContent = time;
        timePicker.appendChild(option);
    }

    timePickerContainer.style.display = "block";

    updateReservationDate();
}

function updateReservationDate() {
    let reservationDate = document.getElementById("reservationDate");
    let timePicker = document.getElementById("timePicker");

    if (selectedDate && timePicker.value) {
        reservationDate.value = `${selectedDate} ${timePicker.value}`;
    }
}

generateCalendar();

document.getElementById('reserve-button').addEventListener('click', function () {
    const id = document.querySelector("#new-like-userid").value;
    if(id){
        console.log(id);
    }
    else{
        console.log("null id")
    }

    const storeId = document.querySelector("#new-like-store-id").value;
    if(storeId)
        console.log(storeId);
    else{
        console.log("null storeid")
    }
    const reservationDate = document.getElementById('reservationDate').value;
    if(reservationDate)
        console.log(reservationDate);
    else{
        console.log("null date")
    }
    fetch("/reservation/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId : id,
            storeId : storeId,
            reservationDate : reservationDate
        })
    }).then(response => {
        if (response.ok) {
            alert("예약이 완료되었습니다!");
        } else {
            alert("예약 실패. 다시 시도해주세요.");
        }
    });
});