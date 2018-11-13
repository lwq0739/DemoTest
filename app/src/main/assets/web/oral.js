
// var english = "We saw a houses of the Chinese traders from a hundred years ago";
// var regoise = "We saw the houses is over a a hundred years ago";
// var result = getBestResult(english,regoise);
// console.log(result.span)

function getBestResult(english,regoise) {
    if (english.length ===0 || regoise.length === 0){
        return getBestResultBean(0,english);
    }
    var englishList = english.trim().split(/\s+/);
    var regoiseList = regoise.trim().split(/\s+/);

    var resultList = [];
    for (var i = 0; i < regoiseList.length; i++) {
        for (var j = 0; j < englishList.length; j++) {
            if (englishList[j].search(regoiseList[i])!==-1&&regoiseList[i].length/englishList[j].length>=0.83){
                resultList[resultList.length]=({"index":i,"position":j,"word":englishList[j],"score":regoiseList[i].length/englishList[j].length});
            }
        }
    }
    var totalList = getList(resultList,0);
    var result = getBestNumList(totalList);
    // console.log(result);
    var resultSpan = "";
    var resultScore = 0;

    var currentPosition = 0;
    for (var i = 0; i < englishList.length; i++) {
        if (currentPosition < result.length && i === result[currentPosition].position){
            resultScore += result[currentPosition].score;
            resultSpan += "<span style=\"color: #3cd0cf\">"+englishList[result[currentPosition].position]+"</span> ";
            currentPosition++;
        } else {
            resultSpan += englishList[i]+" ";
        }
    }
    // console.log(resultScore);
    // console.log(resultSpan);
    return getBestResultBean(resultScore,resultSpan);
}

function getBestResultBean(score, resultSpan) {
    return {"score":score,"span":resultSpan};
}

function getBestNumList(list) {
    var wordNumList = [];
    var maxNum = 0;
    for (var i = 0; i < list.length; i++) {
        if (list[i].length>maxNum){
            maxNum = list[i].length;
        }
    }
    for (var i = 0; i < list.length; i++) {
        if (list[i].length===maxNum){
            wordNumList.push(list[i]);
        }
    }

    if (wordNumList.length === 1){
        return wordNumList[0];
    } else {
        var maxLengthIndex = 0;
        var maxLength = 0;
        for (var i = 0; i < wordNumList.length; i++) {
            var length = 0;
            for (var j = 0; j < wordNumList[i].length; j++) {
                length += wordNumList[i][j].word.length;
            }
            if (length>maxLength){
                maxLength = length;
                maxLengthIndex = i;
            }
        }
        return wordNumList[maxLengthIndex];
    }
}

function getList(list,nextIndex) {

    if (list === null || list.length === 0){
        return [];
    }
    var finalResultList = [];
    var resultList = list.slice(0,nextIndex);
    var lastPosition;
    if(nextIndex===0){
        lastPosition = list[0].position;
    }else {
        lastPosition = list[nextIndex-1].position;
    }

    for (var i = nextIndex; i < list.length; i++) {
        if (list[i].position<lastPosition || (resultList.length > 0  &&  list[i].index === resultList[resultList.length-1].index)||
            (resultList.length > 0  &&  list[i].position === resultList[resultList.length-1].position)){
            continue;
        }
        var minWordList = getMinWord(list.slice(i+1,list.length),lastPosition,list[i].position);


        if (minWordList.length !== 0){
            for (var j = 0; j < minWordList.length; j++) {
                var newList = resultList.concat(list.slice(list.indexOf(minWordList[j]),list.length));
                finalResultList=finalResultList.concat(getList(newList,resultList.length+1));
            }
        }

        resultList.push(list[i]);
        lastPosition = list[i].position;

    }

    finalResultList.push(resultList);
    return finalResultList;
}



function getMinWord(wordList,lastPosition,position) {
    if (wordList==null&&wordList.length<=0){
        return [];
    }
    var list = [];
    for (var i = 0; i < wordList.length; i++) {

        var currentPosition = wordList[i].position;
        if (lastPosition<currentPosition && currentPosition<position){
            list[list.length] = wordList[i];

        }
    }
    return list;

}

function discriminate() {
    var sentence = document.getElementById("sentence").value;
    var english = document.getElementById("english").innerText;
    var result = getBestResult(english,sentence);
    document.getElementById("english2").innerHTML = result.span;
    document.getElementById("score").innerText = "分数:"+result.score;
    var rightScore = result.score/english.trim().split(/\s+/).length*100;
    document.getElementById("right_score").innerText = "百分比分数:"+rightScore+"%";
    var starNum = 0;
    while (rightScore>25){
        starNum ++;
        rightScore -= 25;
    }
    document.getElementById("star").innerText = "星星:"+starNum;
}

