const menu_str = document.getElementById("MenuSentence").textContent;
const menu_arr = menu_str.split(", ");
const MenuDisplay = document.getElementById("MenuDisplay");
document.getElementById("MenuSentence").style.display = "none";

menu_arr.forEach((menu, index) => {
    let menu_text = menu.trim();
    if (index === menu_arr.length - 1) {
        menu_text = menu_text.slice(0, -1);
    }
    MenuDisplay.append(menu_text);
    MenuDisplay.appendChild(document.createElement("br"));
});