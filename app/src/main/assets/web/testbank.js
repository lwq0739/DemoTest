
document.write("<script type='text/javascript' src='GameConstant.js'></script>");
document.write("<script type='text/javascript' src='testdata.js'></script>");
document.write("<script type='text/javascript' src='game/sentence.js'></script>");
document.write("<script type='text/javascript' src='game/tb.js'></script>");
document.write("<script type='text/javascript' src='game/word.js'></script>");
document.write("<script type='text/javascript' src='game/gameUtil.js'></script>");

function getJsonData() {
    return tbData;
}

function getDetailHtml() {
    var data = JSON.parse(getJsonData()).data;
    var testBank = data.testbank;
    console.log("lwqlwqlwq  "+testBank.game_id);
    switch (testBank.game_id) {
        case HM.ID:
            return getWordDetail(data);
        case GF.ID:
            return getWordDetail(data);
        case FC.ID: //fc
            return getSentenceDetail(data);
        case RS.ID:
            return getWordDetail(data);
        case SF.ID: //sf
            return getSentenceDetail(data);
        case WQ.ID:
            return getWordDetail(data);
        case FLASHCARD.ID: //FLASHCARD
            if (testBank.game_type_id === GameTypeId.WORD){
                return getWordDetail(data);
            }else if (testBank.game_type_id === GameTypeId.SENTENCE){
                return getSentenceDetail(data);
            }
            return "";
        case DC.ID:
            return getWordDetail(data);
        case TYLJ.ID: //TYLJ
            return getSentenceDetail(data);
        case TB.ID: //tb
            return getTbDetail(data);
        default:
            return "";
    }
}


function getAnswer(index){
    if (index === -1){
        return "";
    }
    var data = JSON.parse(getJsonData()).data;
    //无报告
    if (data.reports === undefined || data.reports.length===0){
        return
    }
    var report = data.reports[index];
    var answerList = report.answers;
    for (var i = 0; i < answerList.length; i++) {
        var text = "";
        var answer = answerList[i];
        text += (answer.text === null || answer.text === "")?"未作答":answer.text.replace(/ +/," ")+"(" +answer.total+ "人)\n";
        for (var j = 0; j < answer.students.length; j++) {
            if (j === answer.students.length - 1) {
                text += answer.students[i].name + "\n";
            } else {
                text += answer.students[i].name + "/";
            }
        }
    }
    return text;
}



