


function getSentenceDetail(data) {
    var sentenceList = data.exercises_sentence;
    var sentenceHtml;
    for (var i = 0; i < sentenceList.length; i++) {
        var sentence = getSentenceDiv("答案: "+sentenceList[i].sentence,data.reports[i].correct_rate);
        var explain = getExplainDiv("解释: "+(isEmpty(sentenceList[i].explain)?"无":sentenceList[i].explain));
        sentenceHtml += getSentenceContainerDiv(sentence+explain);
    }
    return sentenceHtml;

}

function getSentenceDiv(sentence,report) {
    return "<div class=\"sentenceContainer\"><span class=\"sentence\">"+sentence+"</span>"+
        (isEmpty(report)?"":"<span class=\"percentage\">"+report+"%</span>")+"</div>";
}

function getExplainDiv(explain) {
    return "<div class=\"explainContainer\"><span class=\"explain\">"+explain+"</span></div>";
}

function getSentenceContainerDiv(text) {
    return "<div class=\"itemSentenceContainer\"><div class=\"leftDiv\"></div><div class=\"rightDiv\">"+text+"</div></div>";
}