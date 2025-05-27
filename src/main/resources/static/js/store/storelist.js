document.addEventListener('DOMContentLoaded', () => {
    const toggleDropdown = (toggleButton, optionsContainer, optionItems) => {
        // 목록 열기/닫기
        toggleButton.addEventListener('click', () => {
            optionsContainer.style.display =
                    optionsContainer.style.display === 'block' ? 'none' : 'block';
        });

        // 항목 클릭 시 선택 및 드롭다운 닫기
        optionItems.forEach((item) => {
            item.addEventListener('click', () => {
                optionItems.forEach((i) => i.classList.remove('selected'));
                item.classList.add('selected');
                toggleButton.textContent = item.textContent;
                optionsContainer.style.display = 'none';
            });
        });

        // 외부 클릭 시 닫기
        document.addEventListener('click', (e) => {
            if (!e.target.closest('.options-wrapper')) {
                optionsContainer.style.display = 'none';
            }
        });
    };

    // 지역 드롭다운
    const regionToggleButton = document.getElementById('regionToggleButton');
    const regionOptionsContainer = document.getElementById('regionOptionsContainer');
    const regionItems = document.querySelectorAll('#regionOptionsContainer .option-item');

    toggleDropdown(regionToggleButton, regionOptionsContainer, regionItems);

    // 메뉴 드롭다운
    const menuToggleButton = document.getElementById('menuToggleButton');
    const menuOptionsContainer = document.getElementById('menuOptionsContainer');
    const menuItems = document.querySelectorAll('#menuOptionsContainer .option-item');

    toggleDropdown(menuToggleButton, menuOptionsContainer, menuItems);
});