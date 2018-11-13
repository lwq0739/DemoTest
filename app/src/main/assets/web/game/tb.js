
function getTbDetail(data) {
    var blank = "<span class=\"underline\"></span>";
    var articleData = data.article;
    var article = articleData.article;
    var exercises = articleData.exercises;
    var index = 0;
    while (article.search(blank) !== -1){
        var exerciseItem = exercises[index];
        var answer = exerciseItem.question_list[exerciseItem.answer];
        if (data.reports === undefined || data.reports.length === 0){
            //无报告
            article = article.replace(blank,"<span class=\"underline\">"+answer+"</span>");
        }else {
            //有报告
            var percentage = data.reports[index].correct_rate;
            article = article.replace(blank,"<span class=\"underline\">"+answer+"</span>"+"<span class=\"percentage\">"+percentage+"%</span>");
        }

        index++;
    }
    console.log(article);
    return article;
}