const animationDuration = 0;
const isOpenClass = "modal-is-open";
const openingClass = "modal-is-opening";
const closingClass = "modal-is-closing";
const scrollbarWidthCssVar = "--pico-scrollbar-width";


let visibleModal = null;

export function toggleModal (event : Event) {
  console.log('toggling modal')
  console.log(event)
  event.preventDefault();
  const modal = document.getElementById((event.target as HTMLElement).dataset.target as string);
  if (!(modal instanceof HTMLDialogElement)) return;
  modal && (modal.open ? closeModal(modal) : openModal(modal));
};

export function openModal(modal : HTMLDialogElement) {
  const {documentElement: html} = document;
  html.classList.add(isOpenClass, openingClass)
  setTimeout(() => {
      visibleModal = modal;
      html.classList.remove(openingClass)
    }
    , animationDuration);
  modal.showModal()
}

export function closeModal(modal : HTMLDialogElement) {
  visibleModal = null;
  const {documentElement: html} = document;
  html.classList.add(closingClass);
  setTimeout(() => {
    html.classList.remove(closingClass, isOpenClass);
    html.style.removeProperty(scrollbarWidthCssVar);
    modal.close();
  }, animationDuration);
}
