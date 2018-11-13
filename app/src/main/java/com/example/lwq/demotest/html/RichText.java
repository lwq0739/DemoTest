package com.example.lwq.demotest.html;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    2017-04-12
 * Description:
 */

public class RichText {

    /**
     * &lt;p&gt; 标签之间会使用换行符进行分隔
     */
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH = 0x00000001;
    /**
     * &lt;h1&gt;~&lt;h6&gt; 标签之间会使用换行符进行分隔
     */
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_HEADING = 0x00000002;
    /**
     * &lt;li&gt; 标签之间会使用换行符进行分隔
     */
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM = 0x00000004;
    /**
     * &lt;ul&gt; 标签之间会使用换行符进行分隔
     */
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST = 0x00000008;
    /**
     * &lt;div&gt; 标签之间会使用换行符进行分隔
     */
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_DIV = 0x00000010;
    /**
     * &lt;blockquote&gt; 标签之间会使用换行符进行分隔
     */
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE = 0x00000020;
    /**
     * 使用 CSS color 代替 {@link Color}.
     */
    public static final int FROM_HTML_OPTION_USE_CSS_COLORS = 0x00000100;
    /**
     * 块级别的元素用2个换行符进行分割
     */
    public static final int FROM_HTML_MODE_LEGACY = 0x00000000;
    public static final int FROM_HTML_MODE_COMPACT = FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
        | FROM_HTML_SEPARATOR_LINE_BREAK_HEADING
        | FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
        | FROM_HTML_SEPARATOR_LINE_BREAK_LIST
        | FROM_HTML_SEPARATOR_LINE_BREAK_DIV
        | FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE;

    private final String source;
    private int flags;
    private RichImageGetter richImageGetter;
    private RichTagHandler richTagHandler;
    private OnRichClickListener onClickListener;

    private RichText(String source, int flags, RichImageGetter richImageGetter,
        RichTagHandler richTagHandler,OnRichClickListener onClickListener) {
        this.source = source;
        this.flags = flags;
        this.richImageGetter = richImageGetter;
        this.richTagHandler = richTagHandler;
        this.onClickListener = onClickListener;
    }

    public Spanned convert() {
        Parser parser = new Parser();
        try {
            parser.setProperty(Parser.schemaProperty, HtmlParser.schema);
        } catch (SAXNotSupportedException e) {
            throw new RuntimeException(e);
        } catch (SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        }

        RichTextConvertor convertor =
            new RichTextConvertor(source, richImageGetter, richTagHandler, parser, flags,onClickListener);

        return convertor.convert();
    }

    public interface RichImageGetter {
        @NonNull
        Drawable getDrawable(String source, int width, int height);
    }

    public interface RichTagHandler {
        void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader,
            Attributes attributes);
    }

    public interface OnRichClickListener{
        void onPercentageClick(int index);
        void onUrlClick(String url);
    }

    private static class HtmlParser {
        private static final HTMLSchema schema = new HTMLSchema();
    }

    public static final class Builder {
        private final String souce;
        private int flags = FROM_HTML_MODE_COMPACT;
        private RichImageGetter richImageGetter;
        private RichTagHandler richTagHandler;
        private OnRichClickListener onClickListener;

        public Builder(String source) {
            this.souce = source;
        }

        public Builder setFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder setImageGetter(RichImageGetter richImageGetter) {
            this.richImageGetter = richImageGetter;
            return this;
        }

        public Builder setTagHandler(RichTagHandler richTagHandler) {
            this.richTagHandler = richTagHandler;
            return this;
        }

        public Builder setOnClickListener(OnRichClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public RichText build() {
            return new RichText(souce, flags, richImageGetter, richTagHandler ,onClickListener);
        }
    }
}
