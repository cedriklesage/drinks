document.querySelector('.search-button').addEventListener('click', (e) => {
    document.querySelector('.search-popup').classList.add('active');
    document.querySelector('body').classList.add('no-scroll');
})

document.querySelector('.search-popup-back-button').addEventListener('click', (e) => {
    document.querySelector('.search-popup').classList.remove('active');
    document.querySelector('body').classList.remove('no-scroll');
})