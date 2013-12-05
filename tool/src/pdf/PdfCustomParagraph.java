package pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

public class PdfCustomParagraph {
	private String text;
	private Font font;
	private float firstLineIndent;
	private float spaceAfter=5;
	private float indentationLeft=55;
	private float indentationRight=55;
	private int vAlign = Element.ALIGN_LEFT;
	private int hAlign = Element.ALIGN_CENTER;
	
	public PdfCustomParagraph(String text,Font font) {
		setText(text);
		setFont(font);
	}
	
	public PdfCustomParagraph(String text, Font font, int vAlign) {
		setText(text);
		setFont(font);
		setVAlign(vAlign);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public float getFirstLineIndent() {
		return firstLineIndent;
	}
	public void setFirstLineIndent(float firstLineIndent) {
		this.firstLineIndent = firstLineIndent;
	}
	public float getSpaceAfter() {
		return spaceAfter;
	}
	public void setSpaceAfter(float spaceAfter) {
		this.spaceAfter = spaceAfter;
	}
	public float getIndentationLeft() {
		return indentationLeft;
	}
	public void setIndentationLeft(float indentationLeft) {
		this.indentationLeft = indentationLeft;
	}
	public float getIndentationRight() {
		return indentationRight;
	}
	public void setIndentationRight(float indentationRight) {
		this.indentationRight = indentationRight;
	}
	public int getVAlign() {
		return vAlign;
	}
	public void setVAlign(int align) {
		vAlign = align;
	}
	public int getHAlign() {
		return hAlign;
	}
	public void setHAlign(int align) {
		hAlign = align;
	}
}
