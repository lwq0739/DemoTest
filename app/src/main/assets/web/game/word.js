
function getWordDetail(data) {
    var wordList = data.exercises_word;
    var wordHtml;
    console.log(wordList.length);
    for (var i = 0; i < wordList.length; i++) {
        var sentence = getWordDiv("答案: "+wordList[i].word);
        var explain = getExplainDiv("解释: "+(isEmpty(wordList[i].explain)?"无":wordList[i].explain));
        wordHtml += getWordContainerDiv((i+1),sentence+explain);
    }
    return wordHtml;

}

function getWordDiv(sentence) {
// <img src="img/horn_green_3.png" style="width: 25px;height: 25px;margin-left: 10px">
    return "<div class=\"wordContainer\"><span class=\"word\">"+sentence+"</span></div>";
}

function getExplainDiv(explain) {
    return "<div class=\"wordExplainContainer\"><span class=\"wordExplain\">"+explain+"</span></div>";
}

function getWordContainerDiv(index,text) {
    return "<div class=\"itemWordContainer\"><div class=\"wordLeftDiv\">"+index+". </div><div class=\"wordRightDiv\">"+text+"</div></div>";
}