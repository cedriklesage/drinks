const searchButton = document.querySelectorAll('.search-button');
const searchPopup = document.querySelector('.search-popup');

searchButton.forEach((button) => {
    button.addEventListener('click', (e) => {
        searchPopup.classList.add('active');
        document.querySelector('body').classList.add('no-scroll');
    });
});

document.querySelector('.search-popup-back-button').addEventListener('click', (e) => {
    document.querySelector('.search-popup').classList.remove('active');
    document.querySelector('body').classList.remove('no-scroll');
})