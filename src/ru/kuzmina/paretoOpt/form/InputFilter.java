package ru.kuzmina.paretoOpt.form;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class InputFilter extends DocumentFilter {
    public static final String INT_REGEX = "\\d+";
    public static final String DOUBLE_REGEX = "^[-+]?[0-9]*[.]?[0-9]*$";

    private String regex;

    public InputFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        Document doc = fb.getDocument();
        int textLength = doc.getLength();
        String docText = doc.getText(0, doc.getLength());

        StringBuilder sb = new StringBuilder();
        sb.append(docText.substring(0, offset));
        sb.append(text);
        sb.append(docText.substring(offset, textLength));

        if (sb.toString().matches(this.regex))
            fb.replace(offset, length, text, attrs);

    }
}
