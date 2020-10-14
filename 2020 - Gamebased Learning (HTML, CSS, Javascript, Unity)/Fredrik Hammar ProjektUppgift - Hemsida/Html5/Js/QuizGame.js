window.onload = function () {
    var canvas = document.getElementById("myCanvas");
    var context = canvas.getContext("2d");
    var quizbg = new Image();
    var Question = new String;
    var b1 = new String;
    var b2 = new String;
    var b3 = new String;
    var mx = 0;
    var my = 0;
    var CorrectAnswer = 0;
    var qnumber = 0;
    var rightanswers = 0;
    var wronganswers = 0;
    var QuizFinished = false;
    var lock = false;
    var textpos1 = 45;
    var textposExtra1 = 380;
    var textpos2 = 145;
    var textpos3 = 230;
    var textpos4 = 325;
    var Questions = ["Where is Paris?", "Where is London?", "Where is Rome?", "Who Killed Dumbledore?", "Most Popular Search Engine", "Stars on the flag?", "Most Sold Comic?"];
    var Options = [["France", "Belgium", "Norway"], ["England", "China", "Denmark"], ["Italy", "Brazil", "Canada"], ["Snape", "Drape", "Grape"], ["Oogle", "Google", "Doodle"], ["Fifty", "Forty", "Sixty"], ["Batman", "Superman", "Spiderman"]];

    var removeCharAmount;
    var totalRemoved = 0;
    var found = false;
    var shuffledList;
    var restart = true;

    /* when loading draw background image and set inital questions*/
    quizbg.onload = function () {
        context.drawImage(quizbg, 0, 0);
        SetQuestions(false);
    }//quizbg
    quizbg.src = "Image/quizbg.png";


    /* METHODS I MADE THROUGH PDF TUTORIAL (Which have been modified) --------------------- */
    /* method that sets up the Questions.  I have changed this method alot*/
    /* this method now determines the amount of stars to be added to the options*/
    /* it also now takes in a boolean to check if the questions need to be soft resetet when removing just a star from the options*/
    /* also fiddled around with the text on the canvas*/

    SetQuestions = function (a) {

        Question = Questions[qnumber];
        var chosenOptions = new Array(Options[qnumber].length)
        chosenOptions = Options[qnumber].slice();



        if (a == false) {
            CorrectAnswer = 1 + Math.floor(Math.random() * 3);
            /*Find out max ammount of removed chars */
            var a = 0
            for (i = 0; i < chosenOptions.length; i++) {
                var b = chosenOptions[i].length;
                if (a < b) {
                    a = b;
                }

            }
            removeCharAmount = 3 + Math.floor(Math.random() * Math.floor(a / 2));
            if (removeCharAmount > a) {
                removeCharAmount = a;
            }
            shuffledList = shuffleOptions(chosenOptions);

        } else if (a == true) {
            console.log(chosenOptions);
            console.log(Options[qnumber]);
            if (removeCharAmount <= 0) {
                shuffledList = chosenOptions;
            } else {
                shuffledList = removeStar(chosenOptions, shuffledList);

            }

        }
        if (CorrectAnswer == 1) { b1 = shuffledList[0]; b2 = shuffledList[1]; b3 = shuffledList[2]; }
        if (CorrectAnswer == 2) { b1 = shuffledList[2]; b2 = shuffledList[0]; b3 = shuffledList[1]; }
        if (CorrectAnswer == 3) { b1 = shuffledList[1]; b2 = shuffledList[2]; b3 = shuffledList[0]; }


        context.textBaseline = "middle";
        context.font = "20pt Calibri,Arial";
        context.fillText(Question + ", Chars Removed: " + removeCharAmount, 20, textpos1);
        context.font = "24pt Calibri,Arial";
        context.fillText("Click to Reduce * --> ", 10, textposExtra1);
        context.font = "100pt Calibri,Arial";
        context.fillText(" - ", 260, textposExtra1);
        context.font = "18pt Calibri,Arial";
        context.fillText(b1, 20, textpos2);
        context.fillText(b2, 20, textpos3);
        context.fillText(b3, 20, textpos4);


    }//SetQuestions

    canvas.addEventListener('click', ProcessClick, false);
    /* Listenes for mouse clicks. I have added so that if you click at the bottom of the page you remove a star from each answer*/
    function ProcessClick(ev) {

        my = ev.y - canvas.offsetTop;
        mx = ev.x - canvas.offsetTop;

        if (ev.y == undefined) {
            my = ev.pageY - canvas.offsetTop;
        }

        if (lock) {
            ResetQ();
        }//if lock


        else {
            console.log("this is Y: " + my + " This is X: " + mx);


            if (my > 110 && my < 180) { GetFeedback(1); }
            if (my > 200 && my < 270) { GetFeedback(2); }
            if (my > 290 && my < 359) { GetFeedback(3); }
            if (my < 390 && my > 360 && mx < 240 && mx > 180 && lock == false) { GetFeedback("Reduce"); }


        }//!lock

    }//ProcessClick

    /* provides feedback based on mouse click. i have added a feedback that reduces the stars of each answer by one.*/
    /* also made it so that each answer removes all the star once a guess has been made*/
    /* also fiddled around with the text on the canvas*/

    GetFeedback = function (a) {

        if (a == "Reduce") {
            if (removeCharAmount != 0 && lock == false) {
                softResetQ();

            }

        } else if (a == CorrectAnswer) {
            rightanswers++;
            lock = true;
            removeCharAmount = 0;
            context.clearRect(0, 0, 550, 400);
            context.drawImage(quizbg, 0, 0);
            SetQuestions(true);
            context.drawImage(quizbg, 0, 400, 75, 70, 480, 110 + (90 * (a - 1)), 75, 70);
            context.font = "14pt Calibri,Arial";
            context.fillText("Click again to continue", 380, 380);
        }
        else {
            wronganswers++;
            lock = true;
            removeCharAmount = 0;
            context.clearRect(0, 0, 550, 400);
            context.drawImage(quizbg, 0, 0);
            SetQuestions(true);
            context.drawImage(quizbg, 75, 400, 75, 70, 480, 110 + (90 * (a - 1)), 75, 70);
            context.font = "14pt Calibri,Arial";
            context.fillText("Click to continue", 380, 380);

        }

    }

    /* Resets the quiz I haven ot added anything to this function after the tutorial*/
    ResetQ = function () {
        lock = false;

        context.clearRect(0, 0, 550, 400);
        qnumber++;
        if (qnumber == Questions.length) { EndQuiz(); }
        else {
            context.drawImage(quizbg, 0, 0);

            SetQuestions(false);
        }
    }

    /* Ends the quiz. I have added tracker for removed stars and made it possible to restart the quiz */
    /* also fiddled around with the text on the canvas*/

    EndQuiz = function () {

        context.drawImage(quizbg, 0, 0, 550, 90, 0, 0, 550, 400);
        context.font = "20pt Calibri,Arial";
        context.fillText("You have finished the quiz!", 20, 100);
        context.font = "16pt Calibri,Arial";
        context.fillText("Correct answers: " + String(rightanswers), 20, 200);
        context.fillText("Wrong answers: " + String(wronganswers), 20, 240);
        context.fillText("* Removed: " + String(totalRemoved), 20, 280);
        context.fillText("Click to Restart", 20, 320);
        rightanswers = 0;
        wronganswers = 0;
        totalRemoved = 0;
        lock = true;
        qnumber = 0;

    }
    /* METHODS I ADDED MYSELF ----------------------------- */

    /* Takes in a a list of the possible answers to a question and adds stars into answers based on a random number */
    shuffleOptions = function (O) {
        var i;
        var n = O;
        var Remove;
        var charsList;
        /* I loop through array too single out each answer*/

        for (i = 0; i < n.length; i++) {
            /* I create an array that contains a number for every index of the string*/
            charsList = new Array(n[i].length);
            for (a = 0; a < n[i].length; a++) {
                charsList[a] = a;
            }
            /* I create a loop that loops as many times as the amount of stars set to get removed*/
            for (b = 0; b < removeCharAmount; b++) {
                /* I take out a random number from the just created array and make a star out of that char in the string*/
                var SpecificChar = charsList[Math.floor(Math.random() * charsList.length)];
                removeItem(charsList, SpecificChar);
                n[i] = replaceOnlyOne(n[i], n[i].charAt(SpecificChar), "*");
            }
        }

        return n;

    }
    /* Removes one star on each answer*/
    function removeStar(arr, arr2) {
        for (i = 0; i < arr.length; i++) {
            for (a = 0; a < arr[i].length; a++) {
                if (arr2[i].charAt(a) != arr[i].charAt(a)) {
                    arr2[i] = replaceOnlyOne(arr2[i], arr2[i].charAt(a), arr[i].charAt(a));
                    break;
                }
            }
        }
        return arr2;

    }
    /* Used to shuffle options got a bit tricky so i did not end up using it*/
    function shuffleArray(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]];
        }
        return array;
    }
    /* Remove a specific item from an array*/
    function removeItem(arr, value) {
        var index = arr.indexOf(value);
        if (index > -1) {
            arr.splice(index, 1);
        }
        return arr;
    }
    /* The replace method in javascript replaces every single instance of what you want replaced. This just replaces one instance then stops*/
    function replaceOnlyOne(str, old, replacement) {
        var newStr = new String();
        var len = str.length;
        found = false;
        for (var i = 0; i < len; i++) {
            if (str[i] == old && found == false) {
                newStr = newStr.concat(replacement);
                /* console.log("found"); */
                found = true;
            }
            else {
                newStr = newStr.concat(str[i]);
            }
        }
        return newStr;
    }

    /* A soft reset */
    softResetQ = function () {
        removeCharAmount--;
        totalRemoved++;
        context.clearRect(0, 0, 550, 400);
        context.drawImage(quizbg, 0, 0);
        SetQuestions(true);
    }



};//windowonload