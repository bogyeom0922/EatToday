document.addEventListener('DOMContentLoaded', () => {
    const toggleDropdown = (toggleButton, optionsContainer, optionItems) => {
        toggleButton.addEventListener('click', () => {
            optionsContainer.style.display =
                    optionsContainer.style.display === 'block' ? 'none' : 'block';
        });

        optionItems.forEach((item) => {
            item.addEventListener('click', () => {
                optionItems.forEach((i) => i.classList.remove('selected'));
                item.classList.add('selected');
                toggleButton.textContent = item.textContent;
                optionsContainer.style.display = 'none';
            });
        });

        document.addEventListener('click', (e) => {
            if (!e.target.closest('.options-wrapper')) {
                optionsContainer.style.display = 'none';
            }
        });
    };

    const regionToggleButton = document.getElementById('regionToggleButton');
    const regionOptionsContainer = document.getElementById('regionOptionsContainer');
    const regionItems = document.querySelectorAll('#regionOptionsContainer .option-item');

    toggleDropdown(regionToggleButton, regionOptionsContainer, regionItems);

    const menuToggleButton = document.getElementById('menuToggleButton');
    const menuOptionsContainer = document.getElementById('menuOptionsContainer');
    const menuItems = document.querySelectorAll('#menuOptionsContainer .option-item');

    toggleDropdown(menuToggleButton, menuOptionsContainer, menuItems);
});