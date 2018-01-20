package com.xiaomiquan.mvp.delegate;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

import jp.wasabeef.richeditor.RichEditor;

public class RichTextDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    private RichEditor mEditor;
    private TextView mPreview;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rich_text;
    }

    private void init() {
        mEditor = viewHolder.editor;
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("编辑文章");
        //mEditor.setInputEnabled(false);

        mPreview = viewHolder.preview;
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        viewHolder.rootView.findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        viewHolder.rootView.findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        viewHolder.rootView.findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        viewHolder.rootView.findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });

        viewHolder.rootView.findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        viewHolder.rootView.findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }


    public static class ViewHolder {
        public View rootView;
        public ImageButton action_undo;
        public ImageButton action_redo;
        public ImageButton action_bold;
        public ImageButton action_italic;
        public ImageButton action_subscript;
        public ImageButton action_superscript;
        public ImageButton action_strikethrough;
        public ImageButton action_underline;
        public ImageButton action_heading1;
        public ImageButton action_heading2;
        public ImageButton action_heading3;
        public ImageButton action_heading4;
        public ImageButton action_heading5;
        public ImageButton action_heading6;
        public ImageButton action_txt_color;
        public ImageButton action_bg_color;
        public ImageButton action_indent;
        public ImageButton action_outdent;
        public ImageButton action_align_left;
        public ImageButton action_align_center;
        public ImageButton action_align_right;
        public ImageButton action_insert_bullets;
        public ImageButton action_insert_numbers;
        public ImageButton action_blockquote;
        public ImageButton action_insert_image;
        public ImageButton action_insert_link;
        public ImageButton action_insert_checkbox;
        public RichEditor editor;
        public TextView preview;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.action_undo = (ImageButton) rootView.findViewById(R.id.action_undo);
            this.action_redo = (ImageButton) rootView.findViewById(R.id.action_redo);
            this.action_bold = (ImageButton) rootView.findViewById(R.id.action_bold);
            this.action_italic = (ImageButton) rootView.findViewById(R.id.action_italic);
            this.action_subscript = (ImageButton) rootView.findViewById(R.id.action_subscript);
            this.action_superscript = (ImageButton) rootView.findViewById(R.id.action_superscript);
            this.action_strikethrough = (ImageButton) rootView.findViewById(R.id.action_strikethrough);
            this.action_underline = (ImageButton) rootView.findViewById(R.id.action_underline);
            this.action_heading1 = (ImageButton) rootView.findViewById(R.id.action_heading1);
            this.action_heading2 = (ImageButton) rootView.findViewById(R.id.action_heading2);
            this.action_heading3 = (ImageButton) rootView.findViewById(R.id.action_heading3);
            this.action_heading4 = (ImageButton) rootView.findViewById(R.id.action_heading4);
            this.action_heading5 = (ImageButton) rootView.findViewById(R.id.action_heading5);
            this.action_heading6 = (ImageButton) rootView.findViewById(R.id.action_heading6);
            this.action_txt_color = (ImageButton) rootView.findViewById(R.id.action_txt_color);
            this.action_bg_color = (ImageButton) rootView.findViewById(R.id.action_bg_color);
            this.action_indent = (ImageButton) rootView.findViewById(R.id.action_indent);
            this.action_outdent = (ImageButton) rootView.findViewById(R.id.action_outdent);
            this.action_align_left = (ImageButton) rootView.findViewById(R.id.action_align_left);
            this.action_align_center = (ImageButton) rootView.findViewById(R.id.action_align_center);
            this.action_align_right = (ImageButton) rootView.findViewById(R.id.action_align_right);
            this.action_insert_bullets = (ImageButton) rootView.findViewById(R.id.action_insert_bullets);
            this.action_insert_numbers = (ImageButton) rootView.findViewById(R.id.action_insert_numbers);
            this.action_blockquote = (ImageButton) rootView.findViewById(R.id.action_blockquote);
            this.action_insert_image = (ImageButton) rootView.findViewById(R.id.action_insert_image);
            this.action_insert_link = (ImageButton) rootView.findViewById(R.id.action_insert_link);
            this.action_insert_checkbox = (ImageButton) rootView.findViewById(R.id.action_insert_checkbox);
            this.editor = (RichEditor) rootView.findViewById(R.id.editor);
            this.preview = (TextView) rootView.findViewById(R.id.preview);
        }

    }
}