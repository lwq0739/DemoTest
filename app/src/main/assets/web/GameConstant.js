/**
 * 猜词游戏 1
 * mode 1 => 无发音 2 => 有发音
 */
const HM = function () {
};
HM.ID = 1;
HM.MODE_NO_PRONUNCIATION = 1;
HM.MODE_PRONUNCIATION = 2;

/**
 * 单项选择 2
 */
const SQ = function () {
};
SQ.ID = 2;

/**
 * 连连看 4
 */
const MG = function () {
};
MG.ID = 4;

/**
 * 句型转换 5
 */
const ST = function () {
};
ST.ID = 5;

/**
 * 单词拼写 6
 */
const GF = function () {
};
GF.ID = 6;
GF.MODE_RANDOM = 1;
GF.MODE_DICTATION = 2;
GF.MODE_CUSTOM = 3;

/**
 * 完型填空 7
 */
const CL = function () {
};
CL.ID = 7;


//
/**
 * 连词成句 8
 */
const FC = function () {
};
FC.ID = 8;

/**
 * 还原单词 9
 */
const RS = function () {
};
RS.ID = 9;


/**
 * 阅读理解 10
 */
const RC = function () {
};
RC.ID = 10;

/**
 * 强化炼句 11
 * mode 1 => 简单 2 => 复杂 3 => 默写 4 => 自定义
 */
const SF = function () {
};
SF.ID = 11;
SF.MODE_SIMPLE = 1;
SF.MODE_COMPLEX = 2;
SF.MODE_DICTATION = 3;
SF.MODE_CUSTOM = 4;

/**
 * 词汇选择 12
 * mode 1 => 单词模式 2 => 解释模式 3 => 听写模式
 */
const WQ = function () {
};
WQ.ID = 12;
WQ.MODE_WORD = 1;
WQ.MODE_EXPLAIN = 2;
WQ.MODE_DICTATION = 3;

/**
 * 听力练习 13
 */
const FS = function () {
};
FS.ID = 13;

/**
 * 补全文章 14
 */
const SS = function () {
};
SS.ID = 14;

/**
 * 选词填空 15
 */
const TB = function () {
};
TB.ID = 15;

/**
 * 闪卡练习 16
 * word : mode 1 => 学习模式 2 => 抄写模式
 * sentence : mode 1 => 学习模式
 */
const FLASHCARD = function () {
};
FLASHCARD.ID = 16;
FLASHCARD.MODE_LEARN = 1;
FLASHCARD.MODE_COPY = 2;

/**
 * 单词听写 17
 */
const DC = function () {
};
DC.ID = 17;

/**
 * 听音连句 18
 */
const TYLJ = function () {
};
TYLJ.ID = 18;

/**
 * 口语跟读 18
 */
const FR = function () {
};
FR.ID = 19;

/**
 * 口语看读 20
 */
const LR = function () {
};
LR.ID = 20;

/**
 * 词汇运用 21
 */
const CHYY = function () {
};
CHYY.ID = 21;

/**
 * 听音选图 22
 */
const TYXT = function () {
};
TYXT.ID = 22;

/**
 * GameTypeId
 * WORD（猜词游戏，连连看，单词拼写，还原单词，词汇选择，闪卡练习）
 * SENTENCE（连词成句，强化炼句，闪卡练习）
 * ARTICLE（单项选择，完形填空，阅读理解，听力练习，补全文章，选词填空）
 * GRAMMAR（句型转换）
 */
const GameTypeId = function () {
};
GameTypeId.WORD = 1;
GameTypeId.SENTENCE = 2;
GameTypeId.ARTICLE = 3;
GameTypeId.GRAMMAR = 4;