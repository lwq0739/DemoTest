package com.example.lwq.demotest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lwq.demotest.html.RichText;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private String js = "function getBestResult(english,regoise) {\n"
        + "    if (english.length ===0 || regoise.length === 0){\n"
        + "        return getBestResultBean(0,english);\n"
        + "    }\n"
        + "    var englishList = english.trim().split(/\\s+/);\n"
        + "    var regoiseList = regoise.trim().split(/\\s+/);\n"
        + "\n"
        + "    var resultList = [];\n"
        + "    for (var i = 0; i < regoiseList.length; i++) {\n"
        + "        for (var j = 0; j < englishList.length; j++) {\n"
        + "            if (englishList[j].search(regoiseList[i])!==-1&&regoiseList[i].length/englishList[j].length>=0.83){\n"
        + "                resultList[resultList.length]=({\"index\":i,\"position\":j,\"word\":englishList[j],\"score\":regoiseList[i].length/englishList[j].length});\n"
        + "            }\n"
        + "        }\n"
        + "    }\n"
        + "    var totalList = getList(resultList,0);\n"
        + "    var result = getBestNumList(totalList);\n"
        + "    // console.log(result);\n"
        + "    var resultSpan = \"\";\n"
        + "    var resultScore = 0;\n"
        + "\n"
        + "    var currentPosition = 0;\n"
        + "    for (var i = 0; i < englishList.length; i++) {\n"
        + "        if (currentPosition < result.length && i === result[currentPosition].position){\n"
        + "            resultScore += result[currentPosition].score;\n"
        + "            resultSpan += \"<span style=\\\"color: #3cd0cf\\\">\"+englishList[result[currentPosition].position]+\"</span> \";\n"
        + "            currentPosition++;\n"
        + "        } else {\n"
        + "            resultSpan += englishList[i]+\" \";\n"
        + "        }\n"
        + "    }\n"
        + "    // console.log(resultScore);\n"
        + "    // console.log(resultSpan);\n"
        + "    return getBestResultBean(resultScore,resultSpan);\n"
        + "}\n"
        + "\n"
        + "function getBestResultBean(score, resultSpan) {\n"
        + "    return {\"score\":score,\"span\":resultSpan};\n"
        + "}\n"
        + "\n"
        + "function getBestNumList(list) {\n"
        + "    var wordNumList = [];\n"
        + "    var maxNum = 0;\n"
        + "    for (var i = 0; i < list.length; i++) {\n"
        + "        if (list[i].length>maxNum){\n"
        + "            maxNum = list[i].length;\n"
        + "        }\n"
        + "    }\n"
        + "    for (var i = 0; i < list.length; i++) {\n"
        + "        if (list[i].length===maxNum){\n"
        + "            wordNumList.push(list[i]);\n"
        + "        }\n"
        + "    }\n"
        + "\n"
        + "    if (wordNumList.length === 1){\n"
        + "        return wordNumList[0];\n"
        + "    } else {\n"
        + "        var maxLengthIndex = 0;\n"
        + "        var maxLength = 0;\n"
        + "        for (var i = 0; i < wordNumList.length; i++) {\n"
        + "            var length = 0;\n"
        + "            for (var j = 0; j < wordNumList[i].length; j++) {\n"
        + "                length += wordNumList[i][j].word.length;\n"
        + "            }\n"
        + "            if (length>maxLength){\n"
        + "                maxLength = length;\n"
        + "                maxLengthIndex = i;\n"
        + "            }\n"
        + "        }\n"
        + "        return wordNumList[maxLengthIndex];\n"
        + "    }\n"
        + "}\n"
        + "\n"
        + "function getList(list,nextIndex) {\n"
        + "\n"
        + "    if (list === null || list.length === 0){\n"
        + "        return [];\n"
        + "    }\n"
        + "    var finalResultList = [];\n"
        + "    var resultList = list.slice(0,nextIndex);\n"
        + "    var lastPosition;\n"
        + "    if(nextIndex===0){\n"
        + "        lastPosition = list[0].position;\n"
        + "    }else {\n"
        + "        lastPosition = list[nextIndex-1].position;\n"
        + "    }\n"
        + "\n"
        + "    for (var i = nextIndex; i < list.length; i++) {\n"
        + "        if (list[i].position<lastPosition || (resultList.length > 0  &&  list[i].index === resultList[resultList.length-1].index)||\n"
        + "            (resultList.length > 0  &&  list[i].position === resultList[resultList.length-1].position)){\n"
        + "            continue;\n"
        + "        }\n"
        + "        var minWordList = getMinWord(list.slice(i+1,list.length),lastPosition,list[i].position);\n"
        + "\n"
        + "\n"
        + "        if (minWordList.length !== 0){\n"
        + "            for (var j = 0; j < minWordList.length; j++) {\n"
        + "                var newList = resultList.concat(list.slice(list.indexOf(minWordList[j]),list.length));\n"
        + "                finalResultList=finalResultList.concat(getList(newList,resultList.length+1));\n"
        + "            }\n"
        + "        }\n"
        + "\n"
        + "        resultList.push(list[i]);\n"
        + "        lastPosition = list[i].position;\n"
        + "\n"
        + "    }\n"
        + "\n"
        + "    finalResultList.push(resultList);\n"
        + "    return finalResultList;\n"
        + "}\n"
        + "\n"
        + "\n"
        + "\n"
        + "function getMinWord(wordList,lastPosition,position) {\n"
        + "    if (wordList==null&&wordList.length<=0){\n"
        + "        return [];\n"
        + "    }\n"
        + "    var list = [];\n"
        + "    for (var i = 0; i < wordList.length; i++) {\n"
        + "\n"
        + "        var currentPosition = wordList[i].position;\n"
        + "        if (lastPosition<currentPosition && currentPosition<position){\n"
        + "            list[list.length] = wordList[i];\n"
        + "\n"
        + "        }\n"
        + "    }\n"
        + "    return list;\n"
        + "\n"
        + "}";

    @SuppressLint("SetJavaScriptEnabled")
    @TargetApi(19)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.web);
        mWebView.loadUrl("file:///android_asset/web/home.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JsInteration(this),JsInteration.NAME);
        mWebView.setVerticalScrollBarEnabled(false);

        //TextView textView = findViewById(R.id.tv);
        //String ss = "Reinhardt, <span style=\"color: #3cd0cf\">you</span> <span style=\"color: #3cd0cf\">know</span> <span style=\"color: #3cd0cf\">I</span> <span style=\"color: #3cd0cf\">had</span> <span style=\"color: #3cd0cf\">a</span> <span style=\"color: #3cd0cf\">poster</span> of you <span style=\"color: #3cd0cf\">on</span> <span style=\"color: #3cd0cf\">my</span> <span style=\"color: #3cd0cf\">wall</span> when I was <span style=\"color: #3cd0cf\">younger.</span> I remember the <span style=\"color: #3cd0cf\">poster!</span> My hair was amazing";
        //textView.setText(new RichText.Builder(ss).build().convert());

        //ResultBean result = (ResultBean) runScript(js,"getBestResult",new String[]{"We saw a houses of the Chinese traders from a hundred years ago",
        //    "We saw the house is over a hundred years ago"});
        //System.out.println("lwqlwqlwq  "+result.score+"  "+result.span);


        //TextView text = findViewById(R.id.text);
        //text.setText(BuildConfig.type+" \nproductFlavors配置环境变量 "+BuildConfig.Api+" \nproductFlavors配置不同代码 "+ Constant.S);

        //new MaterialDialog.Builder(this)
        //    .title("sada")
        //    .content("samdlakdpqwk")
        //    .positiveText("确定")
        //    .show();
    }

    public Object runScript(String js, String functionName, Object[] functionParams) {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", Context.javaToJS(MainActivity.this, scope));
            ScriptableObject.putProperty(scope, "javaLoader", Context.javaToJS(MainActivity.class.getClassLoader(), scope));

            rhino.evaluateString(scope, js, "MainActivity", 1, null);

            Function function = (Function) scope.get(functionName, scope);

            Object result = function.call(rhino, scope, scope, functionParams);

            if (result instanceof String) {
                System.out.println("lwqlwqlwq  String "+result.toString() );
                return result;
            } else if (result instanceof NativeJavaObject) {
                System.out.println("lwqlwqlwq  NativeJavaObject "+result.toString() );
                return ((NativeJavaObject) result).getDefaultValue(String.class);
            } else if (result instanceof NativeObject) {
                NativeObject object = (NativeObject) result;
                ResultBean resultBean = new ResultBean();
                resultBean.span = object.get("span").toString();
                resultBean.score = object.get("score").toString();
                //System.out.println("lwqlwqlwq  NativeObject "+object.containsKey("span")+"  "+object.ge);
                //return ((NativeObject) result).getDefaultValue(String.class);
                return resultBean;
            }
            return result.toString();
        } finally {
            Context.exit();
        }
    }


    @TargetApi(19)
    public void click(View view) {
        mWebView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                System.out.println("lwqlwqlwq  "+value);
            }
        });
        //mWebView.loadUrl("JavaScript:changeTitle()");
    }

}
