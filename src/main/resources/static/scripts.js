function togglePassed() {
  var tests = document.getElementsByClassName("SUCCESS");
  if (tests[0].style.display === "none") {
    [...tests].forEach(e => e.style.display = "block")
  } else {
    [...tests].forEach(e => e.style.display = "none")
  }}

function toggleFailed() {
  var tests = document.getElementsByClassName("FAILED");
  if (tests[0].style.display === "none") {
    [...tests].forEach(e => e.style.display = "block")
  } else {
    [...tests].forEach(e => e.style.display = "none")
  }}

function toggleOther() {
  var tests = document.getElementsByClassName("OTHER");
  if (tests[0].style.display === "none") {
    [...tests].forEach(e => e.style.display = "block")
  } else {
    [...tests].forEach(e => e.style.display = "none")
  }
}

function scrollUp(){
    window.scroll(0, 0);
}

function scrollDown(){
    window.scrollTo(0, document.body.scrollHeight);
}