// Fredrik hammar, frha2022

var userNumber;
var randomNumber;
var isCorrect;
var correctAnswer;
var multiply = false;
var addition = false;
var subtract = false;
var yourPoints = 0;
var rightOrWrong = "";

function init() {
    var submitButton = document.getElementById("buttonGuess");
    submitButton.addEventListener("click", onClickedSubmitGuess);

    var quizButton = document.getElementById("buttonNewQuiz");
    quizButton.addEventListener("click", makeQuestion);

    var radios = document.getElementsByName('math');
    for(var i = radios.length; i--; ) {
        radios[i].onclick = handler;
    }

    window.addEventListener("keydown", onPressedKey);
    document.getElementById("solution").innerHTML = "";   
    
    radioButtonValue();
    makeQuestion();

    isCorrect = false;
    randomNumber = 0;
    userNumber = 0;
}

function onPressedKey(event) {
    if (event.keyCode == 13) {
        onClickedSubmitGuess();
    }
}

function onClickedSubmitGuess() {
    console.log("function onClickedSubmit()");
    userNumber = document.getElementById("inputNumber").value;
    var points = document.getElementById("points");
    var border1 = document.getElementById("qnaBorder1");
    var border2 = document.getElementById("qnaBorder2");

    if (correctAnswer == userNumber) {
        //rätt    
        rightOrWrong = "right";
        yourPoints++;
        points.innerHTML = "Your points: " + yourPoints;
        isCorrect = true;
        points.style.color = "#0F0";
        border1.style.borderColor = "#0F0"; 
        border2.style.borderColor = "#0F0";        
       
        document.getElementById("solution").style.color = "#0F0";
    } else {
        //fel
        rightOrWrong = "wrong";
        isCorrect = false;

        border1.style.borderColor = "#F00"; 
        border2.style.borderColor = "#F00"; 

        points.style.color = "#F00";
        document.getElementById("solution").style.color = "#F00";
    }

    printAnswer();

}

function printAnswer() {
    document.getElementById("solution").innerHTML = "You were " + rightOrWrong +
        ". <br>The correct answer is " + correctAnswer;

    document.getElementById("inputNumber").value = "";
    makeQuestion();
}
function makeQuestion() {
    /*radioButtonValue();*/
    var a = Math.floor((Math.random() * 100) + 1);
    var b = Math.floor((Math.random() * 100) + 1);
    var op = " NO ANSWER ";
    if (multiply) {
        correctAnswer = a * b;
        op = " x ";
    } else if (subtract) {
        correctAnswer = a - b;
        op = " - ";
    } else if (addition) {
        correctAnswer = a + b;
        op = " + ";
    }
    document.getElementById("Question").innerHTML = "What is " + a + op + b + "?";
}


var handler = function() {
    radioButtonValue();
};

function radioButtonValue() {

    var selectedValue = document.querySelector('input[name="math"]:checked');

    multiply = false;
    addition = false;
    subtract = false; 

    var labelM = document.getElementById("LABELmultiply"); 
    var labelA = document.getElementById("LABELaddition"); 
    var labelS = document.getElementById("LABELsubtract"); 
    labelM.style.color = "white";
    labelA.style.color = "white";
    labelS.style.color = "white";

    if (selectedValue.value == "multiply") {
        labelM.style.color = "yellow";
        multiply = true;
    } else if (selectedValue.value == "subtract") {
        labelS.style.color = "yellow";
        subtract = true;
    } else if (selectedValue.value == "addition") {
        labelA.style.color = "yellow";
        addition = true;
    }
}

window.addEventListener("load", init);